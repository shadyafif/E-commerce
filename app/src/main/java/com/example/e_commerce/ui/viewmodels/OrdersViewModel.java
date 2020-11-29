package com.example.e_commerce.ui.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.e_commerce.data.model.orderDetails.Billing;
import com.example.e_commerce.data.model.orderDetails.OrderDatum;
import com.example.e_commerce.data.model.orderDetails.Shipping;
import com.example.e_commerce.repository.OrdersRepo;

public class OrdersViewModel extends AndroidViewModel {
    OrdersRepo repo;
    public OrdersViewModel(@NonNull Application application) {
        super(application);
        repo = new OrdersRepo(application);
    }

    public MutableLiveData<String> getMessage() {
        return repo.getOrderResponse();
    }



    public void passData(Billing billing, Shipping shipping,String Payment_method) {
        repo.createOrder(billing,shipping,Payment_method);
    }
}
