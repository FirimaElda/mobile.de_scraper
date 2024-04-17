package util;

import model.CarListing;

import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CarListingParser {

    private CarListingParser() {
    }

    public static void parseYearKmKw(CarListing carListing, String input) {
        SimpleDateFormat format = new SimpleDateFormat("'EZ' MM/yyyy");
        Pattern pattern = Pattern.compile("(EZ \\d{2}/\\d{4}) • ((\\d{1,3}\\.\\d{3}) km) • ((\\d{1,3}) kW)");
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            String yearString = matcher.group(1);
            String kmString = matcher.group(2);
            String kmToIntString = matcher.group(3);
            String kWString = matcher.group(4);
            String kWToIntString = matcher.group(5);
            carListing.setYearString(yearString);
            carListing.setKilometresString(kmString);
            carListing.setKilometres(Integer.parseInt(kmToIntString.replace(".", "")));
            carListing.setKilowattsString(kWString);
            carListing.setKilowatts(Integer.parseInt(kWToIntString));

            try {
                carListing.setYear(format.parse(yearString));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void parsePrice(CarListing carListing, String input) {
        Pattern pattern = Pattern.compile("(\\d{1,3}\\.\\d{3}) €");
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            String priceString = matcher.group(1);
            double price = Double.parseDouble(priceString.replace(".", ""));
            carListing.setPriceString(priceString);
            carListing.setPrice(price);
        }
    }
}
