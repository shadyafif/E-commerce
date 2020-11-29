package com.example.e_commerce.repository;

import com.example.e_commerce.data.network.Api;
import com.example.e_commerce.data.network.RetrofitClient;

public class GlobalRepo {
    private Api apiService =   RetrofitClient.getInstance().getApi();


    public Api getApiService() {
        return apiService;
    }
}
