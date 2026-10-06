package id.co.juaracoding.restassured;

import id.co.juaracoding.restassured.util.RsaUtil;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class LoginApiTest extends BaseRestAssuredTest {

    JSONObject json;

    @BeforeClass
    public void setup() {
        json = new JSONObject();
    }

    @Test
    public void should_return_200_and_token_when_login_valid() {
        json.clear();
        String username = "admin1";
        json.put("username", username);
        json.put("password", RsaUtil.encrypt("Admin1@123"));
        String[] captcha = ambilCaptcha();
        json.put("captcha_answer", captcha[1]);
        json.put("captcha_hash", captcha[0]);

        Response response = specDasar().body(json).
        // request("post","/api/v1/login").
                post("/api/v1/login");
        JsonPath path = response.jsonPath();
        System.out.println("===========RESPONSE DARI SERVER=================");
        response.prettyPrint();
        // response.then()
        // .statusCode(200)
        // .body("status", org.hamcrest.Matchers.equalTo("SUCCESS"))
        // .body("data.token", org.hamcrest.Matchers.notNullValue())
        // .body("data.user.username", org.hamcrest.Matchers.equalTo("admin1"))
        // .body("data.user.role", org.hamcrest.Matchers.equalTo("ADMIN"));
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(path.get("status"), "SUCCESS");
        Assert.assertNotNull(path.get("data.token"));
        Assert.assertNotNull(path.get("data.user"));
        Assert.assertEquals(path.get("data.user.username"), username);
        Assert.assertEquals(path.get("data.user.role"), "ADMIN");
    }

    @Test
    public void should_return_401_API_ECMXS40107_when_password_salah() {
        json.clear();
        String username = "admin1";
        json.put("username", username);
        json.put("password", RsaUtil.encrypt("PasswordSalah123!"));
        String[] captcha = ambilCaptcha();
        json.put("captcha_answer", captcha[1]);
        json.put("captcha_hash", captcha[0]);

        Response response = specDasar().body(json).post("/api/v1/login");

        System.out.println("===========RESPONSE DARI SERVER=================");
        response.prettyPrint();

        response.then()
                .statusCode(401)
                .body("status", org.hamcrest.Matchers.equalTo("ERROR"))
                .body("error_code", org.hamcrest.Matchers.equalTo("API-ECMXS40107"));
        // ⛔ TIDAK meng-assert "message" — teksnya boleh berubah kapan saja tanpa
        // memecahkan test ini.
    }

    @Test
    public void should_return_401_API_ECMXS40107_when_captcha_salah() {
        String[] captcha = ambilCaptcha();
        String ciphertext = RsaUtil.encrypt("Admin1@123");

        Response response = specDasar().body(String.format(
                "{\"username\":\"admin1\",\"password\":\"%s\",\"captcha_answer\":\"salahterus\",\"captcha_hash\":\"%s\"}",
                ciphertext, captcha[0])).post("/api/v1/login");

        System.out.println("===========RESPONSE DARI SERVER=================");
        response.prettyPrint();

        response.then()
                .statusCode(401)
                .body("error_code", org.hamcrest.Matchers.equalTo("API-ECMXS40107"));
    }

    @Test
    public void should_return_401_API_ECMXS40105_when_x_api_key_kosong() {
        Response response = given()
                .contentType("application/json")
                .body("{\"username\":\"admin1\",\"password\":\"x\",\"captcha_answer\":\"x\",\"captcha_hash\":\"x\"}")
                .post(BASE_URL + "/api/v1/login");

        System.out.println("===========RESPONSE DARI SERVER=================");
        response.prettyPrint();

        response.then()
                .statusCode(401)
                .body("error_code", org.hamcrest.Matchers.equalTo("API-ECMXS40105"));
    }

}