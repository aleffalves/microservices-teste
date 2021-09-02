package br.com.erudio.request.converters;

public class NumberConverter {

    public static Double convertToDouble(String strNumbers) {
        if (strNumbers == null) return 0D;
        String number = strNumbers.replaceAll(",", ".");
        if (isNumeric(number)) return Double.parseDouble(number);
        return 0D;
    }

    public static boolean isNumeric(String strNumbers) {
        if (strNumbers == null) return false;
        String number = strNumbers.replaceAll(",", ".");
        return number.matches("[-+]?[0-9]*\\.?[0-9]+");
    }
}
