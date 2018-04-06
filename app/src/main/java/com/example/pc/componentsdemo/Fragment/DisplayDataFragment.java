package com.example.pc.componentsdemo.Fragment;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;

import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import com.example.pc.componentsdemo.Activity.Login;
import com.example.pc.componentsdemo.Others.DatabaseHelper;
import com.example.pc.componentsdemo.Others.Users;
import com.example.pc.componentsdemo.Others.Utility;
import com.example.pc.componentsdemo.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import static android.app.Activity.RESULT_OK;


public class DisplayDataFragment extends Fragment {

    private EditText etUsername;
    private EditText etPassword;
    private EditText etEmail;
    private EditText etMobile;
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
    private Button btnSave;
    private Toolbar myToolbar;
    private ImageView ivProfile;
    private ImageView ivSelectProfile;
    private LinearLayout linearLayout;

    private String username;
    private String password;
    private String email;
    private String mobile;
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
    private String submit;
    private String sbValue;
    private String lstText;
    private String lstItemID = "";
    private String aswitch = "";
    private String gender = "";

    private boolean isUpdate = false;
    private View view;

    private static final int REQUEST_CAMERA = 0;
    private static final int SELECT_FILE = 1;
    private String userChoosenTask;

    private DatabaseHelper dataBaseHelper;
    private SharedPreferences sharedPreferences;
    private List<String> hobbiesList = new ArrayList<>();
    private ArrayAdapter<CharSequence> adapter;
    private Calendar calendar = Calendar.getInstance();
    private Users users;
    private String strmsg = "";
    private int restoredUserID;
    private Float ratingVal = 0.0f;
    private byte[] data = new byte[10];


