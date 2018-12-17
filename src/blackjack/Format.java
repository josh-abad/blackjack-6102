package blackjack;

import java.text.DecimalFormat;

public class Format {
    public static String priceWithDecimal (Double price) {
        DecimalFormat formatter = new DecimalFormat("###,###,###.00");
        return formatter.format(price);
    }

    public static String priceWithoutDecimal (Double price) {
        DecimalFormat formatter = new DecimalFormat("###,###,###.##");
        return formatter.format(price);
    }

    public static String currency(Double price) {
        String toShow = priceWithoutDecimal(price);
        if (toShow.indexOf(".") > 0) {
            return priceWithDecimal(price);
        } else {
            return priceWithoutDecimal(price);
        }
    } 
}