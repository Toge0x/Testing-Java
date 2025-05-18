package ejercicio3.conPOyPFact;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.Set;

public class ProductsComparisonPage {
    WebDriver driver;

    @FindBy(css = "button.button[title='Close Window']")
    WebElement botonClose;

    public ProductsComparisonPage(WebDriver driver) {
        this.driver = driver;
    }

    public String getPageTitle(){
        return driver.getTitle();
    }

    public void clickBotonClose(){
        botonClose.click();
        Set<String> setIds = driver.getWindowHandles();
        String[] handleIds = setIds.toArray(new String[setIds.size()]);
        driver.switchTo().window(handleIds[0]);
    }
}
