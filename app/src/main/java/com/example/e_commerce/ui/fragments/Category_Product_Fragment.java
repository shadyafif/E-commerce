package com.example.e_commerce.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.e_commerce.R;
import com.example.e_commerce.adapter.CategoryProductAdapter;
import com.example.e_commerce.data.model.ProductsDetails.ProductDatum;
import com.example.e_commerce.databinding.FragmentCategoryProductBinding;
import com.example.e_commerce.ui.viewmodels.CategoryProductViewmodel;

import java.util.List;
import java.util.Objects;


public class Category_Product_Fragment extends Fragment {

    private FragmentCategoryProductBinding binding;
    String CategoryId, CategoryName;
    private CategoryProductAdapter categoryProductAdapter;
    private CategoryProductViewmodel categoryProductViewmodel;

    private static final String TAG = Category_Product_Fragment.class.getSimpleName();

    public Category_Product_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCategoryProductBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        Initviews();
        assert getArguments() != null;
        CategoryId = getArguments().getString("id");

        categoryProductViewmodel = new ViewModelProvider(this).get(CategoryProductViewmodel.class);
        categoryProductViewmodel.getAllCategoriesProducts(CategoryId);
        categoryProductViewmodel.getDatumList().observe((LifecycleOwner) getContext(), new Observer<List<ProductDatum>>() {
            @Override
            public void onChanged(List<ProductDatum> productCates) {

                binding.shimmerViewContainer.setVisibility(View.GONE);
                categoryProductAdapter.setCategoryProductList(productCates);
                CategoryName = productCates.get(0).getCategories().get(0).getName();
                binding.toolbar.setTitle(Objects.requireNonNull(CategoryName));
                binding.toolbar.setTitleTextColor(getContext().getResources().getColor(R.color.colorBottomNav));
            }
        });

        return view;
    }

    public void Initviews() {

        binding.shimmerViewContainer.startShimmer();
        categoryProductAdapter = new CategoryProductAdapter(getContext());
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        binding.RecCategroyProduct.setLayoutManager(layoutManager);
        binding.RecCategroyProduct.setAdapter(categoryProductAdapter);
    }
}