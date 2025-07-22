package com.example.test;
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.LoadState;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Random;

public class VaadinCLITest {
    public static void main(String[] args) throws IOException {
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
            Page page = browser.newPage();

// 1. Wejdź na vaadin.com
            page.navigate("https://vaadin.com");

// 2. Kliknij Docs i poczekaj na załadowanie podstrony
            page.locator("a.haas-button.haas-nav-button[href='/docs/latest/']").click();
            page.waitForURL("**/docs/latest/**");  // poczekaj aż naprawdę przejdzie

// 3. Poczekaj na popup cookies, jeśli się pojawi
            Locator cookieButton = page.locator("button:text('OK')");
            try {
                cookieButton.waitFor(new Locator.WaitForOptions().setTimeout(5000));
                cookieButton.click();
                System.out.println("✅ Zamknięto popup cookies.");
            } catch (PlaywrightException e) {
                System.out.println("ℹ️ Popup cookies się nie pojawił w ciągu 5 sekund.");
            }

            // 2. Wczytaj losowe zapytanie z pliku
            List<String> terms = Files.readAllLines(Path.of("search_terms.txt"));
            String query = terms.get(new Random().nextInt(terms.size()));
            System.out.println("🔍 Szukam: " + query);

            // 3. Kliknij ikonę wyszukiwania i wpisz zapytanie
            page.locator("#docs-search-btn").click();
            page.waitForSelector("input[type='search']");
            page.locator("input[type='search']").fill(query);

            // 6. Poczekaj na pojawienie się wyników
            Locator results = page.locator("li[role='option']");
            page.waitForSelector("li[role='option']");

            List<ElementHandle> handles = results.elementHandles();
            if (handles.isEmpty()) {
                System.out.println("❌ Brak wyników dla: " + query);
                return;
            }

            // 7. Wypisz wyniki
            System.out.println("📚 Wyniki:");
            for (ElementHandle handle : handles) {
                String text = handle.textContent().trim();
                System.out.println("• " + text);
            }

            // 8. Kliknij losowy wynik
            ElementHandle selected = handles.get(new Random().nextInt(handles.size()));
            selected.click();

            // 9. Poczekaj na załadowanie artykułu i pobierz nagłówek
            page.waitForLoadState(LoadState.NETWORKIDLE);
            String title = page.locator("main h1").textContent();
            Files.writeString(Path.of("result.txt"), title.trim());

            System.out.println("✅ Zapisano nagłówek: " + title.trim());
        }
    }
}
