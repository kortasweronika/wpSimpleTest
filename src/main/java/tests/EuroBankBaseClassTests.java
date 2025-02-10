package tests;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import pl.b2b.testfactory.annotations.DriverFactory;

public class EuroBankBaseClassTests {

        @DriverFactory(name = "chromeDriver", propertyKey = "chromeDriverPath")
        public static ChromeDriver creatDriver(String driverPath){
            System.setProperty(ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY, driverPath);
            ChromeDriver driver = new ChromeDriver();
            driver.manage().window().maximize();
            return driver;
        }

}