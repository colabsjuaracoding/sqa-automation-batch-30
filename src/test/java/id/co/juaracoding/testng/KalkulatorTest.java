package id.co.juaracoding.testng;

import id.co.juaracoding.latihan.Kalkulator;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class KalkulatorTest {

    private Kalkulator kalkulator;

    @BeforeClass
    public void setUp() {
        kalkulator = new Kalkulator();
    }

    @Test(priority = 0)
    public void one_should_return_8_when_tambah_5_dan_3() {
        Integer hasil = kalkulator.tambah(5, 3);
        Assert.assertEquals(hasil, Integer.valueOf(8), "Hasil penjumlahan 5 dan 3 harusnya 8");
    }

    @Test(priority = 5)
    public void two_should_return_true_when_angka_genap() {
        Assert.assertTrue(kalkulator.apakahGenap(10), "10 seharusnya genap");
    }

    @Test(priority = 10)
    public void three_should_return_false_when_angka_ganjil() {
        Assert.assertFalse(kalkulator.apakahGenap(7), "7 seharusnya tidak genap");
    }

    @Test(priority = 15)
    public void four_should_not_be_null_when_kali_dijalankan() {
        Assert.assertNotNull(kalkulator.kali(4, 5));
    }

    @Test(priority = 1000)
    public void five_should_be_20_when_multiply_4_and_5() {
        Assert.assertEquals(kalkulator.kali(4, 5), 20, "Seharusnya 20");
    }

}