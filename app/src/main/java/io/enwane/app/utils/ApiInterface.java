package io.enwane.app.utils;

import io.enwane.app.model.DeliveryOptimizationResponse;
import io.enwane.app.model.LatLongResponse;
import io.enwane.app.model.OptimizedDeliveryRequest;
import io.enwane.app.model.ShortAddressResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("v1/latlon_to_short.php")
    Call<ShortAddressResponse> getShortAddress(@Query("latlon") String latlon);

    @GET("v1/short_to_latlon.php")
    Call<LatLongResponse> getLatLon(@Query("short_code") String shortCode, @Query("country") String country);

    @POST("v1/delivery_optimization.php")
    Call<DeliveryOptimizationResponse> deliveryOptimization(@Body OptimizedDeliveryRequest optimizedDeliveryRequest);
}
