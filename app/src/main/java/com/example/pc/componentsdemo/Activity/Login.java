package com.example.pc.componentsdemo.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import android.support.design.widget.CoordinatorLayout;
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

import com.example.pc.componentsdemo.Fragment.DisplayDataFragment;
import com.example.pc.componentsdemo.Others.DatabaseHelper;
import com.example.pc.componentsdemo.R;
import com.example.pc.componentsdemo.Others.Users;

import java.util.Calendar;
import java.util.HashMap;

public class Login extends AppCompatActivity {

    private EditText edtEmail;
    private EditText edtPassword;
    private TextView txtForgotpassword;
    private TextView txtSignup;
    private Button btnLogin;
    private TextView txtAdminLogin;
    private RelativeLayout relativeLayout;
    private CoordinatorLayout coordinatorLayout;

    private String password;
    private String email;
    private String login;
    private String forgotpassword;
    private String signup;
    private DatabaseHelper databaseHelper;
    private long mLastClickTime = 0;
    private int logincheck = 0;
    SharedPreferences sharedPreferences;
    Users users;


    public static final String mypreference = "mypref";
    public static final String UserID = "idkey";
    public static final String LOGIN = "mylogin";
    private ProgressDialog mProgress;
    private int restoredUserID;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViews();
        checkLoginSession();

    }


    private void findViews() {

        edtEmail = (EditText) findViewById(R.id.edtemail);
        edtPassword = (EditText) findViewById(R.id.edtpassword);
        txtForgotpassword = (TextView) findViewById(R.id.txtforgotpassword);
        txtSignup = (TextView) findViewById(R.id.txtsignup);
        btnLogin = (Button) findViewById(R.id.btnlogin);
        txtAdminLogin = (TextView) findViewById(R.id.txtadminlogin);
        relativeLayout = (RelativeLayout) findViewById(R.id.rluserlogin);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.cluserlogin);

    }

    private Users showData(int userID) {

        databaseHelper = new DatabaseHelper(getApplicationContext());
        return users = databaseHelper.getUserdata(userID);

    }

    public void checkLoginSession() {

        logincheck = 0;
        showData(restoredUserID);
        sharedPreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(LOGIN, logincheck);
        editor.apply();

        restoredUserID = sharedPreferences.getInt(UserID, 0);

        if (restoredUserID == 0) {
            initView();
        } else {
            Intent intent = new Intent(getApplicationContext(), NavigationDrawerActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void initView() {

        txtAdminLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, AdminLoginActivity.class);
                startActivity(intent);
            }
        });


        txtSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, MainActivity.class);
                startActivity(intent);
            }
        });


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (SystemClock.elapsedRealtime() - mLastClickTime < 3000) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                isvalidData();
            }
        });

        txtForgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private boolean isvalidData() {
        mProgress = new ProgressDialog(this);
        mProgress.setTitle("Please wait...");
        mProgress.setMessage("Authenticating...");
        mProgress.setCancelable(false);
        mProgress.setIndeterminate(true);

        email = edtEmail.getText().toString().trim();
        password = edtPassword.getText().toString().trim();


        if (email.isEmpty()) {
            //Toast.makeText(getApplicationContext(), "Please enter your email", Toast.LENGTH_LONG).show();
            Snackbar.make(coordinatorLayout, "Please enter your email", Snackbar.LENGTH_LONG).show();
            edtEmail.requestFocus();
            return false;
        } else if (!email.matches(emailPattern)) {
            //Toast.makeText(getApplicationContext(), "Invalid email", Toast.LENGTH_LONG).show();
            Snackbar.make(coordinatorLayout, "Invalid email", Snackbar.LENGTH_LONG).show();
            edtEmail.requestFocus();
            return false;
        } else if (password.isEmpty()) {
            //Toast.makeText(getApplicationContext(), "Please enter your password", Toast.LENGTH_LONG).show();
            Snackbar.make(coordinatorLayout, "Please enter your password", Snackbar.LENGTH_LONG).show();
            edtPassword.requestFocus();
            return false;
        } else {
            mProgress.show();
            verifyFromSQLite();
        }
        return true;
    }

    /*
     * This method is to validate the input text fields and verify login credentials from SQLite*/

    private void verifyFromSQLite() {

        databaseHelper = new DatabaseHelper(getApplicationContext());

        users = databaseHelper.checkUser(email, password);

        if (users.getUserid() != null) {


            Toast.makeText(getApplicationContext(), "" + users.getEmail() + "==" + users.getUserid(), Toast.LENGTH_SHORT).show();
            int uid = users.getUserid();

            sharedPreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(UserID, uid);
            editor.putInt(LOGIN, logincheck);
            editor.apply();

            checkLoginSession();

        } else {
            //to show individual error

            if (users.getResult() == 1) {
                mProgress.dismiss();
                Snackbar.make(coordinatorLayout, "Incorrect email", Snackbar.LENGTH_LONG).show();
                edtEmail.requestFocus();

            } else if (users.getResult() == 2) {
                mProgress.dismiss();
                Snackbar.make(coordinatorLayout, "Incorrect password", Snackbar.LENGTH_LONG).show();
                edtPassword.requestFocus();

            } else if (users.getResult() == 3) {
                mProgress.dismiss();
                Snackbar.make(coordinatorLayout, "Email and password doesn't match", Snackbar.LENGTH_LONG).show();
                edtPassword.requestFocus();

            }

        }
    }
}
