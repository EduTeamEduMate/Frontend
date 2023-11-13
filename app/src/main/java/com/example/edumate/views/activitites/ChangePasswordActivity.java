package com.example.edumate.views.activitites;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.example.edumate.R;

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

        changePasswordButton.setOnClickListener(v -> {
            boolean isAllFieldsChecked = checkAllFields();

            if (isAllFieldsChecked) {
                // Add logic to change the password
                // For demonstration purposes, navigating to EmptyPageActivity
                // Replace with your logic for changing the password
                // Intent intent = new Intent(ChangePasswordActivity.this, EmptyPageActivity.class);
                // startActivity(intent);
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
