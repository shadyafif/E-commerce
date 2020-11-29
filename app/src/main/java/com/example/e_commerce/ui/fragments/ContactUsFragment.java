package com.example.e_commerce.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.e_commerce.R;
import com.example.e_commerce.databinding.FragmentContactUsBinding;
import com.example.e_commerce.utlies.Constants;

import java.util.Objects;

import static com.example.e_commerce.utlies.Helper.Replace;


public class ContactUsFragment extends Fragment implements View.OnClickListener {

    private FragmentContactUsBinding binding;

    public ContactUsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentContactUsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        InitViews();
        return view;
    }

    public void InitViews() {
        binding.toolbar.setTitle(Objects.requireNonNull(getContext()).getResources().getString(R.string.ContactUs));
        binding.toolbar.setTitleTextColor(getContext().getResources().getColor(R.color.colorBottomNav));
        binding.btnContactUsSend.setOnClickListener(this);
        binding.IvContactUsFace.setOnClickListener(this);
        binding.IvContactUsTwitter.setOnClickListener(this);
        binding.IvContactUsInsta.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_ContactUs_Send:

                break;

            case R.id.Iv_ContactUs_Face:
                Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
                String facebookUrl = getFacebookPageURL(Objects.requireNonNull(getContext()));
                facebookIntent.setData(Uri.parse(facebookUrl));
                startActivity(facebookIntent);

                break;

            case R.id.Iv_ContactUs_Twitter:
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?screen_name=" + Constants.Twitter_URL)));
                } catch (Exception e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/#!/" + Constants.Twitter_URL)));
                }
                break;

            case R.id.Iv_ContactUs_Insta:

                Intent inInsta = new Intent(Intent.ACTION_VIEW);
                inInsta.setData(Uri.parse(Constants.Insta_URL));
                startActivity(inInsta);
                break;

        }
    }

    public String getFacebookPageURL(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;
            if (versionCode >= 3002850) { //newer versions of fb app
                return "fb://facewebmodal/f?href=" + Constants.FACEBOOK_URL;
            } else { //older versions of fb app
                return "fb://page/" + Constants.FACEBOOK_PAGE_ID;
            }
        } catch (PackageManager.NameNotFoundException e) {
            return Constants.FACEBOOK_URL; //normal web url
        }
    }
}