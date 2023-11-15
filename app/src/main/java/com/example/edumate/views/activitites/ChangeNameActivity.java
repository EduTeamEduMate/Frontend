package com.example.edumate.views.activitites;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.example.edumate.R;
import com.example.edumate.models.SUserData;
import com.example.edumate.models.User;
import com.example.edumate.network.ApiService;
import com.example.edumate.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ChangeNameActivity extends AppCompatActivity {

    EditText oldNameEditText, newNameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_name);

        oldNameEditText = findViewById(R.id.old_name);
        newNameEditText = findViewById(R.id.new_name);

        Button changeButton = findViewById(R.id.change_button);

        changeButton.setOnClickListener(v -> {
            boolean isAllFieldsChecked = checkAllFields();

            if (isAllFieldsChecked) {
                String newName = newNameEditText.getText().toString().trim();
                int userId = SUserData.getInstance().getId(); // Get user ID from stored user data

                User updatedUser = new User();
                updatedUser.setName(newName);
                updatedUser.setEmail(SUserData.getInstance().getEmail());

                ApiService apiService = RetrofitClient.getApiService();
                Call<User> call = apiService.updateUser(userId, updatedUser);

                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (response.isSuccessful()) {
                            SUserData.getInstance().setUser(newName,SUserData.getInstance().getEmail(), SUserData.getInstance().getId());
                            Intent intent = new Intent(ChangeNameActivity.this, ProfileActivity.class);
                            startActivity(intent);
                        } else {
                            newNameEditText.setText("fail 1");
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        newNameEditText.setText("fail 2");
                    }
                });
            }
        });
    }

    private boolean checkAllFields() {
        String oldName = oldNameEditText.getText().toString().trim();
        String newName = newNameEditText.getText().toString().trim();

        if (oldName.isEmpty()) {
            oldNameEditText.setError("Old Name cannot be empty");
            return false;
        }

        return true;
    }
}
