package id.co.juaracoding.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class CobaDoang {

    public static void main(String[] args) throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get("http://localhost:8080");
        Thread.sleep(5000);
        System.out.println("Judul halaman: " + driver.getTitle());
        driver.quit();
    }
}
