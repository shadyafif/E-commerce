package com.example.e_commerce.ui.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.example.e_commerce.data.model.ProductsDetails.ProductDatum;
import com.example.e_commerce.repository.CategoryProductRepo;

import java.util.List;

public class CategoryProductViewmodel extends AndroidViewModel {

    CategoryProductRepo categoryProductRepo;

    public CategoryProductViewmodel(@NonNull Application application) {
        super(application);
        categoryProductRepo = new CategoryProductRepo(application);
    }

    public MutableLiveData<List<ProductDatum>> getDatumList() {
        return categoryProductRepo.getCategroiesProductsList();
    }

    public void getAllCategoriesProducts(String cateId) {
        categoryProductRepo.GetAllCategoriesProducts(cateId);
    }
}
