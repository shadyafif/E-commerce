package com.example.e_commerce.data.model.CartDetails;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "cartTable")
public class LineItem implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int product_id;
    private String name;
    private int price;
    private String Image;
    private String category;
    private int quantity;

    public LineItem() {

    }

    protected LineItem(Parcel in) {
        product_id = in.readInt();
        name = in.readString();
        price = in.readInt();
        Image = in.readString();
        category = in.readString();
        quantity = in.readInt();
    }

    public static final Creator<LineItem> CREATOR = new Creator<LineItem>() {
        @Override
        public LineItem createFromParcel(Parcel in) {
            return new LineItem(in);
        }

        @Override
        public LineItem[] newArray(int size) {
            return new LineItem[size];
        }
    };

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(product_id);
        dest.writeString(name);
        dest.writeInt(price);
        dest.writeString(Image);
        dest.writeString(category);
        dest.writeInt(quantity);
    }
}
