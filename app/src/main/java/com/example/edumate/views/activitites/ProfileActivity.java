package com.example.edumate.views.activitites;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.example.edumate.R;
import androidx.appcompat.app.AppCompatActivity;





public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        LinearLayout changePassword = findViewById(R.id.profile_changePassword);
        changePassword.setOnClickListener(v -> startActivity(new Intent(this, ChangePasswordActivity.class)));
    }
}