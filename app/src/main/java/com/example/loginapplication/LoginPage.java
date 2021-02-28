package com.example.loginapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class LoginPage extends AppCompatActivity {
    TextView nametext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);


        String name=getIntent().getStringExtra("name");

        nametext= (TextView) findViewById(R.id.name);

        nametext.setText(name);

    }
}