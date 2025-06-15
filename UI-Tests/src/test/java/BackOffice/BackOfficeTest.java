package BackOffice;

import LoadConfig.ConfigLoader;
import LogIn.LogInPage;

import io.qameta.allure.Step;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class BackOfficeTest {
    private WebDriver driver;
    private WebDriverWait wait;

    private LogInPage logInPage;
    private BackOfficePage backOfficePage;

    @BeforeEach
    @Step("Initialize WebDriver, pages and set start settings before each test")
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("force-device-scale-factor=1");

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.get("https://sb2admin-altenar2-stage.biahosted.com/Account/Login?ReturnUrl=%2F");

        wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        logInPage = new LogInPage(driver);
        backOfficePage = new BackOfficePage(driver, wait);

        logInSystem();
        goToConfigQA();
    }

    @AfterEach
    @Step("Сlose all browser tabs after each test")
    void tearDown() {
        driver.quit();
    }

    @Test
    @DisplayName("Adding a language should add it to the Language Customization")
    void testAddLanguage() {
        addTestLanguage();

        assertThat("Success message should be displayed",
                isSuccessMessageDisplayed(), is(true));

        assertThat("Language to be added should be displayed",
                isLanguageDisplayed(), is(true));
    }

    @Test
    @DisplayName("Deleting the language should hide it from the Language Customization")
    void testDeleteLanguage() {
        addTestLanguage();
        deleteTestLanguage();

        assertThat("Success message should be displayed",
                isSuccessMessageDisplayed(), is(true));

        assertThat("Language to be deleted should be hidden",
                isLanguageDisplayed(), is(false));
    }

    @Test
    @DisplayName("Adding an event should add it to the language's events")
    void testAddEventUsingHighlightManager() {
        addTestLanguage();
        addTestSport();

        addTestSportEvent();
        backOfficePage.clickSaveConfigBtn();

        assertThat("Event to be added should be displayed",
                isSportEventDisplayed(), is(true));
    }

    @Test
    @DisplayName("Copying event from default language's events should add it to the selected language's events")
    void testAddEventUsingCopyEventsBtn() {
        addTestSport();
        addTestSportEventToDefault();
        addTestLanguage();

        backOfficePage.clickCopyEventsBtn();
        backOfficePage.clickSaveConfigBtn();

        assertThat("Event to be added should be displayed",
                isSportEventDisplayed(), is(true));
    }

    @Test
    @DisplayName("Deleting the event should hide it from the language's events")
    void testDeleteEventUsingHighlightsManager() {
        addTestLanguage();
        addTestSport();
        addTestSportEvent();

        backOfficePage.clickDeleteEventBtn();
        backOfficePage.clickSaveConfigBtn();

        assertThat("Event to be deleted should be hidden",
                isSportEventNotDisplayed(), is(true));
    }

    @Test
    @DisplayName("Adding a sport should add it to the available sports")
    void testAddSport() {
        addTestSport();
        backOfficePage.clickSaveConfigBtn();

        assertThat("Sport to be added should be displayed",
                isSportDisplayed(), is(true));
    }

    @Test
    @DisplayName("Deleting the sport should hide it from the available sports")
    void testDeleteSport() {
        addTestSport();

        backOfficePage.clickSportCheckbox();
        backOfficePage.clickDeleteSportBtn();
        backOfficePage.clickSaveConfigBtn();

        assertThat("Sport to be deleted should be hidden",
                isSportDisplayed(), is(false));
    }

    @Test
    @DisplayName("Entering a language into the search string should display an item with that language")
    void testAddSportSearchingString() {
        backOfficePage.clickEditLanguageBtn();
        backOfficePage.clickAddLanguageBtn();
        backOfficePage.enterTextIntoSearchLanguageString("norwegian");
        backOfficePage.clickSearchLanguageBtn();

        assertThat("Item with the language to be entered should be displayed",
                backOfficePage.getNameOfSearchLanguage(), equalTo("Norwegian"));
    }

    @Step("Log in Highlights Manager using username and password from configuration")
    private void logInSystem() {
        String username = ConfigLoader.get("username");
        String password = ConfigLoader.get("password");

        logInPage.acceptCookies();
        logInPage.enterUsername(username);
        logInPage.enterPassword(password);
        logInPage.clickLoginBtn();
    }

    @Step("Select and go to the test configuration")
    private void goToConfigQA() {
        backOfficePage.clickSkinManagement();
        backOfficePage.clickHighlightsManager();
        backOfficePage.chooseConfigQA();
    }

    @Step("Add the first language to Language Customization")
    private void addTestLanguage() {
        backOfficePage.clickEditLanguageBtn();
        backOfficePage.clickAddLanguageBtn();
        backOfficePage.chooseLanguage();
        backOfficePage.clickAddBtnOfModalWindow();
        backOfficePage.clickSaveLanguageSettingsBtn();
        backOfficePage.clickSaveConfigBtn();
        backOfficePage.clickLanguageBtn();
    }

    @Step("Delete the language from Language Customization")
    private void deleteTestLanguage() {
        backOfficePage.clickEditLanguageBtn();
        backOfficePage.clickDeleteLanguageBtn();
        backOfficePage.chooseLanguage();
        backOfficePage.clickAddBtnOfModalWindow();
        backOfficePage.clickSaveLanguageSettingsBtn();
        backOfficePage.clickSaveConfigBtn();
    }

    @Step("Add a sport to available sports")
    private void addTestSport() {
        backOfficePage.clickAddSportMenu();
        backOfficePage.chooseSport();
        backOfficePage.clickAddSportMenuApplyBtn();
    }

    @Step("Add a first sport event from sport")
    private void addTestSportEvent() {
        backOfficePage.clickSport();
        backOfficePage.clickSportCountry();
        backOfficePage.clickSportChampionship();
        backOfficePage.clickAddSportEvent();
    }

    @Step("Add a first sport event from sport to default language")
    private void addTestSportEventToDefault() {
        backOfficePage.clickDefaultBtn();
        addTestSportEvent();
    }

    @Step("Check the language's displaying in the Language Customization")
    private boolean isLanguageDisplayed() {
        try {
            return wait
                    .until(ExpectedConditions.visibilityOfElementLocated(backOfficePage.getLanguageBtn()))
                    .isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    @Step("Check the success message's displaying in the top right corner")
    private boolean isSuccessMessageDisplayed() {
        try {
            return wait
                    .until(ExpectedConditions.visibilityOfElementLocated(backOfficePage.getSuccessMessage()))
                    .isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    @Step("Check the sport's displaying in the available sports")
    private boolean isSportDisplayed() {
        try {
            return wait
                    .until(ExpectedConditions.visibilityOfElementLocated(backOfficePage.getSport()))
                    .isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    @Step("Check the sport event's displaying in the language's events")
    private boolean isSportEventDisplayed() {
        try {
            return wait
                    .until(ExpectedConditions.visibilityOfElementLocated(backOfficePage.getSportEvent()))
                    .isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    @Step("Check the disappearance of a sport event from the language events")
    private boolean isSportEventNotDisplayed() {
        try {
            return wait.until(ExpectedConditions.invisibilityOfElementLocated(backOfficePage.getSportEvent()));
        } catch (TimeoutException e) {
            return false;
        }
    }
}
