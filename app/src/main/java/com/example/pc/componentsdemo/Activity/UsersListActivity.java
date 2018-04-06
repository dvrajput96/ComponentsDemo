package com.example.pc.componentsdemo.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.pc.componentsdemo.Others.DatabaseHelper;
import com.example.pc.componentsdemo.R;
import com.example.pc.componentsdemo.Others.SQliteListAdapter;
import com.example.pc.componentsdemo.Others.Users;

import java.util.ArrayList;

public class UsersListActivity extends AppCompatActivity {

    private ListView lst_userlist;
    private Toolbar myToolbar;
    private ImageView ivBackArrow;
    private TextView txtToolbar;


    DatabaseHelper dataBaseHelper;
    SQliteListAdapter sQliteListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_list);
        initView();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void initView() {

        lst_userlist = (ListView) findViewById(R.id.lstuserlist);
        myToolbar = (Toolbar) findViewById(R.id.mtoolbar);
        ivBackArrow = (ImageView) findViewById(R.id.ivbackarrow);
        txtToolbar = (TextView) findViewById(R.id.txttoolbar);
        setSupportActionBar(myToolbar);

        txtToolbar.setText("Users List");
        ivBackArrow.setVisibility(View.GONE);

        lst_userlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                try {
                    Users users = (Users) lst_userlist.getItemAtPosition(position);
                    Intent intent = new Intent(getApplicationContext(), DisplayData.class);
                    intent.putExtra("LISTITEMID", String.valueOf(position));
                    intent.putExtra("EMAIL", users.getEmail());
                    intent.putExtra("userId", users.getUserid());
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }


        });

        dataBaseHelper = new DatabaseHelper(UsersListActivity.this);
        ArrayList<Users> usersArrayList = dataBaseHelper.showUserEmail();
        sQliteListAdapter = new SQliteListAdapter(UsersListActivity.this, usersArrayList);
        lst_userlist.setAdapter(sQliteListAdapter);

    }

}
