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

    private final By editLanguageBtn = By.xpath("/html/body/div[1]/div/div[3]/div/div[1]/div/div[2]/div[3]/div/div/div/div[2]/span/button");
    private final By deleteLanguageBtn = By.xpath("//*[@id=\"root\"]/div[1]/div/div[2]/div[3]/div/div/div/div[2]/div/button[1]");
    private final By addLanguageBtn = By.xpath("//*[@id=\"root\"]/div[1]/div/div[2]/div[3]/div/div/div/div[2]/div/button[2]");
    private final By saveLanguageSettingsBtn = By.xpath("//*[@id=\"root\"]/div[1]/div/div[2]/div[3]/div/div/div/div[2]/div/span/button");

    private final By languageModalWindow = By.xpath("/html/body/div[3]/div[3]");
    private final By addBtnOfModalWindow = By.xpath("/html/body/div[3]/div[3]/div/div[3]/button[2]");

    private final By afrikaansLanguageCheckbox = By.xpath("/html/body/div[3]/div[3]/div/div[2]/div[3]/div[1]");
    private final By afrikaansLanguageBtn = By.xpath("//*[@id=\"root\"]/div[1]/div/div[2]/div[3]/div/div/div/div[1]/div[2]/div/button[9]");

    private final By saveConfigBtn = By.xpath("//*[@id=\"root\"]/div[1]/div/div[2]/div[1]/button");
    private final By successMessage = By.xpath("//*[@id=\"root\"]/div[2]");

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

    public void chooseAfrikaansLanguage() {
        wait.until(ExpectedConditions.elementToBeClickable(afrikaansLanguageCheckbox)).click();
    }

    public void clickAddBtnOfModalWindow() {
        driver.findElement(addBtnOfModalWindow).click();
    }

    public void clickSaveLanguageSettingsBtn() {
        wait.until(ExpectedConditions.elementToBeClickable(saveLanguageSettingsBtn)).click();
    }

    public void clickSaveConfigBtn() {
        driver.findElement(saveConfigBtn).click();
    }

    public By getAfrikaansLanguageBtn() {
        return afrikaansLanguageBtn;
    }

    public void clickAfrikaansLanguageBtn() {
        wait.until(ExpectedConditions.elementToBeClickable(afrikaansLanguageBtn)).click();
    }

    public By getSuccessMessage() {
        return successMessage;
    }

}
