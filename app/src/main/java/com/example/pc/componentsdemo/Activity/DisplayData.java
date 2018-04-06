package com.example.pc.componentsdemo.Activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.IdRes;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import com.example.pc.componentsdemo.Others.DatabaseHelper;
import com.example.pc.componentsdemo.R;
import com.example.pc.componentsdemo.Others.Users;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;


public class DisplayData extends AppCompatActivity {

    private EditText etUsername;
    private EditText etPassword;
    private EditText etEmail;
    private RadioGroup rgGender;
    private RadioButton rbMale;
    private RadioButton rbFemale;
    private RadioButton rbOthers;
    private CheckBox cbCricket;
    private CheckBox cbFoolball;
    private CheckBox cbVolleyball;
    private CheckBox cbHockey;
    private TextView tvBirthdate;
    private TextView tvBirthtime;
    private Spinner spState;
    private Spinner spNation;
    private EditText etAddress;
    private SeekBar sbSet;
    private TextView txtSeekbarValue;
    private RatingBar rbRatingBar;
    private Switch aSwitch;
    private Toolbar myToolbar;
    private EditText mobile;
    private ImageView ivBackArrow;
    private TextView txtToolbar;
    private LinearLayout linearLayout;

    private String username;
    private String password;
    private String email;
    private String gender;
    private String male;
    private String female;
    private String others;
    private String cricket;
    private String football;
    private String volleyball;
    private String hockey;
    private String birthdate;
    private String birthtime;
    private String state;
    private String nation;
    private String address;
    private String brightness;
    private String seekbar;
    private String ratingbar;
    private String aswitch;
    private String cno;
    private String sbValue;
    private String lstItemID = "";
    private String lstText;
    private boolean isUpdate = false;

