package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class WpPlNextPageTest {

    private WebDriver driver;
    private boolean init = true;

    private void init() {
        if (init) {
            System.setProperty("webdriver.chrome.driver", "/Users/b2b/Documents/chromedriver-mac-arm64/chromedriver");
            try {
                driver = new ChromeDriver();
            } catch (Exception e) {
                e.printStackTrace();
            }

            PageFactory.initElements(driver, this);
            init = false;
        }
    }

    @Test
    @Parameters("linkName")
    public void nextPage(String linkName) {
        init();

        driver.get("https://stackoverflow.com");
        try {
            Thread.sleep(700);
            acceptAllCookies();

            if (linkName.equals("Tags")) {
                tagLinkClick();
            } else {
                usersLinkClick();
            }
        } catch (InterruptedException ignored) {}
        finally {
            driver.quit();
        }

    }

    @FindBy(xpath = "//button[contains(text(),'Accept all cookies')]")
    private WebElement allCookiesButton ;

    @FindBy(xpath = "//li[contains(concat(' ', normalize-space(@class), ' '), ' ps-relative ')]/a[contains(concat(' ', normalize-space(@class), ' '), ' s-block-link ') and contains(concat(' ', normalize-space(@class), ' '), ' pl8 ') and contains(concat(' ', normalize-space(@class), ' '), ' js-gps-track ') and contains(concat(' ', normalize-space(@class), ' '), ' nav-links--link ') and contains(concat(' ', normalize-space(@class), ' '), ' -link__with-icon ') and @href='/tags']")
    private WebElement tagLink;

    @FindBy(xpath = "//li[contains(concat(' ', normalize-space(@class), ' '), ' ps-relative ')]/a[contains(concat(' ', normalize-space(@class), ' '), ' s-block-link ') and contains(concat(' ', normalize-space(@class), ' '), ' pl8 ') and contains(concat(' ', normalize-space(@class), ' '), ' js-gps-track ') and contains(concat(' ', normalize-space(@class), ' '), ' nav-links--link ') and contains(concat(' ', normalize-space(@class), ' '), ' -link__with-icon ') and @href='/users']")
    private WebElement usersLink;

    @FindBy(css = "a[href='/questions/ask']")
    private WebElement askLink;

    private void acceptAllCookies(){
        allCookiesButton.click();
    }

    private void tagLinkClick(){
        tagLink.click();
    }

    private void usersLinkClick(){
        usersLink.click();
    }

    @AfterTest
    public void tearDown() {
        driver.quit();
    }


}