package com.example.pc.componentsdemo.Activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.annotation.IdRes;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.pc.componentsdemo.Others.DatabaseHelper;
import com.example.pc.componentsdemo.R;
import com.example.pc.componentsdemo.Others.Users;
import com.google.android.gms.internal.et;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private EditText etEmail;
    private EditText etPassword;
    private EditText etConfirmPassword;
    private EditText etmobileno;
    private Button btnSubmit;
    private TextView txtUserLogin;
    private LinearLayout linearLayout;

    private String email;
    private String password;
    private String cpassword;
    private String mobile;
    private String user_id;

    private ProgressDialog mProgress;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    private DatabaseHelper databaseHelper;
    private long mLastClickTime = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

    }

    private void initView() {

        linearLayout = (LinearLayout) findViewById(R.id.lluserlogin);
        etEmail = (EditText) findViewById(R.id.edtemail);
        etPassword = (EditText) findViewById(R.id.edtpassword);
        etConfirmPassword = (EditText) findViewById(R.id.edtconfirmpassword);
        etmobileno = (EditText) findViewById(R.id.edtmobilenumber);
        btnSubmit = (Button) findViewById(R.id.btnsubmit);
        txtUserLogin = (TextView) findViewById(R.id.txtloginuserpanel);

        databaseHelper = new DatabaseHelper(MainActivity.this);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (SystemClock.elapsedRealtime() - mLastClickTime < 3000) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                if (isValidData()) {
                    Users users = new Users();
                    users.setEmail(email);
                    users.setPassword(password);
                    users.setMobileno(mobile);

                    if (databaseHelper.addUser(users)) {
                        Toast.makeText(getApplicationContext(), "Registration Successful", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Invalid Data", Toast.LENGTH_SHORT).show();
                }
            }
        });

        txtUserLogin.setOnClickListener(new View.OnClickListener() {
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

    private void showSnackBar(String str) {
        Snackbar.make(linearLayout, "" + str, Snackbar.LENGTH_LONG).show();
        //snackbar callback method
               /* .setAction("ok", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Snackbar snackbar1 = Snackbar.make(linearLayout, "Snackbar with Callback called.", Snackbar.LENGTH_SHORT);
                        snackbar1.show();
                    }
                });
        snackbar.show();*/
    }

    private boolean isValidData() {


        mProgress = new ProgressDialog(this);
        mProgress.setTitle("Please wait...");
        mProgress.setMessage("Creating Account...");
        mProgress.setCancelable(false);
        mProgress.setIndeterminate(true);

        email = etEmail.getText().toString().trim();
        password = etPassword.getText().toString().trim();
        cpassword = etConfirmPassword.getText().toString().trim();
        mobile = etmobileno.getText().toString().trim();


        if (email.isEmpty()) {
            showSnackBar("Please enter your email");
            etEmail.requestFocus();
            return false;
        } else if (!email.matches(emailPattern)) {
            showSnackBar("Invalid email");
            etEmail.requestFocus();
            return false;
        } else if (password.isEmpty()) {
            showSnackBar("Please enter your password");
            etPassword.requestFocus();
            return false;
        } else if (password.length() < 6) {
            showSnackBar("Enter atleast 6 digit password");
            etPassword.requestFocus();
            return false;
        } else if (cpassword.isEmpty()) {
            showSnackBar("Please confirm your password");
            etConfirmPassword.requestFocus();
            return false;
        } else if (!cpassword.matches(password)) {
            showSnackBar("Password doesn't match");
            etConfirmPassword.requestFocus();
            return false;
        } else if (mobile.isEmpty()) {
            showSnackBar("Please enter your contact number");
            etmobileno.requestFocus();
            return false;
        } else if (mobile.length() < 10) {
            showSnackBar("Invalid contact number");
            etmobileno.requestFocus();
            return false;
        } else {
            mProgress.show();
            if (databaseHelper.checkEmailUnique(email)) {
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
                finish();
            } else {
                mProgress.dismiss();
                showSnackBar("Email is already registered");
                etEmail.requestFocus();
            }
        }
        return true;
    }
}
