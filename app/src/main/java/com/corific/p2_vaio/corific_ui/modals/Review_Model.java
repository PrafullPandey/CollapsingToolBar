package com.corific.p2_vaio.corific_ui.modals;

/**
 * Created by p2 on 25/5/18.
 */

public class Review_Model {

    private String name , message ;
    private int rating ;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
