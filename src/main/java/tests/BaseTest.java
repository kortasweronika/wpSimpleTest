package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import java.io.File;
import java.time.Duration;

public class BaseTest {
    protected WebDriver driver;
    protected WebDriverWait wait;

    @BeforeMethod
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "/Users/b2b/Documents/chromedriver-mac-arm64/chromedriver");
        try {
            ChromeOptions opts = new ChromeOptions();
            opts.addArguments("--headless=new", "--disable-gpu", "--no-first-run", "--no-default-browser-check");

// (opcjonalnie) jeżeli Chrome nie jest w standardowej lokalizacji
// opts.setBinary("/Applications/Google Chrome.app/Contents/MacOS/Google Chrome");

            String work = System.getProperty("testfactory.workdir",
                    System.getProperty("java.io.tmpdir"));

            ChromeDriverService service = new ChromeDriverService.Builder()
                    .withVerbose(true)
                    .withLogFile(new File(work, "chromedriver.log"))
                    .build();
            driver = new ChromeDriver(service, opts);
        } catch (Exception e) {
            e.printStackTrace();
        }

        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) driver.quit();
    }
}
