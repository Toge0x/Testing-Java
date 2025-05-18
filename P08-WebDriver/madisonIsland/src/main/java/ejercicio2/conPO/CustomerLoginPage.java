package ejercicio2.conPO;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CustomerLoginPage {
    WebDriver driver;
    WebElement email;
    WebElement password;
    WebElement formulario;
    WebElement error;

    public CustomerLoginPage(WebDriver driver) {
        this.driver = driver;
        email = driver.findElement(By.id("email"));
        password = driver.findElement(By.id("pass"));
        formulario = driver.findElement(By.id("send2"));
    }

    public void login(String email, String password) {
        this.email.sendKeys(email);
        this.password.sendKeys(password);
        this.formulario.click();
    }

    public String loginError() {
        error = driver.findElement(By.cssSelector(".account-login ul.messages li.error-msg span"));
        return error.getText();
    }

    public String getPageTitle(){
        return driver.getTitle();
    }
}
