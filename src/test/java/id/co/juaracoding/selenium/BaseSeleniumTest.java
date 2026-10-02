package id.co.juaracoding.selenium;

import id.co.juaracoding.selenium.pages.LicenseGatePage;
import id.co.juaracoding.selenium.pages.LoginPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;

public class BaseSeleniumTest {

    public static final String BASE_URL = "http://localhost:8080";
    public static final String LICENSE_KEY = "d461265dd7323fef9755bb3257275d67";

    protected WebDriver driver;

    @BeforeMethod
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--window-size=1366,900");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    protected void bukaHalaman(String path) {
        driver.get(BASE_URL + path);
        LicenseGatePage licenseGatePage = new LicenseGatePage(driver);
        if (licenseGatePage.isDisplayed()) {
            licenseGatePage.activate(LICENSE_KEY);
            driver.get(BASE_URL + path); // lihat jebakan §8.2
        }
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.urlContains(path));
    }

    protected void bukaHalamanLogin() {
        bukaHalaman("/login");
    }

    protected void loginSebagai(String username, String password) throws InterruptedException {
        bukaHalamanLogin();
        new LoginPage(driver).loginAs(username, password);
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.urlContains("/dashboard"));
    }
}