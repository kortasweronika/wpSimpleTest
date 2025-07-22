package tests;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import org.testng.annotations.Test;
import tests.BaseTest;

import java.io.IOException;
import java.nio.file.*;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class VaadinTechnologiesTest extends BaseTest {

        @Test
        public void extractComponentLinks() throws InterruptedException, IOException {
            driver.get("https://vaadin.com");

            // Kliknij przycisk "Docs"
            WebElement docsLink = wait.until(ExpectedConditions.elementToBeClickable(
                    By.cssSelector("a.haas-button.haas-nav-button[href='/docs/latest/']")));
            docsLink.click();

            // Akceptuj ciastka, jeśli popup się pojawi
            try {
                WebElement cookieButton = wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//button[text()='OK']")));
                cookieButton.click();
                System.out.println("✅ Zamknięto popup cookies.");
            } catch (TimeoutException e) {
                System.out.println("ℹ️ Popup cookies się nie pojawił.");
            }

            // Poczekaj na menu komponentów i kliknij "Components"
            WebElement componentsLink = wait.until(ExpectedConditions.elementToBeClickable(
                    By.cssSelector("a[href='/docs/latest/components']")));
            componentsLink.click();

            // Poczekaj na rozwinięcie listy komponentów
            WebElement componentsMenu = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.cssSelector("ul._menu_1jzdj_88")));

            // Zbierz wszystkie linki z sekcji komponentów
            List<WebElement> allLinks = componentsMenu.findElements(By.cssSelector("a[href*='/docs/latest/components/']"));
            List<String> linkTexts = new ArrayList<>();

            for (WebElement link : allLinks) {
                String text = link.getText().trim();
                if (!text.isEmpty()) {
                    linkTexts.add(text);
                }
            }

            // Zapisz do pliku
            Path outputPath = Paths.get("components_list.txt");
            Files.write(outputPath, linkTexts, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

            System.out.println("📄 Zapisano " + linkTexts.size() + " komponentów do pliku components_list.txt");

            driver.quit();
        }
    }
