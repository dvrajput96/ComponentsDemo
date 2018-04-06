package com.example.pc.componentsdemo.Activity;

import android.content.Intent;
import android.os.SystemClock;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.pc.componentsdemo.Others.DatabaseHelper;
import com.example.pc.componentsdemo.R;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText etEmail;
    private Button btnNext;
    private ImageView ivBack;
    private RelativeLayout relativeLayout;

    private String email;
    private String next;
    private String password;

    DatabaseHelper databaseHelper;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private long mLastClickTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        initView();
    }

    private void initView() {

        etEmail = (EditText) findViewById(R.id.edtemail);
        btnNext = (Button) findViewById(R.id.btnnext);
        ivBack = (ImageView) findViewById(R.id.backtouserlogin);
        relativeLayout = (RelativeLayout) findViewById(R.id.rlforgotpassword);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (SystemClock.elapsedRealtime() - mLastClickTime < 3000) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                isValidData();
            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void isValidData() {

        databaseHelper = new DatabaseHelper(ForgotPasswordActivity.this);
        email = etEmail.getText().toString().trim();

        if (email.isEmpty()) {
            Snackbar.make(relativeLayout, "Please enter your email", Snackbar.LENGTH_LONG).show();
            etEmail.requestFocus();
        } else if (!email.matches(emailPattern)) {
            Snackbar.make(relativeLayout, "Invalid email", Snackbar.LENGTH_LONG).show();
            etEmail.requestFocus();
        } else if (databaseHelper.checkEmailUnique(email)) {
            Snackbar.make(relativeLayout, "Email doesn't exist", Snackbar.LENGTH_LONG).show();
            etEmail.requestFocus();
        } else {
            Intent intent = new Intent(getApplicationContext(), ChangePasswordActivity.class);
            intent.putExtra("Email", email);
            startActivity(intent);
        }
    }

}
