package com.myapp.contactdisplay.fragment;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.myapp.contactdisplay.R;
import com.myapp.contactdisplay.database.DBHelper;
import com.myapp.contactdisplay.database.SQLiteListAdapter;

import java.util.ArrayList;
import java.util.jar.Attributes;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ContactListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ContactListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContactListFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ListView dataListView;
    private  ArrayList<String> userEmail = new ArrayList<>();
    private ArrayList<String> userName = new ArrayList<>();
   private ArrayList<String> userPhone = new ArrayList<>();
    private Cursor cursor;
    private SQLiteDatabase SQLITEDATABASE;
    private SQLiteListAdapter ListAdapter;
    private DBHelper mydb = new DBHelper(getActivity());
    ;
    private Button getdetailButton;


    private OnFragmentInteractionListener mListener;

    public ContactListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ContactListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ContactListFragment newInstance() {
        ContactListFragment fragment = new ContactListFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact_list, container, false);
        init(view);
     /*   getdetailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowSQLiteDBdata();
            }
        });*/

        return view;
    }

    private void init(View view) {
        dataListView = view.findViewById(R.id.dataListView);
        getdetailButton = view.findViewById(R.id.getdetailButton);


    }

    private void ShowSQLiteDBdata() {

        SQLITEDATABASE = mydb.getWritableDatabase();

        cursor = SQLITEDATABASE.rawQuery("SELECT * FROM user", null);


        userName.clear();
        userEmail.clear();
        userPhone.clear();


        if (cursor.moveToFirst()) {
            do {
                userName.add(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_NAME)));

                userEmail.add(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_EMAIL)));

                userPhone.add(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_PHONE)));

            } while (cursor.moveToNext());
        }

        ListAdapter = new SQLiteListAdapter(getActivity(),

                userName,
                userEmail,
                userPhone

        );

        dataListView.setAdapter(ListAdapter);

        cursor.close();
    }
    @Override
    public void onResume() {
        displayData();
        super.onResume();
    }
    private void displayData() {
        SQLITEDATABASE = mydb.getReadableDatabase();
        Cursor cursor = SQLITEDATABASE.rawQuery("SELECT * FROM  user",null);
        userName.clear();
     userEmail.clear();
        userPhone.clear();
        if (cursor.moveToFirst()) {
            do {
                userName.add(cursor.getString(cursor.getColumnIndex("name")));
                userEmail.add(cursor.getString(cursor.getColumnIndex("mail")));
                userPhone.add(cursor.getString(cursor.getColumnIndex("phone")));
            } while (cursor.moveToNext());
        }
        SQLiteListAdapter ca = new SQLiteListAdapter(getActivity(),userName, userEmail,userPhone);
        dataListView.setAdapter(ca);
        //code to set adapter to populate list
        cursor.close();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
       /* if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
