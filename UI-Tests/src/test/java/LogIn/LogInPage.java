package LogIn;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LogInPage {
    private WebDriver driver;

    private By usernameInput = By.id("username_input");
    private By passwordInput = By.id("password_input");
    private By logInButton = By.id("login-button");
    private By acceptCookieButton = By.id("accept-cookie-btn");

    public LogInPage(WebDriver driver) {
        this.driver = driver;
        driver.manage().window().maximize();
        driver.get("https://sb2admin-altenar2-stage.biahosted.com/Account/Login?ReturnUrl=%2F");
    }

    public void acceptCookies() {
        driver.findElement(acceptCookieButton).click();
    }

    public void enterUsername(String username) {
        driver.findElement(usernameInput).sendKeys(username);
    }

    public void enterPassword(String password) {
        driver.findElement(passwordInput).sendKeys(password);
    }

    public void clickLogin() {
        driver.findElement(logInButton).click();
    }

    public void logIn(String username, String password) {
        acceptCookies();
        enterUsername(username);
        enterPassword(password);
        clickLogin();
    }
}
