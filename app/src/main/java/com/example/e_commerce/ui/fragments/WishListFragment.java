package com.example.e_commerce.ui.fragments;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.e_commerce.R;
import com.example.e_commerce.adapter.WishListAdapter;
import com.example.e_commerce.data.Room.AppDatabase;
import com.example.e_commerce.data.Room.AppExecutors;
import com.example.e_commerce.data.Room.MainViewModel;
import com.example.e_commerce.data.model.ProductsDetails.ProductDatum;
import com.example.e_commerce.databinding.FragmentWishListBinding;

import java.util.List;
import java.util.Objects;


public class WishListFragment extends Fragment implements View.OnClickListener {

    private FragmentWishListBinding binding;
    private WishListAdapter wishListAdapter;
    private MainViewModel mainViewModel;
    LiveData<List<ProductDatum>> mList;
    AppDatabase mDb;

    public WishListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentWishListBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        InitView();
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        mainViewModel.getDatumList().observe((LifecycleOwner) Objects.requireNonNull(getContext()), new Observer<List<ProductDatum>>() {
            @Override
            public void onChanged(List<ProductDatum> productData) {
                binding.shimmerViewContainer.setVisibility(View.GONE);
                wishListAdapter.setWishListProductsList(productData);
            }
        });


        return view;
    }

    private void InitView() {
        binding.toolbar.setTitle(Objects.requireNonNull(getContext()).getResources().getString(R.string.WishList));
        binding.toolbar.setTitleTextColor(getContext().getResources().getColor(R.color.colorBottomNav));
        binding.shimmerViewContainer.startShimmer();
        wishListAdapter = new WishListAdapter(getContext());
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        binding.RecWishList.setLayoutManager(layoutManager);
        binding.RecWishList.setAdapter(wishListAdapter);
        mDb = AppDatabase.getInstance(getContext());
        mList = mDb.ProductDao().loadProducts();
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        int position = viewHolder.getAdapterPosition();
                        List<ProductDatum> datas = wishListAdapter.getWishListProductsList();
                        mDb.ProductDao().deleteProductFav(datas.get(position));

                    }
                });
            }
        }).attachToRecyclerView(binding.RecWishList);
        binding.ivWishListDelete.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_WishList_delete) {
            showDialog();
        }

    }
    public void showDialog()
    {
        final Dialog dialog = new Dialog(Objects.requireNonNull(getContext()));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.newcustom_layout);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        FrameLayout mDialogNo = dialog.findViewById(R.id.frmNo);
        mDialogNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"Cancel" , Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        FrameLayout mDialogOk = dialog.findViewById(R.id.frmOk);
        mDialogOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {

                        mDb.ProductDao().Deleteall();

                    }
                });
                dialog.cancel();
            }
        });

        dialog.show();
    }
}