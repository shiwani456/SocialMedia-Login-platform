package com.myapp.contactdisplay.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.myapp.contactdisplay.R;
import com.myapp.contactdisplay.application.ActivityUserSpace;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UpdateProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UpdateProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpdateProfileFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ImageView userProfileImageView;
    private TextView userNameTextView, userEmailTextView, userPhoneTextView;
    private ActivityUserSpace session = null;
    private EditText userPhoneEditText, userEmailEditText;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public UpdateProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment UpdateProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UpdateProfileFragment newInstance() {
        UpdateProfileFragment fragment = new UpdateProfileFragment();
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
        View view = inflater.inflate(R.layout.fragment_update_profile, container, false);
        session = new ActivityUserSpace(getActivity());
        init(view);
        userNameTextView.setText(session.getUserName());
        if (session.getUserEmail() == null) {

            userEmailTextView.setVisibility(View.GONE);
            userEmailEditText.setVisibility(View.VISIBLE);
        } else {
            userEmailTextView.setVisibility(View.VISIBLE);
            userEmailTextView.setText(session.getUserEmail());
            userEmailEditText.setVisibility(View.GONE);
        }
        if (session.getUserPhone() == null) {

            userPhoneTextView.setVisibility(View.GONE);
            userPhoneEditText.setVisibility(View.VISIBLE);
        } else {
            userPhoneTextView.setVisibility(View.VISIBLE);
            userPhoneTextView.setText(session.getUserPhone());
            userEmailEditText.setVisibility(View.GONE);
        }
        return view;

    }

    private void init(View view) {
        userProfileImageView = view.findViewById(R.id.userProfileImageView);
        userNameTextView = view.findViewById(R.id.userNameTextView);
        userEmailTextView = view.findViewById(R.id.userEmailTextView);
        userPhoneEditText = view.findViewById(R.id.userPhoneEditText);
        userEmailEditText = view.findViewById(R.id.userEmailEditText);

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);/*
        if (context instanceof OnFragmentInteractionListener) {
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
