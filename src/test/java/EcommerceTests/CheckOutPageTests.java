package EcommerceTests;

import Model.ProductDetails;
import PageObject.CheckOutPage;
import PageObject.ShoppingCartPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CheckOutPageTests extends BaseTest {

    @Autowired
    private CheckOutPage checkOutPage;

    @Autowired
    private ShoppingCartPage shoppingCartPage;


    @Test(description = "Verify that product details in the bag match those on the checkout page")
    public void testProductDetailsMatchInCheckoutBagSection() {
        ProductDetails shoppingCartProductDetails = shoppingCartPage.getProductDetails();
        checkOutPage.storeProductDetailsOnCheckout();
        ProductDetails checkoutPageProductDetails = checkOutPage.getProductDetails();

        Assert.assertEquals(shoppingCartProductDetails.getProductName(), checkoutPageProductDetails.getProductName(), "Product names do not match!");
        Assert.assertEquals(shoppingCartProductDetails.getProductSize(), checkoutPageProductDetails.getProductSize(), "Product sizes do not match!");
        Assert.assertEquals(shoppingCartProductDetails.getProductColour(), checkoutPageProductDetails.getProductColour(), "Product colours do not match!");
        Assert.assertEquals(shoppingCartProductDetails.getProductSubtotal(), checkoutPageProductDetails.getProductSubtotal(), "Product subtotals do not match!");
    }

    @Test(description = "Verify that the delivery and grand total are displayed in the bag section")
    public void testDeliveryAndGrandTotalDisplay() {
        Assert.assertNotNull(checkOutPage.checkAndGetDeliveryTotalText(), "Delivery total not displayed");
        Assert.assertNotNull(checkOutPage.checkAndGetGrandTotalText(), "Grand total not displayed");
    }

    @Test(description = "Verify that the payment information section is visible upon scrolling")
    public void testUserScrollsToPaymentMethod() {
        String paymentInfoText = checkOutPage.checkAndGetPaymentInformationText("Payment methods");
        Assert.assertEquals(paymentInfoText, "Payment methods", "Payment Information text does not match or is not displayed");
    }

    @Test(description = "Verify that user can select and confirm a payment method")
    public void testUserSelectsAndVerifiesPaymentMethod() {
        checkOutPage.clickAndVerifyButtonByLabelText("PayPal");

        Assert.assertTrue(checkOutPage.isPaymentMethodSelected("PayPal"), "PayPal option was not selected or verified successfully");
    }
}
