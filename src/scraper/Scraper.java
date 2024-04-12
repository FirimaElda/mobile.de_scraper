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
    private Properties selectors;

    public Scraper(String url) {
        this.url = url;
        this.selectors = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            selectors.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
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

            // Find the car listings using the CSS selector from the properties file
            List<WebElement> listings = driver.findElements(By.cssSelector(selectors.getProperty("carListingSelector")));

            // Iterate over each listing
            for (WebElement listing : listings) {
                // Extract the car details using the CSS selectors from the properties file
                String make = listing.findElement(By.cssSelector(selectors.getProperty("makeSelector"))).getText();
                String model = listing.findElement(By.cssSelector(selectors.getProperty("modelSelector"))).getText();
                int year = Integer.parseInt(listing.findElement(By.cssSelector(selectors.getProperty("yearSelector"))).getText());
                double price = Double.parseDouble(listing.findElement(By.cssSelector(selectors.getProperty("priceSelector"))).getText());

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