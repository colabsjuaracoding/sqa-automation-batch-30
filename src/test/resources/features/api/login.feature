# mvn -B test "-Dtest=CucumberWebRunner,CucumberApiRunner" "-Dsurefire.suiteXmlFiles="
# mvn -B test "-Dtest=CucumberWebRunner,CucumberApiRunner" "-Dcucumber.filter.tags=@smoke" "-Dsurefire.suiteXmlFiles="
Feature: Login API
  Sebagai automation engineer, saya ingin menguji endpoint POST /api/v1/login langsung lewat REST API.
  Membungkus id.co.juaracoding.restassured.LoginApiTest (pertemuan 3, Unit 4) dalam bentuk Gherkin.

  Background:
    Given saya sudah mengambil captcha dari API

  @smoke
  Scenario: Login API berhasil dengan akun Admin
    When saya kirim login API dengan username "admin1" dan password "Admin1@123"
    Then response API "login" memiliki status code 200
    And response API login memiliki token yang tidak kosong

  @negatif
  Scenario Outline: Login API gagal dan mengembalikan error_code yang benar
    When saya kirim login API dengan username "<username>" dan password "<password>"
    Then response API "login" memiliki status code <status_code>
    And response API "login" memiliki error_code "<error_code>"

    Examples:
      | username | password        | status_code | error_code     |
      | admin1   | PasswordSalah@1 |         401 | API-ECMXS40107 |
      | tidakada | Apapun@123      |         401 | API-ECMXS40107 |
