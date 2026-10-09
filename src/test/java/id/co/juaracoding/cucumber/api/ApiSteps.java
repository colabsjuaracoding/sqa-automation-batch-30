package id.co.juaracoding.cucumber.api;

import id.co.juaracoding.restassured.util.RsaUtil;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
 
import static io.restassured.RestAssured.given;
 
public class ApiSteps {
 
    private static final String BASE_URL = "http://localhost:8080";
    private static final String X_API_KEY = "d461265dd7323fef9755bb3257275d67";
 
    private String captchaHash;
    private String captchaValue;
    private String token;
    private Response responseLogin;
    private Response responseKategori;
 
    @Before
    public void aturBaseUri() {
        RestAssured.baseURI = BASE_URL;
    }
 
    // ---------- Login API ----------
 
    @Given("saya sudah mengambil captcha dari API")
    public void sayaSudahMengambilCaptchaDariApi() {
        Response captchaResponse = given().header("X-API-KEY", X_API_KEY).get("/api/v1/captcha");
        captchaHash = captchaResponse.jsonPath().getString("data.captcha_hash");
        captchaValue = captchaResponse.jsonPath().getString("data.captcha_value");
    }
 
    @When("saya kirim login API dengan username {string} dan password {string}")
    public void sayaKirimLoginApi(String username, String password) {
        String ciphertext = RsaUtil.encrypt(password);
        responseLogin = given()
                .header("X-API-KEY", X_API_KEY)
                .contentType("application/json")
                .body(String.format(
                        "{\"username\":\"%s\",\"password\":\"%s\",\"captcha_answer\":\"%s\",\"captcha_hash\":\"%s\"}",
                        username, ciphertext, captchaValue, captchaHash))
                .post("/api/v1/login");
    }
 
    // @Then("response API login memiliki status code {int}")
    // public void responseApiLoginMemilikiStatusCode(int statusCode) {
    //     Assert.assertEquals(responseLogin.statusCode(), statusCode);
    // }
    @Then("response API {string} memiliki status code {int}")
    public void responseApiMemilikiStatusCode(String kategori,int statusCode) {
        if (kategori.equalsIgnoreCase("login")) {
            Assert.assertEquals(responseLogin.statusCode(), statusCode);
        } else if (kategori.equalsIgnoreCase("kategori")) {
            Assert.assertEquals(responseKategori.statusCode(), statusCode);
        }
    }
 
    @And("response API login memiliki token yang tidak kosong")
    public void responseApiLoginMemilikiTokenYangTidakKosong() {
        Assert.assertNotNull(responseLogin.jsonPath().getString("data.token"));
    }
 
    @And("response API {string} memiliki error_code {string}")
    public void responseApiMemilikiErrorCode(String kategori,String errorCode) {
        // ⛔ Assert ke error_code SAJA, BUKAN teks "message".
        Assert.assertEquals(responseLogin.jsonPath().getString("error_code"), errorCode);        
    }
 
    // ---------- Data Master API ----------
 
    @Given("saya login API sebagai {string} dengan password {string}")
    public void sayaLoginApiSebagaiDenganPassword(String username, String password) {
        Response captchaResponse = given().header("X-API-KEY", X_API_KEY).get("/api/v1/captcha");
        String hash = captchaResponse.jsonPath().getString("data.captcha_hash");
        String value = captchaResponse.jsonPath().getString("data.captcha_value");
        String ciphertext = RsaUtil.encrypt(password);
 
        Response response = given()
                .header("X-API-KEY", X_API_KEY)
                .contentType("application/json")
                .body(String.format(
                        "{\"username\":\"%s\",\"password\":\"%s\",\"captcha_answer\":\"%s\",\"captcha_hash\":\"%s\"}",
                        username, ciphertext, value, hash))
                .post("/api/v1/login");
 
        token = response.jsonPath().getString("data.token");
    }
 
    @When("saya membuat kategori produk baru lewat API")
    public void sayaMembuatKategoriProdukBaruLewatApi() {
        String namaUnik = "Kategori Cucumber " + System.currentTimeMillis();
        responseKategori = given()
                .header("X-API-KEY", X_API_KEY)
                .header("Authorization", "Bearer " + token)
                .contentType("application/json")
                .body("{\"category_name\": \"" + namaUnik + "\"}")
                .post("/api/v1/product-categories");
    }

 
    // @Then("response API kategori memiliki status code {int}")
    // public void responseApiKategoriMemilikiStatusCode(int statusCode) {
    //     Assert.assertEquals(responseKategori.statusCode(), statusCode);
    // }
 
    // @And("response API kategori memiliki error_code {string}")
    // public void responseApiKategoriMemilikiErrorCode(String errorCode) {
    //     Assert.assertEquals(responseKategori.jsonPath().getString("error_code"), errorCode);
    // }
}
