package com.narmware.jainjeevan.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.narmware.jainjeevan.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddVendorFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddVendorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddVendorFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    protected View mRoot;
    protected Spinner mServiceType;
    EditText mEdtName,mEdtState,mEdtCity,mEdtAddress;
    Button mBtnSubmitForm;

    String mName,mState,mCity,mAddress,mService;

    private OnFragmentInteractionListener mListener;

    public AddVendorFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddVendorFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddVendorFragment newInstance(String param1, String param2) {
        AddVendorFragment fragment = new AddVendorFragment();
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

    private void init() {
        //mServiceType = mRoot.findViewById(R.id.form_spinner);
        mEdtName=mRoot.findViewById(R.id.edt_name);
        mEdtCity=mRoot.findViewById(R.id.edt_city);
        mEdtAddress=mRoot.findViewById(R.id.edt_address);
        mEdtState=mRoot.findViewById(R.id.edt_state);

        mBtnSubmitForm=mRoot.findViewById(R.id.btn_submit_form);
        mBtnSubmitForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mName=mEdtName.getText().toString().trim();
                mCity=mEdtCity.getText().toString().trim();
                mState=mEdtState.getText().toString().trim();
                mAddress=mEdtAddress.getText().toString().trim();
                //mService=mEdtName.getText().toString().trim();

            }
        });
       // setSpinner();
    }

    private void setSpinner() {
        List<String> categories = new ArrayList<String>();
        categories.add("Dharamshala");
        categories.add("Bhojanalaya");
        categories.add("Food Vendor");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mServiceType.setAdapter(dataAdapter);

        mServiceType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getContext(), "Hello", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mRoot = inflater.inflate(R.layout.fragment_add_vendor, container, false);
        init();
        return mRoot;
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
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
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
