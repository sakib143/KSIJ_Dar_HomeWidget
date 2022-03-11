package com.learn.widgettest.rest.model;

import com.google.gson.annotations.SerializedName;
import com.learn.widgettest.model.Movie;

import java.util.List;

public class MovieResponse {
    private long page;

    @SerializedName("results")
    private List<Movie> movies;

    public List<Movie> getMovies() {
        return movies;
    }
}
