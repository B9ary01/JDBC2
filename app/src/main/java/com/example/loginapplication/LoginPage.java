package com.example.loginapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.LayoutInflaterCompat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loginapplication.Retrofit.RetrofitCon;
import com.example.loginapplication.Retrofit.myNode;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.dialog.MaterialDialogs;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginPage extends AppCompatActivity {
    TextView nametext;


    EditText edit_email, edit_password,edit_name;
    Button btn_register,btn_login;


  myNode myAPI;
  CompositeDisposable compositeDisposable=new CompositeDisposable();

    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }


    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);


        String name = getIntent().getStringExtra("name");
        nametext = (TextView) findViewById(R.id.name);
        nametext.setText(name);


        // init API
        Retrofit retrofit;

        retrofit = RetrofitCon.getInstance();
       myAPI = retrofit.create(myNode.class);




        myNode myNode;
        String baseUrl="https://192.168.102.104:8000";

        //view
        btn_register = findViewById(R.id.btn_register);
        btn_login = findViewById(R.id.btn_login);

        edit_name = findViewById(R.id.edit_name);
        edit_email = findViewById(R.id.edit_email);
        edit_password = findViewById(R.id.edit_password);




        /////
       /* retrofit=new Retrofit.Builder()
                .baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build();
        myNode=retrofit.create(myNode.class);
        */
        ////

        //search/login
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser1(edit_name.getText().toString());
            }
        });

        //register
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser(edit_name.getText().toString(), edit_password.getText().toString(),
                        edit_email.getText().toString());


            }
        });
    }
/*
    public void loginUser(View v) {
        String usrname=edit_name.getText().toString().trim();
      Call<String>call= myNode.loginUser(usrname);

       call.enqueue(new Callback<String>() {
           @Override
           public void onResponse(Call<String> call, Response<String> response) {

           }

           @Override
           public void onFailure(Call<String> call, Throwable t) {

           }
       });


    }*/


    private void registerUser(String email, String name, String password) {
        View enterDetails = LayoutInflater.from(LoginPage.this).inflate(R.layout.activity_login_page,null);

        /*new MaterialStyledDialog.Builder(this)
                .setTitle("a333!")
                .setDescription("What can we improve? Your feedback is always welcome.")
                .show();*/

    }

    private void loginUser1(String name) {
        compositeDisposable.add(myAPI.loginUser(name).subscribeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                if(s.contains("name")) {
                    Toast.makeText(LoginPage.this, "login success", Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(LoginPage.this,"+s",Toast.LENGTH_SHORT).show();
            }}
        },throwable -> {
            // Error Consumer
            Toast.makeText(LoginPage.this,"unknown",Toast.LENGTH_SHORT).show();

        }));
    }


    }