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
    public static final String KEY_CREATED_KEY = "createdAt";
    public static final String KEY_OBJECT_ID= "objectId";

    public String getDescription(){
        return getString(KEY_Description);
    }
    public void setDescription(String description){
        put(KEY_Description,description);
    }
    public String getCaption(){
        return getString(KEY_Caption);
    }
    public void setCaption(String caption){
        put(KEY_Caption,caption);
    }
    public Integer getPrice(){
        return getInt(KEY_Price);
    }
    public void setPrice(int price){
        put(KEY_Price,price);
    }
    public ParseUser getUser(){
        return getParseUser(KEY_USER);
    }
    public void setUser(ParseUser user){
        put(KEY_USER,user);
    }
    public String getId(){
        return getString(KEY_OBJECT_ID);
    }
}
