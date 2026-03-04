package tests;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.ITestContext;
import org.testng.Reporter;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.*;

import org.apache.commons.io.FileUtils;

public class RedditSearchTest extends BaseTest {

    @Test
    @Parameters({"query1"})
    public void searchMultipleQueries(@Optional("Grid") String query1, ITestContext context) {
        driver.get("https://vaadin.com");

        WebElement docsLink = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("a.haas-button.haas-nav-button[href='/docs/latest/']")));
        docsLink.click();

        try {
            WebElement cookieButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[text()='OK']")));
            cookieButton.click();
        } catch (TimeoutException e) {
            // cookie popup did not appear
        }

        WebElement searchIcon = wait.until(ExpectedConditions.elementToBeClickable(By.id("docs-search-btn")));
        searchIcon.click();

        // Collect search results as JSON
        List<Map<String, String>> results = performSearchAndCollectResults(query1);

        // Store results as JSON in TestNG output
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonOutput = gson.toJson(results);

        // Set as ITestContext attribute (accessible from listeners/reports)
        context.setAttribute("searchResults", jsonOutput);

        // Also log to TestNG Reporter output (visible in test reports)
        Reporter.log("Search results JSON:", true);
        Reporter.log(jsonOutput, true);

        // Close search
        WebElement searchInput = driver.findElement(By.cssSelector("input[type='search'][placeholder='Search documentation']"));
        searchInput.sendKeys(Keys.ESCAPE);
        searchInput.sendKeys(Keys.ESCAPE);

        // Screenshot
        searchInput.sendKeys("Now smile for the camera!");
        try {
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshot, new File("camera_smile.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        driver.quit();
    }

    private List<Map<String, String>> performSearchAndCollectResults(String query) {
        List<Map<String, String>> resultList = new ArrayList<>();

        try {
            WebElement input = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.cssSelector("input[type='search'][placeholder='Search documentation']")));
            input.sendKeys(query);
        } catch (TimeoutException e) {
            return resultList;
        }

        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("li[role='option']")));

        List<WebElement> results = driver.findElements(By.cssSelector("li[role='option']"));
        for (WebElement result : results) {
            try {
                String title = result.findElement(By.cssSelector("div[class*='Title']")).getText();
                String link = result.findElement(By.cssSelector("a")).getAttribute("href");

                Map<String, String> entry = new LinkedHashMap<>();
                entry.put("query", query);
                entry.put("title", title);
                entry.put("link", link);
                resultList.add(entry);
            } catch (Exception e) {
                // skip unparseable result
            }
        }

        return resultList;
    }
}
