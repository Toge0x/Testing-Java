package ejercicio3.conPOyPFact;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ShoesPage {
    WebDriver driver;
    @FindBy(xpath = "//h2[@class='product-name']/a[contains(text(),'Wingtip Cognac Oxford')]/ancestor::li[contains(@class,'item')]//a[contains(@class,'link-compare')]")
    WebElement wingtipShoe;

    @FindBy(xpath = "//h2[@class='product-name']/a[contains(@href, 'suede-loafer')]/ancestor::li[contains(@class,'item')]//a[contains(@class,'link-compare')]")
    WebElement suedeShoe;


    @FindBy(css = "button.button[title='Compare']")
    WebElement compareButton;

    @FindBy(css = ".block-compare a[onclick*='remove all products']")
    WebElement clearAll;

    @FindBy(css = ".success-msg span")
    WebElement messageClear;

    public ShoesPage(WebDriver driver) {
        this.driver = driver;
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    public void clickButtonCompare() {
        compareButton.click();
    }

    public void selectShoeToCompare(int numero){
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        switch(numero) {
            case 5:
                jse.executeScript("arguments[0].scrollIntoView();", wingtipShoe);
                wingtipShoe.click();
                break;
            case 6:
                jse.executeScript("arguments[0].scrollIntoView();", suedeShoe);
                suedeShoe.click();
                break;

        }
    }

}
