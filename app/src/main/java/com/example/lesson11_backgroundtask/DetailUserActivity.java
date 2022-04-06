package com.example.lesson11_backgroundtask;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class DetailUserActivity extends AppCompatActivity {
    TextView nameText, emailText, genderText, statusText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_user);

        getSupportActionBar().hide();
        nameText = findViewById(R.id.name_text);
        emailText = findViewById(R.id.email_text);
        genderText = findViewById(R.id.gender_text);
        statusText = findViewById(R.id.status_text);

        Bundle bundle = getIntent().getExtras();
        if(bundle == null){
            return;
        }
        User user = (User) bundle.get("User");
        nameText.setText("Name: ".concat(user.getName()));
        emailText.setText("Email: ".concat(user.getEmail()));
        genderText.setText("Gender: ".concat(user.getGender()));
        statusText.setText("Status: ".concat(user.getStatus()));

    }
}