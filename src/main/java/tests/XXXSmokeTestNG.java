package tests;

import org.openqa.selenium.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;

public class XXXSmokeTestNG extends BaseTestPerClass {

  @Test
  public void step1_browserStarts_andOpensStake() throws Exception {
    String url = "https://stake.com/sports/home";

    driver.get(url);

    // 1) czekamy aż dokument będzie gotowy
    wait.withTimeout(Duration.ofSeconds(30)).until(d ->
        "complete".equals(((JavascriptExecutor) d).executeScript("return document.readyState"))
    );

    // 2) minimalna asercja: jesteśmy na jakimś https i nie wyleciała pusta strona
    String current = driver.getCurrentUrl();
    String title = driver.getTitle();

    System.out.println("currentUrl = " + current);
    System.out.println("title      = " + title);

    Assert.assertTrue(current.startsWith("https://"), "Not https: " + current);
    Assert.assertFalse(title == null || title.isBlank(), "Empty title -> page likely not loaded / blocked");

    // 3) dowód: screenshot + HTML źródłowe
    Files.write(Path.of("stake_step1.png"),
        ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES));

    Files.writeString(Path.of("stake_step1.html"), driver.getPageSource());
  }
}