package id.co.juaracoding.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
 
import java.time.Duration;
 
public class RegisterPage {
 
    private final WebDriver driver;
    private final WebDriverWait wait;
 
    private final By usernameInput = By.cssSelector("[data-testid='register-username-input']");
    private final By passwordInput = By.cssSelector("[data-testid='register-password-input'] input");
    private final By fullNameInput = By.cssSelector("[data-testid='register-full-name-input']");
    private final By emailInput = By.cssSelector("[data-testid='register-email-input']");
    private final By phoneInput = By.cssSelector("[data-testid='register-phone-number-input']");
    private final By addressInput = By.cssSelector("[data-testid='register-address-input']");
    private final By birthDateInput = By.cssSelector("[data-testid='register-birth-date-input'] input");
    private final By genderSelect = By.cssSelector("[data-testid='register-gender-input']");
    private final By idCardInput = By.cssSelector("[data-testid='register-id-card-number-input']");
    private final By taxIdInput = By.cssSelector("[data-testid='register-tax-id-number-input']");
    private final By captchaValueHint = By.cssSelector("[data-testid='register-captcha-value']");
    private final By captchaInput = By.cssSelector("[data-testid='register-captcha-input']");
    private final By submitButton = By.cssSelector("[data-testid='register-submit-button']");
    private final By chooseYearButton = By.cssSelector(".p-datepicker-select-year");
    private final By magicLink = By.cssSelector("[data-testid='check-email-magic-link']");
 
    public RegisterPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
 
    public void fillWajibFields(String username, String password, String fullName, String email,
                                 String phone, String address, String idCardNumber, String taxIdNumber) {
        driver.findElement(usernameInput).sendKeys(username);
        driver.findElement(passwordInput).sendKeys(password);
        driver.findElement(fullNameInput).sendKeys(fullName);
        driver.findElement(emailInput).sendKeys(email);
        driver.findElement(phoneInput).sendKeys(phone);
        driver.findElement(addressInput).sendKeys(address);
        driver.findElement(idCardInput).sendKeys(idCardNumber);
        driver.findElement(taxIdInput).sendKeys(taxIdNumber);
    }
 
    public void pilihJenisKelamin(String label) {
        driver.findElement(genderSelect).click();
        By option = By.xpath("//ul[@id='reg-gender_list']//li[@aria-label='" + label + "']");
        wait.until(ExpectedConditions.elementToBeClickable(option)).click();
    }
 
    public void pilihTanggalLahir2020Januari15() {
        driver.findElement(birthDateInput).click();
        wait.until(ExpectedConditions.elementToBeClickable(chooseYearButton)).click();
 
        By year2020 = By.xpath("//span[contains(@class,'p-datepicker-year') and normalize-space(text())='2020']");
        wait.until(ExpectedConditions.elementToBeClickable(year2020)).click();
 
        By monthJan = By.xpath("//span[contains(@class,'p-datepicker-month') and normalize-space(text())='Jan']");
        wait.until(ExpectedConditions.elementToBeClickable(monthJan)).click();
 
        By day15 = By.xpath("//td[@aria-label='15']");
        wait.until(ExpectedConditions.elementToBeClickable(day15)).click();
    }

    public void clickMagicLink() {
        wait.until(ExpectedConditions.elementToBeClickable(magicLink)).click();
    }
 
    public void isiCaptchaOtomatis() {
        String captchaValue = wait.until(ExpectedConditions.visibilityOfElementLocated(captchaValueHint)).getText();
        driver.findElement(captchaInput).sendKeys(captchaValue);
    }
 
    public void submit() {
        wait.until(ExpectedConditions.elementToBeClickable(submitButton)).click();
    }
}