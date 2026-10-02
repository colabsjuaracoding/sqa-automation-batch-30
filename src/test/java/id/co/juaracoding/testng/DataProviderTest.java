package id.co.juaracoding.testng;

import id.co.juaracoding.latihan.Kalkulator;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DataProviderTest {

    private final Kalkulator kalkulator = new Kalkulator();

    @DataProvider(name = "dataPenjumlahan")
    public Object[][] dataPenjumlahan() {
        return new Object[][] {
                { 1, 1, 2 },
                { 5, 3, 8 },
                { 10, 20, 30 },
                { -4, 4, 0 }
        };
    }

    @Test(dataProvider = "dataPenjumlahan")
    public void should_return_jumlah_benar_untuk_tiap_pasangan(Integer a, Integer b, Integer diharapkan) {
        Assert.assertEquals(kalkulator.tambah(a, b), diharapkan,
                "Penjumlahan " + a + " + " + b + " salah");
    }

    @DataProvider(name = "dataGenapGanjil")
    public Object[][] dataGenapGanjil() {
        return new Object[][] {
                { 2, true },
                { 3, false },
                { 100, true },
                { 77, false }
        };
    }

    @Test(dataProvider = "dataGenapGanjil")
    public void should_deteksi_genap_dengan_benar(Integer angka, Boolean diharapkan) {
        Assert.assertEquals(kalkulator.apakahGenap(angka), diharapkan,
                "Pemeriksaan genap untuk angka " + angka + " salah");
    }
}