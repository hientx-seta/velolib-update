package com.jcdecaux.velolib.view.adapters

import android.content.Context
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.jcdecaux.velolib.R
import com.jcdecaux.velolib.model.StationItem
import com.jcdecaux.velolib.view.activities.StationActivityDetail
import kotlinx.android.synthetic.main.item_station.view.*

/**
 * Created by hientx on 11/16/2017.
 */

class StationAdapter : RecyclerView.Adapter<StationAdapter.StationViewHolder>() {

    private var mDataSource: List<StationItem>? = null

    fun setDataSource(dataSource: List<StationItem>) {
        this.mDataSource = dataSource
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StationViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_station, parent, false)
        return StationViewHolder(view)
    }

    override fun onBindViewHolder(holder: StationViewHolder, position: Int) {
        val newsItem = mDataSource!![position]
        holder.bind(newsItem)
    }


    override fun getItemCount(): Int {
        return mDataSource?.size ?: 0
    }

    class StationViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val context: Context = itemView.context

        var stationItem: StationItem? = null

        private val stationItemRootLayout: CardView? by lazy {
            itemView.findViewById(R.id.stationItemRootLayout) as CardView?
        }

        private val nameTextView: TextView? by lazy {
            itemView.findViewById(R.id.stationNameTextView) as TextView?
        }
        private val addressTextView: TextView?by lazy {
            itemView.findViewById(R.id.addressTextView) as TextView?
        }

        fun bind(stationItem: StationItem) {
            this.stationItem = stationItem
            nameTextView!!.text = stationItem.name
            addressTextView!!.text = stationItem.address

            itemView.stationItemRootLayout.setOnClickListener { context.startActivity(StationActivityDetail.getIntent(context, stationItem)) }
        }
    }

}