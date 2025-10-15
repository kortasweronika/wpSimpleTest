package tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.*;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.List;
import java.util.Map;

public class DemoqaWebTablesTestNG extends BaseTestPerClass {

  private String baseUrl, emailDomain, firstName, lastName, age, salary, department;
  private Path stateFile;
  private final ObjectMapper om = new ObjectMapper();

  @BeforeMethod
  @Parameters({"baseUrl","emailDomain","firstName","lastName","age","salary","department","stateDir"})
  public void init(
          @Optional("https://demoqa.com") String baseUrl,
          @Optional("example.com") String emailDomain,
          @Optional("Jan") String firstName,
          @Optional("Test") String lastName,
          @Optional("33") String age,
          @Optional("5000") String salary,
          @Optional("QA") String department,
          @Optional String stateDir) throws Exception {
    this.baseUrl = baseUrl;
    this.emailDomain = emailDomain;
    this.firstName = firstName;
    this.lastName = lastName;
    this.age = age;
    this.salary = salary;
    this.department = department;
    Path dir = stateDir == null || stateDir.isBlank()
            ? Paths.get(System.getProperty("java.io.tmpdir"), "testng-shared")
            : Paths.get(stateDir);
    Files.createDirectories(dir);
    this.stateFile = dir.resolve(getClass().getName() + ".json");
  }

  @Test
  public void step1_addRecord_generatesEmail() throws Exception {
    String email = (firstName + "." + lastName + "." + System.currentTimeMillis() + "@" + emailDomain).toLowerCase();
    driver.get(baseUrl + "/webtables");
    closeFixedBannerIfPresent();
    wait.until(ExpectedConditions.elementToBeClickable(By.id("addNewRecordButton"))).click();
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("firstName")));
    driver.findElement(By.id("firstName")).sendKeys(firstName);
    driver.findElement(By.id("lastName")).sendKeys(lastName);
    driver.findElement(By.id("userEmail")).sendKeys(email);
    driver.findElement(By.id("age")).sendKeys(age);
    driver.findElement(By.id("salary")).sendKeys(salary);
    driver.findElement(By.id("department")).sendKeys(department);
    int before = driver.findElements(By.cssSelector(".rt-tbody .rt-tr-group")).size();
    WebElement submit = driver.findElement(By.id("submit"));
    try { submit.click(); } catch (ElementClickInterceptedException e) {
      ((JavascriptExecutor) driver).executeScript("arguments[0].click();", submit);
    }
    wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("userForm")));
    wait.until(d -> {
      List<WebElement> cells = driver.findElements(By.xpath("//div[@class='rt-td' and normalize-space()='" + email + "']"));
      int after = driver.findElements(By.cssSelector(".rt-tbody .rt-tr-group")).size();
      return !cells.isEmpty() || after > before;
    });
    Assert.assertTrue(driver.findElement(By.cssSelector(".rt-table")).getText().contains(email));
    Files.writeString(stateFile, om.writeValueAsString(Map.of("email", email)));

    byte[] shot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    Files.write(Path.of("last.png"), shot);


  }

  @Test
  public void step2_search_usesEmailFromState() throws Exception {
    if (!Files.exists(stateFile)) throw new SkipException("no state");
    Map<?,?> state = om.readValue(Files.readString(stateFile), Map.class);
    Object v = state.get("email");
    if (v == null) throw new SkipException("no email");
    String email = v.toString();

    driver.get(baseUrl + "/webtables");
    closeFixedBannerIfPresent();
    ensureRecordExists(email);

    WebElement search = wait.until(ExpectedConditions.elementToBeClickable(By.id("searchBox")));
    search.clear();
    search.sendKeys(email);
    wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//div[@class='rt-td' and normalize-space()='" + email + "']")));
    Assert.assertTrue(driver.findElement(By.cssSelector(".rt-table")).getText().contains(email));
  }

  private void ensureRecordExists(String email) {
    List<WebElement> cells = driver.findElements(By.xpath("//div[@class='rt-td' and normalize-space()='" + email + "']"));
    if (!cells.isEmpty()) return;
    wait.until(ExpectedConditions.elementToBeClickable(By.id("addNewRecordButton"))).click();
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("firstName")));
    driver.findElement(By.id("firstName")).sendKeys(firstName);
    driver.findElement(By.id("lastName")).sendKeys(lastName);
    driver.findElement(By.id("userEmail")).sendKeys(email);
    driver.findElement(By.id("age")).sendKeys(age);
    driver.findElement(By.id("salary")).sendKeys(salary);
    driver.findElement(By.id("department")).sendKeys(department);
    WebElement submit = driver.findElement(By.id("submit"));
    try { submit.click(); } catch (ElementClickInterceptedException e) {
      ((JavascriptExecutor) driver).executeScript("arguments[0].click();", submit);
    }
    wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("userForm")));
  }

  private void closeFixedBannerIfPresent() {
    try {
      WebElement close = wait.withTimeout(Duration.ofSeconds(2))
                             .until(ExpectedConditions.elementToBeClickable(By.id("close-fixedban")));
      close.click();
    } catch (TimeoutException ignored) {}
    try {
      ((JavascriptExecutor) driver).executeScript("document.querySelector('#fixedban')?.remove();");
    } catch (Exception ignored) {}
  }
}
