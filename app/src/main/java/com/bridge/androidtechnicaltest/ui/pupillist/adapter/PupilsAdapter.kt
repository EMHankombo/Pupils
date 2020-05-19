package com.bridge.androidtechnicaltest.ui.pupillist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bridge.androidtechnicaltest.R
import com.bridge.androidtechnicaltest.db.Pupil
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.pupil_item.view.*

class PupilsAdapter(private var pupils: MutableList<Pupil>) : RecyclerView.Adapter<PupilsAdapter.PupilViewHolder>() {

    fun setPupilsData(pupils:MutableList<Pupil>){
        this.pupils = pupils
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PupilViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.pupil_item, parent, false)

        return PupilViewHolder(view)
    }

    override fun getItemCount(): Int {
        return pupils.size
    }

    override fun onBindViewHolder(holder: PupilViewHolder, position: Int) {
        val pupil = pupils[position]
        holder.apply {
            name.text = pupil.name
            country.text = pupil.value
            Picasso.get().load(pupil.image).error(R.drawable.ic_person_black_24dp).into(picture)

        }

    }

    class PupilViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val name: TextView = itemView.tv_name
        val country: TextView = itemView.tv_country
        val picture: ImageView = itemView.card_picture
    }
}