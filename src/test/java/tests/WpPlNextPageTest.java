package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.*;

public class WpPlNextPageTest {



    private WebDriver driver;
    private boolean init = true;

    public WpPlNextPageTest(){}

    public WpPlNextPageTest(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    @BeforeTest
    private void init() {
        if (init) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
            init = false;
        }
    }

    @Test
    @Parameters("ref")
    public void nextPage(@Optional("Tags") String ref) {
        init();
        PageFactory.initElements(driver, this);

        driver.get("https://stackoverflow.com");
        try {
            Thread.sleep(100);
            acceptAllCookies();

            if (ref.equals("Tags")) {
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

    @FindBy(linkText = "Tags")
    private WebElement tagLink;

    @FindBy(linkText = "Users")
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