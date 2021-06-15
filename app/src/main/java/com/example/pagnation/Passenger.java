package com.example.pagnation;

public class Passenger {

    //Data Variables
    private String imageUrl;
    private String name;
    private int trips;


    public Passenger(String name, int trips){
        this.name = name;
        this.trips = trips;

    }

    //Getters and Setters
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTrips() {
        return trips;
    }

}