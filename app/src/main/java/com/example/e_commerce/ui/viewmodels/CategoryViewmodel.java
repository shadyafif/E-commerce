package com.example.e_commerce.ui.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.e_commerce.data.model.categoriesList.CategoryDatum;
import com.example.e_commerce.repository.CategoryRepo;

import java.util.List;

public class CategoryViewmodel extends AndroidViewModel {
    CategoryRepo categoryRepo;

    public CategoryViewmodel(@NonNull Application application) {
        super(application);
        categoryRepo = new CategoryRepo();
    }

    public MutableLiveData<List<CategoryDatum>> getDatumList()
    {
        return categoryRepo.getCategroiesList();
    }

    public void getAllCategories() {
        categoryRepo.GetCategoriesListRepo();
    }
}
