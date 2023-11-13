package com.example.edumate.views.activitites;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.example.edumate.R;


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
                Intent intent = new Intent(ChangeNameActivity.this, ProfileActivity.class);
                startActivity(intent);
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
