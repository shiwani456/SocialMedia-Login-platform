package com.myapp.contactdisplay.database;

import java.util.ArrayList;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.myapp.contactdisplay.R;


public class SQLiteListAdapter extends BaseAdapter {

        private Context mContext;
        DBHelper controldb;
        SQLiteDatabase db;
        private ArrayList<String> name = new ArrayList<String>();
        private ArrayList<String> mail = new ArrayList<String>();
        private ArrayList<String> phone = new ArrayList<String>();
    public SQLiteListAdapter(Context  context,ArrayList<String> name, ArrayList<String> mail,ArrayList<String> phone)
        {
            this.mContext = context;
            this.name = name;
            this.mail = mail;
            this.phone=phone;
        }
        @Override
        public int getCount() {
            return name.size();
        }
        @Override
        public Object getItem(int position) {
            return null;
        }
        @Override
        public long getItemId(int position) {
            return 0;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final    viewHolder holder;
            controldb =new DBHelper(mContext);
            LayoutInflater layoutInflater;
            if (convertView == null) {
                layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(R.layout.list_display, null);
                holder = new viewHolder();
                holder.nameListTextView = (TextView) convertView.findViewById(R.id.nameListTextView);
                holder.emailListTextView = (TextView) convertView.findViewById(R.id.emailListTextView);
                holder.phoneListTextView = (TextView) convertView.findViewById(R.id.phoneListTextView);
                convertView.setTag(holder);
            } else {
                holder = (viewHolder) convertView.getTag();
            }
            holder.nameListTextView.setText(name.get(position));
            holder.emailListTextView.setText(mail.get(position));
            holder.phoneListTextView.setText(phone.get(position));
            return convertView;
        }
        public class viewHolder {
            TextView nameListTextView;
            TextView emailListTextView;
            TextView phoneListTextView;
        }
}
