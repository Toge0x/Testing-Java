 package ppss;

import org.dbunit.Assertion;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.util.fileloader.FlatXmlDataFileLoader;

import org.junit.jupiter.api.*;

import java.sql.SQLException;

 /* IMPORTANTE:
     Dado que prácticamente todos los métodos de dBUnit lanzan una excepción,
     vamos a usar "throws Esception" en los métodos, para que el código quede más
     legible sin necesidad de usar un try..catch o envolver cada sentencia dbUnit
     con un assertDoesNotThrow()
     Es decir, que vamos a primar la legibilidad de los tests.
     Si la SUT puede lanza una excepción, SIEMPRE usaremos assertDoesNotThrow para
     invocar a la sut cuando no esperemos que se lance dicha excepción (independientemente de que hayamos propagado las excepciones provocadas por dbunit).
 */
public class ClienteDAO_IT {
  
  private ClienteDAO clienteDAO; //SUT
  private IDatabaseTester databaseTester;
  private IDatabaseConnection connection;

  @BeforeEach
  public void setUp() throws Exception {

    String cadena_conexionDB = "jdbc:mysql://localhost:3306/DBUNIT?useSSL=false"; // en código tienes que especificar el schema usado "DBUNIT"
    databaseTester = new JdbcDatabaseTester("com.mysql.cj.jdbc.Driver",   // este es el driver para la conexion con la bbdd
            cadena_conexionDB, "root", "ppss");
    connection = databaseTester.getConnection();

    clienteDAO = new ClienteDAO();
  }

  @Test
  public void D1_insert_should_add_John_to_cliente_when_John_does_not_exist() throws Exception {
    Cliente cliente = new Cliente(1,"John", "Smith");
    cliente.setDireccion("1 Main Street");
    cliente.setCiudad("Anycity");

    //inicializamos la BD
    IDataSet dataSet = new FlatXmlDataFileLoader().load("/cliente-init.xml");
    databaseTester.setDataSet(dataSet);
    databaseTester.onSetup();
    
     //invocamos a la sut
    Assertions.assertDoesNotThrow(()->clienteDAO.insert(cliente));

    //recuperamos los datos de la BD después de invocar al SUT
    IDataSet databaseDataSet = connection.createDataSet();
    ITable actualTable = databaseDataSet.getTable("cliente"); 

    //creamos el dataset con el resultado esperado
    IDataSet expectedDataSet = new FlatXmlDataFileLoader().load("/cliente-esperado.xml");
    ITable expectedTable = expectedDataSet.getTable("cliente");

    Assertion.assertEquals(expectedTable, actualTable);

   }

  @Test
  public void D2_delete_should_remove_John_from_cliente_when_John_is_in_table() throws Exception {
    Cliente cliente =  new Cliente(1,"John", "Smith");
    cliente.setDireccion("1 Main Street");
    cliente.setCiudad("Anycity");

    //inicializamos la BD
    IDataSet dataSet = new FlatXmlDataFileLoader().load("/cliente-esperado.xml");
    databaseTester.setDataSet(dataSet);
    databaseTester.onSetup();

    //invocamos a la SUT
    Assertions.assertDoesNotThrow(()->clienteDAO.delete(cliente));

    //recuperamos los datos de la BD después de invocar al SUT
    IDataSet databaseDataSet = connection.createDataSet();
    ITable actualTable = databaseDataSet.getTable("cliente");
    
    //creamos el dataset con el resultado esperado
    IDataSet expectedDataSet = new FlatXmlDataFileLoader().load("/cliente-init.xml");
    ITable expectedTable = expectedDataSet.getTable("cliente");

    Assertion.assertEquals(expectedTable, actualTable);
  }

  @Test
  public void D3_insert_should_throw_exception_when_client_already_exists() throws Exception {
    // ARRANGE
    Cliente cliente = new Cliente(2 ,"Jorge", "Perez");
    cliente.setDireccion("Gran via");
    cliente.setCiudad("Madrid");

    // inicializacion de la BD
    IDataSet dataSet = new FlatXmlDataFileLoader().load("/cliente-init-D3.xml");
    databaseTester.setDataSet(dataSet);
    databaseTester.onSetup();

    // ACT
    Exception exception = Assertions.assertThrows(SQLException.class, () -> clienteDAO.insert(cliente));

    // ASSERT
    Assertions.assertTrue(exception.getMessage().contains("Duplicate entry"));
  }

  @Test
   public void D4_delete_should_throw_exception_when_client_not_exists() throws Exception {
    // ARRANGE
    Cliente cliente = new Cliente(3 ,"Judith", "González");
    cliente.setDireccion("Gran via");
    cliente.setCiudad("Madrid");

    // inicializamos la bbdd
    IDataSet dataSet = new FlatXmlDataFileLoader().load("/cliente-init-D3.xml");    // misma bbdd que en el test 3
    databaseTester.setDataSet(dataSet);
    databaseTester.onSetup();

    // ACT
    Exception exception = Assertions.assertThrows(SQLException.class, () -> clienteDAO.delete(cliente));

    // ASSERT
    Assertions.assertTrue(exception.getMessage().contains("Delete failed"));
  }

  @Test
   public void D5_update_should_update_John_direccion_to_Other_Street_when_direccion_is_1_Main_Street() throws Exception {
    // ARRANGE
    Cliente cliente =  new Cliente(1,"John", "Smith");
    cliente.setDireccion("Other Street");
    cliente.setCiudad("Anycity");

    // inicializamos la bd
    IDataSet dataSet = new FlatXmlDataFileLoader().load("/cliente-init-D5.xml");
    databaseTester.setDataSet(dataSet);
    databaseTester.onSetup();

    // ACT
    Assertions.assertDoesNotThrow(()->clienteDAO.update(cliente));

    //recuperamos los datos de la BD después de invocar al SUT -- creamos otro dataset para recoger los datos
    IDataSet databaseDataSet = connection.createDataSet();
    ITable actualTable = databaseDataSet.getTable("cliente");

    //creamos el dataset con el resultado esperado
    IDataSet expectedDataSet = new FlatXmlDataFileLoader().load("/cliente-esperado-D5.xml");
    ITable expectedTable = expectedDataSet.getTable("cliente");

    // ASSERT
    Assertion.assertEquals(expectedTable, actualTable);
  }

  @Test
  public void D6_delete_should_throw_exception_when_client_not_exists() throws Exception {
      // ARRANGE
      Cliente cliente = new Cliente(1 ,"John", "Smith");
      cliente.setDireccion("1 Main Street");
      cliente.setCiudad("Anycity");

      // inicializamos la bd
      IDataSet dataSet = new FlatXmlDataFileLoader().load("/cliente-init-D3.xml");
      databaseTester.setDataSet(dataSet);
      databaseTester.onSetup();

      // ACT
      Cliente obtenido = Assertions.assertDoesNotThrow(() -> clienteDAO.retrieve(1));

      // ASSERT
      Assertions.assertAll(() -> Assertions.assertEquals(obtenido.getId(), cliente.getId()),
                           () -> Assertions.assertEquals(obtenido.getNombre(), cliente.getNombre()),
                           () -> Assertions.assertEquals(obtenido.getApellido(), cliente.getApellido()),
                           () -> Assertions.assertEquals(obtenido.getDireccion(), cliente.getDireccion()),
                           () -> Assertions.assertEquals(obtenido.getCiudad(), cliente.getCiudad()));
  }
}
