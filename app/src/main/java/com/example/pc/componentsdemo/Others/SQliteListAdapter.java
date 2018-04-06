package com.example.pc.componentsdemo.Others;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.pc.componentsdemo.R;

import java.util.ArrayList;

/**
 * Created by pc on 12/15/17.
 */

public class SQliteListAdapter extends BaseAdapter {

    Context context;
    private ArrayList<Users> users = new ArrayList<>();

    public SQliteListAdapter(Context context, ArrayList<Users> users) {
        this.context = context;
        this.users = users;
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public Object getItem(int position) {
        return users.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public CharSequence[] getAutofillOptions() {
        return new CharSequence[0];
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Holder holder;

        LayoutInflater layoutInflater;

        if (convertView == null) {
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_view_data, null);

            holder = new Holder();
            holder.txtEmail = (TextView) convertView.findViewById(R.id.txtemailshow);

            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        Users user = users.get(position);
        holder.txtEmail.setText(user.getEmail());

        return convertView;
    }

    private class Holder {
        TextView txtEmail;
    }
}
