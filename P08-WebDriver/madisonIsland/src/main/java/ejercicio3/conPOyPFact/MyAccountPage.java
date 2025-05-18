package ejercicio3.conPOyPFact;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

public class MyAccountPage {
    WebDriver driver;
    @FindBy(css = "#header-nav ol.nav-primary a.level0.has-children[href*='accessories']")
    WebElement accesories;
    @FindBy(css = "a[href*='accessories/shoes.html']")
    WebElement shoes;

    public MyAccountPage(WebDriver driver) {
        this.driver = driver;
        driver.get("http://demo.magento.recolize.com/customer/account/");
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    public void mouseGoAccesoriesMenu(){
        Actions action = new Actions(driver);   // creamos la accion
        action.moveToElement(accesories);       // vamos a accesories y se desbloquea 'shoes'
        action.moveToElement(shoes);            // vamos a shoes
        action.click().perform();               // clickamos
    }

}
