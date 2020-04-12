package com.test.myapplication.utils;

import com.test.myapplication.model.LatLongResponse;
import com.test.myapplication.model.ShortAddressResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("v1/latlon_to_short.php")
    Call<ShortAddressResponse> getShortAddress(@Query("latlon") String latlon);

    @GET("v1/short_to_latlon.php")
    Call<LatLongResponse> getLatLon(@Query("short_code") String shortCode, @Query("country") String country);
}