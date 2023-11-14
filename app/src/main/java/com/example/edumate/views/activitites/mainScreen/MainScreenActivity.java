package com.example.edumate.views.activitites.mainScreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.edumate.R;
import com.example.edumate.views.activitites.OptionsActivity;
import com.example.edumate.views.adapters.TabsAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen_activity);

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        ViewPager2 viewPager = findViewById(R.id.viewPager);

        TabsAdapter tabsAdapter = new TabsAdapter(this);
        viewPager.setAdapter(tabsAdapter);



        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {
                    switch (position) {
                        case 0:
                            tab.setText("Generate");
                            break;
                        case 1:
                            tab.setText("History");
                            break;
                    }
                });
        tabLayoutMediator.attach();

        LinearLayout goToOptionsScreen = findViewById(R.id.goToOptionsScreen);

        goToOptionsScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainScreenActivity.this, OptionsActivity.class);
                startActivity(intent);
            }
        });
    }


}