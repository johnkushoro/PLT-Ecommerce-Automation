package EcommerceTests;

import PageObject.ProductListPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ProductListPageTests extends BaseTest {

    @Autowired
    private ProductListPage productListPage;

    @Test(description = "Verify that the PLP Category Description is displayed and select a product from the PLP Category")
    public void verifyPLPAndSelectsProductTest() throws InterruptedException {
        boolean isVisible = productListPage.isCategoryDescriptionVisible();
        Assert.assertTrue(isVisible, "PLP description content is not displayed.");
        productListPage.clickFirstProductCard();
    }
}
