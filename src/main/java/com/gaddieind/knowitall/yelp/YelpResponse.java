package com.gaddieind.knowitall.yelp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by Alex on 8/15/2015.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class YelpResponse {

   private List<YelpBusiness> businesses;

    public List<YelpBusiness> getBusinesses() {
        return businesses;
    }

    public void setBusinesses(List<YelpBusiness> businesses) {
        this.businesses = businesses;
    }
}
