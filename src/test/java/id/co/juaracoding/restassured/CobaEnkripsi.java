package id.co.juaracoding.restassured;

import id.co.juaracoding.restassured.util.RsaUtil;

public class CobaEnkripsi {
    public static void main(String[] args) {
        String password = RsaUtil.encrypt("Admin1@123");
        System.out.println("Setelah di Enkripsi : " + password);
    }
}