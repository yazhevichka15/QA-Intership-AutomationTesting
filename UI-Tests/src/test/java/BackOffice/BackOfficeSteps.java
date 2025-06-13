package BackOffice;

import LoadConfig.ConfigLoader;
import LogIn.LogInPage;
import io.qameta.allure.Step;

public class BackOfficeSteps {
    @Step
    public static void LogInSystem(LogInPage logInPage) {
        String username = ConfigLoader.get("username");
        String password = ConfigLoader.get("password");

        logInPage.logIn(username, password);
    }
}
