package id.co.juaracoding.restassured;

import id.co.juaracoding.restassured.util.RsaUtil;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;

import static io.restassured.RestAssured.given;

public class BaseRestAssuredTest {

    public static final String BASE_URL = "http://localhost:8080";
    public static final String X_API_KEY = "d461265dd7323fef9755bb3257275d67";
    private JSONObject jsonObject;

    @BeforeClass
    public void setUpBaseUri() {
        RestAssured.baseURI = BASE_URL;
        jsonObject = new JSONObject();
    }

    protected RequestSpecification specDasar() {
        return given()
                .header("X-API-KEY", X_API_KEY)
                .contentType("application/json");
    }

    protected RequestSpecification specDenganToken(String token) {
        return specDasar().header("Authorization", "Bearer " + token);
    }

    protected String[] ambilCaptcha() {
        Response response = specDasar().get("/api/v1/captcha");
        String hash = response.jsonPath().getString("data.captcha_hash");
        String value = response.jsonPath().getString("data.captcha_value");
        return new String[] { hash, value };
    }

    protected String loginDapatkanToken(String username, String plainPassword) {
        String[] captcha = ambilCaptcha();
        String ciphertext = RsaUtil.encrypt(plainPassword);
        jsonObject.put("username", username);
        jsonObject.put("password", ciphertext);
        jsonObject.put("captcha_hash", captcha[0]);
        jsonObject.put("captcha_answer", captcha[1]);

        Response response = specDasar().body(jsonObject).post("/api/v1/login");
        JsonPath path = response.jsonPath();
        String token = path.getString("data.token");
        String usernamez = path.getString("data.user.username");
        String userFullName = path.getString("data.user.full_name");

        // path.getString("status");// SUCCESS
        // path.getInt("code");// 200
        // path.getString("message");// Login berhasil
        if (token == null) {
            throw new IllegalStateException("Login gagal untuk " + username + " — response: " + response.asString());
        }
        return token;
    }
}
