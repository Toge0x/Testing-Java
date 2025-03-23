package ppss;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PremioTest {
    IMocksControl ctrl;
    Premio sut;
    ClienteWebService cliente;

    @BeforeEach
    public void setup() {
        ctrl = EasyMock.createStrictControl();
        sut = EasyMock.partialMockBuilder(Premio.class).addMockedMethod("generaNumero").mock(ctrl);
        cliente = ctrl.mock(ClienteWebService.class);
    }

    @Test
    public void C1_compruebaPremio_should_return_premiado_entrada_champions_when_probability_is_0_07(){
        // Arrange
        float aleatorio = 0.07f;
        String consulta = "entrada final Champions";
        String esperado = "Premiado con " + consulta;

        // Act
        EasyMock.expect(sut.generaNumero()).andReturn(aleatorio);
        Assertions.assertDoesNotThrow(() -> EasyMock.expect(cliente.obtenerPremio()).andReturn(consulta));
        ctrl.replay();

        sut.cliente = cliente;
        String resultado = sut.compruebaPremio();

        // Assert
        Assertions.assertEquals(esperado, resultado);
        ctrl.verify();
    }

    @Test
    public void C2_compruebaPremio_should_throw_exception_when_probability_is_0_05(){
        // Arrange
        float aleatorio = 0.05f;
        String esperado = "No se ha podido obtener el premio";

        // Act
        EasyMock.expect(sut.generaNumero()).andReturn(aleatorio);
        Assertions.assertDoesNotThrow(() -> EasyMock.expect(cliente.obtenerPremio()).andThrow(new ClienteWebServiceException()));
        ctrl.replay();

        sut.cliente = cliente;
        String resultado = sut.compruebaPremio();

        // Assert
        Assertions.assertEquals(esperado, resultado);
        ctrl.verify();
    }

    @Test
    public void C3_compruebaPremio_should_return_sin_premio_when_probability_is_0_48(){
        // Arrange
        float aleatorio = 0.48f;
        String esperado = "Sin premio";

        // Act
        EasyMock.expect(sut.generaNumero()).andReturn(aleatorio);
        ctrl.replay();

        sut.cliente = cliente;
        String resultado = sut.compruebaPremio();

        // Assert
        Assertions.assertEquals(esperado, resultado);
        ctrl.verify();
    }
}
