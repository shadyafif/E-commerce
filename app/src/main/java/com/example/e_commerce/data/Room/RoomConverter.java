package com.example.e_commerce.data.Room;

import androidx.room.TypeConverter;

import com.example.e_commerce.data.model.ProductsDetails.Category;
import com.example.e_commerce.data.model.ProductsDetails.Image;
import com.example.e_commerce.data.model.ProductsDetails.ProductDatum;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

public class RoomConverter {
    @TypeConverter
    public static Date toDate(Long timestamp) {
        return timestamp == null ? null : new Date(timestamp);
    }

    // Two converter methods for Image Class
    @TypeConverter
    public static List<Image> fromStringImage(String value) {
        Type listType = new TypeToken<List<Image>>() {
        }.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromClassImage(List<Image> list) {
        Gson gson = new Gson();
        return gson.toJson(list);
    }


    // Two converter methods for Category Class
    @TypeConverter
    public static List<Category> fromStringCategory(String value) {
        Type listType = new TypeToken<List<Category>>() {
        }.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromClassCategory(List<Category> list) {
        Gson gson = new Gson();
        return gson.toJson(list);
    }


}
