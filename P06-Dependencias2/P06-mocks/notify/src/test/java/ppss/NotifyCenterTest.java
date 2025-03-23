package ppss;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class NotifyCenterTest {
    IMocksControl ctrl;
    NotifyCenter sut;
    MailServer mock;

    @BeforeEach
    public void setup(){
        ctrl = EasyMock.createStrictControl();
        sut = EasyMock.partialMockBuilder(NotifyCenter.class).addMockedMethods("getServer", "sendNotify", "getFecha").mock(ctrl);
        mock = ctrl.mock(MailServer.class);
    }

    @Test
    public void C1_notifyUsers_should_return_exception_failures_during_sending_process_when_two_emails_fail(){
        // Arrange
        LocalDate fecha = LocalDate.of(2025, 3, 11);
        List<String> emails = Arrays.asList("email1", "email2", "email3", "email4");
        String esperado = "Failures during sending process";

        // Act
        EasyMock.expect(sut.getServer()).andReturn(mock);
        EasyMock.expect(sut.getFecha()).andReturn(fecha);
        EasyMock.expect(mock.findMailItemsWithDate(fecha)).andReturn(emails);
        Assertions.assertDoesNotThrow(() -> {
            sut.sendNotify(emails.get(0));
            sut.sendNotify(emails.get(1));
            EasyMock.expectLastCall().andThrow(new FailedNotifyException("hola"));
            sut.sendNotify(emails.get(2));
            EasyMock.expectLastCall().andThrow(new FailedNotifyException("hola"));
            sut.sendNotify(emails.get(3));
        });
        ctrl.replay();
        FailedNotifyException excepcion = Assertions.assertThrows(FailedNotifyException.class, () -> sut.notifyUsers(fecha));

        // Assert
        Assertions.assertEquals(esperado, excepcion.getMessage());
        ctrl.verify();
    }

    @Test
    public void C2_notifyUsers_should_return_exception_failures_during_sending_process_when_two_emails_fail(){
        // Arrange
        LocalDate fecha = LocalDate.of(2025, 3, 12);
        LocalDate actual = LocalDate.of(2025, 2, 12);
        String esperado = "Date error";

        // Act
        EasyMock.expect(sut.getServer()).andReturn(mock);
        EasyMock.expect(sut.getFecha()).andReturn(actual);
        ctrl.replay();
        FailedNotifyException excepcion = Assertions.assertThrows(FailedNotifyException.class, () -> sut.notifyUsers(fecha));

        // Assert
        Assertions.assertEquals(esperado, excepcion.getMessage());
        ctrl.verify();
    }

    @Test
    public void C3_notifyUsers_should_return_nothing_when_no_emails_to_send(){
        // Arrange
        LocalDate fecha = LocalDate.of(2025, 3, 23);
        List<String> emails = Arrays.asList();
        String esperado = "";

        // Act
        EasyMock.expect(sut.getServer()).andReturn(mock);
        EasyMock.expect(sut.getFecha()).andReturn(fecha);
        Assertions.assertDoesNotThrow(() -> EasyMock.expect(mock.findMailItemsWithDate(fecha)).andReturn(emails));
        ctrl.replay();

        // Assert
        Assertions.assertDoesNotThrow(() -> sut.notifyUsers(fecha));
        ctrl.verify();

    }
}
