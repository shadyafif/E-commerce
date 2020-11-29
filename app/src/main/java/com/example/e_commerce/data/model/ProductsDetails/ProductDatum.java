
package com.example.e_commerce.data.model.ProductsDetails;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;

import androidx.room.PrimaryKey;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "ProductDetails")
public class ProductDatum implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("images")
    @Expose
    private List<Image> images = null;
    @SerializedName("categories")
    @Expose
    private List<Category> categories = null;

    @SerializedName("regular_price")
    @Expose
    private String regularPrice;




//    @Ignore
//    public ProductDatum(String name, String price, String description, List<Image> images, List<Category> categories, String regularPrice) {
//        this.name = name;
//        this.price = price;
//        this.description = description;
//        this.images = images;
//        this.categories = categories;
//        this.regularPrice = regularPrice;
//    }

    protected ProductDatum(Parcel in) {
        id = in.readInt();
        name = in.readString();
        price = in.readString();
        description = in.readString();
        regularPrice = in.readString();
    }

    public static final Creator<ProductDatum> CREATOR = new Creator<ProductDatum>() {
        @Override
        public ProductDatum createFromParcel(Parcel in) {
            return new ProductDatum(in);
        }

        @Override
        public ProductDatum[] newArray(int size) {
            return new ProductDatum[size];
        }
    };

    public void setName(String name) {
        this.name = name;
    }


    public void setPrice(String price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public List<Image> getImages() {
        return images;
    }

    public String getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }


    public String getRegularPrice() {
        return regularPrice;
    }

    public void setRegularPrice(String regularPrice) {
        this.regularPrice = regularPrice;
    }



    public ProductDatum() {

    }


    /**
     * Describe contents for parcel implementation
     *
     * @return integer
     */
    @Override
    public int describeContents() {
        return 0;
    }


    /**
     * Writing to parcel
     *
     * @param parcel is a parcel object
     * @param i      is integer
     */
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(price);
        parcel.writeString(description);
        parcel.writeList(images);
        parcel.writeList(categories);
    }

}
