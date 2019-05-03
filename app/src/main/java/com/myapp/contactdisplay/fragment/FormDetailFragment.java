package com.myapp.contactdisplay.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
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
import android.widget.Toast;

import com.myapp.contactdisplay.R;
import com.myapp.contactdisplay.database.DBHelper;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FormDetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FormDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FormDetailFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button addDataButton;
    DBHelper mydb;
    String  name , email;
    String phone;
    private TextInputLayout nameTextInputLayout, emailTextInputLayout, phoneTextInputLayout;
    private EditText userNameEditText, emailEditText, phoneEditText;
    private OnFragmentInteractionListener mListener;

    public FormDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FormDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FormDetailFragment newInstance(String param1, String param2) {
        FormDetailFragment fragment = new FormDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
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
        View view = inflater.inflate(R.layout.fragment_form_detail, container, false);
        init(view);
        addlistenner();
        return view;

    }

    private void init(View view) {

        nameTextInputLayout = (TextInputLayout) view.findViewById(R.id.nameTextInputLayout);
        emailTextInputLayout =(TextInputLayout) view.findViewById(R.id.emailTextInputLayout);
        phoneTextInputLayout = (TextInputLayout) view.findViewById(R.id.phoneTextInputLayout);
        userNameEditText = (EditText) view.findViewById(R.id.userNameEditText);
        emailEditText = (EditText) view.findViewById(R.id.emailEditText);
        phoneEditText = (EditText) view.findViewById(R.id.phoneEditText);

        addDataButton = view.findViewById(R.id.addDataButton);
    }

    private void addlistenner() {

        userNameEditText.addTextChangedListener(new MyTextWatcher(userNameEditText));
        emailEditText.addTextChangedListener(new MyTextWatcher(emailEditText));
        phoneEditText.addTextChangedListener(new MyTextWatcher(phoneEditText));
        addDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = userNameEditText.getText().toString().trim();
                email = (String) emailEditText.getText().toString().trim();
                phone = phoneEditText.getText().toString().trim();
                if (submitForm()) {
                    mydb = new DBHelper(getActivity());
                    mydb.insertData(name, email, phone);
                Toast.makeText(getActivity(), "Data Added Successfully", Toast.LENGTH_SHORT).show();
            }else
                {

                    Toast.makeText(getActivity(), "Please check your details", Toast.LENGTH_SHORT).show();
                }

            }
        });



    }

    private boolean submitForm() {

        if (!validateName()) {
            return false;
        }
        if (!validateEmail()) {
            return false;
        }


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
    private boolean validateName() {
        if (userNameEditText.getText().toString().trim().isEmpty()) {
            nameTextInputLayout.setError(getString(R.string.error_msg_name));
            requestFocus(userNameEditText);
            return false;
        } else {
            nameTextInputLayout.setErrorEnabled(false);
            return true;
        }


    }
    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    private boolean validateEmail() {
        String email = emailEditText.getText().toString().trim();
        if (email.isEmpty() || !isValidEmail(email)) {
            emailTextInputLayout.setError(getString(R.string.error_msg_email));
            requestFocus(emailEditText);
            return false;
        } else {
            emailTextInputLayout.setErrorEnabled(false);
            return true;
        }


    }
    private boolean isValidPhoneNumber(CharSequence phoneNumber) {
        return !TextUtils.isEmpty(phoneNumber) && Patterns.PHONE.matcher(phoneNumber).matches();
    }

    private boolean validatePhoneNo() {
        String phone = phoneEditText.getText().toString().trim();
        if (phone.isEmpty() || !isValidPhoneNumber(phone) || phone.length() != 10) {
            phoneTextInputLayout.setError(getString(R.string.error_msg_phone_no));
            requestFocus(phoneEditText);
            return false;
        } else {
            phoneTextInputLayout.setErrorEnabled(false);
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
                case R.id.userNameEditText:
                    validateName();
                    break;
                case R.id.emailEditText:
                    validateEmail();
                    break;

                case R.id.phoneEditText:
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
        super.onAttach(context);
     /*   if (context instanceof OnFragmentInteractionListener) {
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
