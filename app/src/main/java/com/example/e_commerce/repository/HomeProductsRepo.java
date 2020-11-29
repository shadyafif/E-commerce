package com.example.e_commerce.repository;

import androidx.lifecycle.MutableLiveData;

import com.example.e_commerce.data.model.ProductsDetails.ProductDatum;
import com.example.e_commerce.utlies.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeProductsRepo extends GlobalRepo {

    MutableLiveData<List<ProductDatum>> HomeProductsList = new MutableLiveData<>();
    List<ProductDatum> productCateList;

    public MutableLiveData<List<ProductDatum>> getHomeProductsList() {
        return HomeProductsList;
    }

    public HomeProductsRepo() {

    }

    public void GetHomeProducts() {
        getApiService().getHomeProducts(Constants.CONSUMER_KEY, Constants.SECRET_KEY,"31").enqueue(new Callback<List<ProductDatum>>() {
            @Override
            public void onResponse(Call<List<ProductDatum>> call, Response<List<ProductDatum>> response) {
                productCateList = response.body();
                HomeProductsList.setValue(productCateList);
            }

            @Override
            public void onFailure(Call<List<ProductDatum>> call, Throwable t) {

            }
        });
    }
}
