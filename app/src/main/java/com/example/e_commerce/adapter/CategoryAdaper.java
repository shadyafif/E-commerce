package com.example.e_commerce.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
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
import com.example.e_commerce.data.model.categoriesList.CategoryDatum;
import com.example.e_commerce.ui.activities.MainActivity;
import com.example.e_commerce.ui.fragments.Category_Product_Fragment;

import java.util.ArrayList;
import java.util.List;

import static com.example.e_commerce.utlies.Helper.Replace;

public class CategoryAdaper extends RecyclerView.Adapter<CategoryAdaper.ViewHolder> {

    private List<CategoryDatum> CategoryList = new ArrayList<>();
    Context context;

    public CategoryAdaper(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.txt_Category.setText(CategoryList.get(position).getName());


        if (CategoryList.get(position).getImage() == null) {

            Glide.with(context).load(context.getDrawable(R.drawable.nophoto))
                    .apply(new RequestOptions().override(500, 400))
                    .into(holder.img_Category);

        } else {
            String web = CategoryList.get(position).getImage().getSrc();
            web = web.replace(" ", "%20");
            Glide.with(context).load(web)
                    .apply(new RequestOptions().override(500, 400))
                    .into(holder.img_Category);
        }
        holder.img_Category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity = (MainActivity) context;
                Bundle bundle=new Bundle();
                String id =CategoryList.get(position).getId()+"";
                bundle.putString("id",id);
                Category_Product_Fragment fragment=new Category_Product_Fragment();
                fragment.setArguments(bundle);
                Replace(fragment, R.id.FragmentLoad,mainActivity.getSupportFragmentManager().beginTransaction());
            }
        });

    }

    @Override
    public int getItemCount() {
        if (CategoryList == null) {
            return 0;
        }
        return CategoryList.size();
    }

    public void setCategoryList(List<CategoryDatum> CategoryList) {
        this.CategoryList = CategoryList;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img_Category;
        TextView txt_Category;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_Category = itemView.findViewById(R.id.img_Category);
            txt_Category = itemView.findViewById(R.id.txt_Category);
        }
    }




}
