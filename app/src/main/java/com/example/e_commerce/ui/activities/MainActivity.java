package com.example.e_commerce.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.e_commerce.R;
import com.example.e_commerce.ui.fragments.AccountFragment;
import com.example.e_commerce.ui.fragments.CatagoryFragment;
import com.example.e_commerce.ui.fragments.HomeFragment;
import com.example.e_commerce.ui.fragments.InfoFragment;
import com.example.e_commerce.ui.fragments.OffersFragment;
import com.example.e_commerce.utlies.CurvedBottomNavigationView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import static com.example.e_commerce.utlies.Helper.Add;
import static com.example.e_commerce.utlies.Helper.Replace;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private CurvedBottomNavigationView curvedBottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        curvedBottomNavigationView = findViewById(R.id.curve_bottom_navigation_view);
        curvedBottomNavigationView.inflateMenu(R.menu.bottom_nav_menu);
        curvedBottomNavigationView.getChildAt(0);
        curvedBottomNavigationView.setSelectedItemId(R.id.navigation_home);
        curvedBottomNavigationView.setOnNavigationItemSelectedListener(this);
        HomeFragment homeFragment = new HomeFragment();
        Add(homeFragment, R.id.FragmentLoad, getSupportFragmentManager().beginTransaction());
        FloatingActionButton fab = findViewById(R.id.fab_button);
        fab.setOnClickListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_catagory:
                CatagoryFragment catagoryFragment = new CatagoryFragment();
                Replace(catagoryFragment, R.id.FragmentLoad, getSupportFragmentManager().beginTransaction());
                break;

            case R.id.navigation_account:
                AccountFragment accountFragment = new AccountFragment();
                Replace(accountFragment, R.id.FragmentLoad, getSupportFragmentManager().beginTransaction());
                break;

            case R.id.navigation_offers:
                OffersFragment offersFragment = new OffersFragment();
                Replace(offersFragment, R.id.FragmentLoad, getSupportFragmentManager().beginTransaction());
                break;

            case R.id.navigation_info:
                InfoFragment infoFragment = new InfoFragment();
                Replace(infoFragment, R.id.FragmentLoad, getSupportFragmentManager().beginTransaction());
                break;

            default:
                break;
        }

        return true;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fab_button) {
            HomeFragment homeFragment = new HomeFragment();
            Replace(homeFragment, R.id.FragmentLoad, getSupportFragmentManager().beginTransaction());
        }
    }
}