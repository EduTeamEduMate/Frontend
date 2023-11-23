package com.example.edumate.views.fragments;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.edumate.R;
import com.example.edumate.models.TokenResponse;
import com.example.edumate.models.User;
import com.example.edumate.models.SUserData;
import com.example.edumate.models.UserDataFromLogin;
import com.example.edumate.network.ApiService;
import com.example.edumate.network.RetrofitClient;
import com.example.edumate.views.activitites.mainScreen.MainScreenActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString();

                // Make a network call to login
                ApiService apiService = RetrofitClient.getApiService();
                Call<TokenResponse> call = apiService.loginUser(email, password);

                call.enqueue(new Callback<TokenResponse>() {
                    @Override
                    public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                        if (response.isSuccessful()) {
                            UserDataFromLogin user = response.body().getUser();
                            // Save user data
                            SUserData.getInstance().setUser(user.getName(), user.getEmail(), user.getId());
                            Intent intent = new Intent(getActivity(), MainScreenActivity.class);
                            startActivity(intent);
                        } else {
                            emailEditText.setText(response.message());
                            Toast.makeText(getActivity(), "Signup failed: " + response.message(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<TokenResponse> call, Throwable t) {
                        emailEditText.setText(t.getMessage());
                        Toast.makeText(getActivity(), "Network error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
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
