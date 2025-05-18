package ejercicio3.conPOyPFact;

import org.junit.jupiter.api.*;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.v133.network.model.ReportingApiEndpointsChangedForOrigin;
import org.openqa.selenium.support.PageFactory;

import java.time.Duration;

public class TestShoes {
    WebDriver driver;
    MyAccountPage myAccountPage;
    ShoesPage shoesPage;
    ProductsComparisonPage productsComparisonPage;

    @BeforeAll
    public static void setCookies(){
        Cookies.storeCookiesToFile("copitoo240724@gmail.com", "coposdenieve",  "cookies.data");
    }

    @BeforeEach
    public void setUp(){
        ChromeOptions options = new ChromeOptions();
        options.setBrowserVersion("131");
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        driver = new ChromeDriver(options);
        Cookies.loadCookiesFromFile(driver);

        driver.get("http://demo.magento.recolize.com/customer/account/");

        myAccountPage = PageFactory.initElements(driver, MyAccountPage.class);
        shoesPage = PageFactory.initElements(driver, ShoesPage.class);
        productsComparisonPage = PageFactory.initElements(driver, ProductsComparisonPage.class);
    }

    @AfterEach
    public void tearDown(){
        driver.quit();
    }

    @Test
    public void R6_requirement_PO_compareShoes_should_clear_comparison_when_TwoShoes_are_compared_and_cleared(){
        Assertions.assertEquals("My Account", myAccountPage.getPageTitle());
        myAccountPage.mouseGoAccesoriesMenu();
        Assertions.assertEquals("Shoes - Accessories", shoesPage.getPageTitle());
        shoesPage.selectShoeToCompare(5);
        shoesPage.selectShoeToCompare(6);
        shoesPage.clickButtonCompare();

        shoesPage.driver.get("http://demo.magento.recolize.com/catalog/product_compare/index/");    // cambiamos a la otra ventana emergente
        shoesPage.driver.switchTo().window(shoesPage.driver.getWindowHandle());                     // cambiamos la url del driver
        PageFactory.initElements(driver, ProductsComparisonPage.class);
        Assertions.assertEquals("Products Comparison List - Magento Commerce", driver.getTitle());  // TODO ESTO ES LA VENTANA EMERGENTE
        productsComparisonPage.clickBotonClose();

        shoesPage.driver.get("http://demo.magento.recolize.com/accessories/shoes.html");    // TENEMOS QUE CAMBIAR MANUALMENTE LAS URLS, WINDOWS Y PAGEFACTORY
        shoesPage.driver.switchTo().window(shoesPage.driver.getWindowHandle());
        PageFactory.initElements(driver, ShoesPage.class);
        Assertions.assertEquals("Shoes - Accessories", shoesPage.driver.getTitle());

        shoesPage.clearAll.click();
        Alert alert = driver.switchTo().alert();
        Assertions.assertEquals("Are you sure you would like to remove all products from your comparison?", alert.getText());
        alert.accept();
        Assertions.assertEquals("The comparison list was cleared.", shoesPage.messageClear.getText());
    }
}
