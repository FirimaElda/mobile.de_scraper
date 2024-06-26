import model.CarListing;
import scraper.Scraper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class ScraperApp {
    public static void main(String[] args) {
        // Check if a URL was provided
        if (args.length < 1) {
            System.out.println("Please provide a URL as a command-line argument.");
            return;
        }

        // Get the URL from the command-line arguments
        String url = args[0];

        // Create a new Scraper object
        Scraper scraper = new Scraper(url);

        // Scrape the car listings
        List<CarListing> carListings = scraper.scrape();

        // Print the car listings
        System.out.println("Found " + carListings.size() + " car listings:");
        for (CarListing carListing : carListings) {
            System.out.println(carListing.toStringUsingStringAttributes());
            System.out.println(carListing);
        }

        System.out.println("Average price: " + calculateCarListingAveragePrice(carListings) + " €");
        calculateCarListingAveragePrice(carListings);
    }

    private static BigDecimal calculateCarListingAveragePrice(List<CarListing> carListings) {
        double averagePrice = carListings.stream()
                .mapToDouble(CarListing::getPrice)
                .average()
                .orElse(0);
        BigDecimal averagePriceBigDecimal = BigDecimal.valueOf(averagePrice);
        return averagePriceBigDecimal.setScale(2, RoundingMode.HALF_UP);
    }
}