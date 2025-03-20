package ppss;

import java.time.LocalDate;
import java.util.List;

public class MailServer {
    private String login;
    private String password;

    public MailServer(String login, String password) {
        login = login;
        password = password;
    }

    public List<String> findMailItemsWithDate(LocalDate fecha){
        List<String> emails = List.of("hola", "ppsss");
        return emails;
    }
}
