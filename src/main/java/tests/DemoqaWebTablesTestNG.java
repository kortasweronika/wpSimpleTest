package tests;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;
import java.util.List;

public class DemoqaWebTablesTestNG extends BaseTestPerClass {

  private String baseUrl, emailDomain, firstName, lastName, age, salary, department;
  private String email;

  @BeforeClass
  @Parameters({"baseUrl","emailDomain","firstName","lastName","age","salary","department"})
  public void initParams(
          @Optional("https://demoqa.com") String baseUrl,
          @Optional("example.com") String emailDomain,
          @Optional("Jan") String firstName,
          @Optional("Test") String lastName,
          @Optional("33") String age,
          @Optional("5000") String salary,
          @Optional("QA") String department) {
    this.baseUrl = baseUrl; this.emailDomain = emailDomain;
    this.firstName = firstName; this.lastName = lastName;
    this.age = age; this.salary = salary; this.department = department;
  }

  @Test
  @Parameters({"email"})
  public void step1_addRecord_generatesEmail(@Optional("default@wp.pl") String email) {
    driver.get(baseUrl + "/webtables");
    closeFixedBannerIfPresent();

    // otwórz modal
    wait.until(ExpectedConditions.elementToBeClickable(By.id("addNewRecordButton"))).click();
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("firstName")));

    // wypełnij i zapisz
    driver.findElement(By.id("firstName")).sendKeys(firstName);
    driver.findElement(By.id("lastName")).sendKeys(lastName);
    driver.findElement(By.id("userEmail")).sendKeys(email);
    driver.findElement(By.id("age")).sendKeys(age);
    driver.findElement(By.id("salary")).sendKeys(salary);
    driver.findElement(By.id("department")).sendKeys(department);

    int before = driver.findElements(By.cssSelector(".rt-tbody .rt-tr-group")).size();

    WebElement submit = driver.findElement(By.id("submit"));
    try { submit.click(); }
    catch (ElementClickInterceptedException e) {
      ((JavascriptExecutor) driver).executeScript("arguments[0].click();", submit);
    }

    // 1) modal zniknął
    wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("userForm")));
    // 2) wiersz z mailem (lub liczba wierszy wzrosła)
    wait.until(d -> {
      List<WebElement> cells = driver.findElements(By.xpath("//div[@class='rt-td' and normalize-space()='" + email + "']"));
      int after = driver.findElements(By.cssSelector(".rt-tbody .rt-tr-group")).size();
      return !cells.isEmpty() || after > before;
    });

    Assert.assertTrue(driver.findElement(By.cssSelector(".rt-table")).getText().contains(email),
            "Nowy rekord nie pojawił się w tabeli");
  }

  @Test(dependsOnMethods = "step1_addRecord_generatesEmail")
  public void step2_search_usesEmailFromStep1() {
    // KLUCZ: nie przeładowujemy strony; pracujemy w tej samej sesji i tym samym DOM-ie
    WebElement search = wait.until(ExpectedConditions.elementToBeClickable(By.id("searchBox")));
    search.clear();
    search.sendKeys(email);

    wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//div[@class='rt-td' and normalize-space()='" + email + "']")));

    Assert.assertTrue(driver.findElement(By.cssSelector(".rt-table")).getText().contains(email));
  }

  private void closeFixedBannerIfPresent() {
    try {
      WebElement close = wait.withTimeout(Duration.ofSeconds(2))
                             .until(ExpectedConditions.elementToBeClickable(By.id("close-fixedban")));
      close.click();
    } catch (TimeoutException ignored) {}
    try {
      ((JavascriptExecutor) driver).executeScript(
              "document.querySelector('#fixedban')?.remove();");
    } catch (Exception ignored) {}
  }
}
