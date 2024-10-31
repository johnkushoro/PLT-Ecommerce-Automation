
package PageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.springframework.stereotype.Component;

@Component
public class ProductListPage extends CommonPage {

    public static final String PRODUCT_CARD_CSS = "a.plp-product-card";
    public static final String CATEGORY_DESCRIPTION_SELECTOR = "[data-testid='category-description-content']";

    public ProductListPage(WebDriver driver) {
        super(driver);
    }

    public boolean isCategoryDescriptionVisible() {
        try {
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(CATEGORY_DESCRIPTION_SELECTOR)));
            boolean isVisible = wait.until(ExpectedConditions.visibilityOf(element)) != null;
            System.out.println("Element is present and visible: " + isVisible);
            return isVisible;
        } catch (TimeoutException e) {
            System.out.println("Element not found or not visible within the timeout period.");
            return false;
        }
    }

    public void clickFirstProductCard() throws InterruptedException {
        WebElement firstProductCard = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(PRODUCT_CARD_CSS)));
        firstProductCard.click();
    }
}