    public DisplayDataFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_display_data, container, false);
        initView();
        return view;
    }

    private Users showData(int userID) {


        return users = dataBaseHelper.getUserdata(userID);

    }

    private void initView() {


        dataBaseHelper = new DatabaseHelper(getActivity());

        final int mYear = calendar.get(Calendar.YEAR);
        final int mMonth = calendar.get(Calendar.MONTH);
        final int mDay = calendar.get(Calendar.DAY_OF_MONTH);
        final int hour = calendar.get(Calendar.HOUR_OF_DAY);
        final int minute = calendar.get(Calendar.MINUTE);

        linearLayout = (LinearLayout) view.findViewById(R.id.lldatadisplayfragment);
        ivProfile = (ImageView) view.findViewById(R.id.ivprofile);
        ivSelectProfile = (ImageView) view.findViewById(R.id.ivselectprofile);
        etUsername = (EditText) view.findViewById(R.id.edtname);
        etPassword = (EditText) view.findViewById(R.id.edtpassword);
        etEmail = (EditText) view.findViewById(R.id.edtemail);
        etMobile = (EditText) view.findViewById(R.id.edtmobile);
        rgGender = (RadioGroup) view.findViewById(R.id.set_radiogroup);
        rbMale = (RadioButton) view.findViewById(R.id.rbmale);
        rbFemale = (RadioButton) view.findViewById(R.id.rbfemale);
        rbOthers = (RadioButton) view.findViewById(R.id.rbothers);
        cbCricket = (CheckBox) view.findViewById(R.id.cbcricket);
        cbFoolball = (CheckBox) view.findViewById(R.id.cbFoolball);
        cbVolleyball = (CheckBox) view.findViewById(R.id.cbVolleyball);
        cbHockey = (CheckBox) view.findViewById(R.id.cbHockey);
        tvBirthdate = (TextView) view.findViewById(R.id.txtbdaydate);
        tvBirthtime = (TextView) view.findViewById(R.id.txtbdaytime);
        spState = (Spinner) view.findViewById(R.id.spnstate);
        spNation = (Spinner) view.findViewById(R.id.spnnation);
        etAddress = (EditText) view.findViewById(R.id.edtaddress);
        sbSet = (SeekBar) view.findViewById(R.id.sbset);
        txtSeekbarValue = (TextView) view.findViewById(R.id.txtseekbarvalue);
        rbRatingBar = (RatingBar) view.findViewById(R.id.rbrating);
        aSwitch = (Switch) view.findViewById(R.id.setswitch);
        btnSave = (Button) view.findViewById(R.id.btnsubmit);
        myToolbar = (Toolbar) view.findViewById(R.id.toolbar);

        enableDisable(false);

        ivSelectProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });


        rgGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

                int btn = group.getCheckedRadioButtonId();

                if (btn == R.id.rbmale) {
                    gender = "M";
                } else if (btn == R.id.rbfemale) {
                    gender = "F";
                } else if (btn == R.id.rbothers) {
                    gender = "O";
                }
            }
        });


        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                tvBirthdate.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
            }
        };

        tvBirthdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), date, mYear, mMonth, mDay);
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.setTitle("Select Birthdate");
                datePickerDialog.show();

            }
        });

        tvBirthtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* int Am_Pm = calendar.get(Calendar.AM_PM);*/
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        tvBirthtime.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, false);//Yes 24 hour time
                mTimePicker.setTitle("Select BirthTime");
                mTimePicker.show();
            }
        });

        //find the progress of seekbar and set it
        sbSet.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                txtSeekbarValue.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //find the value of rating bar from both these toasts you will get the value of ratingbar
        rbRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                ratingVal = (Float) rating;
                //Float ratingvalue = (Float) rbRatingBar.getRating();
            }
        });

        //get value of switch
        aSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (aSwitch.isChecked()) {
                    aswitch = "ON";
                } else {
                    aswitch = "OFF";
                }
            }
        });


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!isUpdate) {
                    btnSave.setText("SAVE");
                    enableDisable(true);
                    isValidData();
                    isUpdate = true;
                } else {
                    btnSave.setText("UPDATE");
                    isValidData();
                    Users users = new Users();
                    users.setUsername(username);
                    users.setGender(gender);
                    users.setHobbies(strmsg);
                    users.setBirthdate(birthdate);
                    users.setBirthtime(birthtime);
                    users.setState(state);
                    users.setNation(nation);
                    users.setAddress(address);
                    users.setSeekpercentage(sbValue);
                    users.setMobileno(mobile);
                    users.setRatingvalue(String.valueOf(ratingVal));
                    users.setSwitchvalue(aswitch);
                    users.setBlob(data);
                    dataBaseHelper.updateUser(users, restoredUserID);
                    enableDisable(false);
                    isUpdate = false;
                    Toast.makeText(getActivity(), "Data Saved Successfully", Toast.LENGTH_LONG).show();
                }

            }
        });

        // set the data to the UI component of the DisplayData


        if (getActivity().getIntent().getExtras() != null) {

            restoredUserID = getActivity().getIntent().getExtras().getInt("userId");

        } else {

            sharedPreferences = getActivity().getSharedPreferences(Login.mypreference, Context.MODE_PRIVATE);
            restoredUserID = sharedPreferences.getInt(Login.UserID, 0);
        }

        Users users = showData(restoredUserID);
        //myToolbar.setTitle("Username : " + users.getUsername());
        etUsername.setText(users.getUsername());
        etEmail.setText(users.getEmail());
        etMobile.setText(users.getMobileno());

        if (users.getBlob() != null) {
            //decode image from byte array to bitmap
            ivProfile.setImageBitmap(BitmapFactory.decodeByteArray(users.getBlob(), 0, users.getBlob().length));
        }

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

        if (users.getBirthdate() != null) {
            tvBirthdate.setText(users.getBirthdate());
        }
        if (users.getBirthtime() != null) {
            tvBirthtime.setText(users.getBirthtime());
        }
        if (users.getState() != null) {
            //set particular item to the spinner from XML array which was previously selected at the time of registration for states
            String setState = users.getState();
            adapter = ArrayAdapter.createFromResource(getActivity(), R.array.india_states, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spState.setAdapter(adapter);
            if (!setState.equals(null)) {
                int spinnerPosition = adapter.getPosition(setState);
                spState.setSelection(spinnerPosition);
            }
        }

        if (users.getNation() != null) {
            //set particular item to the spinner from XML array which was previously selected at the time of registration for Nations
            String setNation = users.getNation();
            adapter = ArrayAdapter.createFromResource(getActivity(), R.array.array_countries, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spNation.setAdapter(adapter);
            if (!setNation.equals(null)) {
                int spinnerPosition = adapter.getPosition(setNation);
                spNation.setSelection(spinnerPosition);
            }
        }

        if (users.getAddress() != null) {
            etAddress.setText(users.getAddress());
        }

        if (users.getSeekpercentage() != null && !users.getSeekpercentage().isEmpty()) {
            sbSet.setProgress(Integer.parseInt(users.getSeekpercentage()));
        }
        /*if (users.getRatingvalue().matches("null")) {
            rbRatingBar.setRating(Float.parseFloat(String.valueOf(0.0f)));
        } else*/
        if (users.getRatingvalue() != null && !users.getRatingvalue().isEmpty()) {
            rbRatingBar.setRating(Float.parseFloat(users.getRatingvalue()));
        }
        if (users.getSwitchvalue() != null && !users.getSwitchvalue().isEmpty() && users.getSwitchvalue().equals("ON")) {
            aSwitch.setChecked(true);
        } else {
            aSwitch.setChecked(false);
        }


    }


    private boolean isValidData() {

        username = etUsername.getText().toString().trim();
        mobile = etMobile.getText().toString().trim();
        cricket = cbCricket.getText().toString().trim();
        football = cbFoolball.getText().toString().trim();
        volleyball = cbVolleyball.getText().toString().trim();
        hockey = cbHockey.getText().toString().trim();
        birthdate = tvBirthdate.getText().toString().trim();
        birthtime = tvBirthtime.getText().toString().trim();
        state = spState.getSelectedItem().toString();
        nation = spNation.getSelectedItem().toString();
        address = etAddress.getText().toString().trim();
        sbValue = txtSeekbarValue.getText().toString().trim();

        getHobbyclick();

        return true;
    }

    public void getHobbyclick() {


        ArrayList<String> stringsHobby = new ArrayList<>();
        if (cbCricket.isChecked()) {
            stringsHobby.add("Cricket");
        }
        if (cbFoolball.isChecked()) {
            stringsHobby.add("Football");
        }
        if (cbVolleyball.isChecked()) {
            stringsHobby.add("Volleyball");
        }
        if (cbHockey.isChecked()) {
            stringsHobby.add("Hockey");
        }

        strmsg = stringsHobby.toString().replace("[", "").replace("]", "");// remove square brackets from the aaraylist

    }

    private boolean enableDisable(boolean isUpdate) {

        etUsername.setEnabled(isUpdate);
        etAddress.setEnabled(isUpdate);
        etMobile.setEnabled(isUpdate);
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
        ivProfile.setEnabled(isUpdate);
        ivSelectProfile.setEnabled(isUpdate);

        return true;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (userChoosenTask.equals("Take Photo"))
                        cameraIntent();
                    else if (userChoosenTask.equals("Choose from Library"))
                        galleryIntent();
                } else {
                    //code for deny
                    Toast.makeText(getActivity(), "Permission doesn't granted", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE) {
                // onSelectFromGalleryResult(data);
                Bitmap bm = null;
                if (data != null) {
                    try {
                        bm = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                insertImg(bm);
                ivProfile.setImageBitmap(bm);
            } else if (requestCode == REQUEST_CAMERA) {
                // onCaptureImageResult(data);
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");

                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                thumbnail.compress(Bitmap.CompressFormat.JPEG, 80, bytes);

                File destination = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".jpg");
                FileOutputStream fo;
                try {
                    destination.createNewFile();
                    fo = new FileOutputStream(destination);
                    fo.write(bytes.toByteArray());
                    fo.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                insertImg(thumbnail);
                ivProfile.setImageBitmap(thumbnail);
            }
        } else {

        }
    }

/*    private void onSelectFromGalleryResult(Intent data) {
        Bitmap bm = null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        insertImg(bm);
        ivProfile.setImageBitmap(bm);
    }

    private void onCaptureImageResult(Intent data) {

        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".jpg");
        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        insertImg(thumbnail);
        ivProfile.setImageBitmap(thumbnail);
    }*/

    public void insertImg(Bitmap img) {

        data = getBitmapAsByteArray(img); // this is a function

    }

    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream);
        return outputStream.toByteArray();
    }

    private void selectImage() {


        final CharSequence[] items = {"Take Photo", "Choose from Library", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Photo !");

        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {


                if (items[item].equals("Take Photo")) {
                    boolean result = Utility.checkPermission(getActivity());
                    userChoosenTask = "Take Photo";
                    if (result)
                        cameraIntent();
                } else if (items[item].equals("Choose from Library")) {
                    boolean result = Utility.checkPermission(getActivity());
                    userChoosenTask = "Choose from Library";
                    if (result)
                        galleryIntent();
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();

    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }


}
