package com.example.e_commerce.repository;

import androidx.lifecycle.MutableLiveData;

import com.example.e_commerce.data.model.categoriesList.CategoryDatum;
import com.example.e_commerce.utlies.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryRepo extends GlobalRepo {
    MutableLiveData<List<CategoryDatum>> CategroiesList = new MutableLiveData<>();
    List<CategoryDatum> datumList;
    public CategoryRepo() {

    }

    public MutableLiveData<List<CategoryDatum>> getCategroiesList() {
        return CategroiesList;
    }

    public void GetCategoriesListRepo()
    {
        getApiService().getCategories(Constants.CONSUMER_KEY,Constants.SECRET_KEY).enqueue(new Callback<List<CategoryDatum>>() {
            @Override
            public void onResponse(Call<List<CategoryDatum>> call, Response<List<CategoryDatum>> response) {
                datumList =  response.body();
                CategroiesList.setValue(datumList);
            }

            @Override
            public void onFailure(Call<List<CategoryDatum>> call, Throwable t) {

            }
        });
    }
}
