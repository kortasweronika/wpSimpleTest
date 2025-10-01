package tests;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

public class StaticVarTwoStepPlaygroundTestNG extends BaseTestPerClass {

  private static volatile String label;

  @Test
  public void t1_prepare() {
    label = "btn-" + System.currentTimeMillis();
    Assert.assertNotNull(label);
  }

  @Test
  public void t2_apply() {
    if (label == null) throw new SkipException("no label");

    driver.get("https://uitestingplayground.com/textinput");

    WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("newButtonName")));
    input.clear();
    input.sendKeys(label);

    WebElement btn = driver.findElement(By.id("updatingButton"));
    btn.click();

    wait.until(ExpectedConditions.textToBePresentInElement(btn, label));
    Assert.assertEquals(btn.getText(), label);
  }
}
