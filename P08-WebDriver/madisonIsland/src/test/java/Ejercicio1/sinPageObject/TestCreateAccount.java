package Ejercicio1.sinPageObject;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

public class TestCreateAccount {
    WebDriver driver;

    @BeforeEach
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.setBrowserVersion("131");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("http://demo.magento.recolize.com");
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Tag("OnlyOnce")
    @Test
    public void S1_srequirement_createAccount_should_create_new_account_in_the_demo_store_when_this_account_does_not_exist(){
        Assertions.assertEquals("Madison Island", driver.getTitle());   // comprobamos que estamos en la página correcta - Paso 1

        driver.findElement(By.cssSelector("div.account-cart-wrapper a.skip-link.skip-account")).click();    // clickamos el boton del registro
        driver.findElement(By.cssSelector("div#header-account div.links ul a[title='Log In']")).click();   // clickamos en el botón de login - Paso 2
        Assertions.assertEquals("Customer Login", driver.getTitle());       // comprobamos que estamos en la página que queremos - Paso 3

        driver.findElement(By.cssSelector("div.buttons-set a[title='Create an Account'")).click();  // clickamos para crear cuenta  - Paso 4
        Assertions.assertEquals("Create New Customer Account", driver.getTitle());          // Paso 5

        driver.findElement(By.id("firstname")).sendKeys("Antonio");                     // rellenamos los campos excepto confirmation
        driver.findElement(By.id("lastname")).sendKeys("Martinez Santa");
        driver.findElement(By.id("email_address")).sendKeys("antoniomartinezua@gmail.com");
        driver.findElement(By.id("password")).sendKeys("AntonioUA123");
        driver.findElement(By.cssSelector("div.buttons-set button[title='Register']")).click();     // pulsamos el boton de register - Paso 6

        





    }
}
