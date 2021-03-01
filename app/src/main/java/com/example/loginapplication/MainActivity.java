package com.example.loginapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {

    private EditText Name;
    private EditText Password;
    private TextView Info;
    private Button Login;
    private int counter=4;

    private Button second;


    TextView text,errorText;

    Button show;

    Credentials credentials=new Credentials("admin","1234");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        second=findViewById(R.id.second);

        Name = (EditText)findViewById(R.id.etName);
        Password = (EditText)findViewById(R.id.etPassword);
        Info = (TextView) findViewById(R.id.tvinfo);
        Login = (Button) findViewById(R.id.btnLogin);


        Info.setText("No of attempts remaining: 4");

        credentials.setPassword("1234");
        credentials.setUsername("splitsy");


        text = (TextView) findViewById(R.id.textView);

        errorText = (TextView) findViewById(R.id.textView2);

        show = (Button) findViewById(R.id.button);
        show.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View view) {

                new Async().execute();

            }

        });

        second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, ConActivity.class);
                startActivity(intent);
            }
        });





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
            Intent intent = new Intent(MainActivity.this, ConActivity.class);
            startActivity(intent);
        }else{
            counter--;
            Info.setText("No of attempts remaining: "+ String.valueOf(counter));
            if (counter == 0) {
                Login.setEnabled(false);
            }
        }}




    //JDBC
    class Async extends AsyncTask<Void, Void, Void> {


        String records = "", error = "";

        @Override

        protected Void doInBackground(Void... voids) {

            try {

                Class.forName("com.mysql.jdbc.Driver");

                Connection connection = DriverManager.getConnection("jdbc:mysql://192.168.102.1", "sysadmin", "Splitsy14");

                Statement statement = connection.createStatement();

                ResultSet resultSet = statement.executeQuery("SELECT * FROM members");

                while (resultSet.next()) {

                    records += resultSet.getString(1) + " " + resultSet.getString(2) + "\n";

                }

            } catch (Exception e) {

                error = e.toString();

            }

            return null;

        }

        @Override

        protected void onPostExecute(Void aVoid) {

            text.setText(records);

            if (error != "")

                errorText.setText(error);

            super.onPostExecute(aVoid);

        }


    }
}
