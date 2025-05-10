package ppss.matriculacion.dao;

import org.dbunit.Assertion;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.util.fileloader.FlatXmlDataFileLoader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ppss.matriculacion.to.AlumnoTO;

import java.time.LocalDate;

@Tag("Integracion-Fase1")
public class AlumnoDAOIT {
    private IDatabaseTester databaseTester;
    private IDatabaseConnection connection;
    private IAlumnoDAO alumnoDAO;   // contiene la sut a probar
    private AlumnoTO alumnoTO;      // contiene los datos de un alumno

    @BeforeEach
    public void setUp() throws Exception {

        String cadena_conexionDB = "jdbc:mysql://localhost:3306/matriculacion?useSSL=false"; // en código tienes que especificar el schema usado "DBUNIT"
        databaseTester = new JdbcDatabaseTester("com.mysql.cj.jdbc.Driver",   // este es el driver para la conexion con la bbdd
                cadena_conexionDB, "root", "ppss");
        connection = databaseTester.getConnection();
        alumnoDAO = new FactoriaDAO().getAlumnoDAO();
        alumnoTO = new AlumnoTO();

        // inicializamos la bd      -- VA EN EL SETUP PORQUE TODOS LOS TESTS PARTEN DE ESTA INICIALIZACIÓN
        IDataSet dataSet = new FlatXmlDataFileLoader().load("/tabla2.xml");
        databaseTester.setDataSet(dataSet);
        databaseTester.onSetup();
    }

    @Test
    public void testA1_addAlumno_should_add_Elena_when_trying_to_add_alumno() throws Exception {
        // ARRANGE
        alumnoTO.setNif("33333333C");       // datos del alumno a introducir
        alumnoTO.setNombre("Elena Aguirre Juarez");
        alumnoTO.setFechaNacimiento(LocalDate.of(1985, 2, 22));

        // ACT
        Assertions.assertDoesNotThrow(() -> alumnoDAO.addAlumno(alumnoTO));

        // despues de actuar con la SUT creamos la nueva copia de la bbdd
        IDataSet databaseDataSet = connection.createDataSet();
        ITable actualTable = databaseDataSet.getTable("alumnos");

        // creamos el dataset esperado y la tabla esperada
        IDataSet expectedDataSet = new FlatXmlDataFileLoader().load("/tabla3.xml");
        ITable expectedTable = expectedDataSet.getTable("alumnos");

        // ASSERT
        Assertion.assertEquals(expectedTable, actualTable);
    }

    @Test
    public void testA2_addAlumno_should_throw_exception_when_alumno_nif_already_exists() throws Exception {
        // ARRANGE
        alumnoTO.setNif("11111111A");               // datos del alumno a introducir
        alumnoTO.setNombre("Alfonso Ramirez Ruiz");
        alumnoTO.setFechaNacimiento(LocalDate.of(1982, 2, 22));

        // ACT
        DAOException excepcion = Assertions.assertThrows(DAOException.class, () -> alumnoDAO.addAlumno(alumnoTO));

        // ASSERT
        Assertions.assertEquals("Error al conectar con BD", excepcion.getMessage());
    }

    @Test
    public void testA3_addAlumno_should_throw_exception_when_alumno_name_is_null() throws Exception {
        // ARRANGE
        alumnoTO.setNif("44444444D");       // datos del alumno a introducir
        alumnoTO.setNombre(null);
        alumnoTO.setFechaNacimiento(LocalDate.of(1982, 2, 22));

        // ACT  -- No creamos los IDataSet ni nada porque sabemos que lanzará excepción
        DAOException excepcion = Assertions.assertThrows(DAOException.class, () -> alumnoDAO.addAlumno(alumnoTO));

        // ASSERT
        Assertions.assertEquals("Error al conectar con BD", excepcion.getMessage());
    }

    @Test
    public void testA4_addAlumno_should_throw_exception_when_alumno_is_null() throws Exception {
        // ARRANGE
        alumnoTO = null;       // datos del alumno a introducir

        // ACT  -- No creamos los IDataSet ni nada porque sabemos que lanzará excepción
        DAOException excepcion = Assertions.assertThrows(DAOException.class, () -> alumnoDAO.addAlumno(alumnoTO));

        // ASSERT
        Assertions.assertEquals("Alumno nulo", excepcion.getMessage());
    }

    @Test
    public void testA5_addAlumno_should_throw_exception_when_alumno_nif_is_null() throws Exception {
        // ARRANGE
        alumnoTO.setNif(null);       // datos del alumno a introducir
        alumnoTO.setNombre("Pedro Garcia Lopez");
        alumnoTO.setFechaNacimiento(LocalDate.of(1982, 2, 22));

        // ACT  -- No creamos los IDataSet ni nada porque sabemos que lanzará excepción
        DAOException excepcion = Assertions.assertThrows(DAOException.class, () -> alumnoDAO.addAlumno(alumnoTO));

        // ASSERT
        Assertions.assertEquals("Error al conectar con BD", excepcion.getMessage());
    }

    @Test
    public void testB1_addAlumno_should_return_tabla4_when_nif_to_delete_exists() throws Exception {
        // ARRANGE
        String nif = "11111111A";       // datos del alumno a borrar

        // ACT
        Assertions.assertDoesNotThrow(() -> alumnoDAO.delAlumno(nif));
        // cogemos el nuevo dataset una vez hemos hecho los cambios en la SUT
        IDataSet databaseDataSet = connection.createDataSet();
        ITable actualTable = databaseDataSet.getTable("alumnos");
        // creamos el resultado esperado (dataset)
        IDataSet expectedDataSet = new FlatXmlDataFileLoader().load("/tabla4.xml");
        ITable expectedTable = expectedDataSet.getTable("alumnos");

        // ASSERT
        Assertion.assertEquals(expectedTable, actualTable);
    }

    @Test
    public void testB2_addAlumno_should_throw_exception_when_nif_not_exists() throws Exception {
        // ARRANGE
        String nif = "33333333C";       // datos del alumno a borrar

        // ACT
        DAOException excepcion = Assertions.assertThrows(DAOException.class, () -> alumnoDAO.delAlumno(nif));

        // ASSERT
        Assertions.assertEquals("No se ha borrado ningun alumno", excepcion.getMessage());
    }

}
