package com.example.mongotest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.mongotest.Class.User;

import java.util.List;

public class CustomAdapter extends BaseAdapter {

    private Context mContext;
    private List<User> lstUsers;

    public CustomAdapter(Context mContext, List<User> isUsers) {
        this.mContext = mContext;
        this.lstUsers = isUsers;
    }

    @Override
    public int getCount() {
        return lstUsers.size();
    }

    @Override
    public Object getItem(int position) {
        return lstUsers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.row,null);

        TextView txtUser = (TextView)view.findViewById(R.id.txtUser);
        txtUser.setText(lstUsers.get(position).getUser());

        return view;
    }
}
