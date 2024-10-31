
package PageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class AccountLoginPage extends CommonPage {

    public static final String EMAIL_ADDRESS_INPUT_FIELD = "#login-email";
    public static final String PASSWORD_INPUT_FIELD = "#login-password";
    public static final String BUTTON_WITH_TEXT_XPATH = "//button[.//div[contains(text(), '%s')]]";
    public static final String HEADER_NAVIGATION_BAR_XPATH = "//ul[@id='frame-header-nav-v1']//a[normalize-space(text())='%s']";
    public static final String ITEM_CATEGORY_XPATH = "//a[contains(@class, 'font-brand-thin') and text()='%s']";

    public AccountLoginPage(WebDriver driver) {
        super(driver);
    }

    public void enterAccountLoginCredentials(String emailAddress, String password) {
        WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(EMAIL_ADDRESS_INPUT_FIELD)));
        emailInput.clear();
        emailInput.sendKeys(emailAddress);

        WebElement passwordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(PASSWORD_INPUT_FIELD)));
        passwordInput.clear();
        passwordInput.sendKeys(password);
    }

    public void clickButtonByText(String buttonText) {
        String xpath = String.format(BUTTON_WITH_TEXT_XPATH, buttonText);
        WebElement button = driver.findElement(By.xpath(xpath));
        button.click();
        wait.until(ExpectedConditions.urlContains("/customer/account/edit"));
    }

    public boolean isAccountPageDisplayed() {
        return Objects.requireNonNull(driver.getCurrentUrl()).contains("/customer/account/edit");
    }


    public void hoverHeaderNavLinkByText(String linkText) {
        String linkXpath = String.format(HEADER_NAVIGATION_BAR_XPATH, linkText);
        WebElement link = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(linkXpath)));

        Actions actions = new Actions(driver);
        actions.moveToElement(link).build().perform();
    }

    public void clickItemCategoryByText(String linkText) {
        String linkXpath = String.format(ITEM_CATEGORY_XPATH, linkText);
        WebElement link = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(linkXpath)));
        link.click();
    }
}
