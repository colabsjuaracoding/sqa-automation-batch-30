package id.co.juaracoding.testng;

import id.co.juaracoding.latihan.Kalkulator;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class SiklusHidupTest {

    private Kalkulator kalkulator;

    @BeforeClass
    public void siapkanSekaliUntukSeluruhKelas() {
        System.out.println("[@BeforeClass] dijalankan SEKALI sebelum semua @Test di kelas ini");
    }

    @BeforeMethod
    public void siapkanSebelumTiapTest() {
        kalkulator = new Kalkulator();
        System.out.println("[@BeforeMethod] objek Kalkulator dibuat baru");
    }

    @Test(priority = 0)
    public void test_pertama() {
        System.out.println("[@Test] test_pertama");
        Assert.assertEquals(kalkulator.tambah(1, 1), Integer.valueOf(2));
    }

    @Test(priority = 5)
    public void test_kedua() {
        System.out.println("[@Test] test_kedua");
        Assert.assertEquals(kalkulator.kurang(9, 4), Integer.valueOf(5));
    }

    @AfterMethod
    public void bersihkanSetelahTiapTest() {
        kalkulator = null;
        System.out.println("[@AfterMethod] objek Kalkulator dibuang");
    }

    @AfterClass
    public void bersihkanSekaliUntukSeluruhKelas() {
        System.out.println("[@AfterClass] dijalankan SEKALI setelah semua @Test di kelas ini");
    }
}