package com.example.marketplaceapp;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Post")
public class Post extends ParseObject {

    public static final String KEY_Description = "description";
    public static final String KEY_Caption = "caption";
    public static final String KEY_Price = "price";
    public static final String KEY_USER = "user";

    public String getDescription(){
        return KEY_Description;
    }
    public void setDescription(String description){
        put(KEY_Description,description);
    }
    public String getCaption(){
        return KEY_Caption;
    }
    public void setCaption(String caption){
        put(KEY_Description,caption);
    }
    public String getPrice(){
        return KEY_Price;
    }
    public void setPrice(String price){
        put(KEY_Description,price);
    }
    public ParseUser getUser(){
        return getParseUser(KEY_USER);
    }
    public void setUser(ParseUser user){
        put(KEY_Description,user);
    }
}
