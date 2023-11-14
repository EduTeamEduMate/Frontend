package com.example.edumate.views.activitites;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.edumate.R;
import com.example.edumate.views.activitites.mainScreen.MainScreenActivity;

public class OptionsActivity extends AppCompatActivity {
    private RecyclerView.Adapter adapterTrends;
    private RecyclerView recyclerViewTrends;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.options_activity);

        initRecyclerView();
        profileInit();


    }

    private void profileInit() {
        LinearLayout profileBtn = findViewById(R.id.profileBtn);
        profileBtn.setOnClickListener(v -> startActivity(new Intent(OptionsActivity.this,ProfileActivity.class)));

        LinearLayout ourTeam = findViewById(R.id.options_our_team);
        ourTeam.setOnClickListener(v -> startActivity(new Intent(this,OurTeam.class)));

        LinearLayout homebtn = findViewById(R.id.goToMainScreen);
        homebtn.setOnClickListener(v -> startActivity(new Intent(this, MainScreenActivity.class)));


    }

    private void initRecyclerView() {

    }
}