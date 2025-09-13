package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
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

    driver.manage().window().maximize();
    wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    PageFactory.initElements(driver, this);
  }

  @AfterClass(alwaysRun = true)
  public void tearDownClass() {
    if (driver != null) driver.quit();
  }
}
