package com.example.edumate.views.activitites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.example.edumate.R;
import com.example.edumate.views.adapters.ViewPagerAdapter;
import com.example.edumate.views.fragments.SignupTabFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

public class AuthenticationActivity extends AppCompatActivity
        implements SignupTabFragment.OnSignupInteractionListener {

    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private com.example.edumate.views.adapters.ViewPagerAdapter adapter;


    @Override
    public void onNavigateToLogin() {
        viewPager2.setCurrentItem(0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authentication_activity);

        Objects.requireNonNull(getSupportActionBar()).hide();

        tabLayout = findViewById(R.id.tab_layout);
        viewPager2 = findViewById(R.id.view_pager);

        tabLayout.addTab(tabLayout.newTab().setText("Login"));
        tabLayout.addTab(tabLayout.newTab().setText("Signup"));

        FragmentManager fragmentManager = getSupportFragmentManager();
        adapter = new ViewPagerAdapter(fragmentManager, getLifecycle());
        viewPager2.setAdapter(adapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });

    }
}