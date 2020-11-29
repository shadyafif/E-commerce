package com.example.e_commerce.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.e_commerce.R;
import com.example.e_commerce.data.model.ProductsDetails.ProductDatum;

import java.util.ArrayList;
import java.util.List;

public class WishListAdapter extends RecyclerView.Adapter<WishListAdapter.ViewHolder> {
    private List<ProductDatum> WishListProductsList = new ArrayList<>();
    private Context context;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_wish_list, parent, false);
        return new ViewHolder(v);
    }

    public WishListAdapter(Context context) {
        this.context = context;
    }

    public List<ProductDatum> getWishListProductsList() {
        return WishListProductsList;
    }

    public void setWishListProductsList(List<ProductDatum> wishListProductsList) {
        WishListProductsList = wishListProductsList;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductDatum currentItem = WishListProductsList.get(position);
        holder.tv_WishList_title.setText(currentItem.getName());
        holder.tv_WishList_price.setText(currentItem.getRegularPrice());

        if (currentItem.getImages().size() == 0) {
            Glide.with(context).load(context.getDrawable(R.drawable.nophoto))
                    .apply(new RequestOptions().override(500, 400))
                    .into(holder.iv_WishList_Fav);

        } else {


            Glide.with(context).load(currentItem.getImages().get(0).getSrc())
                    .apply(new RequestOptions().override(500, 400))
                    .into(holder.iv_WishList_Fav);
        }

    }

    @Override
    public int getItemCount() {
        if (WishListProductsList == null) {
            return 0;
        }
        return WishListProductsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_WishList_Fav;
        TextView tv_WishList_title, tv_WishList_price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_WishList_Fav = itemView.findViewById(R.id.iv_WishList_Fav);
            tv_WishList_title = itemView.findViewById(R.id.tv_WishList_title);
            tv_WishList_price = itemView.findViewById(R.id.tv_WishList_price);
        }
    }
}
