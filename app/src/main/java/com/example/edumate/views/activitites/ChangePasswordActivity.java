package com.example.edumate.views.activitites;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.edumate.R;
import com.example.edumate.models.SUserData;
import com.example.edumate.models.User;
import com.example.edumate.network.ApiService;
import com.example.edumate.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends AppCompatActivity {

    EditText oldPasswordEditText, newPasswordEditText, confirmNewPasswordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password);

        oldPasswordEditText = findViewById(R.id.old_password);
        newPasswordEditText = findViewById(R.id.new_password);
        confirmNewPasswordEditText = findViewById(R.id.confirm_new_password);

        Button changePasswordButton = findViewById(R.id.change_password_button);

        ConstraintLayout backButton = findViewById(R.id.password_back_btn);
        backButton.setOnClickListener(v -> finish());

        changePasswordButton.setOnClickListener(v -> {
            boolean isAllFieldsChecked = checkAllFields();

            if (isAllFieldsChecked) {
                String newPassword = newPasswordEditText.getText().toString().trim();
                int userId = SUserData.getInstance().getId(); // Get user ID from stored user data

                User updatedUser = new User();
                updatedUser.setName(SUserData.getInstance().getUsername());
                updatedUser.setEmail(SUserData.getInstance().getEmail());
                updatedUser.setPassword(newPassword);

                ApiService apiService = RetrofitClient.getApiService();
                Call<User> call = apiService.updateUserPassword(userId, updatedUser);

                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (response.isSuccessful()) {
                            Intent intent = new Intent(ChangePasswordActivity.this, ProfileActivity.class);
                            startActivity(intent);
                        } else {
                            newPasswordEditText.setText("fail 1");
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        newPasswordEditText.setText("fail 2");
                    }
                });
            }
        });
    }

    private boolean checkAllFields() {
        String oldPassword = oldPasswordEditText.getText().toString();
        String newPassword = newPasswordEditText.getText().toString();
        String confirmNewPassword = confirmNewPasswordEditText.getText().toString();

        if (oldPassword.isEmpty()) {
            oldPasswordEditText.setError("Old Password cannot be empty");
            return false;
        }

        if (newPassword.isEmpty() || newPassword.length() < 8) {
            newPasswordEditText.setError("New Password must be at least 8 characters");
            return false;
        }

        if (!newPassword.equals(confirmNewPassword)) {
            confirmNewPasswordEditText.setError("New Passwords do not match");
            return false;
        }

        return true;
    }
}
