package ppss;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

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

    // SUT NO TESTABLE - Refactorizar FACTORIA LOCAL AHORA SÍ TESTABLE
    public void notifyUsers(LocalDate fecha) throws FailedNotifyException {
        int failed = 0;
        MailServer server = getServer();        // SEAM
        List<String> emails;
        //LocalDate today = LocalDate.now();      // dependencia externa, new LocalDate, NO SEAM
        LocalDate today = getFecha();               // SEAM
        if (today.isEqual(fecha)) {
            emails = server.findMailItemsWithDate(fecha);   // dependencia externa
            for (String email : emails) {
                try {
                    sendNotify(email);
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