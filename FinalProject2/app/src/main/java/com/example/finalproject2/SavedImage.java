package com.example.finalproject2;

public class SavedImage {
    private String date;
    private String imageUrl;
    private String hdImageUrl;
    private String explanation;

    public SavedImage(String date, String imageUrl, String hdImageUrl, String explanation) {
        this.date = date;
        this.imageUrl = imageUrl;
        this.hdImageUrl = hdImageUrl;
        this.explanation = explanation;
    }

    public String getDate() {
        return date;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getHdImageUrl() {
        return hdImageUrl;
    }

    public String getExplanation() {
        return explanation;
    }
}
