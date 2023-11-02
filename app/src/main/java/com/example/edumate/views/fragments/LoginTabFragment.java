package com.example.edumate.views.fragments;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import com.example.edumate.R;
import com.example.edumate.views.activitites.EmptyPageActivity;

public class LoginTabFragment extends Fragment {

    EditText emailEditText, passwordEditText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login_tab, container, false);

        emailEditText = view.findViewById(R.id.login_email);
        passwordEditText = view.findViewById(R.id.login_password);

        Button loginButton = view.findViewById(R.id.login_button);

        loginButton.setOnClickListener(v -> {
            boolean isAllFieldsChecked = CheckAllFields();

            if (isAllFieldsChecked) {
                // Navigate to the empty page (EmptyPageActivity)
                Intent intent = new Intent(getActivity(), EmptyPageActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private boolean CheckAllFields() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString();

        if (email.isEmpty() || !isEmailValid(email)) {
            emailEditText.setError("Invalid or empty email");
            return false;
        }

        if (password.isEmpty() || password.length() < 8) {
            passwordEditText.setError("Password must be at least 8 characters");
            return false;
        }

        return true;
    }

    private boolean isEmailValid(String email) {
        // You can implement your email validation logic here.
        return email.matches("^[a-zA-Z0-9._-]+@mail.aub.edu$");
    }
}
