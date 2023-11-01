package com.example.edumate.views.activitites;


import android.content.Intent;
import android.os.Bundle;
import com.example.edumate.R;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;





public class IntroActivity extends AppCompatActivity {
    private AppCompatButton introBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        introBtn=findViewById(R.id.options_team_btn_back);

        introBtn.setOnClickListener(v -> startActivity(new Intent(IntroActivity.this, MainActivity.class)));
    }
}