package com.narmware.jainjeevan.fragment

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.narmware.jainjeevan.R
import com.narmware.jainjeevan.adapter.RecommendedAdapter
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [HomeFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment:Fragment() {

    // TODO: Rename and change types of parameters
    private var mParam1:String? = null
    private var mParam2:String? = null
    private var mListener:OnFragmentInteractionListener? = null
    private var mRecommendedRecycler:RecyclerView? = null
    private var mView:View? = null
    private var mRecommendedAdapter: RecommendedAdapter? = null
    val mData = listOf<String>("Vendor 1", "Vendor 2", "Vendor 3", "Vendor 4", "Vendor 5", "Vendor 6", "Vendor 7")

    private fun init() {
        mRecommendedAdapter = RecommendedAdapter(context, mData)
        mRecommendedRecycler= mView?.findViewById<RecyclerView>(R.id.home_recycler_recomm)
        mRecommendedRecycler?.adapter = mRecommendedAdapter
        mRecommendedRecycler?.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL, false)
        mRecommendedRecycler?.itemAnimator = DefaultItemAnimator()

    }

    public override fun onCreate(savedInstanceState:Bundle?) {
        super.onCreate(savedInstanceState)
        if (getArguments() != null)
        {
            mParam1 = getArguments().getString(ARG_PARAM1)
            mParam2 = getArguments().getString(ARG_PARAM2)
        }
    }

    public override fun onCreateView(inflater:LayoutInflater?, container:ViewGroup?,
                                     savedInstanceState:Bundle?):View? {
        // Inflate the layout for this fragment
        mView= inflater!!.inflate(R.layout.fragment_home, container, false)
        init()
        return mView
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri:Uri) {
        if (mListener != null)
        {
            mListener!!.onFragmentInteraction(uri)
        }
    }

    public override fun onAttach(context:Context?) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener)
        {
            mListener = context as OnFragmentInteractionListener?
        }
        else
        {
            throw RuntimeException((context!!.toString() + " must implement OnFragmentInteractionListener"))
        }
    }

    public override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html) for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri:Uri)
    }

    companion object {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private val ARG_PARAM1 = "param1"
        private val ARG_PARAM2 = "param2"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1:String, param2:String):HomeFragment {
            val fragment = HomeFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.setArguments(args)
            return fragment
        }
    }
}// Required empty public constructor
