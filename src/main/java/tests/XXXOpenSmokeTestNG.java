package tests;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;

public class XXXOpenSmokeTestNG extends BaseTestPerClass {

  @Test
  public void opensStake_orAtLeastShowsWhyNot() throws Exception {
    String url = "https://stake.com/";

    driver.get(url);

    // 1) czekamy na document.readyState=complete (nie gwarantuje, że SPA dogra wszystko, ale wykrywa "wisi")
    wait.withTimeout(Duration.ofSeconds(30)).until(d ->
        "complete".equals(((JavascriptExecutor) d).executeScript("return document.readyState"))
    );

    // 2) podstawowe sygnały
    String current = driver.getCurrentUrl();
    String title = safe(driver::getTitle);
    String html = driver.getPageSource();

    System.out.println("currentUrl = " + current);
    System.out.println("title      = " + title);

    // 3) dowody do debugowania
    Files.write(Path.of("open_step1.png"),
        ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES));
    Files.writeString(Path.of("open_step1.html"), html);

    // 4) wykrywanie typowych przypadków "strona się nie otworzyła jak trzeba"
    String lower = (title + "\n" + html).toLowerCase();

    boolean looksLikeBotCheck =
        lower.contains("checking your browser") ||
        lower.contains("cloudflare") ||
        lower.contains("attention required") ||
        lower.contains("verify you are human") ||
        lower.contains("captcha");

    boolean looksLikeGeoBlock =
        lower.contains("not available in your region") ||
        lower.contains("isn't available in your region") ||
        lower.contains("access denied") ||
        lower.contains("forbidden") ||
        lower.contains("country") && lower.contains("restricted");

    boolean looksLikeBrowserError =
        title != null && (
          title.contains("This site can’t be reached") ||
          title.contains("ERR_") ||
          title.contains("Privacy error")
        );

    // 5) “zdany” warunek: mamy sensowny tytuł i nie wpadliśmy w oczywisty blok/error
    Assert.assertTrue(current.startsWith("https://"), "Not https / redirect to strange url: " + current);
    Assert.assertFalse(title == null || title.isBlank(), "Empty title -> likely blocked/failed; see open_step1.html/png");

    Assert.assertFalse(looksLikeBrowserError, "Browser error page; see open_step1.html/png");
    Assert.assertFalse(looksLikeBotCheck, "Bot/CF check detected; see open_step1.html/png");
    Assert.assertFalse(looksLikeGeoBlock, "Geo-block detected; see open_step1.html/png");

    // Opcjonalnie: poczekaj na <body> widoczne (czasem daje lepszy sygnał niż readyState)
    wait.withTimeout(Duration.ofSeconds(10))
        .until(ExpectedConditions.visibilityOfElementLocated(By.tagName("body")));
  }

  private static String safe(ThrowingSupplier<String> s) {
    try { return s.get(); } catch (Exception e) { return ""; }
  }

  @FunctionalInterface
  interface ThrowingSupplier<T> { T get() throws Exception; }
}