package id.co.juaracoding.restassured.util;

public class GlobalUtil {

    public static boolean validasiFormatUrl(String url) {
        if (url.startsWith("http://localhost:8080/reset-password?token=")
                || url.startsWith("https://localhost:8080/reset-password?token=")) {
            return true;
        } else {
            return false;
        }
    }
}
