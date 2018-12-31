package com.narmware.jainjeevan.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.narmware.jainjeevan.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddressFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddressFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddressFragment extends Fragment implements OnMapReadyCallback {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String LAT = "lat";
    private static final String LONG = "long";
    private static final String ADDRESS = "addr";
    private static final String DHARAM_NAME = "dharam_name";
    private static final String CONTACT_NAME = "contact_name";
    private static final String CONTACT_NUMBER = "contact_number";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private GoogleMap mMap;
    Double latitude,longitude;
    String mAddr,mDharamName,mContactName,mContactNumber;

    TextView mTxtAddr,mTxtContactPerson,mTxtContactNo,mTxtContactTitle;
    String[] mPhone1;
    static Activity activity;

    public AddressFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static AddressFragment newInstance(String latitude, String longitude,String address,String dharamshala,String contact_name,String contact_number) {
        AddressFragment fragment = new AddressFragment();
        Bundle args = new Bundle();
        args.putString(LAT, latitude);
        args.putString(LONG, longitude);
        args.putString(ADDRESS, address);
        args.putString(DHARAM_NAME, dharamshala);
        args.putString(CONTACT_NAME, contact_name);
        args.putString(CONTACT_NUMBER, contact_number);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            try {
                latitude = Double.valueOf(getArguments().getString(LAT));
                longitude = Double.valueOf(getArguments().getString(LONG));
            }catch (Exception e)
            {}
                mAddr = getArguments().getString(ADDRESS);
                mDharamName = getArguments().getString(DHARAM_NAME);
                mContactName = getArguments().getString(CONTACT_NAME);
                mContactNumber = getArguments().getString(CONTACT_NUMBER);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_address, container, false);
        SupportMapFragment mapFragment= (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        activity=getActivity();

        mTxtAddr=view.findViewById(R.id.txt_address);
        mTxtContactPerson=view.findViewById(R.id.txt_contact_person);
        mTxtContactNo=view.findViewById(R.id.txt_contact_no);
        mTxtContactTitle=view.findViewById(R.id.txt_contact_title);

        mTxtAddr.setText(mAddr);
        mTxtContactNo.setText(mContactNumber);
        mTxtContactPerson.setText(mContactName);
        if(mContactName.equals(""))
        {
            mTxtContactPerson.setText("No contact person name available");
        }

        mTxtContactTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(mContactNumber==null || mContactNumber.equals(""))
                {
                }else{

                    if(mContactNumber.contains("/"))
                    {
                        String[] separated_nums = mContactNumber.split("/");
                        Log.e("Numbers",separated_nums[0]+"  "+separated_nums[1]);

                        mPhone1=new String[separated_nums.length];

                        for(int i=0;i<separated_nums.length;i++)
                        {
                            mPhone1[i]=separated_nums[i];
                        }

                        registerForContextMenu(mTxtContactTitle);
                        getActivity().openContextMenu(mTxtContactTitle);
                    }
                    else{
                        makeCall(mContactNumber);
                    }
                }
            }
        });

        mTxtContactNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(mContactNumber==null || mContactNumber.equals(""))
                {
                }else{

                    if(mContactNumber.contains("/"))
                    {
                        String[] separated_nums = mContactNumber.split("/");
                        Log.e("Numbers",separated_nums[0]+"  "+separated_nums[1]);

                        mPhone1=new String[separated_nums.length];

                        for(int i=0;i<separated_nums.length;i++)
                        {
                            mPhone1[i]=separated_nums[i];
                        }

                        registerForContextMenu(mTxtContactNo);
                        getActivity().openContextMenu(mTxtContactNo);
                    }
                    else{
                        makeCall(mContactNumber);
                    }
                }
            }
        });
        return view;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        Log.e("Numbers",mPhone1.length+"  ");


        if (v.getId() == R.id.txt_contact_no) {

            menu.setHeaderTitle("Select phone number to dial");

            for(int i=0;i<mPhone1.length;i++)
            {
                menu.add(mPhone1[i]);
            }
        }

        if (v.getId() == R.id.txt_contact_title) {

            menu.setHeaderTitle("Select phone number to dial");
            for(int i=0;i<mPhone1.length;i++)
            {
                menu.add(mPhone1[i]);
            }
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        try {
            String number = String.valueOf(item.getTitle());
            makeCall(number);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return super.onContextItemSelected(item);

    }

    public void makeCall(String phone)
    {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
        startActivity(intent);
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

    @Override
    public void onMapReady(GoogleMap googleMap) {

        try{
            mMap = googleMap;
            // Add a marker in Sydney and move the camera
            Log.e("LatLng",latitude+"  "+longitude);
            LatLng dharamshala_name = new LatLng(latitude, longitude);
            mMap.addMarker(new MarkerOptions().position(dharamshala_name).title(mDharamName));
            mMap.moveCamera( CameraUpdateFactory.newLatLngZoom(dharamshala_name, 14.0f) );

           // mMap.moveCamera(CameraUpdateFactory.newLatLng(dharamshala_name));
        }catch (Exception e)
        {
            e.printStackTrace();
        }

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
