package scraper;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import model.CarListing;
import util.CarListingParser;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Properties;

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

            // Fluent Wait
            Wait<WebDriver> wait = new FluentWait<>(driver)
                    .withTimeout(Duration.ofSeconds(30))
                    .pollingEvery(Duration.ofSeconds(5))
                    .ignoring(NoSuchElementException.class);

            // Find the car listings using the CSS selector from the properties file
            List<WebElement> listings = wait.until(d -> {
                List<WebElement> elements = d.findElements(By.cssSelector(selectors.getProperty("carListingSelector")));
                return !elements.isEmpty() ? elements : null;
            });

            // Iterate over each listing
            for (WebElement listing : listings) {
                // Extract the car details using the CSS selectors from the properties file
                String title = listing.findElement(By.cssSelector(selectors.getProperty("titleSelector"))).getText();
                //String model = listing.findElement(By.cssSelector(selectors.getProperty("modelSelector"))).getText();
                String year = listing.findElement(By.cssSelector(selectors.getProperty("yearSelector"))).getText();
                String price = listing.findElement(By.cssSelector(selectors.getProperty("priceSelector"))).getText();

                // Create a new CarListing object and add it to the list
                CarListing carListing = new CarListing();
                carListing.setListingTitle(title);
                carListing = CarListingParser.parseDetails(carListing, year);
                carListings.add(carListing);
            }
        } finally {
            // Close the browser
            driver.quit();
        }

        return carListings;
    }
}
