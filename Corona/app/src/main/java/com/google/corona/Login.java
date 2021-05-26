package com.google.corona;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Login extends AppCompatActivity {

    private String userName;
    private String password;
    private EditText userNameText;
    private EditText pwText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userNameText = findViewById(R.id.email);
        pwText = findViewById(R.id.pw);
    }


    public void signIn(View view) {

        userName = userNameText.getText().toString();
        password=pwText.getText().toString();

        if(userName.isEmpty()){
            userNameText.setError("Please Enter an username");

            Intent intent = new Intent(this,Admin.class);
            startActivity(intent);
            finish();

        }else if(password.isEmpty()){
            pwText.setError("Please Enter your password");

        }else if(password.length() < 2){

            pwText.setError("Please Enter password minimum of 8 Characters");

        }else{

            if(userName.equals("admin") && password.equals("12345678")){

                Intent intent = new Intent(this,Admin.class);
                startActivity(intent);
                finish();

            }else if(userName.equals("user") && password.equals("12345678")){

                Intent intent = new Intent(this,User.class);
                startActivity(intent);
                finish();

            }else{
                pwText.setError("Wrong username or password");
            }


        }


    }


}