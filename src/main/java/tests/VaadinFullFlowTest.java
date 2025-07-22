package tests;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;
import tests.BaseTest;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;

public class VaadinFullFlowTest extends BaseTest {

    @Test
    public void fullComponentSearchAndScreenshotFlow() throws IOException, InterruptedException {
        driver.get("https://vaadin.com");

        // Kliknij "Docs"
        WebElement docsLink = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("a.haas-button.haas-nav-button[href='/docs/latest/']")));
        docsLink.click();

        // Zamknij popup cookies (jeśli jest)
        try {
            WebElement cookieButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[text()='OK']")));
            cookieButton.click();
            System.out.println("✅ Zamknięto popup cookies.");
        } catch (TimeoutException e) {
            System.out.println("ℹ️ Popup cookies się nie pojawił.");
        }

        // Kliknij "Components"
        WebElement componentsLink = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("a[href='/docs/latest/components']")));
        componentsLink.click();

        // Poczekaj na menu
        WebElement menuList = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("ul._menu_1jzdj_88")));

        // Pobierz <a> z menu komponentów
        List<WebElement> listItems = driver.findElements(By.cssSelector("ul._menu_1jzdj_88 li._menuItem_1jzdj_96 a"));
        List<String> componentNames = new ArrayList<>();

        for (WebElement link : listItems) {
            String text = link.getText().trim();
            if (!text.isEmpty()) {
                componentNames.add(text);
            }
        }

        // Zapisz do pliku
        Path outputPath = Paths.get("components_list.txt");
        Files.write(outputPath, componentNames, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        System.out.println("📄 Zapisano " + componentNames.size() + " komponentów do pliku components_list.txt");

        // Wybierz losowy komponent z pliku
        List<String> lines = Files.readAllLines(outputPath);
        if (lines.isEmpty()) {
            System.out.println("⚠️ Plik z komponentami jest pusty.");
            return;
        }
        String randomComponent = lines.get(new Random().nextInt(lines.size()));
        System.out.println("🎯 Wylosowano komponent: " + randomComponent);

        // Kliknij ikonę wyszukiwania
        WebElement searchIcon = wait.until(ExpectedConditions.elementToBeClickable(By.id("docs-search-btn")));
        searchIcon.click();

        // Wprowadź frazę
        WebElement searchInput = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("input[type='search'][placeholder='Search documentation']")));
        searchInput.sendKeys(randomComponent);

        // Poczekaj na wyniki
        List<WebElement> results = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                By.cssSelector("li[role='option']")));

        if (results.isEmpty()) {
            System.out.println("❌ Brak wyników dla: " + randomComponent);
            return;
        }

        // Wyświetl wyniki
        System.out.println("\n🔍 Wyniki dla frazy: " + randomComponent);
        for (WebElement result : results) {
            try {
                String title = result.findElement(By.cssSelector("div[class*='Title']")).getText();
                String link = result.findElement(By.cssSelector("a")).getAttribute("href");
                System.out.println("• " + title + " → " + link);
            } catch (Exception e) {
                System.out.println("⚠️ Błąd przy parsowaniu wyniku.");
            }
        }

        // Kliknij losowy wynik
        WebElement randomResult = results.get(new Random().nextInt(results.size()));
        WebElement linkToClick = randomResult.findElement(By.cssSelector("a"));
        String destinationUrl = linkToClick.getAttribute("href");
        String articleTitle = randomResult.findElement(By.cssSelector("div[class*='Title']")).getText();
        System.out.println("🧭 Klikam w: " + articleTitle + " → " + destinationUrl);
        linkToClick.click();

        // Poczekaj na załadowanie docelowej strony
        Thread.sleep(3000); // Zabezpieczenie, można podmienić na wait z ExpectedConditions.titleContains

        // Screenshot
        TakesScreenshot screenshotTaker = (TakesScreenshot) driver;
        File srcFile = screenshotTaker.getScreenshotAs(OutputType.FILE);
        File destFile = new File("component_screenshot.png");
        Files.copy(srcFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        System.out.println("📸 Zapisano zrzut ekranu jako component_screenshot.png");
    }
}
