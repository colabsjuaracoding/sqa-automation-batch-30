package id.co.juaracoding.selenium;

import id.co.juaracoding.selenium.pages.DashboardPage;
import id.co.juaracoding.selenium.pages.LoginPage;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
 
import java.time.Duration;
 
/**
 * mvn -B test "-Dtest=LoginTest -Dsurefire.suiteXmlFiles="
 * LoginTest
 */
public class LoginTest extends BaseSeleniumTest {
 
    @Test
    public void should_redirect_to_dashboard_when_login_valid() throws InterruptedException {
        bukaHalamanLogin();
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginAs("admin1", "Admin1@123");
 
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.urlContains("/dashboard"));
 
        Assert.assertTrue(driver.getCurrentUrl().contains("/dashboard"),
                "Setelah login valid, browser harus pindah ke /dashboard. URL aktual: " + driver.getCurrentUrl());
 
        DashboardPage dashboardPage = new DashboardPage(driver);
        Assert.assertTrue(dashboardPage.getLoggedInUserName().contains("Admin Satu"),
                "Nama user yang login harus tampil di navbar");
    }

/**
 * 🔴 Assertion HANYA ke ikon severity toast (data-testid="toast-icon-error"), ⛔ BUKAN ke teks
 * pesannya — assert kode/ikon, bukan teks pesan, diterapkan ke lapis UI: teks toast boleh
 * diperbaiki trainer kapan saja tanpa memecahkan test ini.
 * @throws InterruptedException 
 */
@Test
public void should_show_error_toast_when_password_wrong() throws InterruptedException {
    bukaHalamanLogin();
    LoginPage loginPage = new LoginPage(driver);
    loginPage.loginAs("admin1", "PasswordSalahBanget123!");
 
    Thread.sleep(2000);
    Assert.assertTrue(loginPage.isErrorToastShown(), "Toast error harus muncul saat password salah");
    Assert.assertTrue(driver.getCurrentUrl().contains("/login"),
            "Login gagal TIDAK boleh pindah dari /login. URL aktual: " + driver.getCurrentUrl());
}

}