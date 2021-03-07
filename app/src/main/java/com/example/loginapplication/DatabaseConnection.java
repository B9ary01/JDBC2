package com.example.loginapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;


public class DatabaseConnection extends AppCompatActivity {

    EditText name,email,pass;
    Button register,login;
    ConnectionClass connectionClass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database_connection);

        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        name= (EditText) findViewById(R.id.name);
        email= (EditText) findViewById(R.id.email);
        pass= (EditText) findViewById(R.id.pass);
        register= (Button) findViewById(R.id.register);
        login= (Button) findViewById(R.id.login);

        connectionClass = new ConnectionClass();


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Doregister doregister = new Doregister();
                doregister.execute("");
            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dologin dologin=new Dologin();
                dologin.execute();
            }
        });


    }

    public class Doregister extends AsyncTask<String,String,String>
    {


        String namestr=name.getText().toString();
        Integer emailstr= Integer.valueOf(email.getText().toString());
        String passstr=pass.getText().toString();
        String z="";
        boolean isSuccess=false;

        @Override
        protected void onPreExecute() {
            z=" Loading, please wait ...";
        }

        @Override
        protected String doInBackground(String... params) {

            if(namestr.trim().equals("") ||passstr.trim().equals(""))
                z = "Please enter all fields....";
            else
            {
                try {
                    Connection con = connectionClass.CONN();
                    if (con == null) {
                        z = "Please check your internet connection";
                    } else {

                       //insert query
                        String query="insert into test9 values('"+namestr+"','"+passstr+"','"+emailstr+"')";

                        Statement stmt = con.createStatement();
                        stmt.executeUpdate(query);

                        z = "Register successfull";
                        isSuccess=true;


                    }
                }
                catch (Exception ex)
                {
                    isSuccess = false;
                    z = "Exceptions"+ex;
                }
            }
            return z;
        }

        @Override
        protected void onPostExecute(String s) {

            Toast.makeText(getBaseContext(),""+z,Toast.LENGTH_LONG).show();


            if(isSuccess) {
                startActivity(new Intent(DatabaseConnection.this, LoginPage.class));

            }



        }
    }


    private class Dologin extends AsyncTask<String,String,String>{


        String namestr=name.getText().toString();
        String emailstr=email.getText().toString();
        String passstr=pass.getText().toString();
        String z="";
        boolean isSuccess=false;

        String nm,em,password;


        @Override
        protected void onPreExecute() {

            Toast.makeText(getApplicationContext(), "loading.....", Toast.LENGTH_LONG).show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            if(namestr.trim().equals("")|| emailstr.trim().equals("") ||passstr.trim().equals(""))
                z = "Please enter all fields....";
            else
            {
                try {
                    Connection con = connectionClass.CONN();
                    if (con == null) {
                        z = "Please check your internet connection";
                    } else {

                        String query="insert into test4 where name='"+namestr+"' and email='"+emailstr+"' and password = '"+passstr+"'";


                        Statement stmt = con.createStatement();
                        // stmt.executeUpdate(query);


                        ResultSet rs=stmt.executeQuery(query);

                        while (rs.next())

                        {
                            nm= rs.getString(1);
                            em=rs.getString(2);
                            password=rs.getString(3);




                            if(nm.equals(namestr)&& em.equals(emailstr)&&password.equals(passstr))
                            {

                                isSuccess=true;
                                z = "Login successfull";

                            }

                            else

                                isSuccess=false;



                        }





                    }
                }
                catch (Exception ex)
                {
                    isSuccess = false;
                    z = "Exceptions"+ex;
                }
            }
            return z;        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(getBaseContext(),""+z,Toast.LENGTH_LONG).show();


            if(isSuccess) {

                Intent intent=new Intent(DatabaseConnection.this,LoginPage.class);

                intent.putExtra("name",namestr);

                startActivity(intent);
            }




        }
    }
}

/*

package com.example.loginapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class ConActivity extends AppCompatActivity {
//https://stackoverflow.com/questions/1713429/remote-mysql-access
    //https://appsbuilders.org/guides/android-studio-tutorial-register-and-login-with-nodejs-and-mysql/
    //https://github.com/jeesteeye/Real-Time

    TextView textView;
    EditText editName, editPassword, editEmail;


    private static final String DB_URL="jdbc:mysql://localhost:3306/lapsi?autoReconnect=true&useSSL=false";
    //"jdbc:mysql://localhost:3306//students?autoReconnect=true&useSSL=false",
    //                "root", "password")
    private static final String USER="root";
    private static final String PASS="binod111";
   // String query="select  * from students";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_con);

        textView=findViewById(R.id.result);
        editName=findViewById(R.id.editname);
        editEmail=findViewById(R.id.editemail);
        editPassword=findViewById(R.id.editpassword);

    }

    public void btnConn(View view){
        Send objSend=new Send();
        objSend.execute("");
    }

    private class Send extends AsyncTask<String, String, String>{
        String msg="";
        String txtName=editName.getText().toString();
        String txtPassword=editPassword.getText().toString();

        String txtEmail=editEmail.getText().toString();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            textView.setText("Please wait inserting data into database.....");
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Properties properties = new Properties();
                properties.put("connectTimeout", "2000");
                //192.168.0.17
                //192.168.30
                //92.168.102.104
                //192.168.0.33 (port 34706)
                //192.168.102.1
                //&useUnicode=true&characterEncoding=UTF-8
                //192.100.0.000:3306/DBname", "root", "root")
                Connection conn = DriverManager.getConnection(DB_URL,USER,PASS);

                //dbConnectionString = "jdbc:mysql://"+dbHost+":"+dbPort+"/"+dbTable+"?user="+dbUser+"&password="+dbPassword;
//                Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/users?user=root&password=Splitsy14",properties);

              if(conn==null){
                  msg="error, problem with connection..";
              }else{
               String query="insert into test4(name,password,email) values(' "+txtName+" ',' "+txtPassword+" ',' "+txtEmail+" ')";
                  Statement stmt=conn.createStatement();
                  stmt.executeUpdate(query);
                  msg="data inserted successfully! ";
              }
              conn.close();

            } catch (ClassNotFoundException e) {
                msg="connection error";
                e.printStackTrace();
            } catch (SQLException throwables) {
                msg="error!!!";
                throwables.printStackTrace();
            }
            return msg;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            textView.setText(msg);
        }
    }




}
 */