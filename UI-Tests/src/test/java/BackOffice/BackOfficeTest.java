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
    @Step("")
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
    @Step("")
    void tearDown() {
        driver.quit();
    }

    @Test
    @DisplayName("")
    void testAddLanguage() {
        addTestLanguage();

        assertThat("",
                isSuccessMessageDisplayed(), is(true));

        assertThat("",
                isLanguageDisplayed(), is(true));
    }

    @Test
    @DisplayName("")
    void testDeleteLanguage() {
        addTestLanguage();
        deleteTestLanguage();

        assertThat("",
                isSuccessMessageDisplayed(), is(true));

        assertThat("",
                isLanguageDisplayed(), is(false));
    }

    @Test
    @DisplayName("")
    void testAddEventUsingHighlightManager() {
        addTestLanguage();
        addTestSport();

        addTestSportEvent();
        backOfficePage.clickSaveConfigBtn();

        assertThat("",
                isSportEventDisplayed(), is(true));
    }

    @Test
    @DisplayName("")
    void testAddEventUsingCopyEventsBtn() {
        addTestSport();
        addTestSportEventToDefault();
        addTestLanguage();

        backOfficePage.clickCopyEventsBtn();
        backOfficePage.clickSaveConfigBtn();

        assertThat("",
                isSportEventDisplayed(), is(true));
    }

    @Test
    @DisplayName("")
    void testDeleteEventUsingHighlightsManager() {
        addTestLanguage();
        addTestSport();
        addTestSportEvent();

        backOfficePage.clickDeleteEventBtn();
        backOfficePage.clickSaveConfigBtn();

        assertThat("",
                isSportEventNotDisplayed(), is(true));
    }

    @Test
    @DisplayName("")
    void testAddSport() {
        addTestSport();
        backOfficePage.clickSaveConfigBtn();

        assertThat("",
                isSportDisplayed(), is(true));
    }

    @Test
    @DisplayName("")
    void testDeleteSport() {
        addTestSport();

        backOfficePage.clickSportCheckbox();
        backOfficePage.clickDeleteSportBtn();
        backOfficePage.clickSaveConfigBtn();

        assertThat("",
                isSportDisplayed(), is(false));
    }

    @Test
    @DisplayName("")
    void testAddSportSearchingString() throws InterruptedException {
        backOfficePage.clickEditLanguageBtn();
        backOfficePage.clickAddLanguageBtn();
        backOfficePage.enterTextIntoSearchLanguageString("norwegian");
        backOfficePage.clickSearchLanguageBtn();

        assertThat("",
                backOfficePage.getNameOfSearchLanguage(), equalTo("Norwegian"));
    }


    @Step("")
    private void logInSystem() {
        String username = ConfigLoader.get("username");
        String password = ConfigLoader.get("password");

        logInPage.acceptCookies();
        logInPage.enterUsername(username);
        logInPage.enterPassword(password);
        logInPage.clickLoginBtn();
    }

    @Step("")
    private void goToConfigQA() {
        backOfficePage.clickSkinManagement();
        backOfficePage.clickHighlightsManager();
        backOfficePage.chooseConfigQA();
    }

    @Step("")
    private void addTestLanguage() {
        backOfficePage.clickEditLanguageBtn();
        backOfficePage.clickAddLanguageBtn();
        backOfficePage.chooseLanguage();
        backOfficePage.clickAddBtnOfModalWindow();
        backOfficePage.clickSaveLanguageSettingsBtn();
        backOfficePage.clickSaveConfigBtn();
        backOfficePage.clickLanguageBtn();
    }

    @Step("")
    private void deleteTestLanguage() {
        backOfficePage.clickEditLanguageBtn();
        backOfficePage.clickDeleteLanguageBtn();
        backOfficePage.chooseLanguage();
        backOfficePage.clickAddBtnOfModalWindow();
        backOfficePage.clickSaveLanguageSettingsBtn();
        backOfficePage.clickSaveConfigBtn();
    }

    @Step("")
    private void addTestSport() {
        backOfficePage.clickAddSportMenu();
        backOfficePage.chooseSport();
        backOfficePage.clickAddSportMenuApplyBtn();
    }



    @Step("")
    private void addTestSportEvent() {
        backOfficePage.clickSport();
        backOfficePage.clickSportCountry();
        backOfficePage.clickSportChampionship();
        backOfficePage.clickAddSportEvent();
    }

    @Step("")
    private void addTestSportEventToDefault() {
        backOfficePage.clickDefaultBtn();
        addTestSportEvent();
    }

    @Step("")
    private boolean isLanguageDisplayed() {
        try {
            return wait
                    .until(ExpectedConditions.visibilityOfElementLocated(backOfficePage.getLanguageBtn()))
                    .isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    @Step("")
    private boolean isSuccessMessageDisplayed() {
        try {
            return wait
                    .until(ExpectedConditions.visibilityOfElementLocated(backOfficePage.getSuccessMessage()))
                    .isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    @Step("")
    private boolean isSportDisplayed() {
        try {
            return wait
                    .until(ExpectedConditions.visibilityOfElementLocated(backOfficePage.getSport()))
                    .isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    @Step("")
    private boolean isSportEventDisplayed() {
        try {
            return wait
                    .until(ExpectedConditions.visibilityOfElementLocated(backOfficePage.getSportEvent()))
                    .isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    @Step("")
    private boolean isSportEventNotDisplayed() {
        try {
            return wait.until(ExpectedConditions.invisibilityOfElementLocated(backOfficePage.getSportEvent()));
        } catch (TimeoutException e) {
            return false;
        }
    }
}
