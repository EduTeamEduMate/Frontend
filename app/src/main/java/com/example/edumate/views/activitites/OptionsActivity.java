package com.example.edumate.views.activitites;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.edumate.R;

public class OptionsActivity extends AppCompatActivity {
    private RecyclerView.Adapter adapterTrends;
    private RecyclerView recyclerViewTrends;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initRecyclerView();
        profileInit();
    }

    private void profileInit() {
        LinearLayout profileBtn = findViewById(R.id.profileBtn);
        profileBtn.setOnClickListener(v -> startActivity(new Intent(OptionsActivity.this,ProfileActivity.class)));
    }

    private void initRecyclerView() {
//        ArrayList<TrendsDomain> items = new ArrayList<>();
//        items.add(new TrendsDomain("Future in AI, What will\n" +
//                "tomorrow be like?", "The National", "trends"));
//        items.add(new TrendsDomain("Important points in\n" +
//                "concluding a work contract", "Reuters", "trends2"));
//        items.add(new TrendsDomain("Future in AI, What will\n" +
//                "tomorrow be like?", "The National", "trends"));

        //recyclerViewTrends = findViewById(R.id.view1);
        //recyclerViewTrends.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        //adapterTrends = new TrendsAdapter(items);
        //recyclerViewTrends.setAdapter(adapterTrends);
    }
}