package com.gaddieind.knowitall.yelp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Alex on 8/15/2015.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class YelpBusiness {

    private String name;
    private Number rating;
    private Number distance;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Number getRating() {
        return rating;
    }

    public void setRating(Number rating) {
        this.rating = rating;
    }

    public Number getDistance() {
        return distance;
    }

    public void setDistance(Number distance) {
        this.distance = distance;
    }
}
