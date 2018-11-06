package com.example.adrian.wroclawtour;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class Place implements Serializable{

    public String category;
    public String name;
    public String imageSmall;
    public String imageBig;
    public String description;
    public String address;
    public String rate;
    public String longtitude;
    public String latitude;

    public Place(){
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Place(String category, String name, String imageSmall, String imageBig, String description, String address,
                 String rate, String longtitude, String latitude){
        this.category = category;
        this.name = name;
        this.imageSmall = imageSmall;
        this.imageBig = imageBig;
        this.description = description;
        this.address = address;
        this.rate = rate;
        this.longtitude = longtitude;
        this.latitude = latitude;

    }

    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("category", category);
        result.put("name", name);
        result.put("imageSmall", imageSmall);
        result.put("imageBig", imageBig);
        result.put("description", description);
        result.put("address", address);
        result.put("rate", rate);
        result.put("longtitude", longtitude);
        result.put("latitude", latitude);

        return result;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageSmall() {
        return imageSmall;
    }

    public void setImageSmall(String imageSmall) {
        this.imageSmall = imageSmall;
    }

    public String getImageBig() {
        return imageBig;
    }

    public void setImageBig(String imageBig) {
        this.imageBig = imageBig;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(String longtitude) {
        this.longtitude = longtitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
}
