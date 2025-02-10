package demoEuroBank;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import static java.util.concurrent.TimeUnit.SECONDS;

public class Ajax {
private WebDriver driver;

	public Ajax(WebDriver driver) {
		this.driver = driver;
	}

	public WebElement waitForIt(WebElement webElement) {
//		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
//				.withTimeout(20, SECONDS)
//				.pollingEvery(2, SECONDS)
//				.ignoring(NoSuchElementException.class, ElementNotInteractableException.class);
		WebElement element = null;//wait.until(ExpectedConditions.elementToBeClickable(webElement));
		return element;
	}

	public boolean waitForAjax() {

//	    WebDriverWait wait = new WebDriverWait(driver, 30);
//
//	    ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
//	      public Boolean apply(WebDriver driver) {
//	        try {
//	          return ((Long)((JavascriptExecutor)driver).executeScript("return jQuery.active") == 0);
//	        }
//	        catch (Exception e) {
//
//	          return true;
//	        }
//	      }
//	    };
//
//
//	    ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {
//	      public Boolean apply(WebDriver driver) {
//	        return ((JavascriptExecutor)driver).executeScript("return document.readyState")
//	        .toString().equals("complete");
//	      }
//	    };

//	  return wait.until(jQueryLoad) && wait.until(jsLoad);
		return true;
	}

	

}
