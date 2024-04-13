import model.CarListing;
import scraper.Scraper;

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
        for (CarListing carListing : carListings) {
            System.out.println(carListing.toStringUsingStringAttributes());
        }
    }
}