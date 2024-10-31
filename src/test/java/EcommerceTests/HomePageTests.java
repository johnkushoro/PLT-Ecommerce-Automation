
package EcommerceTests;

import PageObject.HomePage;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

public class HomePageTests extends BaseTest {

    @Autowired
    private HomePage homePage;


    @Test
    public void testHomePageNavigation() {
        Assert.assertNotNull(driver.getTitle(), "Page title should not be null.");
    }

    @Test
    public void navigateToLoginPageFromHomePageTest() throws InterruptedException {
        homePage.acceptCookies();
        homePage.clickAccountNavIcon();
        Assert.assertTrue(homePage.isAccountLoginPageDisplayed(), "Login page should be displayed after clicking the Account Nav icon.");

    }
}
