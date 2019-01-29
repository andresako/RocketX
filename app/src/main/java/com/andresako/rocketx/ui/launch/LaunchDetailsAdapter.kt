package com.andresako.rocketx.ui.launch

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.andresako.rocketx.R
import com.andresako.rocketx.data.room.entity.LaunchEntity
import kotlinx.android.synthetic.main.launch_details_item.view.*

class LaunchDetailsAdapter(
    private val launchList: MutableList<Any>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val adapterList = mutableListOf<Any>()

    companion object {
        const val DATE = 0
        const val LAUNCH = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            DATE -> DateViewHolder(inflateViewHolder(parent, R.layout.launch_details_header) as TextView)
            LAUNCH -> LaunchViewHolder(inflateViewHolder(parent, R.layout.launch_details_item) as View)
            else -> throw RuntimeException("Error inflating view")
        }

    override fun getItemViewType(position: Int): Int =
        when (launchList[position]) {
            is LaunchHeader -> DATE
            is LaunchEntity -> LAUNCH
            else -> throw RuntimeException("Wrong Item view type")
        }


    override fun getItemCount(): Int {
        return launchList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is DateViewHolder -> holder.bind(launchList[position] as LaunchHeader)
            is LaunchViewHolder -> holder.bind(launchList[position] as LaunchEntity)
        }
    }

    private fun inflateViewHolder(parent: ViewGroup, @LayoutRes layoutRes: Int): View? {
        return LayoutInflater.from(parent.context).inflate(layoutRes, parent, false)
    }

    fun updateLaunches(launchList: List<LaunchEntity>) {
        adapterList.clear()
        launchList.sortedBy { launch -> launch.launchDateUnix }

        var count = 0
        for (launch in launchList) {
            if (launch.launchYear > count) {
                count = launch.launchYear
                adapterList.add(LaunchHeader(count.toString()))
            }
            adapterList.add(launch)
        }

        this.launchList.clear()
        this.launchList.addAll(adapterList)

        notifyDataSetChanged()
    }
}

class DateViewHolder(private val view: TextView) : RecyclerView.ViewHolder(view) {
    fun bind(header: LaunchHeader) {
        view.text = header.year
    }
}

class LaunchViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
    fun bind(launch: LaunchEntity) = with(view) {
        val success = launch.launchSuccess
        val shortDate = launch.launchDateUtc.substring(0, 10)

        launchMissionName.text = itemView.context.getString(R.string.mission_name, launch.missionName)
        launchDate.text = itemView.context.getString(R.string.mission_date, shortDate)
        launchSuccess.text =
                itemView.context.getString(if (success) R.string.mission_success else R.string.mission_fail)
    }
}

data class LaunchHeader(
    val year: String
)
