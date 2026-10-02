package id.co.juaracoding.latihan;

public class Kalkulator {
    // public Integer tambah(Integer a, Integer b) {
    // if (a == null || b == null) {
    // return null;
    // }
    // return a + b;
    // }

    public Integer tambah(Integer a, Integer b) {
        return a + b;
    }

    public Integer kurang(Integer a, Integer b) {
        return a - b;
    }

    public Integer kali(Integer a, Integer b) {
        return a * b;
    }

    public Integer bagi(Integer a, Integer b) {
        // 5/0
        if (b == 0) {
            throw new ArithmeticException("Pembagi tidak boleh nol");
        }
        return a / b;
    }

    public Boolean apakahGenap(Integer angka) {
        return angka % 2 == 0;
    }

}
