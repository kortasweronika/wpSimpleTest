package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import java.time.Duration;

public class BaseTestPerClass {
  protected WebDriver driver;
  protected WebDriverWait wait;

  @BeforeClass(alwaysRun = true)
  public void setUpClass() {
    System.setProperty("webdriver.chrome.driver",
            "/Users/b2b/Documents/chromedriver-mac-arm64/chromedriver");

    ChromeOptions opt = new ChromeOptions();
    opt.setAcceptInsecureCerts(true);
    opt.addArguments(
            "--ignore-certificate-errors",
            "--allow-insecure-localhost",
            "--disable-features=BlockInsecurePrivateNetworkRequests"
    );

    driver = new ChromeDriver(opt);
    driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
    wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    PageFactory.initElements(driver, this);
  }

  @AfterMethod(alwaysRun = true)
  public void cleanUpAfterEachTest() {
    if (driver == null) return;

    // Zamknij ewentualne dodatkowe karty/okna pozostawiając główne
    try {
      String main = driver.getWindowHandle();
      for (String h : driver.getWindowHandles()) {
        if (!h.equals(main)) {
          driver.switchTo().window(h).close();
        }
      }
      driver.switchTo().window(main);
    } catch (NoSuchSessionException ignored) {}

    try { driver.manage().deleteAllCookies(); } catch (Exception ignored) {}
  }

  @AfterClass(alwaysRun = true)
  public void tearDownClass() {
    if (driver != null) {
      try { driver.quit(); } catch (Exception ignored) {}
      driver = null;
    }
  }
}
