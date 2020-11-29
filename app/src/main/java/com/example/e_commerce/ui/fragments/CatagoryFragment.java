package com.example.e_commerce.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.e_commerce.R;
import com.example.e_commerce.adapter.CategoryAdaper;
import com.example.e_commerce.data.model.categoriesList.CategoryDatum;
import com.example.e_commerce.databinding.CategoryLayoutBinding;
import com.example.e_commerce.databinding.FragmentCatagoryBinding;
import com.example.e_commerce.ui.viewmodels.CategoryViewmodel;
import com.example.e_commerce.utlies.Helper;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.List;

import static com.example.e_commerce.utlies.Helper.Replace;


public class CatagoryFragment extends Fragment{

    CategoryViewmodel categoryViewmodel;
    private CategoryAdaper adapter;
    private FragmentCatagoryBinding binding;

    public CatagoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCatagoryBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        InitViews();
        categoryViewmodel = new ViewModelProvider(this).get(CategoryViewmodel.class);
        categoryViewmodel.getAllCategories();


        categoryViewmodel.getDatumList().observe((LifecycleOwner) getContext(), new Observer<List<CategoryDatum>>() {
            @Override
            public void onChanged(List<CategoryDatum> categoryData) {
                binding.shimmerViewContainer.setVisibility(View.GONE);
                adapter.setCategoryList(categoryData);
            }
        });
        return view;
    }

    public void InitViews() {
        binding.shimmerViewContainer.startShimmer();
        adapter = new CategoryAdaper(getContext());
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        binding.RecCategroies.setLayoutManager(layoutManager);
        binding.RecCategroies.setAdapter(adapter);
    }


}