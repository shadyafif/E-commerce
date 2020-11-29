package com.example.e_commerce.repository;

import android.content.Context;
import android.widget.Toast;
import androidx.lifecycle.MutableLiveData;

import com.example.e_commerce.data.model.ProductsDetails.ProductDatum;
import com.example.e_commerce.utlies.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CategoryProductRepo extends GlobalRepo {

    MutableLiveData<List<ProductDatum>> CategroiesProductsList = new MutableLiveData<>();
    List<ProductDatum> productCateList;
    Context context;

    public MutableLiveData<List<ProductDatum>> getCategroiesProductsList() {
        return CategroiesProductsList;
    }

    public CategoryProductRepo(Context context) {
        this.context = context;
    }

    public void GetAllCategoriesProducts(String CategoryId) {
        getApiService().getCategoryProduct(Constants.CONSUMER_KEY, Constants.SECRET_KEY, CategoryId, ""+31).enqueue(new Callback<List<ProductDatum>>() {
            @Override
            public void onResponse(Call<List<ProductDatum>> call, Response<List<ProductDatum>> response) {
                productCateList = response.body();
                CategroiesProductsList.setValue(productCateList);
            }

            @Override
            public void onFailure(Call<List<ProductDatum>> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }
}
