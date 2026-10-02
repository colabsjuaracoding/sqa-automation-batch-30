package id.co.juaracoding.testng;

import id.co.juaracoding.latihan.Kalkulator;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PrioritasTest {

    private final Kalkulator kalkulator = new Kalkulator();

    @Test(priority = 1)
    public void langkah_1_login() {
        System.out.println("[priority=1] langkah_1_login");
        Assert.assertTrue(true);
    }

    @Test(priority = 2, dependsOnMethods = "langkah_1_login")
    public void langkah_2_buka_dashboard() {
        System.out.println("[priority=2] langkah_2_buka_dashboard");
        Assert.assertEquals(kalkulator.tambah(2, 2), Integer.valueOf(4));
    }

    @Test(priority = 3, enabled = false)
    public void langkah_3_belum_siap_jadi_dimatikan() {
        Assert.fail("Method ini TIDAK dijalankan karena enabled = false");
    }

    @Test(expectedExceptions = ArithmeticException.class)
    public void should_throw_exception_when_bagi_dengan_nol() {
        kalkulator.bagi(10, 0);
    }
}