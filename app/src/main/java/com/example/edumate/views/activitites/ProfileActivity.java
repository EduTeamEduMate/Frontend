package com.example.edumate.views.activitites;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.edumate.R;
import com.example.edumate.models.SUserData;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;


public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        TextView name = findViewById(R.id.textView4);
        name.setText(SUserData.getInstance().getUsername());

        TextView email = findViewById(R.id.textView5);
        email.setText(SUserData.getInstance().getEmail());

        LinearLayout userName = findViewById(R.id.profile_username);
        userName.setOnClickListener(v -> startActivity(new Intent(this, ChangeNameActivity.class)));

        ConstraintLayout backbtn = findViewById(R.id.profile_back_btn);
        backbtn.setOnClickListener(v -> startActivity(new Intent(this, OptionsActivity.class)));

        LinearLayout changePassword = findViewById(R.id.profile_changePassword);
        changePassword.setOnClickListener(v -> startActivity(new Intent(this, ChangePasswordActivity.class)));

        CardView logout = findViewById(R.id.profile_logout);
        logout.setOnClickListener(v -> startActivity(new Intent(this, AuthenticationActivity.class)));
    }
}