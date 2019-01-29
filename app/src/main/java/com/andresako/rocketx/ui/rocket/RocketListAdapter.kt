package com.andresako.rocketx.ui.rocket

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.andresako.rocketx.R
import com.andresako.rocketx.data.room.entity.RocketEntity
import kotlinx.android.synthetic.main.rocket_list_item.view.*

class RocketListAdapter(
    private val rocketList: MutableList<RocketEntity>,
    private val onClick: (String, String) -> Unit
) : RecyclerView.Adapter<RocketViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RocketViewHolder {
        return RocketViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.rocket_list_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return rocketList.size
    }

    override fun onBindViewHolder(holder: RocketViewHolder, position: Int) {
        holder.bindView(rocketList[position], onClick)
    }

    fun updateList(rocketListNew: List<RocketEntity>) {
        rocketList.clear()
        rocketList.addAll(rocketListNew)
        notifyDataSetChanged()
    }
}

class RocketViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bindView(rocket: RocketEntity, onClick: (String, String) -> Unit) = with(itemView) {
        rocketName.text = itemView.context.getString(R.string.rocket_name, rocket.rocketName)
        rocketCountry.text = itemView.context.getString(R.string.rocket_country, rocket.country)
        rocketEnginesCount.text = itemView.context.getString(R.string.rocket_engines, rocket.numberOfEngines)
        setOnClickListener { onClick(rocket.rocketId, rocket.description) }
    }
}
