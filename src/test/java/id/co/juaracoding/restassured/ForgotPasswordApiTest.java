package id.co.juaracoding.restassured;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.Assert;

import id.co.juaracoding.restassured.util.GlobalUtil;
import id.co.juaracoding.restassured.util.RsaUtil;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

import org.json.simple.JSONObject;

public class ForgotPasswordApiTest extends BaseRestAssuredTest {

        String token = "";
        String password = "";
        JSONObject json = new JSONObject();
        String username = "customer1";

        @Test(priority = 0)
        public void should_return_200_and_magic_link_when_email_terdaftar() {
                String[] captcha = ambilCaptcha();

                Response response = specDasar().body(String.format(
                                "{\"email\":\"customer1@simpleapps.test\",\"captcha_answer\":\"%s\",\"captcha_hash\":\"%s\"}",
                                captcha[1], captcha[0])).post("/api/v1/forgot-password");

                System.out.println("=================RESPONSE SERVER=================");
                response.prettyPrint();

                JsonPath path = response.jsonPath();
                response.then()
                                .statusCode(200)
                                .body("status", equalTo("SUCCESS"));

                String magicLink = path.getString("data.magic_link");
                // http://localhost:8080/reset-password?token=87GN5llnoZk2s8QrM0HaBWWr5VOzV7juF-dLZXbFYzA
                token = magicLink.replace("http://localhost:8080/reset-password?token=", "");
                System.out.println("NILAI TOKEN : " + token);
                Assert.assertTrue(GlobalUtil.validasiFormatUrl(magicLink),
                                "Format magic_link tidak sesuai dengan yang diharapkan");

        }

        @Test(priority = 5)
        public void ganti_password_dengan_magic_link() {
                password = "Paul@" + String.valueOf(System.currentTimeMillis()).substring(0, 5);
                json.clear();
                json.put("email", "customer1@simpleapps.test");
                json.put("password", password);
                json.put("confirm_password", password);
                json.put("token", token);

                Response response = specDasar().body(json).post("/api/v1/reset-password");

                // http://localhost:8080/api/v1/reset-password
                // {
                // "email": "customer1@simpleapps.test",
                // "password": "PaulPaul@123",
                // "confirm_password": "PaulPaul@123",
                // "token": "87GN5llnoZk2s8QrM0HaBWWr5VOzV7juF-dLZXbFYzA"
                // }
                System.out.println("=================RESPONSE SERVER GANTI PASSWORD=================");
                response.prettyPrint();

                response.then()
                                .statusCode(200)
                                .body("status", equalTo("SUCCESS"))
                                .body("message", equalTo("Kata sandi berhasil diubah, silakan login"));
        }

        @Test(priority = 10)
        public void login_setelah_password_berhasil_diganti() {
                String[] captcha = ambilCaptcha();
                json.clear();
                json.put("username", username);
                json.put("password", RsaUtil.encrypt(password));
                json.put("captcha_hash", captcha[0]);
                json.put("captcha_answer", captcha[1]);

                Response response = specDasar().body(json).post("/api/v1/login");

                System.out.println("=================RESPONSE SERVER LOGIN=================");
                response.prettyPrint();

                response.then()
                                .statusCode(200)
                                .body("status", equalTo("SUCCESS"))
                                .body("data.token", notNullValue());
        }

        // @Test(priority = 30)
        // public void should_return_400_jika_captcha_salah() {
        // String[] captcha = ambilCaptcha();

        // Response response = specDasar().body(String.format(
        // "{"email":"customer1@simpleapps.test","captcha_answer":"salah","captcha_hash":"%s"}",
        // captcha[0])).post("/api/v1/forgot-password");

        // System.out.println("=================RESPONSE SERVER=================");
        // response.prettyPrint();

        // response.then()
        // .statusCode(400)
        // .body("status", equalTo("FAILED"))
        // .body("message", equalTo("Captcha is incorrect."));
        // }

        // @Test(priority = 40)
        // public void should_return_400_jika_email_kosong() {
        // String[] captcha = ambilCaptcha();

        // Response response = specDasar().body(String.format(
        // "{\"email\":\"\",\"captcha_answer\":\"%s\",\"captcha_hash\":\"%s\"}",
        // captcha[1], captcha[0])).post("/api/v1/forgot-password");

        // System.out.println("=================RESPONSE SERVER=================");
        // response.prettyPrint();

        // response.then()
        // .statusCode(400)
        // .body("status", equalTo("FAILED"))
        // .body("message", equalTo("Email must be a valid email address."));
        // }

        @Test
        public void should_return_200_juga_when_email_tidak_terdaftar() {
                String[] captcha = ambilCaptcha();

                Response response = specDasar().body(String.format(
                                "{\"email\":\"tidak-pernah-daftar-%d@simpleapps.test\",\"captcha_answer\":\"%s\",\"captcha_hash\":\"%s\"}",
                                System.currentTimeMillis(), captcha[1], captcha[0])).post("/api/v1/forgot-password");

                System.out.println("=================RESPONSE SERVER=================");
                response.prettyPrint();
                // 🔴 TETAP 200 — server TIDAK BOLEH membedakan email terdaftar vs tidak.
                response.then()
                                .statusCode(200)
                                .body("status", equalTo("SUCCESS"))
                                .body("data.magic_link", nullValue());
        }
}