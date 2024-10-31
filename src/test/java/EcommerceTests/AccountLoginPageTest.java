package EcommerceTests;

import PageObject.AccountLoginPage;
import Utils.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;


public class AccountLoginPageTest extends BaseTest {

    @Autowired
    private AccountLoginPage accountLoginPage;


    @Test
    public void accountLoginPageTest() {

        String emailAddress = Config.getPropertyWithException("emailAddress");
        String password = Config.getPropertyWithException("passWord");
        accountLoginPage.enterAccountLoginCredentials(emailAddress, password);
        accountLoginPage.clickButtonByText("Sign In");
        Assert.assertTrue(accountLoginPage.isAccountPageDisplayed(), "Login page should be displayed after clicking the Account Nav icon.");
    }

    @Test
    public void navigateToPLP() {
        accountLoginPage.hoverHeaderNavLinkByText("DRESSES");
        accountLoginPage.clickItemCategoryByText("Midi Dresses");
    }

    @Test
    public void verifyPLPNavigation() {
        String currentUrl = driver.getCurrentUrl();
        Assert.assertNotNull(currentUrl, "Current URL should not be null after navigation.");
        Assert.assertTrue(currentUrl.contains("midi-dresses"), "URL should contain 'midi-dresses' after navigation.");
    }
}