package LogIn;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LogInPage {
    private final WebDriver driver;

    private final By usernameInput = By.id("username_input");
    private final By passwordInput = By.id("password_input");
    private final By logInButton = By.id("login-button");
    private final By acceptCookieButton = By.id("accept-cookie-btn");

    public LogInPage(WebDriver driver) {
        this.driver = driver;
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

    public void clickLoginBtn() {
        driver.findElement(logInButton).click();
    }
}
