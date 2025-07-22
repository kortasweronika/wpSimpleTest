package tests;
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class VaadinPlaywrightTest extends BasePlaywrightTest {

    @Test
    public void fullVaadinComponentSearchTest() throws IOException {
        // 1. Wejdź na vaadin.com
        page.navigate("https://vaadin.com");

        // 2. Kliknij "Docs"
        page.locator("a.haas-button.haas-nav-button[href='/docs/latest/']").click();

        // 3. Zamknij popup cookies jeśli się pojawi
        Locator cookieBtn = page.locator("button:text('OK')");
        try {
            cookieBtn.waitFor(new Locator.WaitForOptions().setTimeout(10_000).setState(WaitForSelectorState.VISIBLE));
            cookieBtn.click();
            System.out.println("✅ Zamknięto popup cookies.");
        } catch (PlaywrightException e) {
            System.out.println("ℹ️ Popup cookies się nie pojawił.");
        }


        // 4. Kliknij "Components" w menu
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Components").setExact(true)).click();


// 5. Poczekaj na listę komponentów i zbierz linki
        Locator componentLinks = page.locator("a[href^='/docs/latest/components/']");
        componentLinks.first().waitFor();  // Czekamy aż pierwszy się pojawi
        List<String> componentNames = componentLinks.allInnerTexts();


        // 6. Zbierz teksty linków komponentów
        Path file = Path.of("components_list.txt");
        Files.write(file, componentNames);
        System.out.println("📄 Zapisano " + componentNames.size() + " komponentów do pliku components_list.txt");


        // 7. Wylosuj jeden komponent
        Collections.shuffle(componentNames);
        String query = componentNames.get(0);
        System.out.println("🎲 Wylosowano komponent: " + query);

        // 8. Kliknij ikonę wyszukiwania
        page.locator("#docs-search-btn").click();

        // 9. Wpisz zapytanie
        Locator searchInput = page.locator("input[type='search'][placeholder='Search documentation']");
        searchInput.fill(query);

        // 10. Poczekaj na wyniki i wypisz je
        Locator results = page.locator("li[role='option']");
        results.first().waitFor();

        List<ElementHandle> allResults = results.elementHandles();
        if (allResults.isEmpty()) {
            System.out.println("❌ Brak wyników dla: " + query);
            return;
        } else {
            System.out.println("\n🔍 Wyniki dla frazy: " + query);
        }

        // 11. Wybierz losowy wynik i kliknij
        int index = new Random().nextInt(allResults.size());
        ElementHandle selected = allResults.get(index);

        String title = selected.querySelector("div[class*='Title']").innerText();
        String href = selected.querySelector("a").getAttribute("href");
        String fullUrl = href.startsWith("http") ? href : "https://vaadin.com" + href;

        System.out.println("📎 Klikam w: " + title + " → " + fullUrl);
        page.navigate(fullUrl);

// 12. Screenshot strony
        page.screenshot(new Page.ScreenshotOptions().setPath(Path.of("vaadin_result.png")));
        System.out.println("📸 Zrobiono screenshot wynikowej strony.");

    }
}
