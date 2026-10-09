# mvn -B test -Dtest=CucumberWebRunner "-Dsurefire.suiteXmlFiles="
Feature: Login Web
  Sebagai user Simple Apps, saya ingin login lewat halaman Web supaya saya bisa masuk ke Dashboard.
  Membungkus id.co.juaracoding.selenium.LoginTest (pertemuan 2, Unit 3) dalam bentuk Gherkin.

  Background:
    Given aplikasi Simple Apps sudah menyala di halaman login

  @smoke
  Scenario: Login berhasil dengan akun Admin
    When saya login sebagai "admin1" dengan password "Admin1@123"
    Then browser harus pindah ke halaman dashboard
    And nama user yang login mengandung "Admin Satu"

  @negatif
  Scenario Outline: Login gagal karena kredensial salah
    When saya login sebagai "<username>" dengan password "<password>"
    Then toast error harus muncul di halaman login
    And browser tetap berada di halaman login
# DATA DRIVEN

    Examples:
      | username | password                |
      | admin1   | PasswordSalahBanget123! |
      | admin1   | SalahLagiInii999!       |
