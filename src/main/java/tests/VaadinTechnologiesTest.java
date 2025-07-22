package tests;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.io.*;
import java.nio.file.*;
import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

public class VaadinTechnologiesTest extends BaseTest {

    private static final String COMPONENTS_FILE = "components_list.txt";
    private static final String SCREENSHOT_FILE = "component_result.png";

    @Test
    public void extractAndSearchComponent() throws IOException, InterruptedException {
        driver.get("https://vaadin.com");

        // 🔹 Kliknij w „Docs”
        try {
            WebElement docsLink = wait.until(ExpectedConditions.elementToBeClickable(
                    By.cssSelector("a.haas-button.haas-nav-button[href='/docs/latest/']")));
            docsLink.click();
            System.out.println("✅ Kliknięto w 'Docs'");
        } catch (Exception e) {
            System.out.println("❌ Błąd przy klikaniu 'Docs'");
            return;
        }

        // 🔹 Zamknij popup cookies
        try {
            WebElement cookieButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[text()='OK']")));
            cookieButton.click();
            System.out.println("✅ Zamknięto popup cookies.");
        } catch (TimeoutException e) {
            System.out.println("ℹ️ Popup cookies się nie pojawił.");
        }

        // 🔹 Poczekaj aż załadujemy się na /docs/latest/
        try {
            wait.until(ExpectedConditions.urlContains("/docs/latest"));
            System.out.println("✅ Załadowano stronę Docs.");
        } catch (TimeoutException e) {
            System.out.println("❌ Nie przeładowano na /docs/latest/");
            return;
        }

        // 🔹 Kliknij w „Components”
        try {
            WebElement componentsLink = wait.until(ExpectedConditions.elementToBeClickable(
                    By.cssSelector("a[href='/docs/latest/components']")));
            componentsLink.click();
            System.out.println("✅ Kliknięto w 'Components'");
        } catch (Exception e) {
            System.out.println("❌ Nie udało się kliknąć w 'Components'");
            return;
        }

        // 🔹 Pobierz listę <a> komponentów
        List<WebElement> listItems;
        try {
            WebElement menuList = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.cssSelector("ul._menu_1jzdj_88")));
            listItems = driver.findElements(By.cssSelector("ul._menu_1jzdj_88 li._menuItem_1jzdj_96 a"));
        } catch (Exception e) {
            System.out.println("❌ Nie udało się załadować listy komponentów.");
            return;
        }

        List<String> componentNames = new ArrayList<>();
        for (WebElement link : listItems) {
            String text = link.getText().trim();
            if (!text.isEmpty()) {
                componentNames.add(text);
            }
        }

        if (componentNames.isEmpty()) {
            System.out.println("⚠️ Brak komponentów do zapisania.");
            return;
        }

        Files.write(Paths.get(COMPONENTS_FILE), componentNames,
                StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        System.out.println("📄 Zapisano " + componentNames.size() + " komponentów do " + COMPONENTS_FILE);

        // 🔹 Wylosuj frazę
        String query = componentNames.get(new Random().nextInt(componentNames.size()));
        System.out.println("🎯 Wylosowana fraza: " + query);

        // 🔹 Wyszukaj frazę
        try {
            WebElement input = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.cssSelector("input[type='search'][placeholder='Search documentation']")));
            input.sendKeys(query);
            System.out.println("✅ Wpisano frazę do wyszukiwarki.");
        } catch (TimeoutException e) {
            System.out.println("❌ Nie znaleziono pola wyszukiwania.");
            return;
        }

        // 🔹 Poczekaj na wyniki
        List<WebElement> results;
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("li[role='option']")));
            results = driver.findElements(By.cssSelector("li[role='option']"));
        } catch (Exception e) {
            System.out.println("❌ Brak wyników wyszukiwania.");
            return;
        }

        if (results.isEmpty()) {
            System.out.println("❌ Lista wyników jest pusta.");
            return;
        }

        // 🔹 Kliknij losowy wynik
        try {
            WebElement selected = results.get(new Random().nextInt(results.size()));
            WebElement resultLink = selected.findElement(By.cssSelector("a"));
            String resultUrl = resultLink.getAttribute("href");
            resultLink.click();
            System.out.println("➡️ Kliknięto wynik: " + resultUrl);
        } catch (Exception e) {
            System.out.println("❌ Nie udało się kliknąć w wynik.");
            return;
        }

        // 🔹 Zrób screenshot
        Thread.sleep(2000);
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        Files.copy(screenshot.toPath(), Paths.get(SCREENSHOT_FILE), StandardCopyOption.REPLACE_EXISTING);
        System.out.println("📸 Screenshot zapisany jako " + SCREENSHOT_FILE);
    }
}