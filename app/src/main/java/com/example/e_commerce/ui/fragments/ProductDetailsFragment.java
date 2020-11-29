package com.example.e_commerce.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.e_commerce.R;
import com.example.e_commerce.data.Room.AppDatabase;
import com.example.e_commerce.data.Room.AppExecutors;
import com.example.e_commerce.data.model.CartDetails.LineItem;
import com.example.e_commerce.data.model.ProductsDetails.ProductDatum;
import com.example.e_commerce.databinding.FragmentProductDetailsBinding;
import com.google.android.material.snackbar.Snackbar;
import java.util.Objects;



public class ProductDetailsFragment extends Fragment implements View.OnClickListener {

    private ProductDatum datum;
    private FragmentProductDetailsBinding binding;
    private AppDatabase mDb;
    private int OrderNum = 1;


    public ProductDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProductDetailsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        Initviews();
        return view;
    }


    public void Initviews() {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            datum = bundle.getParcelable("Product");
        }

        binding.tvDetailsName.setText(datum.getName());
        binding.tvDetailsCategory.setText(datum.getCategories().get(0).getName());
        binding.tvDetailsPrice.setText(datum.getRegularPrice());
        binding.tvDetailsDescription.setText(datum.getDescription());

        Glide.with(this).load(datum.getImages().get(0).getSrc())
                .apply(new RequestOptions())
                .into(binding.ivDetailsProduct);
        mDb = AppDatabase.getInstance(Objects.requireNonNull(getActivity()).getApplicationContext());

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                String ProductName = binding.tvDetailsName.getText().toString().trim();
                ProductDatum datum = mDb.ProductDao().fetchById(ProductName);
                if (datum != null) {
                    binding.ivDetailsFavorite.setChecked(true);
                } else {
                    binding.ivDetailsFavorite.setChecked(false);
                }
            }
        });
        binding.ivDetailsFavorite.setOnClickListener(this);
        binding.ivDetailsPlus.setOnClickListener(this);
        binding.ivDetailsMinus.setOnClickListener(this);
        binding.ivDetailsCart.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_Details_Favorite:
                RoomGrud(v);
                break;
            case R.id.iv_Details_Plus:
                OrderNum++;
                binding.tvDetailsQuantity.setText(String.valueOf(OrderNum));
                break;
            case R.id.iv_Details_Minus:
                if (OrderNum == 1) {
                    binding.tvDetailsQuantity.setText("1");
                } else {
                    OrderNum--;
                    binding.tvDetailsQuantity.setText(String.valueOf(OrderNum));
                }
                break;

            case R.id.iv_Details_Cart:
                AddToCart(v);
                break;
        }
    }

    private void AddToCart(View view) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                LineItem cart = mDb.ProductDao().fetchCart(datum.getName());
                if (cart != null) {
                    int Qtn = cart.getQuantity();
                    cart.setQuantity(Qtn +OrderNum);
                    mDb.ProductDao().updateQty(cart);
                    String CartMessage = getContext().getResources().getString(R.string.AddedSuccessfully);
                    Snackbar.make(view, CartMessage, Snackbar.LENGTH_SHORT).addCallback(new Snackbar.Callback() {
                        @Override
                        public void onDismissed(Snackbar transientBottomBar, int event) {
                            super.onDismissed(transientBottomBar, event);

                        }
                    }).show();


                } else {
                    LineItem cartData = new LineItem();
                    cartData.setProduct_id(datum.getId());
                    cartData.setName(datum.getName());
                    cartData.setCategory(datum.getCategories().get(0).getName());
                    cartData.setImage(datum.getImages().get(0).getSrc());
                    cartData.setPrice(Integer.parseInt(datum.getPrice()));
                    cartData.setQuantity(1);
                    mDb.ProductDao().insertToCart(cartData);
                }
            }
        });
    }

    public void RoomGrud(View v) {
        if (binding.ivDetailsFavorite.isChecked()) {
            String message = getActivity().getResources().getString(R.string.AddedSuccessfully);
            AppExecutors.getInstance().diskIO().execute(() -> {

                mDb.ProductDao().insertProductFav(datum);

            });
            Snackbar.make(v, message, Snackbar.LENGTH_SHORT).addCallback(new Snackbar.Callback() {
                @Override
                public void onDismissed(Snackbar transientBottomBar, int event) {
                    super.onDismissed(transientBottomBar, event);

                }
            }).show();

        } else {
            String messageDelete = getActivity().getResources().getString(R.string.Deleted);
            AppExecutors.getInstance().diskIO().execute(() -> {

                mDb.ProductDao().deleteProductFav(datum);

            });
            Snackbar.make(v, messageDelete, Snackbar.LENGTH_SHORT).addCallback(new Snackbar.Callback() {
                @Override
                public void onDismissed(Snackbar transientBottomBar, int event) {
                    super.onDismissed(transientBottomBar, event);

                }
            }).show();
        }
    }
}