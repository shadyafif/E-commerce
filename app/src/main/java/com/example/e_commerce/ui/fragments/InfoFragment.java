package com.example.e_commerce.ui.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.e_commerce.R;
import com.example.e_commerce.databinding.FragmentInfoBinding;

import java.util.Objects;

import static com.example.e_commerce.utlies.Helper.Replace;


public class InfoFragment extends Fragment implements View.OnClickListener {

    private FragmentInfoBinding binding;

    public InfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentInfoBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        binding.toolbar.setTitle(view.getResources().getString(R.string.information));
        binding.toolbar.setTitleTextColor(view.getResources().getColor(R.color.colorBottomNav));
        binding.txtInfoUpdateAccount.setOnClickListener(this);
        binding.txtInfoWishLish.setOnClickListener(this);
        binding.txtInfoLanguage.setOnClickListener(this);
        binding.txtInfoRoles.setOnClickListener(this);
        binding.txtInfoShare.setOnClickListener(this);
        binding.txtInfoContactUs.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_Info_UpdateAccount:

                break;

            case R.id.txt_Info_WishLish:
                WishListFragment wishListFragment = new WishListFragment();
                Replace(wishListFragment, R.id.FragmentLoad, Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction());
                break;

            case R.id.txt_Info_Language:

                break;

            case R.id.txt_Info_Roles:

                break;

            case R.id.txt_Info_Share:

                break;

            case R.id.txt_Info_ContactUs:
                ContactUsFragment contactUsFragment = new ContactUsFragment();
                Replace(contactUsFragment, R.id.FragmentLoad, Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction());
                break;

        }
    }
}
