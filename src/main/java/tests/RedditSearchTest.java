package tests;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;
import org.apache.commons.io.FileUtils;

public class RedditSearchTest extends BaseTest {

    @Test
    @Parameters({"query1", "query2"})
    public void searchMultipleQueries(@Optional("Grid") String query1, @Optional("tooltip") String query2) {
        driver.get("https://vaadin.com");

        // Kliknij przycisk "Docs"
        WebElement docsLink = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("a.haas-button.haas-nav-button[href='/docs/latest/']")));
        docsLink.click();

        // Zamknij popup cookies (jeśli się pojawi)
        try {
            WebElement cookieButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[text()='OK']")));
            cookieButton.click();
            System.out.println("✅ Zamknięto popup cookies.");
        } catch (TimeoutException e) {
            System.out.println("ℹ️ Popup cookies się nie pojawił.");
        }

        // Kliknij ikonę wyszukiwania
        WebElement searchIcon = wait.until(ExpectedConditions.elementToBeClickable(By.id("docs-search-btn")));
        searchIcon.click();

        // Szukaj pierwszego zapytania
        performSearchAndPrintResults(query1);

        // Wyczyść pole ESC+ESC
        WebElement searchInput = driver.findElement(By.cssSelector("input[type='search'][placeholder='Search documentation']"));
        searchInput.sendKeys(Keys.ESCAPE);
        searchInput.sendKeys(Keys.ESCAPE);

        // Szukaj drugiego zapytania
        performSearchAndPrintResults(query2);

        // Ostatni krok: wpisz specjalną frazę
        searchInput.sendKeys(Keys.ESCAPE);
        searchInput.sendKeys(Keys.ESCAPE);
        searchInput.sendKeys("Now smile for the camera!");

        // Zrób screenshot
        try {
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshot, new File("camera_smile.png"));
            System.out.println("📸 Screenshot zapisany jako camera_smile.png");
        } catch (IOException e) {
            System.out.println("❌ Nie udało się zapisać screenshota.");
            e.printStackTrace();
        }

        // Zamknij przeglądarkę (ręcznie — nie przez AfterMethod)
        driver.quit();
    }

    private void performSearchAndPrintResults(String query) {
        try {
            WebElement input = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.cssSelector("input[type='search'][placeholder='Search documentation']")));
            input.sendKeys(query);
        } catch (TimeoutException e) {
            System.out.println("❌ Nie udało się znaleźć pola wyszukiwania.");
            return;
        }

        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("li[role='option']")));

        List<WebElement> results = driver.findElements(By.cssSelector("li[role='option']"));
        if (results.isEmpty()) {
            System.out.println("❌ Brak wyników dla: " + query);
        } else {
            System.out.println("\n🔍 Wyniki dla frazy: " + query);
            for (WebElement result : results) {
                try {
                    String title = result.findElement(By.cssSelector("div[class*='Title']")).getText();
                    String link = result.findElement(By.cssSelector("a")).getAttribute("href");
                    System.out.println("• " + title + " → " + link);
                } catch (Exception e) {
                    System.out.println("⚠️ Błąd przy parsowaniu wyniku.");
                }
            }
        }
    }
}
