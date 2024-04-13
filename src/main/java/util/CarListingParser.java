package util;

import model.CarListing;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CarListingParser {

    private CarListingParser() {
    }

    public static CarListing parseDetails(CarListing carListing, String input) {
        Pattern pattern = Pattern.compile("(EZ \\d{2}/\\d{4}) • (\\d{1,3}\\.\\d{3} km) • (\\d{1,3} kW)");
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            String yearString = matcher.group(1);
            String kmString = matcher.group(2);
            String kWString = matcher.group(3);
            System.out.println(input);
            System.out.println("Parsing details...");
            System.out.println(yearString);
            System.out.println(kmString);
            System.out.println(kWString);
            carListing.setYearString(yearString);
            carListing.setKilometresString(kmString);
            carListing.setKilowattsString(kWString);
            return carListing;
        }
        return null;
    }
}
