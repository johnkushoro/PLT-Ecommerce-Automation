
package EcommerceTests;

import Model.ProductDetails;
import PageObject.ProductDetailPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ProductDetailPageTests extends BaseTest{

    @Autowired
    private ProductDetailPage productDetailPage;


    @Test
    public void testSelectProductOptionsAndAddToBag() throws InterruptedException {
        productDetailPage.selectRandomProductColorAndSize();

        ProductDetails productDetails = productDetailPage.getProductDetails();

        Assert.assertNotNull(productDetails.getProductName(), "Product name should not be null.");
        Assert.assertNotNull(productDetails.getProductSize(), "Product size should not be null.");
        Assert.assertNotNull(productDetails.getProductColour(), "Product color should not be null.");

        System.out.println("Product added to bag with details: " + productDetails);
    }

    @Test(dependsOnMethods = {"testSelectProductOptionsAndAddToBag"})
    public void testClickBagIcon() {
        productDetailPage.clickBagIcon();
        Assert.assertTrue(productDetailPage.isBagPageDisplayed(), "Bag page should be displayed after clicking the bag icon.");
    }
}
