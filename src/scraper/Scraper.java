package src.scraper;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import src.model.CarListing;

import java.util.ArrayList;
import java.util.List;

public class Scraper {

    private String url;

    public Scraper(String url) {
        this.url = url;
    }

    public List<CarListing> scrape() {
        List<CarListing> carListings = new ArrayList<>();

        // Set Firefox to run in headless mode
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--headless");

        // Create a new instance of the Firefox driver
        WebDriver driver = new FirefoxDriver(options);

        try {
            // Navigate to the car listings page
            driver.get(url);

            // Find the car listings (replace with actual CSS selector)
            List<WebElement> listings = driver.findElements(By.cssSelector(".car-listing"));

            // Iterate over each listing
            for (WebElement listing : listings) {
                // Extract the car details (replace with actual CSS selectors)
                String make = listing.findElement(By.cssSelector(".make")).getText();
                String model = listing.findElement(By.cssSelector(".model")).getText();
                int year = Integer.parseInt(listing.findElement(By.cssSelector(".year")).getText());
                double price = Double.parseDouble(listing.findElement(By.cssSelector(".price")).getText());

                // Create a new CarListing object and add it to the list
                CarListing carListing = new CarListing(make, model, year, price);
                carListings.add(carListing);
            }
        } finally {
            // Close the browser
            driver.quit();
        }

        return carListings;
    }
}