package tests;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class WikipediaSharedStaticVarTestNG extends BaseTestPerClass {

  private static volatile String keyword;

  @Test
  public void t1_prepareKeyword() {
    keyword = "qa-" + System.currentTimeMillis();
    Assert.assertNotNull(keyword);
  }

  @Test(dependsOnMethods = "t1_prepareKeyword")
  public void t2_searchWithKeyword() {
    if (keyword == null) throw new SkipException("no keyword");

    String url = "https://en.wikipedia.org/w/index.php?search=" +
                 URLEncoder.encode(keyword, StandardCharsets.UTF_8);
    driver.get(url);

    try {
      ((JavascriptExecutor) driver).executeScript(
              "document.querySelector('#frb-main, .cdx-modal, #pcookienotice, .wmf-cookienotice')?.remove();");
    } catch (Exception ignored) {}

    WebElement searchInput = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.cssSelector("input[name='search']")));

    String value = searchInput.getAttribute("value");
    Assert.assertTrue(value != null && value.contains(keyword),
            "Search input does not echo keyword. value=" + value);
  }
}
