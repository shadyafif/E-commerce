package com.example.e_commerce.data.Room;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.e_commerce.data.model.ProductsDetails.ProductDatum;

import java.util.List;

public class MainViewModel extends AndroidViewModel {
    /**
     * Wrapping the <list<Datum> with LiveData
     * to avoid requiring the data every time
     **/
    LiveData<List<ProductDatum>> datumList;

    public MainViewModel(@NonNull Application application) {
        super(application);
        AppDatabase dataBase = AppDatabase.getInstance(this.getApplication());
        datumList = dataBase.ProductDao().loadProducts();
    }



    public LiveData<List<ProductDatum>> getDatumList() {
        return datumList;
    }
}
