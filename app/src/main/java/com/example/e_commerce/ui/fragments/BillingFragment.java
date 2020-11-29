package com.example.e_commerce.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.e_commerce.R;
import com.example.e_commerce.data.Room.AppDatabase;
import com.example.e_commerce.data.Room.AppExecutors;
import com.example.e_commerce.data.model.CartDetails.LineItem;
import com.example.e_commerce.data.model.orderDetails.Billing;
import com.example.e_commerce.data.model.orderDetails.OrderDatum;
import com.example.e_commerce.data.model.orderDetails.Shipping;
import com.example.e_commerce.data.network.RetrofitClient;
import com.example.e_commerce.databinding.FragmentBillingBinding;
import com.example.e_commerce.ui.viewmodels.OrdersViewModel;
import com.example.e_commerce.utlies.Constants;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.constraintlayout.widget.Constraints.TAG;


public class BillingFragment extends Fragment implements View.OnClickListener {

    private FragmentBillingBinding binding;
    private OrdersViewModel viewModel;
    private String Payment_Method;


    public BillingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentBillingBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        initView();
        viewModel = new ViewModelProvider(getActivity()).get(OrdersViewModel.class);


        viewModel.getMessage().observe(getActivity(), data ->
                Toast.makeText(getContext(), data, Toast.LENGTH_SHORT).show());

        return view;

    }

    private void initView() {
        String Total = getArguments().getString("Total");
        binding.tvBillingTotal.setText(Total);

        binding.btnBillingComplete.setOnClickListener(this);
        binding.rbBillingDelivery.setOnClickListener(this);
        binding.rbBillingVisa.setOnClickListener(this);
    }

    private void getUserInput() {
        String email = binding.etBillingEmail.getEditText().getText().toString().trim();
        String firstName = binding.etBillingFirstName.getEditText().getText().toString().trim();
        String lastName = binding.etBillingLastName.getEditText().getText().toString().trim();
        String address = binding.etBillingAddress.getEditText().getText().toString().trim();
        String postCode = binding.etBillingPostcode.getEditText().getText().toString().trim();
        String city = binding.etBillingCity.getEditText().getText().toString().trim();
        String country = binding.etBillingCountry.getEditText().getText().toString().trim();
        String phoneNumber = binding.etBillingPhone.getEditText().getText().toString().trim();

//        Billing billing = new Billing(firstName, lastName, address, city, postCode, country, email, phoneNumber);
        Billing billing = new Billing("shady", "ghattas", "mansoura", "mansoura", "35111",
                "mansoura", "shady@gmail.com", "01062286001");
//        Shipping shipping = new Shipping(firstName, lastName, address, city, postCode, country);
        Shipping shipping = new Shipping("shady", "ghattas", "mansoura", "mansoura",
                "35111", "mansoura");

        viewModel.passData(billing, shipping, Payment_Method);


    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_Billing_Complete) {
            getUserInput();
        } else if (v.getId() == R.id.rb_billing_delivery) {
            binding.rbBillingVisa.setChecked(false);
            binding.rbBillingDelivery.setChecked(true);
            binding.Constraint.setVisibility(View.GONE);
            binding.tvCashTitle.setVisibility(View.VISIBLE);
            binding.tvVisaTitle.setVisibility(View.GONE);
            Payment_Method = "Cash On Delivery";
        } else if (v.getId() == R.id.rb_billing_Visa) {
            binding.rbBillingDelivery.setChecked(false);
            binding.rbBillingVisa.setChecked(true);
            binding.Constraint.setVisibility(View.VISIBLE);
            binding.tvCashTitle.setVisibility(View.GONE);
            binding.tvVisaTitle.setVisibility(View.VISIBLE);
            Payment_Method = "Using visa";
        }


    }
}