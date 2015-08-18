package com.gaddieind.knowitall.yelp;

import org.junit.Test;

/**
 * Created by Alex on 8/15/2015.
 */
public class YelpAPI_IT {

    @Test
    public void testYelp(){
        YelpAPI yelp = new YelpAPI("HHIpstEWoNBBSvysbCLZEw", "O-XjoyJJ2x-xPvUTEYgo3oawuj4", "uPbPq4zszHhGdhpucEtQvlatXnvt_JRE", "niTM1NyPaHBNeYo0H35skG54RZI");

        YelpResponse result = yelp.searchForBusinessesByLocation("movies", "2 sunset lane, st peters mo 63376", 20, 2);

        System.out.println(result);
    }
}
