package com.example.e_commerce.ui.fragments;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.e_commerce.R;
import com.example.e_commerce.adapter.CartListAdapter;
import com.example.e_commerce.data.Room.AppDatabase;
import com.example.e_commerce.data.Room.AppExecutors;
import com.example.e_commerce.data.Room.CartViewModel;
import com.example.e_commerce.data.model.CartDetails.LineItem;
import com.example.e_commerce.data.network.CartInterface;
import com.example.e_commerce.databinding.FragmentAccountBinding;

import java.util.List;
import java.util.Objects;

import static com.example.e_commerce.utlies.Helper.Replace;

public class AccountFragment extends Fragment implements View.OnClickListener, CartInterface {

    FragmentAccountBinding binding;
    private CartListAdapter cartListAdapter;
    private CartViewModel cartViewModel;
    private LiveData<List<LineItem>> mlist;
    AppDatabase mDb;

    public AccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAccountBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        InitView(view);
        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);
        cartViewModel.getCartDatumList().observe((LifecycleOwner) Objects.requireNonNull(getContext()), new Observer<List<LineItem>>() {
            @Override
            public void onChanged(List<LineItem> cartData) {
                cartListAdapter.setCartProductsList(cartData);
                if (cartListAdapter.getItemCount() == 0) {
                    binding.Constraint.setVisibility(View.GONE);
                    binding.ivEmptyCart.setVisibility(View.VISIBLE);
                    binding.tvEmptyCart.setVisibility(View.VISIBLE);
                } else {
                    binding.Constraint.setVisibility(View.VISIBLE);
                    binding.ivEmptyCart.setVisibility(View.GONE);
                    binding.tvEmptyCart.setVisibility(View.GONE);
                }

            }
        });
        return view;
    }

    public void InitView(View view) {
        binding.toolbar.setTitle(view.getResources().getString(R.string.MyCart));
        binding.toolbar.setTitleTextColor(view.getResources().getColor(R.color.colorBottomNav));
        cartListAdapter = new CartListAdapter(getContext(), this);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        binding.RecCart.setLayoutManager(layoutManager);
        binding.RecCart.setAdapter(cartListAdapter);
        mDb = AppDatabase.getInstance(getContext());
        mlist = mDb.ProductDao().loadAllCart();
        binding.ivCartListDelete.setOnClickListener(this);
        binding.btnViewResults.setOnClickListener(this);
    }

    @Override
    public void setValue(int value) {
        binding.tvBillTotal.setText(String.valueOf(value));
        binding.tvSubTotal.setText(String.valueOf(value));
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_CartList_delete) {
            showDialog();
        } else if (id == R.id.btn_view_results) {
            String Total = binding.tvBillTotal.getText().toString().trim();
            Bundle bundle=new Bundle();
            bundle.putString("Total", Total);
            //set Fragmentclass Arguments
            BillingFragment billingFragment=new BillingFragment();
            billingFragment.setArguments(bundle);
            Replace(billingFragment,R.id.FragmentLoad, getActivity().getSupportFragmentManager().beginTransaction());
        }
    }

    private void showDialog() {
        final Dialog dialog = new Dialog(Objects.requireNonNull(getContext()));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.deletecart);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        FrameLayout mDialogNo = dialog.findViewById(R.id.frmNo);
        mDialogNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Cancel", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        FrameLayout mDialogOk = dialog.findViewById(R.id.frmOk);
        mDialogOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppExecutors.getInstance().diskIO().execute(() -> {
                    mDb.ProductDao().DeleteallCart();
                });
                dialog.cancel();
                setValue(0);
            }
        });

        dialog.show();
    }
}