
package PageObject;

import Model.ProductDetails;
import Utils.DataStore;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Random;

@Component
public class ProductDetailPage extends CommonPage {

    public static final String COLOR_SWATCH_ANCHOR_SELECTOR = "a[id$='-pdp-related-colour-link']";
    public static final String SIZE_BUTTON_SELECTOR = "button.relative.cursor-pointer.inline-block";
    public static final String OUT_OF_STOCK_SELECTOR = "p.text-red.leading-5.font-brand-thin.mb-4";
    public static final String ADD_TO_BAG_BUTTON_SELECTOR = "button#add-to-bag-button";
    public static final String PRODUCT_NAME_SELECTOR = "h1#pdp-name";
    public static final String BAG_ICON_SELECTOR = "a.block.leading-none[href='/checkout/cart']";

    private final DataStore dataStore;
    private final ProductDetails productDetails;
    public final String productDetailsModel = "productDetailsModel";

    @Autowired
    public ProductDetailPage(WebDriver driver, DataStore dataStore, ProductDetails productDetails) {
        super(driver);
        this.dataStore = dataStore;
        this.productDetails = productDetails;
    }

    public void selectRandomProductColorAndSize() throws InterruptedException, TimeoutException {
        while (true) {
            selectRandomProductColor();
            String selectedSize = selectRandomAvailableSize();
            var productSize = selectedSize != null ? selectedSize : "Out of stock";

            if (selectedSize != null) {
                productDetails.setProductSize(productSize);
                WebElement productNameElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(PRODUCT_NAME_SELECTOR)));
                productDetails.setProductName(productNameElement.getText());

                System.out.println(productDetails.toString());
                dataStore.setValue(productDetailsModel, productDetails);
                clickAddToBagButton();
                return;
            }
        }
    }

    public void selectRandomProductColor() throws InterruptedException {
        List<WebElement> colorSwatches = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(COLOR_SWATCH_ANCHOR_SELECTOR)));
        if (colorSwatches.isEmpty()) {
            return;
        }

        Random random = new Random();
        WebElement randomColorSwatch = colorSwatches.get(random.nextInt(colorSwatches.size()));
        String selectedColor = randomColorSwatch.findElement(By.tagName("img")).getAttribute("alt");

        randomColorSwatch.click();
        productDetails.setProductColour(selectedColor);
        System.out.println("Color changed successfully to: " + selectedColor);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(ADD_TO_BAG_BUTTON_SELECTOR)));
    }

    public ProductDetails getProductDetails() {
        return (ProductDetails) dataStore.getValue(productDetailsModel);
    }

    public String selectRandomAvailableSize() {
        List<WebElement> sizeButtons = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(SIZE_BUTTON_SELECTOR)));
        if (sizeButtons.isEmpty()) {
            return null;
        }

        Random random = new Random();
        for (int i = 0; i < sizeButtons.size(); i++) {
            WebElement randomSizeButton = sizeButtons.get(random.nextInt(sizeButtons.size()));
            String selectedSize = randomSizeButton.getText();
            randomSizeButton.click();

            List<WebElement> outOfStockElements = driver.findElements(By.cssSelector(OUT_OF_STOCK_SELECTOR));
            boolean isOutOfStock = outOfStockElements.stream().anyMatch(element -> element.getText().contains("out of stock"));

            sizeButtons.remove(randomSizeButton);

            if (isOutOfStock) {
                if (sizeButtons.isEmpty()) {
                    return null;
                }
            } else {
                return selectedSize;
            }
        }
        return null;
    }

    public void clickAddToBagButton() {
        WebElement addToBagButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(ADD_TO_BAG_BUTTON_SELECTOR)));
        addToBagButton.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(BAG_ICON_SELECTOR)));
    }

    public void clickBagIcon() {
        WebElement bagIcon = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(BAG_ICON_SELECTOR)));
        bagIcon.click();
        wait.until(ExpectedConditions.urlContains("/checkout/cart"));
    }

    public boolean isBagPageDisplayed() {
        return Objects.requireNonNull(driver.getCurrentUrl()).contains("/checkout/cart");
    }
}
