package BackOffice;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BackOfficePage {
    private WebDriver driver;
    private WebDriverWait wait;

    private By skinManagement = By.xpath("//*[@id=\"sidebar-menu\"]/div/ul/li[2]/a");
    private By highlightsManager = By.xpath("//*[@id=\"sidebar-menu\"]/div/ul/li[2]/ul/li[1]/a");
    private By configQA = By.xpath("//*[@id=\"root\"]/div[1]/div/div[2]/div/div");

    public BackOfficePage(WebDriver driver) {
        this.driver = driver;
        driver.manage().window().maximize();
    }

    public void clickSkinManagement() {
        driver.findElement(skinManagement).click();
    }

    public void clickHighlightsManager() {
        driver.findElement(highlightsManager).click();
    }

    public void chooseConfig() {
        driver.findElement(configQA).click();
    }

    public void startTest() throws InterruptedException {
        clickSkinManagement();
        Thread.sleep(2000);
        clickHighlightsManager();
        Thread.sleep(2000);
        chooseConfig();
    }
}
