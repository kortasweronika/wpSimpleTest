package com.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.testng.annotations.Test;

public class WpPlNextPageTest {

    @Test
    public void testNextPage() {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();

        try {
            // Navigate to the wp.pl website
            driver.get("https://stackoverflow.com/");

            // Wait for the page to load completely
            Thread.sleep(5000);  // Adjust the wait time as needed
            WebElement button = driver.findElement(By.xpath("//button[contains(text(),'Accept all cookies')]"));
            button.click();
            Thread.sleep(5000);
            // Find the element for the next page (e.g., a navigation link or button)
            driver.findElement(By.linkText("Tags")).click();

            // Wait for the next page to load completely
            Thread.sleep(5000);  // Adjust the wait time as needed

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close the browser
            driver.quit();
        }
    }
    public static void main(String[] args) {
        // Set the path to the chromedriver executable


        // Initialize a new instance of the Chrome driver
        WebDriver driver = new ChromeDriver();

        try {
            // Navigate to the wp.pl website
            driver.get("https://www.wp.pl");

            // Wait for the page to load completely
            Thread.sleep(5000);  // Adjust the wait time as needed

            // Find the element for the next page (e.g., a navigation link or button)
            WebElement nextPageLink = driver.findElement(By.xpath("//a[contains(text(), 'Next')]"));

            // Click the next page link
            nextPageLink.click();

            // Wait for the next page to load completely
            Thread.sleep(5000);  // Adjust the wait time as needed

            // Verify that the next page has loaded (this is a placeholder, adjust as needed)
            WebElement someElementOnNextPage = driver.findElement(By.id("someElementId"));
            if (someElementOnNextPage.isDisplayed()) {
                System.out.println("Next page loaded successfully.");
            } else {
                System.out.println("Failed to load the next page.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close the browser
            driver.quit();
        }
    }
}