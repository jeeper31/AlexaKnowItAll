package com.gaddieind.knowitall.yelp;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by Alex on 8/15/2015.
 */
public enum YelpSortingEnum {
    best_match(0),
    distance(1),
    rating(2);

    private int sortValue = 0;

    YelpSortingEnum(int sortValue){
        this.sortValue = sortValue;
    }

    public static YelpSortingEnum getSortValue(String sortingType) {
        if(StringUtils.equalsIgnoreCase("distance", sortingType)){
            return distance;
        }else if(StringUtils.equalsIgnoreCase("rating", sortingType)){
            return rating;
        }else {
            return rating;
        }
    }

    public int getSortValue() {
        return sortValue;
    }
}
