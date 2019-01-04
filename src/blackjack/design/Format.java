package blackjack.design;

import java.text.DecimalFormat;

public class Format {

    public static String currency(Double price) {
        return (withoutDecimal(price).indexOf(".") > 0) 
            ? withDecimal(price) : withoutDecimal(price);
    } 

    private Format() {
        // Do not instantiate
    }

    private static String withDecimal (Double price) {
        return new DecimalFormat("###,###,###.00").format(price);
    }

    private static String withoutDecimal (Double price) {
        return new DecimalFormat("###,###,###.##").format(price);
    }
}