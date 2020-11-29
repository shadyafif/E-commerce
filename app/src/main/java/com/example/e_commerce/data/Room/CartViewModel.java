package com.example.e_commerce.data.Room;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.e_commerce.data.model.CartDetails.LineItem;

import java.util.List;

public class CartViewModel extends AndroidViewModel {

    LiveData<List<LineItem>> CartDatumList;
    public CartViewModel(@NonNull Application application) {
        super(application);
        AppDatabase dataBase = AppDatabase.getInstance(this.getApplication());
        CartDatumList = dataBase.ProductDao().loadAllCart();
    }

    public LiveData<List<LineItem>> getCartDatumList() {
        return CartDatumList;
    }
}
