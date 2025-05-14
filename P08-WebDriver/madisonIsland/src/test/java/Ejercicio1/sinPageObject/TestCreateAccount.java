package Ejercicio1.sinPageObject;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;


public class TestCreateAccount {
    WebDriver driver;

    @BeforeEach
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.setBrowserVersion("131");
        //options.addArguments("--headless=new");   esto siempre quitado para ver el proceso del test
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
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
        Assertions.assertEquals("Madison Island", driver.getTitle());   // PASO 1
        driver.findElement(By.cssSelector("div.account-cart-wrapper a.skip-link.skip-account")).click();
        driver.findElement(By.cssSelector("div#header-account div.links ul a[title='Log In']")).click();   // PASO 2
        Assertions.assertEquals("Customer Login", driver.getTitle());                                      // PASO 3
        driver.findElement(By.cssSelector("div.buttons-set a[title='Create an Account']")).click(); // PASO 4
        Assertions.assertEquals("Create New Customer Account", driver.getTitle());                  // PASO 5
        driver.findElement(By.id("firstname")).sendKeys("Copito");
        driver.findElement(By.id("lastname")).sendKeys("Linda");
        driver.findElement(By.id("email_address")).sendKeys("copito1240724@gmail.com");
        driver.findElement(By.id("password")).sendKeys("coposdenieve");
        driver.findElement(By.cssSelector("div.buttons-set button[title='Register']")).click();    // PASO 6
        Assertions.assertEquals("This is a required field.", driver.findElement(By.id("advice-required-entry-confirmation")).getText());     // PASO 7
        driver.findElement(By.id("confirmation")).sendKeys("coposdenieve");
        driver.findElement(By.cssSelector("div.buttons-set button[title='Register']")).submit();    // PASO 8
        Assertions.assertEquals("My Account", driver.getTitle());
        Assertions.assertEquals("Thank you for registering with Madison Island.", driver.findElement(By.cssSelector("li.success-msg span")).getText());
        Assertions.assertEquals("Hello, Copito Linda!", driver.findElement(By.cssSelector("div.welcome-msg p.hello")).getText());
    }
}
