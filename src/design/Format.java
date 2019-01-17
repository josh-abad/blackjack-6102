package design;

import java.text.DecimalFormat;

/**
 * A special currency formatter.
 * @author Joshua Abad
 */
public class Format {

    /**
     * Returns a string representation of a value in currency format.
     * 
     * <p>This is similar to an ordinary currency format, i.e. $9.99, $1,000.00,
     * except that it omits the currency symbol and empty decimal points. The 
     * earlier examples should then look like this: 9.99, 1,000.
     * 
     * @param price the value
     * @return a string representation in currency format
     */
    public static String currency(Double price) {
        return (withoutDecimal(price).indexOf(".") > 0) 
            ? withDecimal(price) : withoutDecimal(price);
    } 

    private static String withDecimal (Double price) {
        return new DecimalFormat("###,###,###.00").format(price);
    }

    private static String withoutDecimal (Double price) {
        return new DecimalFormat("###,###,###.##").format(price);
    }

    private Format() {
        // Do not instantiate
    }
}