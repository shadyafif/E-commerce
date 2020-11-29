package com.example.e_commerce.ui.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.e_commerce.data.model.ProductsDetails.ProductDatum;
import com.example.e_commerce.repository.OffersRepo;

import java.util.List;

public class OffersViewModel extends AndroidViewModel {
    private OffersRepo offersRepo;

    public OffersViewModel(@NonNull Application application) {
        super(application);
        offersRepo = new OffersRepo();
    }

    public MutableLiveData<List<ProductDatum>> getDatumList() {
        return offersRepo.getOffersProductsList();
    }

    public void getAllCategoriesProducts() {
        offersRepo.GetOffersProducts();
    }
}


