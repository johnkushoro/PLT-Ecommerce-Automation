

package PageObject;

import Model.ProductDetails;
import Utils.DataStore;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CheckOutPage extends CommonPage {


    public static final String CHECKOUT_PRODUCT_NAME = "li.bag-item__name";
    public static final String CHECKOUT_PRODUCT_SIZE = "div.bag__items li:nth-child(2)";
    public static final String CHECKOUT_PRODUCT_COLOUR = "div.bag__items li:nth-child(3)";
    public static final String CHECKOUT_PRODUCT_SUBTOTAL = "li.subtotal span:nth-child(2)";
    public static final String CHECKOUT_DELIVERY_TOTAL = "li[class='bag__totals-item'] span:nth-child(2)";
    public static final String CHECKOUT_GRAND_TOTAL = ".bag__totals-item.grand-total span:last-child";
    public static final String CHECKOUT_PAYMENT_METHOD_TITLE = ".box__title-container .box__title";
    public static final String CHECKOUT_PAYMENT_METHOD_RADIO_BUTTON_BY_LABEL = "//label[.//h4[text()='%$']]//input[@type='radio']";

    private final DataStore dataStore;
    private final ProductDetails productDetails;
    public String ProductDetailsModel = "CheckOutPage";

    @Autowired
    public CheckOutPage(WebDriver driver, DataStore dataStore, ProductDetails productDetails) {
        super(driver);
        this.dataStore = dataStore;
        this.productDetails = productDetails;
    }

    public ProductDetails getProductDetails() {
        return (ProductDetails) dataStore.getValue(ProductDetailsModel);
    }

    public void storeProductDetailsOnCheckout() {
        storeProductNameOnCheckout();
        storeProductSizeOnCheckout();
        storeProductColourOnCheckout();
        storeProductSubtotalOnCheckout();
        System.out.println(productDetails.toString());
        dataStore.setValue(ProductDetailsModel, productDetails);
    }

    public void storeProductNameOnCheckout() {
        String productNameOnCheckout = ProductNameOnCheckout();
        productDetails.setProductName(productNameOnCheckout);
    }

    public void storeProductSizeOnCheckout() {
        String productSizeOnCheckout = ProductSizeOnCheckout();
        productDetails.setProductSize(productSizeOnCheckout);
    }

    public void storeProductColourOnCheckout() {
        String productColourOnCheckout = ProductColourOnCheckout();
        productDetails.setProductColour(productColourOnCheckout);
    }

    public void storeProductSubtotalOnCheckout() {
        String productSubtotalOnCheckout = ProductSubtotalOnCheckout();
        productDetails.setProductSubtotal(productSubtotalOnCheckout);
    }

    public String ProductNameOnCheckout() {
        WebElement checkoutProductNameElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(CHECKOUT_PRODUCT_NAME)));
        String productName = checkoutProductNameElement.getText();
        System.out.println("Product Name on Checkout: " + productName);
        return productName;
    }


    public String ProductSizeOnCheckout() {
        WebElement checkoutProductSizeElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(CHECKOUT_PRODUCT_SIZE)));
        String textContent = checkoutProductSizeElement.getText();
        return textContent.replaceAll("\\D+", "");
    }

    public String ProductColourOnCheckout() {
        WebElement checkoutProductColourElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(CHECKOUT_PRODUCT_COLOUR)));
        String fullText = checkoutProductColourElement.getText();
        return fullText.replace("Colour: ", "").trim();
    }

    public String ProductSubtotalOnCheckout() {
        WebElement checkoutSubtotalElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(CHECKOUT_PRODUCT_SUBTOTAL)));
        return checkoutSubtotalElement.getText();
    }


    public String checkAndGetDeliveryTotalText() {
        WebElement deliveryTotalElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(CHECKOUT_DELIVERY_TOTAL)));
        return deliveryTotalElement.isDisplayed() ? deliveryTotalElement.getText() : "Grand total not displayed";

    }

    public String checkAndGetGrandTotalText() {
        WebElement grandTotalElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(CHECKOUT_GRAND_TOTAL)));
        return grandTotalElement.isDisplayed() ? grandTotalElement.getText() : "Grand total not displayed";
    }


    public String checkAndGetPaymentInformationText(String expectedText) {
        int maxAttempts = 5;
        int attempts = 0;

        while (attempts < maxAttempts) {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(CHECKOUT_PAYMENT_METHOD_TITLE)));

            List<WebElement> paymentInformationElements = driver.findElements(By.cssSelector(CHECKOUT_PAYMENT_METHOD_TITLE));

            for (WebElement element : paymentInformationElements) {
                String actualText = getPaymentInformationText(element);
                if (expectedText.equals(actualText)) {
                    return actualText;
                }
            }
            driver.navigate().refresh();
            attempts++;
        }
        return "Expected text not displayed after 5 attempts";
    }

    private String getPaymentInformationText(WebElement paymentInformationElement) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", paymentInformationElement);
        return paymentInformationElement.isDisplayed() ? paymentInformationElement.getText() : "";
    }

    public void clickAndVerifyButtonByLabelText(String labelText) {
        String xpathExpression = CHECKOUT_PAYMENT_METHOD_RADIO_BUTTON_BY_LABEL.replace("LABEL_TEXT", labelText);
        WebElement radioButtonElement = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathExpression)));
        radioButtonElement.click();

        boolean isSelected = wait.until(ExpectedConditions.elementToBeSelected(By.xpath(xpathExpression)));
        if (!isSelected) {
            throw new AssertionError("Radio button with label '" + labelText + "' was not selected.");
        }
    }

    public boolean isPaymentMethodSelected(String labelText) {
        try {
            String xpathExpression = CHECKOUT_PAYMENT_METHOD_RADIO_BUTTON_BY_LABEL.replace("LABEL_TEXT", labelText);
            WebElement radioButtonElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpathExpression)));
            return radioButtonElement.isSelected();
        } catch (Exception e) {
            return false;
        }
    }


}
