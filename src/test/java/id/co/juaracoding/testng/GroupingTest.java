package id.co.juaracoding.testng;

import id.co.juaracoding.latihan.Kalkulator;
import org.testng.Assert;
import org.testng.annotations.Test;

public class GroupingTest {

    private final Kalkulator kalkulator = new Kalkulator();

    @Test(groups = "smoke")
    public void smoke_penjumlahan_dasar() {
        System.out.println("[group=smoke] smoke_penjumlahan_dasar");
        Assert.assertEquals(kalkulator.tambah(2, 3), Integer.valueOf(5));
    }

    @Test(groups = "smoke")
    public void smoke_pengurangan_dasar() {
        System.out.println("[group=smoke] smoke_pengurangan_dasar");
        Assert.assertEquals(kalkulator.kurang(9, 3), Integer.valueOf(6));
    }

    @Test(groups = { "regression", "e2e" })
    public void regression_perkalian_angka_besar() {
        System.out.println("[group=regression] regression_perkalian_angka_besar");
        Assert.assertEquals(kalkulator.kali(1000, 1000), Integer.valueOf(1_000_000));
    }

    @Test(groups = { "smoke", "regression" })
    public void milik_dua_group_sekaligus() {
        System.out.println("[group=smoke+regression] milik_dua_group_sekaligus");
        Assert.assertEquals(kalkulator.bagi(100, 4), Integer.valueOf(25));
    }
}
