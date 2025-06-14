package BackOffice;

import LoadConfig.ConfigLoader;
import LogIn.LogInPage;
import io.qameta.allure.Step;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BackOfficeTest {
    private WebDriver driver;
    private WebDriverWait wait;

    private LogInPage logInPage;
    private BackOfficePage backOfficePage;

    @BeforeEach
    @Step("")
    void setUp() {
        //Для отладки - из-за нестандартного расширения экрана
        ChromeOptions options = new ChromeOptions();
        options.addArguments("force-device-scale-factor=1");

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.get("https://sb2admin-altenar2-stage.biahosted.com/Account/Login?ReturnUrl=%2F");

        wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        logInPage = new LogInPage(driver);
        backOfficePage = new BackOfficePage(driver, wait);
    }

    @BeforeEach
    @Step("")
    void startSettings() {
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
        backOfficePage.clickEditLanguageBtn();
        backOfficePage.clickAddLanguageBtn();
        backOfficePage.chooseAfrikaansLanguage();
        backOfficePage.clickAddBtnOfModalWindow();
        backOfficePage.clickSaveLanguageSettingsBtn();
        backOfficePage.clickSaveConfigBtn();

        assertThat("",
                isSuccessMessageDisplayed(), is(true));

        assertThat("",
                isAfrikaansLanguageDisplayed(), is(true));
    }

//    @Test
//    @DisplayName("")
//    void testAddLanguageWithInvalidCount() {
//    }




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
    private boolean isAfrikaansLanguageDisplayed() {
        try {
            return wait
                    .until(ExpectedConditions.visibilityOfElementLocated(backOfficePage.getAfrikaansLanguageBtn()))
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
}
