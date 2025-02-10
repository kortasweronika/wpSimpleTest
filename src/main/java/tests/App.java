package tests;//package demoEuroBank;
//
//import org.openqa.selenium.JavascriptExecutor;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.support.FindBy;
//import org.openqa.selenium.support.ui.ExpectedCondition;
//import org.openqa.selenium.support.ui.WebDriverWait;
//
///**
// * Hello world!
// *
// */
//public class App {
//
//    //LOGIN PAGE
//    @FindBy(xpath = "//*[@id=\"login_id\"]")
//    private WebElement idText;
//
//    @FindBy (xpath = "//*[@id=\"login_next\"]")
//    private WebElement nextButton;
//
//    @FindBy (xpath = "//*[@id=\"login_password\"]")
//    private WebElement passwordText;
//
//    @FindBy (xpath = "//*[@id=\"login_next\"]")
//    private WebElement loginButton;
//
//    private void enterId(String id){
//        idText.sendKeys(id);
//    }
//
//    private void clickNextButton(){
//        nextButton.click();
//    }
//
//    private void enterPassword(String password){
//        passwordText.sendKeys(password);
//    }
//
//    private void clickLoginButton(){
//        loginButton.click();
//    }
//
//    public void signIn(String id, String password){
//        System.out.println("wkladam id "+id);
//        enterId(id);
////        method.waitForAjax(driver);
//        clickNextButton();
//        enterPassword(password);
//        clickLoginButton();
//    }
//
//    //PRZELEW KRAJOWY
//    @FindBy (xpath = "/html/body/section/div/div/nav/ul/li[3]/div/a")
//    private WebElement platnosciButton;
//
//    @FindBy (xpath = "//*[@id=\"form_receiver\"]")
//    private WebElement nazwaOdbiorcyText;
//
//    @FindBy (xpath = "//*[@id=\"form_account_to\"]")
//    private WebElement naRachunekText;
//
//    @FindBy (xpath = "//*[@id=\"form_amount\"]")
//    private WebElement kwotaText;
//
//    @FindBy (xpath = "//*[@id=\"form_title\"]")
//    private WebElement tytulText;
//
//    @FindBy (xpath = "/html/body/section/div/div/div/div[2]/div[2]/form/div[2]/div/button")
//    private WebElement dalejButton;
//
//    @FindBy (xpath = "/html/body/section/div/div/div/div[2]/div[2]/form/div[2]/div/button")
//    private WebElement zatwierdzButton;
//
//    private void clickPlatnosciButton(){
//        platnosciButton.click();
//    }
//
//    private void enterNazwaOdbiorcy(String nazwaOdbiorcy){
//        nazwaOdbiorcyText.sendKeys(nazwaOdbiorcy);
//    }
//
//    private void enterNaRachunek(String rachunek){
//        naRachunekText.sendKeys(rachunek);
//    }
//
//    private void enterKwota(String kwota){
//        kwotaText.sendKeys(kwota);
//    }
//
//    private void enterTytul(String tytulPrzelewu){
//        tytulText.clear();
//        tytulText.sendKeys(tytulPrzelewu);
//    }
//
//    private void clickDalejButton(){
//        dalejButton.click();
//    }
//
//    private void clickZatwierdzButton(){
//        zatwierdzButton.click();
//    }
//
//    public void przelewKrajowy(String nazwaOdbiorcy, String rachunek, String kwota, String tytulPrzelewu){
//        clickPlatnosciButton();
//        enterNazwaOdbiorcy(nazwaOdbiorcy);
//        enterNaRachunek(rachunek);
//        enterKwota(kwota);
//        enterTytul(tytulPrzelewu);
//        clickDalejButton();
//        clickZatwierdzButton();
//    }
//
//    //SPRAWDZENIE SALDA KONTA I WYLOGOWANIE
//
//    @FindBy (xpath = "/html/body/section/div/div/nav/ul/li[2]/div/a")
//    private WebElement kontaOsobisteButton;
//
//    @FindBy (xpath = "/html/body/header/div/a[3]")
//    private WebElement wylogujButton;
//
//    private void clickKontaOsobisteButton(){
//        kontaOsobisteButton.click();
//    }
//
//    private void clickWylogujButton(){
//        wylogujButton.click();
//    }
//
//    public void saldoKontaIWylogowanie(){
//        clickKontaOsobisteButton();
//        clickWylogujButton();
//    }
//
//}
