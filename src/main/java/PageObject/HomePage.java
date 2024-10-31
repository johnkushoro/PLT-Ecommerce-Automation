package PageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.springframework.stereotype.Component;
import org.testng.Assert;

import java.util.Objects;

@Component
public class HomePage extends CommonPage {

    public static final String COOKIE_BUTTON_BY_TEXT_XPATH = "//div[@id='onetrust-button-group']/button[text()='%s']";
    public static final String ACCOUNT_PAGE_NAVIGATION_ICON = "//a[@id='account-link']";

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void acceptCookies() throws InterruptedException {
        clickCookieButtonByText();
        Assert.assertTrue(isCookieBannerHidden(), "Cookie banner should be hidden after accepting cookies.");
    }

    private void clickCookieButtonByText() throws InterruptedException {
        String buttonXpath = String.format(COOKIE_BUTTON_BY_TEXT_XPATH, "Accept All");
        WebElement button = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(buttonXpath)));
        wait.until(ExpectedConditions.elementToBeClickable(button));
        button.click();
        wait.until(ExpectedConditions.invisibilityOf(button));
    }

    private boolean isCookieBannerHidden() {
        String buttonXpath = String.format(COOKIE_BUTTON_BY_TEXT_XPATH, "Accept All");
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(buttonXpath)));
    }

    public void clickAccountNavIcon() {
        WebElement accountNavIcon = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(ACCOUNT_PAGE_NAVIGATION_ICON)));
        accountNavIcon.click();
        wait.until(ExpectedConditions.urlContains("/customer/account/login"));
    }

    public boolean isAccountLoginPageDisplayed() {
        return Objects.requireNonNull(driver.getCurrentUrl()).contains("/customer/account/login");
    }


}
