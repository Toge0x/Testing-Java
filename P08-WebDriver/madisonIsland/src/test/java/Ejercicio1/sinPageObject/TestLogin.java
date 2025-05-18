package Ejercicio1.sinPageObject;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

public class TestLogin {

    WebDriver driver;

    @BeforeEach
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.setBrowserVersion("131");
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


    @Test
    public void R2_requirement_loginOK_should_login_with_success_when_user_account_exists() {
        Assertions.assertEquals("Madison Island", driver.getTitle());       // PASO 1
        driver.findElement(By.cssSelector("div.account-cart-wrapper a.skip-link.skip-account")).click();
        driver.findElement(By.cssSelector("div#header-account div.links ul a[title='Log In']")).click();   // PASO 2
        Assertions.assertEquals("Customer Login", driver.getTitle());       // PASO 3
        driver.findElement(By.id("email")).sendKeys("copitoo240724@gmail.com");
        driver.findElement(By.id("send2")).click();                         // PASO 4
        Assertions.assertEquals("This is a required field.", driver.findElement(By.id("advice-required-entry-pass")).getText());    // PASO 5
        driver.findElement(By.id("pass")).sendKeys("coposdenieve");
        driver.findElement(By.id("send2")).click();                         // PASO 6
        Assertions.assertEquals("My Account", driver.getTitle());
        Assertions.assertEquals("Hello, Copito Linda!", driver.findElement(By.cssSelector("div.welcome-msg p.hello")).getText());   // PASO 7
    }

    @Test
    public void R3_requirement_loginFailed_should_fail_when_user_account_not_exists(){
        Assertions.assertEquals("Madison Island", driver.getTitle());       // PASO 1
        driver.findElement(By.cssSelector("div.account-cart-wrapper a.skip-link.skip-account")).click();
        driver.findElement(By.cssSelector("div#header-account div.links ul a[title='Log In']")).click();   // PASO 2
        Assertions.assertEquals("Customer Login", driver.getTitle());       // PASO 3
        driver.findElement(By.id("email")).sendKeys("copitoo240724@gmail.com");
        driver.findElement(By.id("pass")).sendKeys("coposdenieve1");
        driver.findElement(By.id("send2")).click();                         // PASO 4
        Assertions.assertEquals("Invalid login or password.", driver.findElement(By.cssSelector("ul.messages li.error-msg span")).getText());   // PASO 5
    }
}
