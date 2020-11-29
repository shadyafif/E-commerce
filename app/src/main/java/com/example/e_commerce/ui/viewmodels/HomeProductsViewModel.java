package com.example.e_commerce.ui.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.e_commerce.data.model.ProductsDetails.ProductDatum;
import com.example.e_commerce.repository.HomeProductsRepo;

import java.util.List;

public class HomeProductsViewModel extends AndroidViewModel {
    private HomeProductsRepo homeProductsRepo;
    public HomeProductsViewModel(@NonNull Application application) {
        super(application);
        homeProductsRepo= new HomeProductsRepo();
    }

    public MutableLiveData<List<ProductDatum>> GetDatumList()
    {
        return homeProductsRepo.getHomeProductsList();
    }

    public void getHomeProductsList()
    {
        homeProductsRepo.GetHomeProducts();
    }
}
