package ppss;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class NotifyCenterTest {
    NotifyCenter sutPM;
    MailServer mailMock;

    @BeforeEach
    public void setup(){
        sutPM = EasyMock.partialMockBuilder(NotifyCenter.class).addMockedMethods("getServer", "sendNotify", "getFecha").mock();
        mailMock = EasyMock.mock(MailServer.class);
    }

    @Test
    public void C1_notifyUsers_should_return_exception_when_fail_email2_email3(){
        // Arrange
        LocalDate fecha = LocalDate.of(2025, 3, 11);
        List<String> emails = Arrays.asList("email1", "email2", "email3", "email4");
        String esperado = "Failures during sending process";

        // Act
        Assertions.assertDoesNotThrow(() -> EasyMock.expect(sutPM.getServer()).andReturn(mailMock));
        Assertions.assertDoesNotThrow(() -> EasyMock.expect(sutPM.getFecha()).andReturn(fecha));
        EasyMock.expect(mailMock.findMailItemsWithDate(fecha)).andReturn(emails);
        Assertions.assertDoesNotThrow(() -> {       // ejecucion de toda la sut
            sutPM.sendNotify(emails.get(0));
            EasyMock.expectLastCall();
            sutPM.sendNotify(emails.get(1));
            EasyMock.expectLastCall().andThrow(new FailedNotifyException("da igual"));
            sutPM.sendNotify(emails.get(2));
            EasyMock.expectLastCall().andThrow(new FailedNotifyException("da igual"));
            sutPM.sendNotify(emails.get(3));
            EasyMock.expectLastCall();
        });
        EasyMock.replay(mailMock, sutPM);
        FailedNotifyException exception = Assertions.assertThrows(FailedNotifyException.class, () -> sutPM.notifyUsers(fecha));

        // Assert
        Assertions.assertEquals(esperado, exception.getMessage());
        EasyMock.verify(mailMock, sutPM);
    }

    @Test
    public void C2_notifyUsers_should_return_date_error_when_actual_is_not_equal_to_date(){
        // Arrange
        LocalDate fecha = LocalDate.of(2025, 3, 11);
        LocalDate actual = LocalDate.of(2025, 2, 12);
        String esperado = "Date error";

        // Act
        Assertions.assertDoesNotThrow(() -> EasyMock.expect(sutPM.getServer()).andReturn(mailMock));    // necesariamente???
        Assertions.assertDoesNotThrow(() -> EasyMock.expect(sutPM.getFecha()).andReturn(fecha));
        EasyMock.replay(mailMock, sutPM);
        FailedNotifyException exception = Assertions.assertThrows(FailedNotifyException.class, () -> sutPM.notifyUsers(actual));

        // Assert
        Assertions.assertEquals(esperado, exception.getMessage());
        EasyMock.verify(mailMock, sutPM);
    }

    @Test
    public void C3_notifyUsers_should_return_nothing_when_no_emails_on_server(){
        // Arrange
        LocalDate fecha = LocalDate.of(2025, 3, 11);
        List<String> emails = Arrays.asList();
        String esperado = "";

        // Act
        Assertions.assertDoesNotThrow(() -> EasyMock.expect(sutPM.getServer()).andReturn(mailMock));
        Assertions.assertDoesNotThrow(() -> EasyMock.expect(sutPM.getFecha()).andReturn(fecha));
        EasyMock.expect(mailMock.findMailItemsWithDate(fecha)).andReturn(emails);
        EasyMock.replay(mailMock, sutPM);

        // Assert
        Assertions.assertDoesNotThrow(() -> sutPM.notifyUsers(fecha));
        EasyMock.verify(mailMock, sutPM);
    }
}
