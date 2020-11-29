package com.example.e_commerce.data.network;

import com.example.e_commerce.data.model.ProductsDetails.ProductDatum;
import com.example.e_commerce.data.model.categoriesList.CategoryDatum;
import com.example.e_commerce.data.model.orderDetails.OrderDatum;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Api {

    @GET("products/categories")
    Call<List<CategoryDatum>> getCategories(@Query("consumer_key") String key,

                                            @Query("consumer_secret") String secret);

    @GET("products")
    Call<List<ProductDatum>> getHomeProducts(@Query("consumer_key") String key,
                                             @Query("consumer_secret") String secret,
                                             @Query("per_page") String page);

    // GetAllProductsByCategoryId
    @GET("products")
    Call<List<ProductDatum>> getCategoryProduct(@Query("consumer_key") String key,
                                               @Query("consumer_secret") String secret,
                                               @Query("category") String category,
                                               @Query("per_page") String page);

    // GetAllProductsByCategoryId
    @GET("products")
    Call<List<ProductDatum>> getOffers(@Query("consumer_key") String key,
                                       @Query("consumer_secret") String secret,
                                       @Query("on_sale") boolean IsSale);

    @POST("orders")
    Call<OrderDatum> createOrder(@Query("consumer_key") String key,
                                 @Query("consumer_secret") String secret,
                                 @Body OrderDatum orderDatum);
}
