package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.time.Duration;

public class BaseTestPerClass {
  protected WebDriver driver;
  protected WebDriverWait wait;

  @BeforeClass(alwaysRun = true)
  public void setUpClass() {
    System.setProperty("webdriver.chrome.driver", "/Users/b2b/Documents/chromedriver-mac-arm64/chromedriver");
    try {
      driver = new ChromeDriver();
    } catch (Exception e) {
      e.printStackTrace();
    }

    ChromeOptions opt = new ChromeOptions();
    opt.setAcceptInsecureCerts(true);
    opt.addArguments(
            "--ignore-certificate-errors",
            "--allow-insecure-localhost",
            "--disable-features=BlockInsecurePrivateNetworkRequests"
    );
    driver = new ChromeDriver(opt);
    driver.manage().timeouts().pageLoadTimeout(java.time.Duration.ofSeconds(60));
    wait = new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(15));
    PageFactory.initElements(driver, this);
  }

  @AfterClass(alwaysRun = true)
  public void tearDownClass() {
    if (driver != null) driver.quit();
  }
}
