package id.co.juaracoding.restassured;

import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

public class ForgotPasswordApiTest extends BaseRestAssuredTest {

    @Test
    public void should_return_200_and_magic_link_when_email_terdaftar() {
        String[] captcha = ambilCaptcha();

        Response response = specDasar().body(String.format(
                "{\"email\":\"customer1@simpleapps.test\",\"captcha_answer\":\"%s\",\"captcha_hash\":\"%s\"}",
                captcha[1], captcha[0])).post("/api/v1/forgot-password");

        response.then()
                .statusCode(200)
                .body("status", equalTo("SUCCESS"))
                .body("data.magic_link", notNullValue());
    }

    @Test
    public void should_return_200_juga_when_email_tidak_terdaftar() {
        String[] captcha = ambilCaptcha();

        Response response = specDasar().body(String.format(
                "{\"email\":\"tidak-pernah-daftar-%d@simpleapps.test\",\"captcha_answer\":\"%s\",\"captcha_hash\":\"%s\"}",
                System.currentTimeMillis(), captcha[1], captcha[0])).post("/api/v1/forgot-password");

        // 🔴 TETAP 200 — server TIDAK BOLEH membedakan email terdaftar vs tidak.
        response.then()
                .statusCode(200)
                .body("status", equalTo("SUCCESS"))
                .body("data.magic_link", nullValue());
    }
}