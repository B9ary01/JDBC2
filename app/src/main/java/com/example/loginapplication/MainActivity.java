package com.example.loginapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText Name;
    private EditText Password;
    private TextView Info;
    private Button Login;
    private int counter=4;

    Credentials credentials=new Credentials("Admin","12345678");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Name = (EditText)findViewById(R.id.etName);
        Password = (EditText)findViewById(R.id.etPassword);
        Info = (TextView) findViewById(R.id.tvinfo);
        Login = (Button) findViewById(R.id.btnLogin);

        Info.setText("No of attempts remaining: 4");

        credentials.setPassword("1234");
        credentials.setUsername("Splitsy");


        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate(Name.getText().toString(), Password.getText().toString());
            }
        });
    }


    private void validate(String userName, String userPassword){
        if(userName.isEmpty() || userPassword.isEmpty()){
            Toast.makeText(MainActivity.this, "Please fill in the details correctly!", Toast.LENGTH_SHORT).show();
        }
        else if((userName.equals(credentials.getUsername()) && (userPassword.equals(credentials.getPassword())))){
            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            startActivity(intent);
        }else{
            counter--;
            Info.setText("No of attempts remaining: "+ String.valueOf(counter));
            if (counter == 0) {
                Login.setEnabled(false);
            }
        }
    }



}