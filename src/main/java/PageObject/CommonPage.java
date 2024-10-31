
package PageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class CommonPage {

    protected final WebDriver driver;
    protected final WebDriverWait wait;
    private static final Duration PAGE_LOAD_TIMEOUT = Duration.ofSeconds(10);

    @Autowired
    public CommonPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(this.driver, PAGE_LOAD_TIMEOUT);
    }
}