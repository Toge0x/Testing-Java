package ppss;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class MailServer{

    public MailServer(String login, String password) {
        login = login;
        password = password;
    }

    public List<String> findMailItemsWithDate(LocalDate fecha) {
        return Arrays.asList("hola", "hola");
    }
}
