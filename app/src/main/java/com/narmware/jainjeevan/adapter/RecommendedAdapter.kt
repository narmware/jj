package com.narmware.jainjeevan.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.narmware.jainjeevan.R

/**
 * Created by rohitsavant on 07/08/18.
 */
class RecommendedAdapter(context: Context, data: ArrayList<String>): RecyclerView.Adapter<RecommendedAdapter.Item> (){

    val mContext: Context
    val mData: ArrayList<String>

    init{
        mContext = context;
        mData = data
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): Item {
        val view = LayoutInflater.from(mContext).inflate(R.layout.item_recommended, parent, false);
        return Item(view)
    }

    override fun onBindViewHolder(holder: Item?, position: Int) {
        val data :String = mData.get(position)
        holder!!.title.setText(data)
    }

    class Item : RecyclerView.ViewHolder {

        val image: ImageView
        val title: TextView

        constructor(itemView: View?) : super(itemView) {
            image = itemView!!.findViewById(R.id.recomm_image)
            title = itemView!!.findViewById(R.id.recomm_title)
        }

    }
}