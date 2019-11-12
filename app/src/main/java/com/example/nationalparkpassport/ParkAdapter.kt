package com.example.nationalparkpassport

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class ParkAdapter constructor(private val parks: List<Park>, private val rowClickedCallback: (String) -> Unit) : RecyclerView.Adapter<ParkAdapter.ViewHolder>() {
    override fun getItemCount(): Int = parks.size

    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.row_park, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentPark = parks[position]

        holder.nameTextView.text = currentPark.name
        holder.addressTextView.text = currentPark.address

        holder.cardViewHolder.setOnClickListener {
            val parkCode = currentPark.parkCode
            rowClickedCallback(parkCode)
        }


    }

    class ViewHolder constructor(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.findViewById(R.id.parkName)
        val addressTextView: TextView = view.findViewById(R.id.parkAddress)
        val cardViewHolder: CardView = view.findViewById(R.id.cardView)
    }



}


