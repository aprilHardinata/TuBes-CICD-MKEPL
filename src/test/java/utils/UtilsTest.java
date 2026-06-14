package utils;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UtilsTest {

    @Test
    public void testFormatRupiah() {
        // Arrange
        double harga = 150000.0;

        // Act
        String hasil = Utils.formatRupiah(harga);

        // Assert
        // NumberFormat dengan locale id-ID pada Java 22 biasanya menghasilkan format seperti: "Rp 150.000,00" atau "Rp150.000,00"
        // Kita menggunakan assertTrue karena beberapa versi Java memiliki spasi yang berbeda (non-breaking space)
        assertTrue(hasil.contains("150.000"), "Format tidak mengandung angka 150.000");
        assertTrue(hasil.contains("Rp"), "Format tidak mengandung simbol Rp");
    }
}
