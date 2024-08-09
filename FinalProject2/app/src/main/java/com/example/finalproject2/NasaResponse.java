package com.example.finalproject2;

import com.google.gson.annotations.SerializedName;

public class NasaResponse {

    @SerializedName("url")
    private String url;

    @SerializedName("date")
    private String date;

    @SerializedName("hdurl")
    private String hdurl;

    @SerializedName("explanation")
    private String explanation;

    public String getUrl() {
        return url;
    }

    public String getDate() {
        return date;
    }

    public String getHdurl() {
        return hdurl;
    }

    public String getExplanation() {
        return explanation;
    }
}
