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
    private BackOfficeSteps steps;

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
        steps = new BackOfficeSteps(wait, logInPage, backOfficePage);

        steps.logInSystem();
        steps.goToConfigQA();
    }

    @AfterEach
    @Step("Сlose all browser tabs after each test")
    void tearDown() {
        driver.quit();
    }

    @Test
    @DisplayName("Adding a language should add it to the Language Customization")
    void testAddLanguage() {
        steps.addTestLanguage();

        assertThat("Success message should be displayed",
                steps.isSuccessMessageDisplayed(), is(true));

        assertThat("Language to be added should be displayed",
                steps.isLanguageDisplayed(), is(true));
    }

    @Test
    @DisplayName("Deleting the language should hide it from the Language Customization")
    void testDeleteLanguage() {
        steps.addTestLanguage();
        steps.deleteTestLanguage();

        assertThat("Success message should be displayed",
                steps.isSuccessMessageDisplayed(), is(true));

        assertThat("Language to be deleted should be hidden",
                steps.isLanguageDisplayed(), is(false));
    }

    @Test
    @DisplayName("Adding an event should add it to the language's events")
    void testAddEventUsingHighlightManager() {
        steps.addTestLanguage();
        steps.addTestSport();

        steps.addTestSportEvent();
        backOfficePage.clickSaveConfigBtn();

        assertThat("Event to be added should be displayed",
                steps.isSportEventDisplayed(), is(true));
    }

    @Test
    @DisplayName("Copying event from default language's events should add it to the selected language's events")
    void testAddEventUsingCopyEventsBtn() {
        steps.addTestSport();
        steps.addTestSportEventToDefault();
        steps.addTestLanguage();

        backOfficePage.clickCopyEventsBtn();
        backOfficePage.clickSaveConfigBtn();

        assertThat("Event to be added should be displayed",
                steps.isSportEventDisplayed(), is(true));
    }

    @Test
    @DisplayName("Deleting the event should hide it from the language's events")
    void testDeleteEventUsingHighlightsManager() {
        steps.addTestLanguage();
        steps.addTestSport();
        steps.addTestSportEvent();

        backOfficePage.clickDeleteEventBtn();
        backOfficePage.clickSaveConfigBtn();

        assertThat("Event to be deleted should be hidden",
                steps.isSportEventNotDisplayed(), is(true));
    }

    @Test
    @DisplayName("Adding a sport should add it to the available sports")
    void testAddSport() {
        steps.addTestSport();
        backOfficePage.clickSaveConfigBtn();

        assertThat("Sport to be added should be displayed",
                steps.isSportDisplayed(), is(true));
    }

    @Test
    @DisplayName("Deleting the sport should hide it from the available sports")
    void testDeleteSport() {
        steps.addTestSport();

        backOfficePage.clickSportCheckbox();
        backOfficePage.clickDeleteSportBtn();
        backOfficePage.clickSaveConfigBtn();

        assertThat("Sport to be deleted should be hidden",
                steps.isSportDisplayed(), is(false));
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
}
