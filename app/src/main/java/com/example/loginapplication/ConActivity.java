
package com.example.loginapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConActivity extends AppCompatActivity {

    TextView textView;
    EditText editName, editPassword, editEmail;

    //get host ip address hostname -I | awk '{print $1}'
    //url for database connection
    private static final String DB_URL="jdbc:mysql://192.168.102.104:3306/users";
    private static final String USER="root"; //   user
    private static final String PASS="Splitsy14"; //  password


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
            textView.setText("Please wait, inserting data into database.....");
        }

        @Override
        protected String doInBackground(String... strings) {
            if(txtName.trim().equals("") || txtPassword.trim().equals("") || txtEmail.trim().equals(""))
            msg= "Please enter all fields....";
            else {
                try {
                    //JDBC driver
                    Class.forName("com.mysql.cj.jdbc.Driver");

                    //connecting to mysql database using driver
                    Connection conn = DriverManager.getConnection("jdbc:mysql://192.168.102.104:3306/users",
                            "root", "Splitsy14");

                    if (conn != null) {
                        msg = "connected ";
                    }
                    if (conn == null) {
                        msg = "error, problem with connection..";
                    } else {
                        //insert into database table
                        String query1 = "insert into members(name,password,email) values(' " + txtName + " ',' " + txtPassword + " ',' " + txtEmail + " ')";
                        Statement stmt1 = conn.createStatement();
                        stmt1.executeUpdate(query1);

                   /*
                   //get data from database
                   String query="select * from members";
                    Statement statement=conn.createStatement();
                    ResultSet resultSet=statement.executeQuery(query);
                    while(resultSet.next()){
                        String data="";
                        for(int a=1;a<9;a++){
                            data+=resultSet.getString(a)+" : ";
                        }
                    }*/
                        msg = " success! ";
                    }
                    conn.close();

                } catch (ClassNotFoundException e) {
                    msg = "connection error";
                    e.printStackTrace();
                } catch (SQLException throwables) {
                    msg = "error!!!";
                    throwables.printStackTrace();
                }}
                return msg;
            }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            textView.setText(msg);
        }
    }




}
