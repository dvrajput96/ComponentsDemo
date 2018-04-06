package com.example.pc.componentsdemo.Others;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pc.componentsdemo.Fragment.UsersListFragment;
import com.example.pc.componentsdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc on 12/28/17.
 */

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.MyViewHolder> {

    private List<Users> usersList = new ArrayList<>();
    Context context;
    UsersListFragment usersListFragment;


    @Override
    public UserListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view_data, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(UserListAdapter.MyViewHolder holder, final int position) {
        Users users = usersList.get(position);
        holder.useremail.setText(users.getEmail());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usersListFragment.openDetails(position);
            }
        });

    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }

    public UserListAdapter(Context activity, List<Users> usersList, UsersListFragment usersListFragment)
    {
        this.usersList = usersList;
        this.context = activity;
        this.usersListFragment = usersListFragment;
    }

    public void addData(List<Users> userlist) {
        usersList=userlist;
        notifyDataSetChanged();

    }

    public class MyViewHolder extends ViewHolder {
        public TextView useremail;

        public MyViewHolder(View itemView) {
            super(itemView);
            useremail = (TextView) itemView.findViewById(R.id.txtemailshow);
        }
    }
}
