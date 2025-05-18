package ejercicio2.conPO;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class TestLogin2 {
    WebDriver driver;
    HomePage homePage;
    CustomerLoginPage customerLoginPage;
    MyAccountPage myAccountPage;

    @BeforeEach
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.setBrowserVersion("131");
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        driver = new ChromeDriver(options);
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void R4_requierement_PO_loginOK_should_login_with_success_when_user_account_exists(){
        homePage = new HomePage(driver);
        Assertions.assertEquals("Madison Island", homePage.getTitle());
        homePage.mostrarDesplegable();
        homePage.goToLogin();
        customerLoginPage = new CustomerLoginPage(driver);
        Assertions.assertEquals("Customer Login", customerLoginPage.getPageTitle());
        customerLoginPage.login("copitoo240724@gmail.com", "coposdenieve");
        // aqui ya ha enviado el formulario de login
        myAccountPage = new MyAccountPage(driver);
        Assertions.assertEquals("My Account", myAccountPage.getPageTitle());
    }

    @Test
    public void R5_requierement_PO_loginFailed_should_fail_when_user_account_not_exists(){
        homePage = new HomePage(driver);
        Assertions.assertEquals("Madison Island", homePage.getTitle());
        homePage.mostrarDesplegable();
        homePage.goToLogin();
        customerLoginPage = new CustomerLoginPage(driver);
        Assertions.assertEquals("Customer Login", customerLoginPage.getPageTitle());
        customerLoginPage.login("copitodenieve@gmail.com", "copitodenieve");
        Assertions.assertEquals("Invalid login or password.", customerLoginPage.loginError());
    }
}
