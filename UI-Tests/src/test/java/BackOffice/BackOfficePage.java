package BackOffice;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BackOfficePage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By skinManagement = By.xpath("//*[@id=\"sidebar-menu\"]/div/ul/li[2]/a");
    private final By highlightsManager = By.xpath("//*[@id=\"sidebar-menu\"]/div/ul/li[2]/ul/li[1]/a");
    private final By configQA = By.xpath("//*[@id=\"root\"]/div[1]/div/div[2]/div/div");

    private final By saveConfigBtn = By.xpath("//*[@id=\"root\"]/div[1]/div/div[2]/div[1]/button");
    private final By successMessage = By.xpath("//*[@id=\"root\"]/div[2]");

    private final By editLanguageBtn = By.xpath("/html/body/div[1]/div/div[3]/div/div[1]/div/div[2]/div[3]/div/div/div/div[2]/span/button");
    private final By deleteLanguageBtn = By.xpath("//*[@id=\"root\"]/div[1]/div/div[2]/div[3]/div/div/div/div[2]/div/button[1]");
    private final By addLanguageBtn = By.xpath("//*[@id=\"root\"]/div[1]/div/div[2]/div[3]/div/div/div/div[2]/div/button[2]");
    private final By saveLanguageSettingsBtn = By.xpath("//*[@id=\"root\"]/div[1]/div/div[2]/div[3]/div/div/div/div[2]/div/span/button");

    private final By addBtnOfModalWindow = By.xpath("/html/body/div[3]/div[3]/div/div[3]/button[2]");

    private final By languageCheckbox = By.xpath("/html/body/div[3]/div[3]/div/div[2]/div[3]/div[1]");
    private final By languageBtn = By.xpath("//*[@id=\"root\"]/div[1]/div/div[2]/div[3]/div/div/div/div[1]/div[2]/div/button[5]");
    private final By defaultLanguageBtn = By.xpath("//*[@id=\"root\"]/div[1]/div/div[2]/div[3]/div/div/div/div[1]/div[2]/div/button[1]");

    private final By sport = By.xpath("//*[@id=\"root\"]/div[1]/div/div[1]/div[2]/div[1]/div[2]/div");
    private final By sportCheckbox = By.xpath("/html/body/div[1]/div/div[3]/div/div[1]/div/div[1]/div[2]/div[1]/div[2]/div/div[1]/span/input");
    private final By sportCountry = By.xpath("//*[@id=\"root\"]/div[1]/div/div[1]/div[2]/div[1]/div/div[2]/div/div/div[1]/div[1]");
    private final By sportChampionship = By.xpath("//*[@id=\"root\"]/div[1]/div/div[1]/div[2]/div[1]/div/div[2]/div/div/div[1]/div[2]/div/div/div");
    private final By sportDeleteBtn = By.xpath("/html/body/div[1]/div/div[3]/div/div[1]/div/div[1]/div[2]/div[1]/div[3]/span/button");

    private final By addSportMenu = By.xpath("//*[@id=\"root\"]/div[1]/div/div[1]/div[1]/div[1]");
    private final By addSportMenuApplyBtn = By.xpath("//*[@id=\"root\"]/div[1]/div/div[1]/div[1]/div[2]/div/div/div[3]/button[2]");
    private final By sportMenuCheckbox = By.xpath("//*[@id=\"root\"]/div[1]/div/div[1]/div[1]/div[2]/div/div/div[2]/div[3]");

    private final By sportEvent = By.xpath("/html/body/div[1]/div/div[3]/div/div[1]/div/div[2]/div[5]");
    private final By eventDeleteBtn = By.xpath("//*[@id=\"root\"]/div[1]/div/div[2]/div[5]/div/div[1]/div/span[3]/button");
    private final By sportEventAddBtn = By.xpath("//*[@id=\"root\"]/div[1]/div/div[2]/div[4]/div/div[2]/div[1]/button");
    private final By copyEventsBtn = By.xpath("//*[@id=\"root\"]/div[1]/div/div[2]/div[4]/div/div[2]/div[2]/button");

    private final By searchLanguageString = By.xpath("/html/body/div[3]/div[3]/div/div[1]/div/div/div/input");
    private final By searchLanguageBtn = By.xpath("/html/body/div[3]/div[3]/div/div[1]/div/div/div/div/span[2]/button");
    private final By searchLanguage = By.xpath("/html/body/div[3]/div[3]/div/div[2]/div[2]/div/div/p");

    public BackOfficePage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public void clickSkinManagement() {
        wait.until(ExpectedConditions.elementToBeClickable(skinManagement)).click();
    }

    public void clickHighlightsManager() {
        wait.until(ExpectedConditions.elementToBeClickable(highlightsManager)).click();
    }

    public void chooseConfigQA() {
        wait.until(ExpectedConditions.elementToBeClickable(configQA)).click();
    }

    public void clickEditLanguageBtn() {
        wait.until(ExpectedConditions.elementToBeClickable(editLanguageBtn)).click();
    }

    public void clickAddLanguageBtn() {
        wait.until(ExpectedConditions.elementToBeClickable(addLanguageBtn)).click();
    }

    public void clickDeleteLanguageBtn() {
        wait.until(ExpectedConditions.elementToBeClickable(deleteLanguageBtn)).click();
    }

    public void clickSaveLanguageSettingsBtn() {
        wait.until(ExpectedConditions.elementToBeClickable(saveLanguageSettingsBtn)).click();
    }

    public void clickAddBtnOfModalWindow() {
        driver.findElement(addBtnOfModalWindow).click();
    }

    public void clickSaveConfigBtn() {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(successMessage));
        wait.until(ExpectedConditions.elementToBeClickable(saveConfigBtn)).click();
    }

    public By getSuccessMessage() {
        return successMessage;
    }

    public By getLanguageBtn() {
        return languageBtn;
    }

    public void chooseLanguage() {
        wait.until(ExpectedConditions.elementToBeClickable(languageCheckbox)).click();
    }

    public void clickLanguageBtn() {
        wait.until(ExpectedConditions.elementToBeClickable(languageBtn)).click();
    }

    public void clickSport() {
        wait.until(ExpectedConditions.elementToBeClickable(sport)).click();
    }

    public void clickSportCheckbox() {
        driver.findElement(sportCheckbox).click();
    }

    public void clickDeleteSportBtn() {
        driver.findElement(sportDeleteBtn).click();
    }

    public void clickSportCountry() {
        wait.until(ExpectedConditions.elementToBeClickable(sportCountry)).click();
    }

    public void clickSportChampionship() {
        wait.until(ExpectedConditions.elementToBeClickable(sportChampionship)).click();
    }

    public void clickAddSportEvent() {
        wait.until(ExpectedConditions.elementToBeClickable(sportEventAddBtn)).click();
    }

    public void clickDeleteEventBtn() {
        wait.until(ExpectedConditions.elementToBeClickable(eventDeleteBtn)).click();
    }

    public void clickDefaultBtn() {
        wait.until(ExpectedConditions.elementToBeClickable(defaultLanguageBtn)).click();
    }

    public void clickCopyEventsBtn() {
        wait.until(ExpectedConditions.elementToBeClickable(copyEventsBtn)).click();
    }

    public By getSportEvent() {
        return sportEvent;
    }

    public By getSport() {
        return sport;
    }

    public void clickAddSportMenu() {
        wait.until(ExpectedConditions.elementToBeClickable(addSportMenu)).click();
    }

    public void clickAddSportMenuApplyBtn() {
        wait.until(ExpectedConditions.elementToBeClickable(addSportMenuApplyBtn)).click();
    }

    public void chooseSport() {
        wait.until(ExpectedConditions.elementToBeClickable(sportMenuCheckbox)).click();
    }

    public void enterTextIntoSearchLanguageString(String text) {
        wait.until(ExpectedConditions.elementToBeClickable(searchLanguageString)).sendKeys(text);
    }

    public void clickSearchLanguageBtn() {
        driver.findElement(searchLanguageBtn).click();
    }

    public String getNameOfSearchLanguage() {
        return driver.findElement(searchLanguage).getText();
    }
}
