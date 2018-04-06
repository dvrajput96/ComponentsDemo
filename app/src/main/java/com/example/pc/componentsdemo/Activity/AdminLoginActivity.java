package com.example.pc.componentsdemo.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import static com.example.pc.componentsdemo.Activity.Login.LOGIN;

public class AdminLoginActivity extends AppCompatActivity {

    private EditText edtEmail;
    private EditText edtPassword;
    private Button btnLogin;
    private TextView txtUserLogin;
    private RelativeLayout relativeLayout;

    private String password;
    private String email;
    private String login;
    private DatabaseHelper databaseHelper;
    private ProgressDialog mProgress;
    public static final String mypreference = "mypref";
    SharedPreferences sharedPreferences;
    private int logincheck = 0;
    private long mLastClickTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        checkLoginSession();
    }

    private void initView() {

        edtEmail = (EditText) findViewById(R.id.edtemail);
        edtPassword = (EditText) findViewById(R.id.edtpassword);
        txtUserLogin = (TextView) findViewById(R.id.txtuserlogin);
        btnLogin = (Button) findViewById(R.id.btnlogin);
        relativeLayout = (RelativeLayout) findViewById(R.id.rladminlogin);

        txtUserLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (SystemClock.elapsedRealtime() - mLastClickTime < 3000) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                isValidData();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private boolean isValidData() {

        sharedPreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);

        mProgress = new ProgressDialog(this);
        mProgress.setTitle("Please wait...");
        mProgress.setMessage("Authenticating...");
        mProgress.setCancelable(false);
        mProgress.setIndeterminate(true);

        email = edtEmail.getText().toString().trim();
        password = edtPassword.getText().toString().trim();


        if (email.isEmpty()) {
            Snackbar.make(relativeLayout, "Please enter your email", Snackbar.LENGTH_LONG).show();
            edtEmail.requestFocus();
            return false;
        } else if (password.isEmpty()) {
            Snackbar.make(relativeLayout, "Please enter your password", Snackbar.LENGTH_LONG).show();
            edtPassword.requestFocus();
            return false;
        } else {
            mProgress.show();
            if (email.equals("admin") && password.equals("admin")) {
                logincheck = 1;
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt(Login.LOGIN, logincheck);
                editor.apply();
                checkLoginSession();

            } else {
                mProgress.dismiss();
                Snackbar.make(relativeLayout, "Invalid email or password", Snackbar.LENGTH_LONG).show();
            }

        }
        return true;
    }

    public void checkLoginSession() {

        sharedPreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        logincheck = sharedPreferences.getInt(Login.LOGIN, 0);
        if (logincheck == 1) {
            Intent intent = new Intent(AdminLoginActivity.this, NavigationDrawerActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        } else {
            initView();
        }

    }
}
