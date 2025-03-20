package ppss;

import java.time.LocalDate;
import java.util.List;

public class NotifyCenter {
    private String login = "root";
    private String password = "7l65a43";

    public MailServer getServer() {
        return new MailServer(login, password);
    }

    public void sendNotify(String email) throws FailedNotifyException{
        throw new UnsupportedOperationException("Not supported yet");
    }

    public LocalDate getFecha(){
        return LocalDate.now();
    }

    // SUT
    public void notifyUsers(LocalDate fecha) throws FailedNotifyException {
        int failed = 0;
        MailServer server = getServer();        // SEAM
        List<String> emails;
        //LocalDate today = LocalDate.now();      // dependencia externa - new LocalDate NO TESTABLE
        LocalDate today = getFecha();               // Ahora sí, testable
        if (today.isEqual(fecha)) {
            emails = server.findMailItemsWithDate(fecha);   // dependencia en SEAM
            for (String email : emails) {
                try {
                    sendNotify(email);                  // Testable, metodo de clase facilmente mockeable
                } catch (FailedNotifyException ex) {
                    failed++;
                }
            }
        } else {
            throw new FailedNotifyException("Date error");
        }
        if (failed >0) {
            throw new FailedNotifyException("Failures during sending process");
        }
    }
}