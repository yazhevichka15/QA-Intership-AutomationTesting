package BackOffice;

import LogIn.LogInPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static BackOffice.BackOfficeSteps.LogInSystem;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class BackOfficeTest {
    private WebDriver driver;
    BackOfficePage backOfficePage;
    LogInPage logInPage;

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        logInPage = new LogInPage(driver);
        backOfficePage = new BackOfficePage(driver);

        LogInSystem(logInPage);
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void ProbaTest() throws InterruptedException {
        Thread.sleep(5000);
        backOfficePage.startTest();
        Thread.sleep(5000);
        assertThat("", 1, is(1));
    }
}
