package EcommerceTests;

import Model.ProductDetails;
import PageObject.ProductDetailPage;
import PageObject.ShoppingCartPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ShoppingCartPageTests extends BaseTest {

    @Autowired
    private ShoppingCartPage shoppingCartPage;

    @Autowired
    private ProductDetailPage productDetailPage;

    @Test(description = "Verify that the product in the cart matches the selected product")
    public void verifyProductInCartMatchesSelectedProduct() {
        shoppingCartPage.storeProductDetailsInBag();
        ProductDetails productDetailsPageModel = productDetailPage.getProductDetails();
        ProductDetails shoppingCartPageModel = shoppingCartPage.getProductDetails();

        Assert.assertEquals(productDetailsPageModel.getProductName(), shoppingCartPageModel.getProductName(), "Product names do not match!");
        Assert.assertEquals(productDetailsPageModel.getProductSize(), shoppingCartPageModel.getProductSize(), "Product sizes do not match!");
        Assert.assertEquals(productDetailsPageModel.getProductColour(), shoppingCartPageModel.getProductColour(), "Product colors do not match!");
    }

    @Test(description = "Verify that the cart subtotal is stored")
    public void verifyCartSubtotalIsStored() {
        String storedProductSubtotalInBag = shoppingCartPage.getProductDetails().getProductSubtotal();
        Assert.assertNotNull(storedProductSubtotalInBag, "The cart subtotal was not stored");
    }

    @Test(description = "Verify that the user can proceed to checkout")
    public void verifyUserProceedsToCheckout() throws InterruptedException {
        shoppingCartPage.clickProceedToCheckOutButton();
        Assert.assertTrue(shoppingCartPage.isAtCheckoutPage(), "The user was not navigated to the checkout page.");
    }
}

