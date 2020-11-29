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
import com.example.e_commerce.adapter.OffersPorductAdapter;
import com.example.e_commerce.data.model.ProductsDetails.ProductDatum;
import com.example.e_commerce.databinding.FragmentOffersBinding;
import com.example.e_commerce.ui.viewmodels.OffersViewModel;

import java.util.List;
import java.util.Objects;


public class OffersFragment extends Fragment {

    private FragmentOffersBinding binding;
    private OffersPorductAdapter offersPorductAdapter;
    private OffersViewModel offersViewModel;


    public OffersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentOffersBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        Initviews();
        offersViewModel=new ViewModelProvider(this).get(OffersViewModel.class);
        offersViewModel.getAllCategoriesProducts();
        offersViewModel.getDatumList().observe((LifecycleOwner) getContext(), new Observer<List<ProductDatum>>() {
            @Override
            public void onChanged(List<ProductDatum> productData) {
                binding.shimmerViewContainer.setVisibility(View.GONE);
                offersPorductAdapter.setOffersProductList(productData);
            }
        });
        return view;
    }

    public void Initviews() {
        binding.toolbar.setTitle(Objects.requireNonNull(getContext()).getResources().getString(R.string.Offers));
        binding.toolbar.setTitleTextColor(getContext().getResources().getColor(R.color.colorBottomNav));
        binding.shimmerViewContainer.startShimmer();
        offersPorductAdapter = new OffersPorductAdapter(getContext());
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        binding.RecOffersProduct.setLayoutManager(layoutManager);
        binding.RecOffersProduct.setAdapter(offersPorductAdapter);
    }
}