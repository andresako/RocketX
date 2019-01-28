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
import java.text.SimpleDateFormat
import java.util.*

class LaunchDetailsAdapter(
    val launchList: MutableList<Any>,
    val description: String
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

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


    override fun getItemCount(): Int {
        return launchList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is DateViewHolder -> holder.bind(launchList[position] as String)
            is LaunchViewHolder -> holder.bind(launchList[position] as LaunchEntity)
        }
    }

    fun inflateViewHolder(parent: ViewGroup, @LayoutRes layoutRes: Int): View? {
        return LayoutInflater.from(parent.context).inflate(layoutRes, parent, false)
    }

}

class DateViewHolder(val view: TextView) : RecyclerView.ViewHolder(view) {
    fun bind(header: String) {
        view.text = header
    }
}

class LaunchViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    fun bind(launch: LaunchEntity) = with(view) {
        val date = launch.launchDateUtc.toDate().formatTo("dd MMM yyyy")
        val success = launch.launchSuccess.toString()

        launchMissionName.text = launch.missionName
        launchDate.text = date
        launchSuccess.text = success
    }
}


fun String.toDate(dateFormat: String = "yyyy-MM-dd HH:mm:ss", timeZone: TimeZone = TimeZone.getTimeZone("UTC")): Date {
    val parser = SimpleDateFormat(dateFormat, Locale.getDefault())
    parser.timeZone = timeZone
    return parser.parse(this)
}

fun Date.formatTo(dateFormat: String, timeZone: TimeZone = TimeZone.getDefault()): String {
    val formatter = SimpleDateFormat(dateFormat, Locale.getDefault())
    formatter.timeZone = timeZone
    return formatter.format(this)
}
