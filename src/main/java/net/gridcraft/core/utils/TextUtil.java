package net.gridcraft.core.utils;

import java.text.DecimalFormat;

public class TextUtil {
    private static final DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");

    public static boolean isDouble(String s, boolean checkNegative) {
        try {
            double amount = Double.parseDouble(s);

            if (checkNegative && amount < 0) return false;

            return true;
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
