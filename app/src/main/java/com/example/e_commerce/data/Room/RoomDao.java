package com.example.e_commerce.data.Room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.e_commerce.data.model.CartDetails.LineItem;
import com.example.e_commerce.data.model.ProductsDetails.ProductDatum;

import java.util.List;

@Dao
public interface RoomDao {

    //WishListDao
    @Query("SELECT * FROM ProductDetails")
    LiveData<List<ProductDatum>> loadProducts();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertProductFav(ProductDatum ProductEntry);


    @Delete
    void deleteProductFav(ProductDatum ProductEntry);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateProduct(ProductDatum ProductEntry);

    @Query("SELECT * FROM ProductDetails WHERE id= :id")
    LiveData<List<ProductDatum>> loadAllِArticalById(int id);

    @Query("SELECT * FROM ProductDetails WHERE name = :name")
    ProductDatum fetchById(String name);

    @Query("DELETE FROM ProductDetails")
    void Deleteall();

    //CartDao


    @Query("SELECT * FROM cartTable")
    LiveData<List<LineItem>> loadAllCart();

    @Query("SELECT * FROM cartTable where name = :name")
    LineItem fetchCart(String name);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertToCart(LineItem cart);

    @Delete
    void deleteCart(LineItem cart);

    @Query("SELECT Sum (quantity * price) FROM cartTable")
    int getSum();

    @Update
    void updateQty(LineItem cart);

    @Query("DELETE FROM cartTable")
    void DeleteallCart();

    @Query("SELECT * FROM cartTable")
    List<LineItem> loadAllCartList();
}
