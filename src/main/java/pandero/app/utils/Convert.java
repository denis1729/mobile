package pandero.app.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * La clase Convert es un utilitario que nos facilita conversiones
 *
 * @author Denis Camacho Camacho
 * @since 12/21/2021
 */
public class Convert {
    private Convert() {
    }

    public static double redondeo(double numero, int digitos) {
        return BigDecimal.valueOf(numero).setScale(digitos, RoundingMode.HALF_UP).doubleValue();
    }

    public static String toString(int data) {
        return String.valueOf(data);
    }

    public static String toString(double data) {
        return String.valueOf(data);
    }

    public static double toDouble(String data) {
        return Double.parseDouble(data);
    }

    public static int toInt(String data) {
        return Integer.parseInt(data);
    }

    public static String numberFormatUS(double number) {
        return NumberFormat.getCurrencyInstance(Locale.US).format(number);
    }
}
