package com.kano.btlnhom2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.kano.btlnhom2.Adapter.ViewpagerAdapter;
import com.kano.btlnhom2.utils.SystemUtils;

public class MainActivity extends AppCompatActivity {

    ViewPager2 viewPager;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SystemUtils.setLocale(this);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Drawable drawable= getResources().getDrawable(R.drawable.baseline_density_medium_24);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(drawable);
        viewPager = findViewById(R.id.viewpager);
        bottomNavigationView = findViewById(R.id.bottom_nav_bar);

        ViewpagerAdapter viewpagerAdapter = new ViewpagerAdapter(this);
        viewPager.setAdapter(viewpagerAdapter);

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position){
                    case 0:
                        bottomNavigationView.getMenu().findItem(R.id.nav_1).setChecked(true);
                        break;
                    case 1:
                        bottomNavigationView.getMenu().findItem(R.id.nav_2).setChecked(true);
                        break;
                    case 2:
                        bottomNavigationView.getMenu().findItem(R.id.nav_3).setChecked(true);
                        break;
                    case 3:
                        bottomNavigationView.getMenu().findItem(R.id.nav_4).setChecked(true);
                        break;
                    case 4:
                        bottomNavigationView.getMenu().findItem(R.id.nav_5).setChecked(true);
                        break;
                        
                }
            }
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.nav_1){
                    viewPager.setCurrentItem(0);
                }
                else if (item.getItemId()== R.id.nav_2) {
                    viewPager.setCurrentItem(1);
                }
                else if (item.getItemId()== R.id.nav_3) {
                    viewPager.setCurrentItem(2);
                }
                else if (item.getItemId()== R.id.nav_4) {
                    viewPager.setCurrentItem(3);
                }else if (item.getItemId()== R.id.nav_5) {
                    viewPager.setCurrentItem(4);
                }

                return false;
            }
        });

    }
}
