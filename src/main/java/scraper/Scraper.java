package scraper;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
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

    private final String url;
    private final Properties selectors;

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
                    .ignoring(NoSuchElementException.class)
                    .ignoring(StaleElementReferenceException.class);

            String cssSelectorString = selectors.getProperty("carListingSelector") + ", "
                    + selectors.getProperty("carListingEyeCatcherSelector") + ", "
                    + selectors.getProperty("carListingTopSelector");

            // Loop over each page of search results
            int pageNumber = 1;
            boolean hasNextPage = true;
            while (hasNextPage) {
                System.out.println("Scraping page " + pageNumber++ + "...");
                ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");

                // Wait for a while to let the page load
                try {
                    Thread.sleep(2000);  // Wait for 2 seconds
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // Find the car listings using the CSS selector from the properties file
                List<WebElement> listings = wait.until(d -> {
                    List<WebElement> elements = d.findElements(By.cssSelector(cssSelectorString));
                    return !elements.isEmpty() ? elements : null;
                });

                // Iterate over each listing
                System.out.println("Scraping listings..." +
                        "\nFound " + listings.size() + " listings on this page.");
                for (WebElement listing : listings) {
                    // Extract the car details using the CSS selectors from the properties file
                    String title = listing.findElement(By.cssSelector(selectors.getProperty("titleSelector"))).getText();
                    String yearKmKwUnparsed = listing.findElement(By.cssSelector(selectors.getProperty("yearSelector"))).getText();
                    String price = listing.findElement(By.cssSelector(selectors.getProperty("priceSelector"))).getText();

                    // Create a new CarListing object and add it to the list
                    CarListing carListing = new CarListing();
                    carListing.setListingTitle(title);
                    CarListingParser.parsePrice(carListing, price);
                    CarListingParser.parseYearKmKw(carListing, yearKmKwUnparsed);
                    carListings.add(carListing);
                }

                // Check if there is a next page
                try {
                    System.out.println("Checking for next page...");
                    WebElement nextPageButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(selectors.getProperty("nextPageSelector"))));
                    //nextPageButton.click();
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", nextPageButton);
                } catch (NoSuchElementException e) {
                    System.out.println("No next page found.");
                    hasNextPage = false;
                } catch (TimeoutException e) {
                    System.out.println("No next page found due to TimeoutException.");
                    hasNextPage = false;
                }
            }
        } finally {
            // Close the browser
            driver.quit();
        }

        return carListings;
    }
}
