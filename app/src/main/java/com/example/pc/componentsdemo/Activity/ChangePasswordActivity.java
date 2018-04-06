package com.example.pc.componentsdemo.Activity;

import android.app.ProgressDialog;
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
import android.widget.Toast;

import com.example.pc.componentsdemo.Others.DatabaseHelper;
import com.example.pc.componentsdemo.R;
import com.example.pc.componentsdemo.Others.Users;

public class ChangePasswordActivity extends AppCompatActivity {

    private EditText edtNewpassword;
    private EditText edtConfirmpassword;
    private Button btnPasswordsave;
    private RelativeLayout relativeLayout;
    private ImageView ivBack;

    private String newPassword;
    private String confirmPassword;
    private String email;

    DatabaseHelper databaseHelper;
    private long mLastClickTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        initView();
    }

    private void initView() {

        edtNewpassword = (EditText) findViewById(R.id.edtnewpassword);
        edtConfirmpassword = (EditText) findViewById(R.id.edtconfirmpassword);
        btnPasswordsave = (Button) findViewById(R.id.btnpasswordsave);
        relativeLayout = (RelativeLayout) findViewById(R.id.rlchangepassword);
        ivBack = (ImageView) findViewById(R.id.backtoforgotpassword);

        email = getIntent().getExtras().getString("Email"); // got email value here

        btnPasswordsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (SystemClock.elapsedRealtime() - mLastClickTime < 3000) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();

                databaseHelper = new DatabaseHelper(ChangePasswordActivity.this);
                isValidData();
                Users users = databaseHelper.checkEmail(email);
                users.setPassword(newPassword);
                databaseHelper.updateUserPassword(users, email);
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

    private boolean isValidData() {

        newPassword = edtNewpassword.getText().toString().trim();
        confirmPassword = edtConfirmpassword.getText().toString().trim();

        if (newPassword.isEmpty()) {
            Snackbar.make(relativeLayout, "Please enter a new password", Snackbar.LENGTH_LONG).show();
            edtNewpassword.requestFocus();
            return false;
        } else if (newPassword.length() < 6) {
            Snackbar.make(relativeLayout, "Enter atleast 6 digit password", Snackbar.LENGTH_LONG).show();
            edtNewpassword.requestFocus();
            return false;
        } else if (confirmPassword.isEmpty()) {
            Snackbar.make(relativeLayout, "Please confirm your password", Snackbar.LENGTH_LONG).show();
            edtConfirmpassword.requestFocus();
            return false;
        } else if (!newPassword.equals(confirmPassword)) {
            Snackbar.make(relativeLayout, "Password doesn't match", Snackbar.LENGTH_LONG).show();
            edtConfirmpassword.requestFocus();
            return false;
        } else {
            Intent intent = new Intent(ChangePasswordActivity.this, Login.class);
            Toast.makeText(getApplicationContext(), "Password Changed Successfully", Toast.LENGTH_SHORT).show();
            startActivity(intent);
        }

        return true;
    }
}
