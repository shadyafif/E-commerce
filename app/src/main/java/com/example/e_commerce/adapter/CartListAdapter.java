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
import com.example.e_commerce.data.Room.AppDatabase;
import com.example.e_commerce.data.Room.AppExecutors;
import com.example.e_commerce.data.model.CartDetails.LineItem;
import com.example.e_commerce.data.network.CartInterface;
import java.util.ArrayList;
import java.util.List;


public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.ViewHolder> {
    List<LineItem> CartProductsList = new ArrayList<>();
    private Context context;
    private AppDatabase mDb;
    private CartInterface callBack;
    int totalItemPrice,billTotal;
    int Index = 0;


    public CartListAdapter(Context context, CartInterface cartInterface) {
        this.context = context;
        this.callBack = cartInterface;
    }

    public List<LineItem> getCartProductsList() {
        return CartProductsList;
    }

    public void setCartProductsList(List<LineItem> cartProductsList) {
        CartProductsList = cartProductsList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_cart, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        LineItem currentItem = CartProductsList.get(position);
        // Displays values on views
        holder.tv_Cart_title.setText(currentItem.getName());
        holder.tv_Cart_price.setText(String.valueOf(currentItem.getPrice()));
        holder.tv_category_cart.setText(currentItem.getCategory());
        holder.tv_qty.setText(String.valueOf(currentItem.getQuantity()));
        // Get url of the image
        String url = currentItem.getImage();
        if (url == null) {
            Glide.with(context).load(context.getDrawable(R.drawable.nophoto))
                    .apply(new RequestOptions().override(500, 400))
                    .into(holder.iv_Cart_product);

        } else {


            Glide.with(context).load(url)
                    .apply(new RequestOptions().override(500, 400))
                    .into(holder.iv_Cart_product);
        }


        // Update total price based on quantity
        int qty = Integer.parseInt(holder.tv_qty.getText().toString());
        int itemPrice = currentItem.getPrice();
        totalItemPrice = itemPrice * qty;
        holder.tv_Cart_price.setText(String.valueOf(totalItemPrice));
        mDb = AppDatabase.getInstance(context);


        // Update bill total in database
        AppExecutors.getInstance().diskIO().execute(() -> {
            if (Index >= 1) {
                billTotal = mDb.ProductDao().getSum();
            } else {
                billTotal = mDb.ProductDao().getSum();
                callBack.setValue(billTotal);
            }
            Index++;
        });


        // Click listener on Add button
        holder.iv_Cart_add.setOnClickListener(view -> {
            // Get quantity value
            int qtyIndex = Integer.parseInt(holder.tv_qty.getText().toString());
            // Update quantity value
            qtyIndex++;
            holder.tv_qty.setText(String.valueOf(qtyIndex));
            // Get total price of current item
            int total = Integer.parseInt(holder.tv_Cart_price.getText().toString());
            // Update total price of current item
            total += itemPrice;
            holder.tv_Cart_price.setText(String.valueOf(total));
            // Update bill total of all items
            billTotal += itemPrice;
            callBack.setValue(billTotal);
        });


        // Click listener on Subtract button
        holder.iv_Cart_subtract.setOnClickListener(view -> {
            // Get quantity value
            int qtyIndex = Integer.parseInt(holder.tv_qty.getText().toString());
            // Disable clickable on subtract button
            if (qtyIndex == 1) {
                holder.tv_qty.setText("1");

            } else {
                // Update quantity value
                qtyIndex--;
                holder.tv_qty.setText(String.valueOf(qtyIndex));

                // Get total price of current item
                int total = Integer.parseInt(holder.tv_Cart_price.getText().toString());
                // Update total price of current item
                total -= itemPrice;
                holder.tv_Cart_price.setText(String.valueOf(total));
                // Update bill total of all items
                billTotal -= itemPrice;
                callBack.setValue(billTotal);
            }
        });

        holder.iv_Cart_Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int price = currentItem.getPrice();
                int total = price * currentItem.getQuantity();
                AppExecutors.getInstance().diskIO().execute(() -> {
                    billTotal = mDb.ProductDao().getSum();
                    mDb.ProductDao().deleteCart(CartProductsList.get(position));

                });
                callBack.setValue(billTotal - total);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (CartProductsList == null) {
            return 0;
        }
        return CartProductsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_Cart_product, iv_Cart_subtract, iv_Cart_add, iv_Cart_Delete;
        TextView tv_Cart_title, tv_category_cart, tv_qty, tv_Cart_price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_Cart_product = itemView.findViewById(R.id.iv_Cart_product);
            iv_Cart_subtract = itemView.findViewById(R.id.iv_Cart_subtract);
            iv_Cart_add = itemView.findViewById(R.id.iv_Cart_add);
            iv_Cart_Delete = itemView.findViewById(R.id.iv_Cart_Delete);
            tv_Cart_title = itemView.findViewById(R.id.tv_Cart_title);
            tv_category_cart = itemView.findViewById(R.id.tv_category_cart);
            tv_qty = itemView.findViewById(R.id.tv_qty);
            tv_Cart_price = itemView.findViewById(R.id.tv_Cart_price);
        }
    }
}
