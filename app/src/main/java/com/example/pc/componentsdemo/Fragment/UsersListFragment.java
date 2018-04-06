package com.example.pc.componentsdemo.Fragment;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pc.componentsdemo.Activity.DisplayData;
import com.example.pc.componentsdemo.Others.DatabaseHelper;
import com.example.pc.componentsdemo.Others.UserListAdapter;
import com.example.pc.componentsdemo.Others.Users;
import com.example.pc.componentsdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class UsersListFragment extends Fragment {

    private ListView lst_userlist;
    private View v;
    private RecyclerView rvUserList;

    DatabaseHelper dataBaseHelper;
    UserListAdapter userListAdapter;
    private List<Users> userlist = new ArrayList<>();


    public UsersListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_users_list, container, false);
        initView();
        return v;
    }

    private void initView() {

        rvUserList = (RecyclerView) v.findViewById(R.id.rvuserlist);

        rvUserList.setHasFixedSize(true);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        rvUserList.setLayoutManager(mLayoutManager);

        userListAdapter = new UserListAdapter(getActivity(), userlist, UsersListFragment.this);
        rvUserList.setAdapter(userListAdapter);

        dataBaseHelper = new DatabaseHelper(getActivity());
        userlist = dataBaseHelper.showUserEmail();
        userListAdapter.addData(userlist);


    }

    public void openDetails(int position) {

        Toast.makeText(getActivity(), "" + position, Toast.LENGTH_SHORT).show();

        Users users = userlist.get(position);

        Intent intent = new Intent(getActivity(), DisplayData.class);
        intent.putExtra("LISTITEMID", String.valueOf(position));
        intent.putExtra("EMAIL", users.getEmail());
        intent.putExtra("userId", users.getUserid());

        startActivity(intent);

    }
}
