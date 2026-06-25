package utils;

import akun.ManajerAkun;
import exception.LoginException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pengguna.PembeliPenjual;
import pengguna.Pengguna;
import produk.Produk;

import static org.junit.jupiter.api.Assertions.*;

public class UtilsTest {

    private ManajerAkun manajerAkun;
    private Produk produk;
    private PembeliPenjual user;

    @BeforeEach
    public void setUp() {
        manajerAkun = new ManajerAkun();
        user = new PembeliPenjual("budi", "budi123", "budi@gmail.com", "0812345678");
        PembeliPenjual penjual = new PembeliPenjual("penjual1", "pass123", "penjual@gmail.com", "081122334455");
        produk = new Produk("P001", "Laptop Gaming", 15000000.0, 10, "Laptop kencang", "Bandung", penjual);
    }

    // 1. Unit Test Bawaan (Utils)
    @Test
    public void testFormatRupiah() {
        // Arrange
        double harga = 150000.0;

        // Act
        String hasil = Utils.formatRupiah(harga);

        // Assert
        assertTrue(hasil.contains("150.000"), "Format tidak mengandung angka 150.000");
        assertTrue(hasil.contains("Rp"), "Format tidak mengandung simbol Rp");
    }

    // 2. Unit Test Login Sukses (ManajerAkun)
    @Test
    public void testLoginSukses() throws LoginException {
        // Act
        Pengguna loggedInUser = manajerAkun.login("user1", "user1");

        // Assert
        assertNotNull(loggedInUser, "User seharusnya tidak null ketika login berhasil");
        assertEquals("user1", loggedInUser.getUsername(), "Username harus sesuai");
    }

    // 3. Unit Test Login Gagal - SKENARIO ERROR (ManajerAkun)
    @Test
    public void testLoginGagal_SkenarioError() {
        // Assert & Act
        Exception exception = assertThrows(LoginException.class, () -> {
            manajerAkun.login("user1", "salahpassword");
        }, "Seharusnya melempar LoginException saat password salah");

        assertEquals("Username atau password salah.", exception.getMessage());
    }

    // 4. Unit Test Kurangi Stok Sukses (Produk)
    @Test
    public void testKurangiStokSukses() {
        // Act
        produk.kurangiStok(3);

        // Assert
        assertEquals(7, produk.getStok(), "Stok harus berkurang dari 10 menjadi 7");
    }

    // 5. Unit Test Kurangi Stok Melebihi Batas - SKENARIO ERROR (Produk)
    @Test
    public void testKurangiStokMelebihiStok_SkenarioError() {
        // Assert & Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            produk.kurangiStok(15);
        }, "Seharusnya melempar IllegalArgumentException saat mengurangi stok melebihi ketersediaan");

        assertEquals("Stok tidak mencukupi.", exception.getMessage());
    }

    // 6. Unit Test Tambah Ke Keranjang (PembeliPenjual)
    @Test
    public void testTambahKeKeranjang() {
        // Act
        user.tambahKeKeranjang(produk, 2);

        // Assert
        assertEquals(1, user.getKeranjang().size(), "Keranjang harus berisi 1 jenis produk");
        assertEquals(2, user.getKeranjang().get(produk), "Jumlah produk harus 2");
    }
}
