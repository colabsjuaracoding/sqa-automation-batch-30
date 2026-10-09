package id.co.juaracoding.cucumber.web;

import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import id.co.juaracoding.selenium.pages.DashboardPage;
import id.co.juaracoding.selenium.pages.LicenseGatePage;
import id.co.juaracoding.selenium.pages.LoginPage;

import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

 
import java.time.Duration;
 
public class WebSteps {
 
    private static final String BASE_URL = "http://localhost:8080";
    private static final String LICENSE_KEY = "d461265dd7323fef9755bb3257275d67";
 
    private WebDriver driver;
 
    @Before
    public void bukaBrowser() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--window-size=1366,900");
        driver = new ChromeDriver(options);//12121
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
    }
 
    @After
    public void afterScenario(Scenario scenario) {
        if (scenario.isFailed() && driver != null) {
            // Ambil screenshot langsung sebagai byte[] agar bisa di-embed ke Extent Report
            byte[] screenshotBytes = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshotBytes, "image/png", "Screenshot on failure");

            // Simpan juga ke file PNG sebagai arsip
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            File dest = new File("target/screenshots/" + scenario.getName().replaceAll("\\s+", "_") + "_" + timestamp + ".png");
            dest.getParentFile().mkdirs();
            try {
                FileUtils.writeByteArrayToFile(dest, screenshotBytes);
                System.out.println("Saved screenshot: " + dest.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (driver != null) driver.quit();
    }
 
    @Given("aplikasi Simple Apps sudah menyala di halaman login")
    public void aplikasiSudahMenyalaDiHalamanLogin() {
        driver.get(BASE_URL + "/login");
        LicenseGatePage licenseGatePage = new LicenseGatePage(driver);
        if (licenseGatePage.isDisplayed()) {
            licenseGatePage.activate(LICENSE_KEY);
            driver.get(BASE_URL + "/login");   // gerbang Lisensi selalu mendarat di /login (setup §8.2)
        }
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.urlContains("/login"));
    }
 
    @When("saya login sebagai {string} dengan password {string}")
    public void sayaLoginSebagaiDenganPassword(String username, String password) throws InterruptedException {

        new LoginPage(driver).loginAs(username, password);
    }
 
    @Then("browser harus pindah ke halaman dashboard")
    public void browserHarusPindahKeHalamanDashboard() {
        // int intX = 1/0;
        // System.out.println(intX);
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.urlContains("/dashboard"));
        Assert.assertTrue(driver.getCurrentUrl().contains("/dashboardz"));
    }
 
    @And("nama user yang login mengandung {string}")
    public void namaUserYangLoginMengandung(String namaUser) {
        Assert.assertTrue(new DashboardPage(driver).getLoggedInUserName().contains(namaUser));
    }
 
    // INI SUDAH DIBUAT SEBELUMNYA, JADI TIDAK PERLU DITULIS ULANG !!
    @Then("toast error harus muncul di halaman login")
    public void toastErrorHarusMuncul() {
        Assert.assertTrue(new LoginPage(driver).isErrorToastShown());
    }
 
    @And("browser tetap berada di halaman login")
    public void browserTetapBeradaDiHalamanLogin() {
        Assert.assertTrue(driver.getCurrentUrl().contains("/login"));
    }
}