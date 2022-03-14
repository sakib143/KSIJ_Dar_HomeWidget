package com.learn.widgettest.rest.service;


import com.learn.widgettest.rest.model.MovieResponse;

import java.util.Map;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.FieldMap;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

public interface MovieService {

    String SORT_BY_POPULARITY_DESC = "popularity.desc";
    String SORT_BY_VOTE_AVERAGE_DESC = "vote_average.desc";
    String SORT_BY_VOTE_COUNT_DESC = "vote_count.desc";


    @POST("Get_SpData")
    Call<MovieResponse> getMovies(@Body Map<String, String> data);
}
