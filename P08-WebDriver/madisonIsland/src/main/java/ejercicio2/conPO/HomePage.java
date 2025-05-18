package ejercicio2.conPO;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


public class HomePage {
    WebDriver driver;
    WebElement login;
    WebElement account;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        driver.get("http://demo.magento.recolize.com/");
        account = driver.findElement(By.cssSelector(".account-cart-wrapper span.label"));
    }

    public String getTitle(){
        return driver.getTitle();
    }

    public void mostrarDesplegable(){
        account.click();
        login = driver.findElement(By.cssSelector("#header-account a[title='Log In']"));
    }

    public void goToLogin(){
        login.click();
    }
}
