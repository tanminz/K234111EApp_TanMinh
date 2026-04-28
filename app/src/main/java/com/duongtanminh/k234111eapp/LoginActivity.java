package com.duongtanminh.k234111eapp;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    EditText edtUserName, edtPassword;
    TextView txtMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // ánh xạ view
        edtUserName = findViewById(R.id.edtUserName);
        edtPassword = findViewById(R.id.edtPassword);
        txtMessage = findViewById(R.id.txtMessage);
    }

    public void loginSystem(View view) {
        String username = edtUserName.getText().toString();
        String password = edtPassword.getText().toString();

        if(username.equalsIgnoreCase("admin") && password.equals("123")) {
            txtMessage.setText("Login successful!!!!!!");
        } else {
            txtMessage.setText("Login failed!!!!!!");
        }
    }

    public void exitSystem(View view) {
        finish();
    }
}