package ppss;


import org.easymock.EasyMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PremioTest {
    Premio sutPM;                   // solo mockearemos generaNumero
    ClienteWebService clienteM;     // mock completo de la clase

    @BeforeEach
    void setUp() {
        sutPM = EasyMock.partialMockBuilder(Premio.class).addMockedMethods("generaNumero").mock();
        clienteM = EasyMock.mock(ClienteWebService.class);
    }

    @Test
    public void C1_compruebaPremio_should_return_entrada_final_Champions_when_probability_is_0_07(){
        // Arrange
        String consulta = "entrada final Champions";
        String esperado = "Premiado con " + consulta;
        float aleatorio = 0.07f;

        // Act
        Assertions.assertDoesNotThrow(() -> EasyMock.expect(clienteM.obtenerPremio()).andReturn(consulta));
        Assertions.assertDoesNotThrow(() -> EasyMock.expect(sutPM.generaNumero()).andReturn(aleatorio));
        EasyMock.replay(clienteM, sutPM);   // activamos las expectativas

        sutPM.cliente = clienteM;
        String resultado = sutPM.compruebaPremio();

        // Assert
        Assertions.assertEquals(esperado, resultado);
        EasyMock.verify(clienteM, sutPM);
    }

    @Test
    public void C2_compruebaPremio_should_return_client_web_exception_when_probability_is_0_05(){
        // Arrange
        String esperado = "No se ha podido obtener el premio";
        float aleatorio = 0.05f;

        // Act
        Assertions.assertDoesNotThrow(() -> EasyMock.expect(clienteM.obtenerPremio()).andThrow(new ClienteWebServiceException()));
        Assertions.assertDoesNotThrow(() -> EasyMock.expect(sutPM.generaNumero()).andReturn(aleatorio));
        EasyMock.replay(clienteM, sutPM);

        sutPM.cliente = clienteM;
        String resultado = sutPM.compruebaPremio();

        // Assert
        Assertions.assertEquals(esperado, resultado);
        EasyMock.verify(clienteM, sutPM);
    }

    @Test
    public void C3_compruebaPremio_should_return_sin_premio_when_probability_is_0_48(){
        // Arrange
        String esperado = "Sin premio";
        float aleatorio = 0.48f;

        // Act
        Assertions.assertDoesNotThrow(() -> EasyMock.expect(sutPM.generaNumero()).andReturn(aleatorio));
        EasyMock.replay(sutPM);

        sutPM.cliente = clienteM;
        String resultado = sutPM.compruebaPremio();

        // Assert
        Assertions.assertEquals(esperado, resultado);
        EasyMock.verify(sutPM);
    }
}
