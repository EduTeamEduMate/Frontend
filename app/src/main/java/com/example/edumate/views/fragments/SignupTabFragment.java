package com.example.edumate.views.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.edumate.R;

public class SignupTabFragment extends Fragment {

    EditText nameEditText, emailEditText, passwordEditText, confirmPasswordEditText;

    private OnSignupInteractionListener listener;

    public interface OnSignupInteractionListener {
        void onNavigateToLogin();
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnSignupInteractionListener) {
            listener = (OnSignupInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnSignupInteractionListener");
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup_tab, container, false);

        nameEditText = view.findViewById(R.id.signup_name);
        emailEditText = view.findViewById(R.id.signup_email);
        passwordEditText = view.findViewById(R.id.signup_password);
        confirmPasswordEditText = view.findViewById(R.id.signup_confirm);

//        final View rootView = view.findViewById(R.id.signup_root_layout);
//        rootView.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
//            int heightDiff = rootView.getRootView().getHeight() - rootView.getHeight();
//            if (heightDiff > 10) { // arbitrary value, adjust as needed
//                rootView.setPadding(0, 100, 0, heightDiff);
//            } else {
//                rootView.setPadding(0, 100, 0, 0);
//            }
//        });

        Button signupButton = view.findViewById(R.id.signup_button);

        signupButton.setOnClickListener(v -> {
            boolean isAllFieldsChecked = CheckAllFields();

            if (isAllFieldsChecked) {
                listener.onNavigateToLogin();
            }
        });

        return view;
    }

    private boolean CheckAllFields() {
        String name = nameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString();
        String confirmPassword = confirmPasswordEditText.getText().toString();

        if (name.isEmpty()) {
            nameEditText.setError("Name cannot be empty");
            return false;
        }

        if (email.isEmpty() || !isEmailValid(email)) {
            emailEditText.setError("Invalid or empty email");
            return false;
        }

        if (password.isEmpty() || password.length() < 8) {
            passwordEditText.setError("Password must be at least 8 characters");
            return false;
        }

        if (!password.equals(confirmPassword)) {
            confirmPasswordEditText.setError("Passwords do not match");
            return false;
        }

        return true;
    }

    private boolean isEmailValid(String email) {
        // You can implement your email validation logic here.
        return email.matches("^[a-zA-Z0-9._-]+@mail.aub.edu$");
    }
}