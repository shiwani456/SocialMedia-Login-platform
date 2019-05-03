package com.myapp.contactdisplay.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.myapp.contactdisplay.R;
import com.myapp.contactdisplay.activity.VerifyPhoneActivity;
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
    private Button verificationButton;
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

        } else {
            userEmailTextView.setText(session.getUserEmail());
        }
        if (session.getUserPhone() == null) {


        } else {
         //    userPhoneTextView.setText("+91"+session.getUserPhone());

        }
        userPhoneEditText.addTextChangedListener(new MyTextWatcher(userPhoneEditText));
        verificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String no = userPhoneEditText.getText().toString();
                if (submitForm()) {
//                    userPhoneTextView.setText(no);
                    session.setUserPhone(no);
                    Intent intent = new Intent(getActivity(), VerifyPhoneActivity.class);
                    intent.putExtra("mobile", no);
                    startActivity(intent);

                }

            }
        });


        return view;

    }


    private void init(View view) {
        userProfileImageView = view.findViewById(R.id.userProfileImageView);
        userNameTextView = view.findViewById(R.id.userNameTextView);
        verificationButton = view.findViewById(R.id.verificationButton);
        userEmailTextView = view.findViewById(R.id.userEmailTextView);
        userPhoneEditText = view.findViewById(R.id.userPhoneEditText);
        Glide.with(getActivity())
                .load(session.getUserImageUrl())
                .apply(RequestOptions.centerCropTransform().circleCrop())
                .into(userProfileImageView);

    }

    private boolean submitForm() {


        if (!validatePhoneNo()) {
            return false;
        }


        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private boolean isValidPhoneNumber(CharSequence phoneNumber) {
        return !TextUtils.isEmpty(phoneNumber) && Patterns.PHONE.matcher(phoneNumber).matches();
    }

    private boolean validatePhoneNo() {
        String phone = userPhoneEditText.getText().toString().trim();
        if (phone.isEmpty() || !isValidPhoneNumber(phone) || phone.length() != 10) {
            userPhoneEditText.setError("Enter a valid mobile");
            requestFocus(userPhoneEditText);
            return false;
        } else {
            return true;

        }

    }

    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.userPhoneEditText:
                    validatePhoneNo();
                    break;
            }
        }
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
