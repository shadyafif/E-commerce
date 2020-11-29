package com.example.e_commerce.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.e_commerce.R;
import com.example.e_commerce.data.Room.AppDatabase;
import com.example.e_commerce.data.Room.AppExecutors;
import com.example.e_commerce.data.model.ProductsDetails.ProductDatum;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class OffersPorductAdapter extends RecyclerView.Adapter<OffersPorductAdapter.ViewHolder> {
    private List<ProductDatum> OffersProductsList = new ArrayList<>();
    private Context context;
    private AppDatabase mDb;
    public OffersPorductAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_category_layout, parent, false);
        return new ViewHolder(v);
    }

    public void setOffersProductList(List<ProductDatum> OffersProductList) {
        this.OffersProductsList = OffersProductList;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ProductDatum currentItem = OffersProductsList.get(position);
        holder.txt_Category_product_name.setText(currentItem.getName());
        holder.txt_Category_name.setText(currentItem.getCategories().get(0).getName());
        holder.txt_Category_product_Price.setText(currentItem.getPrice());
        holder.txt_Category_product_regular_price.setText(currentItem.getRegularPrice());
        holder.txt_Category_product_Price.setPaintFlags(holder.txt_Category_product_Price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        if (currentItem.getImages().size() == 0) {
            Glide.with(context).load(context.getDrawable(R.drawable.nophoto))
                    .apply(new RequestOptions().override(500, 400))
                    .into(holder.img_Category_Product);

        } else {


            Glide.with(context).load(currentItem.getImages().get(0).getSrc())
                    .apply(new RequestOptions().override(500, 400))
                    .into(holder.img_Category_Product);
        }

        mDb = AppDatabase.getInstance(context.getApplicationContext());
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                ProductDatum datum = mDb.ProductDao().fetchById(currentItem.getName());
                if (datum != null) {
                    holder.chk_Category_Favorite.setChecked(true);
                } else {
                    holder.chk_Category_Favorite.setChecked(false);
                }
            }
        });
        holder.chk_Category_Favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.chk_Category_Favorite.isChecked()) {
                    String message = context.getResources().getString(R.string.AddedSuccessfully);
                    AppExecutors.getInstance().diskIO().execute(() -> {

                        mDb.ProductDao().insertProductFav(currentItem);

                    });
                    Snackbar.make(v, message, Snackbar.LENGTH_SHORT).addCallback(new Snackbar.Callback() {
                        @Override
                        public void onDismissed(Snackbar transientBottomBar, int event) {
                            super.onDismissed(transientBottomBar, event);

                        }
                    }).show();

                } else {
                    String messageDelete = context.getResources().getString(R.string.Deleted);
                    AppExecutors.getInstance().diskIO().execute(() -> {

                        mDb.ProductDao().deleteProductFav(currentItem);

                    });
                    Snackbar.make(v, messageDelete, Snackbar.LENGTH_SHORT).addCallback(new Snackbar.Callback() {
                        @Override
                        public void onDismissed(Snackbar transientBottomBar, int event) {
                            super.onDismissed(transientBottomBar, event);

                        }
                    }).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (OffersProductsList == null) {
            return 0;
        }
        return OffersProductsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img_Category_Product, img_Category_Cart;
        TextView txt_Category_product_name, txt_Category_name, txt_Category_product_Price, txt_Category_product_regular_price, tv_label_Doller;
        CheckBox chk_Category_Favorite;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_Category_Product = itemView.findViewById(R.id.img_Category_Product);
            img_Category_Cart = itemView.findViewById(R.id.img_Category_Cart);
            txt_Category_product_name = itemView.findViewById(R.id.txt_Category_product_name);
            txt_Category_name = itemView.findViewById(R.id.txt_Category_name);
            txt_Category_product_Price = itemView.findViewById(R.id.txt_Category_product_Price);
            txt_Category_product_regular_price = itemView.findViewById(R.id.txt_Category_product_regular_price);
            tv_label_Doller = itemView.findViewById(R.id.tv_label_Doller);
            chk_Category_Favorite = itemView.findViewById(R.id.chk_Category_Favorite);
        }
    }
}
