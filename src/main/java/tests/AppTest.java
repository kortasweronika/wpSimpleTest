package tests;


import demoEuroBank.Ajax;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pl.b2b.testfactory.TestFactoryUtils;

//import demoEuroBank.App;

/**
 * Unit test for simple App.
 */
public class AppTest {
    private WebDriver driver;
    private boolean init = true;
    private Ajax method;
//    @BeforeTest
//    public void openEuroBank(){
//        init();
//        driver.get("http://demo.eurobank.pl/logowanie_etap_1.html");
//    }
    private void init() {
        if (init) {
            driver = TestFactoryUtils.getDriver("chromeDriver", ChromeDriver.class);
//        this.method = new TestMethods(driver);
//            PageFactory.initElements(driver, App.class);
//            System.setProperty("webdriver.chrome.driver", "C:\\Users\\weronika\\IdeaProjects\\tf\\ProjectDemoEuroBank\\src\\resources\\chromedriver.exe");
//            driver = new ChromeDriver();
            PageFactory.initElements(driver, this);

            method = new Ajax(driver);
//                        driver.manage().window().maximize();
            init = false;
        }
    }

    @Test
    @Parameters({"id", "password"})
    public void loginPageTest(String id, String password) {
        init();
        driver.get("http://demo.eurobank.pl/logowanie_etap_1.html");
        signIn(id, password);
////        przelewKrajowy(odbiorca, nrKonta, kwotaPrzelewu, tytulPrzelewu);
//        saldoKontaIWylogowanie();

//        close();
    }

    @Test
    @Parameters({"nazwaOdbiorcy", "nrKonta", "kwotaPrzelewu", "tytulPrzelewu"})
    public void przelewKrajowyTest(String nazwaOdbiorcy, String nrKonta, String kwotaPrzelewu, String tytulPrzelewu){
        init();
        przelewKrajowy(nazwaOdbiorcy, nrKonta, kwotaPrzelewu, tytulPrzelewu);
        saldoKontaIWylogowanie();

    }

    //    @After
    public void close() {
        driver.quit();
    }

    //LOGIN PAGE
    @FindBy(xpath = "//*[@id=\"login_id\"]")
    private WebElement idText;

    @FindBy (xpath = "//*[@id=\"login_next\"]")
    private WebElement nextButton;

    @FindBy (xpath = "//*[@id=\"login_password\"]")
    private WebElement passwordText;

    @FindBy (xpath = "//*[@id=\"login_next\"]")
    private WebElement loginButton;

    private void enterId(String id){
        idText.sendKeys(id);
    }

    private void clickNextButton(){
        nextButton.click();
    }

    private void enterPassword(String password){
        passwordText.sendKeys(password);
    }

    private void clickLoginButton(){

        loginButton.click();
    }

    public void signIn(String id, String password){
        enterId(id);
        clickNextButton();
        method.waitForIt(passwordText);
        enterPassword(password);
//        method
        clickLoginButton();
    }

    //PRZELEW KRAJOWY
    @FindBy (xpath = "/html/body/section/div/div/nav/ul/li[3]/div/a")
    private WebElement platnosciButton;

    @FindBy (xpath = "//*[@id=\"form_receiver\"]")
    private WebElement nazwaOdbiorcyText;

    @FindBy (xpath = "//*[@id=\"form_account_to\"]")
    private WebElement naRachunekText;

    @FindBy (xpath = "//*[@id=\"form_amount\"]")
    private WebElement kwotaText;

    @FindBy (xpath = "//*[@id=\"form_title\"]")
    private WebElement tytulText;

    @FindBy (xpath = "/html/body/section/div/div/div/div[2]/div[2]/form/div[2]/div/button")
    private WebElement dalejButton;

    @FindBy (xpath = "/html/body/section/div/div/div/div[2]/div[2]/form/div[2]/div/button")
    private WebElement zatwierdzButton;

    private void clickPlatnosciButton(){
        platnosciButton.click();
    }

    private void enterNazwaOdbiorcy(String nazwaOdbiorcy){
        nazwaOdbiorcyText.sendKeys(nazwaOdbiorcy);
    }

    private void enterNaRachunek(String rachunek){
        naRachunekText.sendKeys(rachunek);
    }

    private void enterKwota(String kwota){
        kwotaText.sendKeys(kwota);
    }

    private void enterTytul(String tytulPrzelewu){
        tytulText.clear();
        tytulText.sendKeys(tytulPrzelewu);
    }

    private void clickDalejButton(){
        dalejButton.click();
    }

    private void clickZatwierdzButton(){
        zatwierdzButton.click();
    }

    public void przelewKrajowy(String nazwaOdbiorcy, String rachunek, String kwota, String tytulPrzelewu){
        System.out.println("przelew krajowy");
        method.waitForIt(platnosciButton);
        clickPlatnosciButton();
        System.out.println("platnosci");
        enterNazwaOdbiorcy(nazwaOdbiorcy);
        enterNaRachunek(rachunek);
        enterKwota(kwota);
        enterTytul(tytulPrzelewu);
        TestFactoryUtils.addScreenShotFromSeleniumDriver(driver);
        sleep();
        clickDalejButton();
        clickZatwierdzButton();
    }

    private void sleep() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //SPRAWDZENIE SALDA KONTA I WYLOGOWANIE

    @FindBy (xpath = "/html/body/section/div/div/nav/ul/li[2]/div/a")
    private WebElement kontaOsobisteButton;

    @FindBy (xpath = "/html/body/header/div/a[3]")
    private WebElement wylogujButton;

    private void clickKontaOsobisteButton(){
        kontaOsobisteButton.click();
    }

    private void clickWylogujButton(){
        wylogujButton.click();
    }

    public void saldoKontaIWylogowanie(){
        clickKontaOsobisteButton();
        TestFactoryUtils.addScreenShotFromSeleniumDriver(driver);
        sleep();
        clickWylogujButton();
    }

}
