package net.gridcraft.core.utils;

import java.text.DecimalFormat;

public class TextUtil {
    private static final DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");

    public static boolean isDouble(String s, boolean checkNegative) {
        try {
            double amount = Double.parseDouble(s);

            return !(checkNegative && amount < 0);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static String decimalFormat(double number) {
        if (number == 0)
            return "0.00";

        return decimalFormat.format(number);
    }
}
