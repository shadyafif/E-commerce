package com.example.e_commerce.repository;

import androidx.lifecycle.MutableLiveData;

import com.example.e_commerce.data.model.ProductsDetails.ProductDatum;
import com.example.e_commerce.utlies.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OffersRepo extends GlobalRepo {
    MutableLiveData<List<ProductDatum>> OffersProductsList = new MutableLiveData<>();
    List<ProductDatum> offersProList;

    public MutableLiveData<List<ProductDatum>> getOffersProductsList() {
        return OffersProductsList;
    }

    public void GetOffersProducts()
    {
        getApiService().getOffers(Constants.CONSUMER_KEY, Constants.SECRET_KEY,true).enqueue(new Callback<List<ProductDatum>>() {
            @Override
            public void onResponse(Call<List<ProductDatum>> call, Response<List<ProductDatum>> response) {
                offersProList= response.body();
                OffersProductsList.setValue(offersProList);
            }

            @Override
            public void onFailure(Call<List<ProductDatum>> call, Throwable t) {

            }
        });
    }
}
