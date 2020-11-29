package com.example.e_commerce.repository;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.e_commerce.data.Room.AppDatabase;
import com.example.e_commerce.data.Room.AppExecutors;
import com.example.e_commerce.data.model.CartDetails.LineItem;
import com.example.e_commerce.data.model.orderDetails.Billing;
import com.example.e_commerce.data.model.orderDetails.OrderDatum;
import com.example.e_commerce.data.model.orderDetails.Shipping;
import com.example.e_commerce.data.network.RetrofitClient;
import com.example.e_commerce.utlies.Constants;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class OrdersRepo extends GlobalRepo {

    /**
     * Initialization
     */
    Context context;
    private final MutableLiveData<String> orderResponse = new MutableLiveData<>();
    private AppDatabase mDb;
    List<LineItem> CrtLst;

    /**
     * Default constructor
     */
    public OrdersRepo(Context context) {
        this.context = context;
    }


    /**
     * Getter
     */
    public MutableLiveData<String> getOrderResponse() {
        return orderResponse;
    }

    public void createOrder(Billing billing, Shipping shipping, String Payment_method) {

        mDb = AppDatabase.getInstance(Objects.requireNonNull(context));
        AppExecutors.getInstance().diskIO().execute(() -> {
            CrtLst = mDb.ProductDao().loadAllCartList();
            Log.d(TAG, "getUserInput: " + "" + CrtLst.size());


        });
        OrderDatum orderDatum = new OrderDatum(billing, shipping, Payment_method, CrtLst);
        Call<OrderDatum> call = RetrofitClient.getInstance().getApi().createOrder(Constants.CONSUMER_KEY,
                Constants.SECRET_KEY, orderDatum);
        call.enqueue(new Callback<OrderDatum>() {
            @Override
            public void onResponse(@NonNull Call<OrderDatum> call, @NonNull Response<OrderDatum> response) {
                OrderDatum data = response.body();
                if (response.isSuccessful() && data != null) {
                    orderResponse.setValue("Successful response");

                } else {

                }
            }

            @Override
            public void onFailure(@NonNull Call<OrderDatum> call, @NonNull Throwable t) {

            }
        });


    }
}
