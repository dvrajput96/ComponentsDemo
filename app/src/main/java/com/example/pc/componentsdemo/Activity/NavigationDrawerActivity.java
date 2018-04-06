package com.example.pc.componentsdemo.Activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pc.componentsdemo.Fragment.AboutUsFragment;
import com.example.pc.componentsdemo.Fragment.DisplayDataFragment;
import com.example.pc.componentsdemo.Fragment.MapFragment;
import com.example.pc.componentsdemo.Fragment.UsersListFragment;
import com.example.pc.componentsdemo.Others.CurrentLocation;
import com.example.pc.componentsdemo.Others.DatabaseHelper;
import com.example.pc.componentsdemo.Others.Users;
import com.example.pc.componentsdemo.R;

import static com.example.pc.componentsdemo.Others.CurrentLocation.MY_PERMISSIONS_REQUEST_LOCATION;

public class NavigationDrawerActivity extends AppCompatActivity {

    private TextView txtTitle;
    private TextView txtSubTitle;
    private ImageView imgProfile;
    private Menu menu;
    public static final String mypreference = "mypref";

    private View navHeader;
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private Toolbar toolbar;
    private DatabaseHelper databaseHelper;
    private Users users;
    private SharedPreferences sharedPreferences;
    private int restoredUserID;
    private int logincheck = 0;
    private boolean islogin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);
        initView();
        if (savedInstanceState == null) {
            if (logincheck == 0) {
                this.setTitle("Profile");
                Fragment displayDataFragment = new DisplayDataFragment();
                openFragment(displayDataFragment);
            } else if (logincheck == 1) {
                this.setTitle("Users List");
                Fragment usersListFragment = new UsersListFragment();
                openFragment(usersListFragment);
            }
        }
    }

    private void initView() {

        databaseHelper = new DatabaseHelper(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        setSupportActionBar(toolbar);

        // get menu from navigationView
        menu = navigationView.getMenu();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        // Navigation view header
        navHeader = navigationView.getHeaderView(0);
        txtTitle = (TextView) navHeader.findViewById(R.id.txttitle);

        txtSubTitle = (TextView) navHeader.findViewById(R.id.txtsubtitle);
        imgProfile = (ImageView) navHeader.findViewById(R.id.imageView);


        // initializing navigation menu
        setUpNavigationView();


        sharedPreferences = getSharedPreferences(Login.mypreference, Context.MODE_PRIVATE);
        logincheck = sharedPreferences.getInt(Login.LOGIN, 0);
        restoredUserID = sharedPreferences.getInt(Login.UserID, 0);

        if (logincheck == 0) {

            Users users = showData(restoredUserID);

            if (users.getUsername() != null && users.getEmail() != null) {
                txtTitle.setText(users.getUsername());
                txtSubTitle.setText(users.getEmail());
             //   imgProfile.setImageBitmap(BitmapFactory.decodeByteArray(users.getBlob(), 0, users.getBlob().length));
            }

        } else if (logincheck == 1) {

            txtTitle.setText("Admin");
            txtSubTitle.setText("admin@gmail.com");
        }

    }

    private Users showData(int userID) {


        return users = databaseHelper.getUserdata(userID);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // The action bar home/up action should open or close the drawer.
        switch (item.getItemId()) {
            case android.R.id.home:
                drawer.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setUpNavigationView() {

        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {


                switch (menuItem.getItemId()) {

                    case R.id.nav_display_data:
                        setTitle("Profile");
                        Fragment displayDataFragment = new DisplayDataFragment();
                        openFragment(displayDataFragment);
                        break;
                    case R.id.nav_users_list:
                        setTitle("Users List");
                        Fragment usersListFragment = new UsersListFragment();
                        openFragment(usersListFragment);
                        break;
                    case R.id.nav_map:
                        setTitle("Location");
                        Fragment mapFragment = new MapFragment();
                        CurrentLocation location = new CurrentLocation(NavigationDrawerActivity.this);
                        location.checkPermission();
                        openFragment(mapFragment);
                        break;
                    case R.id.nav_about_us:
                        setTitle("About Us");
                        Fragment aboutUsFragment = new AboutUsFragment();
                        openFragment(aboutUsFragment);
                        break;

                    case R.id.nav_share:
                        // launch new intent instead of loading fragment
                        try {
                            Intent intent = new Intent(Intent.ACTION_SEND);
                            intent.setType("text/plain");
                            intent.putExtra(Intent.EXTRA_SUBJECT, "DrawerDemo");
                            String sAux = "\nLet me recommend you this application\n\n";
                            sAux = sAux + "https://play.google.com/store/apps \n\n";
                            intent.putExtra(Intent.EXTRA_TEXT, sAux);
                            startActivity(Intent.createChooser(intent, "Choose one"));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    case R.id.logout:
                        createDialog();
                        break;

                    default:
                        /*displayDataFragment = new DisplayDataFragment();
                        openFragment(displayDataFragment);*/

                }
                //setTitle(menuItem.getTitle());
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION:

                switch (resultCode) {
                    case Activity.RESULT_OK:
                        // All required changes were successfully made

                        break;
                    case Activity.RESULT_CANCELED:
                        // The user was asked to change settings, but chose not to
                        Toast.makeText(this, "GPS not Enabled", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
                break;
        }
    }

    private void openFragment(Fragment fragment) {

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.frame, fragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    private void createDialog() {

        AlertDialog.Builder alertDlg = new AlertDialog.Builder(this);
        alertDlg.setIcon(R.drawable.ic_warning_black_24dp);
        alertDlg.setTitle("Really Logout ?");
        alertDlg.setMessage("Are you sure you want to Logout?");
        alertDlg.setCancelable(false); // We avoid that the dialong can be cancelled, forcing the user to choose one of the options
        alertDlg.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        sharedPreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);
                        SharedPreferences.Editor e = sharedPreferences.edit();

                        if (logincheck == 0) {
                            Intent i = new Intent(NavigationDrawerActivity.this, Login.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(i);
                        } else if (logincheck == 1) {
                            Intent i = new Intent(NavigationDrawerActivity.this, AdminLoginActivity.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(i);
                        }
                        e.clear();
                        e.apply();
                        NavigationDrawerActivity.this.finish();   //finish current activity
                    }
                }
        );
        alertDlg.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override

            public void onClick(DialogInterface dialog, int which) {
                // We do nothing
            }
        });
        alertDlg.create().show();
    }

    private void createDialogExit() {

        AlertDialog.Builder alertDlg = new AlertDialog.Builder(this);
        alertDlg.setIcon(R.drawable.ic_warning_black_24dp);
        alertDlg.setTitle("Really Exit ?");
        alertDlg.setMessage("Are you sure you want to exit?");
        alertDlg.setCancelable(false); // We avoid that the dialong can be cancelled, forcing the user to choose one of the options
        alertDlg.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        NavigationDrawerActivity.this.finish();
                    }
                }
        );
        alertDlg.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override

            public void onClick(DialogInterface dialog, int which) {
                // We do nothing
            }

        });
        alertDlg.create().show();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
            return;
        }
        createDialogExit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_drawer, menu);
        isEnable();
        return true;
    }

    public void isEnable() {

        if (logincheck == 0) {
            MenuItem menuItem = menu.findItem(R.id.nav_users_list);
            menuItem.setVisible(false);
        } else if (logincheck == 1) {
            MenuItem menuItem = menu.findItem(R.id.nav_display_data);
            MenuItem menuItem1 = menu.findItem(R.id.nav_about_us);
            menuItem.setVisible(false);
            menuItem1.setVisible(false);
        }
    }
}
