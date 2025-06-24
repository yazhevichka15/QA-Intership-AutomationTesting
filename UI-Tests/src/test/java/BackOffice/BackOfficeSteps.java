package BackOffice;

import LoadConfig.ConfigLoader;
import LogIn.LogInPage;
import io.qameta.allure.Step;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BackOfficeSteps {
    private final WebDriverWait wait;
    private final LogInPage logInPage;
    private final BackOfficePage backOfficePage;

    private final ConfigLoader configLoader = new ConfigLoader();

    public BackOfficeSteps(WebDriverWait wait, LogInPage logInPage, BackOfficePage backOfficePage) {
        this.wait = wait;
        this.logInPage = logInPage;
        this.backOfficePage = backOfficePage;
    }

    @Step("Log in Highlights Manager using username and password from configuration")
    public void logInSystem() {
        String username = configLoader.getUsername();
        String password = configLoader.getPassword();

        logInPage.acceptCookies();
        logInPage.enterUsername(username);
        logInPage.enterPassword(password);
        logInPage.clickLoginBtn();
    }

    @Step("Select and go to the test configuration")
    public void goToConfigQA() {
        backOfficePage.clickSkinManagement();
        backOfficePage.clickHighlightsManager();
        backOfficePage.chooseConfigQA();
    }

    @Step("Add the first language to Language Customization")
    public void addTestLanguage() {
        backOfficePage.clickEditLanguageBtn();
        backOfficePage.clickAddLanguageBtn();
        backOfficePage.chooseLanguage();
        backOfficePage.clickAddBtnOfModalWindow();
        backOfficePage.clickSaveLanguageSettingsBtn();
        backOfficePage.clickSaveConfigBtn();
        backOfficePage.clickLanguageBtn();
    }

    @Step("Delete the language from Language Customization")
    public void deleteTestLanguage() {
        backOfficePage.clickEditLanguageBtn();
        backOfficePage.clickDeleteLanguageBtn();
        backOfficePage.chooseLanguage();
        backOfficePage.clickAddBtnOfModalWindow();
        backOfficePage.clickSaveLanguageSettingsBtn();
        backOfficePage.clickSaveConfigBtn();
    }

    @Step("Add a sport to available sports")
    public void addTestSport() {
        backOfficePage.clickAddSportMenu();
        backOfficePage.chooseSport();
        backOfficePage.clickAddSportMenuApplyBtn();
    }

    @Step("Add a first sport event from sport")
    public void addTestSportEvent() {
        backOfficePage.clickSport();
        backOfficePage.clickSportCountry();
        backOfficePage.clickSportChampionship();
        backOfficePage.clickAddSportEvent();
    }

    @Step("Add a first sport event from sport to default language")
    public void addTestSportEventToDefault() {
        backOfficePage.clickDefaultBtn();
        addTestSportEvent();
    }

    @Step("Check the language's displaying in the Language Customization")
    public boolean isLanguageDisplayed() {
        try {
            return wait
                    .until(ExpectedConditions.visibilityOfElementLocated(backOfficePage.getLanguageBtn()))
                    .isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    @Step("Check the success message's displaying in the top right corner")
    public boolean isSuccessMessageDisplayed() {
        try {
            return wait
                    .until(ExpectedConditions.visibilityOfElementLocated(backOfficePage.getSuccessMessage()))
                    .isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    @Step("Check the sport's displaying in the available sports")
    public boolean isSportDisplayed() {
        try {
            return wait
                    .until(ExpectedConditions.visibilityOfElementLocated(backOfficePage.getSport()))
                    .isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    @Step("Check the sport event's displaying in the language's events")
    public boolean isSportEventDisplayed() {
        try {
            return wait
                    .until(ExpectedConditions.visibilityOfElementLocated(backOfficePage.getSportEvent()))
                    .isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    @Step("Check the disappearance of a sport event from the language events")
    public boolean isSportEventNotDisplayed() {
        try {
            return wait.until(ExpectedConditions.invisibilityOfElementLocated(backOfficePage.getSportEvent()));
        } catch (TimeoutException e) {
            return false;
        }
    }
}
