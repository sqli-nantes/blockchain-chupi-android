package com.example.joel.automotive;

/**
 * Created by alb on 13/10/16.
 * Java Class for Wrapped Destination with Precomputed distance and prices
 */

public class Destination {
    private String destinationName;
    private int distance;
    private int price;

    public String getDestinationName() {
        return destinationName;
    }

    public void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