    private DatabaseHelper dataBaseHelper;
    private SharedPreferences sharedPreferences;
    private List<String> hobbiesList = new ArrayList<>();
    private ArrayAdapter<CharSequence> adapter;
    private Calendar calendar = Calendar.getInstance();
    private Users users;
    private String strmsg = "";
    private int restoredUserID;
    private Float ratingVal = 0.0f;


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_data);
        initView();
        setSupportActionBar(myToolbar);
    }

    private Users showData(int userID) {

        return users = dataBaseHelper.getUserdata(userID);

    }

    private void initView() {

        dataBaseHelper = new DatabaseHelper(this);

        linearLayout = (LinearLayout) findViewById(R.id.lldatadisplay);
        etUsername = (EditText) findViewById(R.id.edtname);
        etPassword = (EditText) findViewById(R.id.edtpassword);
        etEmail = (EditText) findViewById(R.id.edtemail);
        mobile = (EditText) findViewById(R.id.edtmobile);
        rgGender = (RadioGroup) findViewById(R.id.set_radiogroup);
        rbMale = (RadioButton) findViewById(R.id.rbmale);
        rbFemale = (RadioButton) findViewById(R.id.rbfemale);
        rbOthers = (RadioButton) findViewById(R.id.rbothers);
        cbCricket = (CheckBox) findViewById(R.id.cbcricket);
        cbFoolball = (CheckBox) findViewById(R.id.cbFoolball);
        cbVolleyball = (CheckBox) findViewById(R.id.cbVolleyball);
        cbHockey = (CheckBox) findViewById(R.id.cbHockey);
        tvBirthdate = (TextView) findViewById(R.id.txtbdaydate);
        tvBirthtime = (TextView) findViewById(R.id.txtbdaytime);
        spState = (Spinner) findViewById(R.id.spnstate);
        spNation = (Spinner) findViewById(R.id.spnnation);
        etAddress = (EditText) findViewById(R.id.edtaddress);
        sbSet = (SeekBar) findViewById(R.id.sbset);
        txtSeekbarValue = (TextView) findViewById(R.id.txtseekbarvalue);
        rbRatingBar = (RatingBar) findViewById(R.id.rbrating);
        aSwitch = (Switch) findViewById(R.id.setswitch);
        myToolbar = (Toolbar) findViewById(R.id.mtoolbar);
        ivBackArrow = (ImageView) findViewById(R.id.ivbackarrow);
        txtToolbar = (TextView) findViewById(R.id.txttoolbar);

        enableDisable(false);


        ivBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), NavigationDrawerActivity.class);
                startActivity(i);
                finish();
            }
        });
        // set the data to the UI component of the DisplayData

        //after admin login user list
        if (getIntent().getExtras() != null) {

            restoredUserID = getIntent().getExtras().getInt("userId");

        } else {

            //after userlogin user display data

            sharedPreferences = getSharedPreferences(Login.mypreference, Context.MODE_PRIVATE);
            restoredUserID = sharedPreferences.getInt(Login.UserID, 0);
            ivBackArrow.setVisibility(View.GONE);
        }

        Users users = showData(restoredUserID);

        txtToolbar.setText("Username : " + users.getEmail());
        etUsername.setText(users.getUsername());
        etEmail.setText(users.getEmail());
        mobile.setText(users.getMobileno());


        if (users.getGender() != null) {
            if (users.getGender().equals("M")) {
                rbMale.setChecked(true);
            } else if (users.getGender().equals("F")) {
                rbFemale.setChecked(true);
            } else if (users.getGender().equals("O")) {
                rbOthers.setChecked(true);
            }
        }
        if (users.getHobbies() != null) {

            hobbiesList = (Arrays.asList(users.getHobbies().trim().split(",")));//split List and remove comma


            for (String strValue : hobbiesList) {
                if (strValue.trim().equals("Cricket")) {
                    cbCricket.setChecked(true);
                }
                if (strValue.trim().equals("Football")) {
                    cbFoolball.setChecked(true);
                }
                if (strValue.trim().equals("Volleyball")) {
                    cbVolleyball.setChecked(true);
                }
                if (strValue.trim().equals("Hockey")) {
                    cbHockey.setChecked(true);
                }

            }

        }

        tvBirthdate.setText(users.getBirthdate());
        tvBirthtime.setText(users.getBirthtime());

        //set particular item to the spinner from XML array which was previously selected at the time of registration for states
        String setState = users.getState();
        adapter = ArrayAdapter.createFromResource(this, R.array.india_states, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spState.setAdapter(adapter);
        if (!setState.equals(null)) {
            int spinnerPosition = adapter.getPosition(setState);
            spState.setSelection(spinnerPosition);
        }

        //set particular item to the spinner from XML array which was previously selected at the time of registration for Nations
        String setNation = users.getNation();
        adapter = ArrayAdapter.createFromResource(this, R.array.array_countries, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spNation.setAdapter(adapter);
        if (!setNation.equals(null)) {
            int spinnerPosition = adapter.getPosition(setNation);
            spNation.setSelection(spinnerPosition);
        }


        etAddress.setText(users.getAddress());

        if (users.getSeekpercentage() != null && !users.getSeekpercentage().isEmpty()) {
            sbSet.setProgress(Integer.parseInt(users.getSeekpercentage()));
        }

        if (users.getRatingvalue() != null && !users.getRatingvalue().isEmpty()) {
            rbRatingBar.setRating(Float.parseFloat(users.getRatingvalue()));
        }

        if (users.getSwitchvalue() != null && !users.getSwitchvalue().isEmpty() && users.getSwitchvalue().equals("ON")) {
            aSwitch.setChecked(true);
        } else {
            aSwitch.setChecked(false);
        }
    }

    private boolean enableDisable(boolean isUpdate) {

        etUsername.setEnabled(isUpdate);
        mobile.setEnabled(isUpdate);
        etAddress.setEnabled(isUpdate);
        rbMale.setClickable(isUpdate);
        rbFemale.setClickable(isUpdate);
        rbOthers.setClickable(isUpdate);
        cbCricket.setClickable(isUpdate);
        cbFoolball.setClickable(isUpdate);
        cbVolleyball.setClickable(isUpdate);
        cbHockey.setClickable(isUpdate);
        tvBirthdate.setEnabled(isUpdate);
        tvBirthtime.setEnabled(isUpdate);
        spState.setClickable(isUpdate);
        spNation.setClickable(isUpdate);
        rbRatingBar.setEnabled(isUpdate);
        sbSet.setEnabled(isUpdate);
        aSwitch.setEnabled(isUpdate);

        return true;
    }

}