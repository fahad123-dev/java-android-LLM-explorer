package com.fahad.finalproject;

import java.io.Serializable;
public class LLM implements Serializable {
    private final String name;          // Marked final since it shouldn't change
    private final String description;
    private final int imageResId;      // Local drawable resource ID
    private final String imageUrl;     // Remote image URL
    private float rating;              // Mutable rating

    public LLM(String name, String description, int imageResId, String imageUrl) {
        this.name = name;
        this.description = description;
        this.imageResId = imageResId;
        this.imageUrl = imageUrl;
        this.rating = 0f;  // Explicit float literal
    }
    // Getters
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getImageResId() {
        return imageResId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public float getRating() {
        return rating;
    }

    // Setter
    public void setRating(float rating) {
        if (rating >= 0f && rating <= 5f) {  // Validate input
            this.rating = rating;
        }
    }
}

