package id.co.juaracoding.selenium;

import id.co.juaracoding.selenium.pages.RegisterPage;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import id.co.juaracoding.selenium.pages.LoginPage;
import id.co.juaracoding.selenium.pages.DashboardPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.time.Duration;

public class RegistrasiTest extends BaseSeleniumTest {

        long now = System.currentTimeMillis();// 02102026205614.222
        String username = String.format("selenium%06d", now % 1_000_000L);
        String password = "Selenium1@123";

        @Test(priority = 0)
        public void should_redirect_to_check_email_when_registrasi_valid() throws InterruptedException {
                bukaHalaman("/register");

                // Data unik per run (username/email/No.HP/KTP/NPWP semuanya UNIQUE di database)
                // —
                // dibangun dari jam saat ini, supaya test ini BOLEH dijalankan berkali-kali
                // tanpa bentrok.

                String email = username + "@example.com";
                String phoneNumber = String.format("0812%08d", now % 100_000_000L);
                String idCardNumber = String.format("%016d", now);
                String taxIdNumber = String.format("%016d", now + 1);
                System.out.println("USERNAME : " + username + " -- PASSWORD : " + password + " --EMAIL : " + email);

                RegisterPage registerPage = new RegisterPage(driver);
                registerPage.fillWajibFields(username, password, "Selenium Uji Coba", email,
                                phoneNumber, "Jl. Uji Coba Selenium No. 1", idCardNumber, taxIdNumber);
                registerPage.pilihJenisKelamin("Pria");
                registerPage.pilihTanggalLahir2020Januari15();
                registerPage.isiCaptchaOtomatis();
                registerPage.submit();

                new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.urlContains("/check-email"));
                Assert.assertTrue(driver.getCurrentUrl().contains("/check-email"),
                                "Registrasi sukses harus mengarahkan ke /check-email. URL aktual: "
                                                + driver.getCurrentUrl());
                
                registerPage.clickMagicLink();
                new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.urlContains("/activate"));
        }

        @Test(priority = 5)
        public void lanjut_ke_login() throws InterruptedException {
                bukaHalamanLogin();
                LoginPage loginPage = new LoginPage(driver);
                loginPage.loginAs(username, password);

                new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.urlContains("/dashboard"));

                Assert.assertTrue(driver.getCurrentUrl().contains("/dashboard"),
                                "Setelah login valid, browser harus pindah ke /dashboard. URL aktual: "
                                                + driver.getCurrentUrl());

                DashboardPage dashboardPage = new DashboardPage(driver);
                Assert.assertTrue(dashboardPage.getLoggedInUserName().contains("Selenium Uji Coba"),
                                "Nama user yang login harus tampil di navbar");
        }

}