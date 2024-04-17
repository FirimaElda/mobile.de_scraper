package model;

import java.util.Date;

public class CarListing {

    private String listingTitle;
    private String make;
    private String model;
    private Date year;
    private String yearString;
    private double price;
    private String priceString;
    private int kilometres;
    private String kilometresString;
    private int kilowatts;
    private String kilowattsString;

    public CarListing() {
    }

    public CarListing(String listingTitle, String make, String model, Date year, double price) {
        this.listingTitle = listingTitle;
        this.make = make;
        this.model = model;
        this.year = year;
        this.price = price;
    }

    public CarListing(String listingTitle, String make, String model, String yearString, String priceString, String kilometresString, String kilowattsString) {
        this.listingTitle = listingTitle;
        this.make = make;
        this.model = model;
        this.yearString = yearString;
        this.priceString = priceString;
        this.kilometresString = kilometresString;
        this.kilowattsString = kilowattsString;
    }

    public String getListingTitle() {
        return listingTitle;
    }

    public void setListingTitle(String listingTitle) {
        this.listingTitle = listingTitle;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Date getYear() {
        return year;
    }

    public void setYear(Date year) {
        this.year = year;
    }

    public String getYearString() {
        return yearString;
    }

    public void setYearString(String yearString) {
        this.yearString = yearString;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPriceString() {
        return priceString;
    }

    public void setPriceString(String priceString) {
        this.priceString = priceString;
    }

    public int getKilometres() {
        return kilometres;
    }

    public void setKilometres(int kilometres) {
        this.kilometres = kilometres;
    }

    public String getKilometresString() {
        return kilometresString;
    }

    public void setKilometresString(String kilometresString) {
        this.kilometresString = kilometresString;
    }

    public int getKilowatts() {
        return kilowatts;
    }

    public void setKilowatts(int kilowatts) {
        this.kilowatts = kilowatts;
    }

    public String getKilowattsString() {
        return kilowattsString;
    }

    public void setKilowattsString(String kilowattsString) {
        this.kilowattsString = kilowattsString;
    }

    public String toStringUsingStringAttributes() {
        return "CarListing{" +
                "listingTitle='" + listingTitle + '\'' +
                ", make='" + make + '\'' +
                ", model='" + model + '\'' +
                ", yearString='" + yearString + '\'' +
                ", priceString='" + priceString + '\'' +
                ", kilometresString='" + kilometresString + '\'' +
                ", kilowattsString='" + kilowattsString + '\'' +
                '}';
    }

    @Override
    public String toString() {
        return "CarListing{" +
                "listingTitle='" + listingTitle + '\'' +
                ", make='" + make + '\'' +
                ", model='" + model + '\'' +
                ", year=" + year +
                ", price=" + price +
                ", kilometres=" + kilometres +
                ", kilowatts=" + kilowatts +
                '}';
    }
}