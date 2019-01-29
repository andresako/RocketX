package com.andresako.rocketx.ui.launch

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.andresako.rocketx.R
import com.andresako.rocketx.data.NetworkStatus
import com.andresako.rocketx.data.Status
import com.andresako.rocketx.data.room.entity.LaunchEntity
import com.andresako.rocketx.ui.base.BaseFragment
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.launch_details_fragment.*
import javax.inject.Inject

class LaunchDetailsFragment : BaseFragment() {

    @Inject
    lateinit var viewModel: LaunchDetailsViewModel
    lateinit var rocketId: String

    var adapter: LaunchDetailsAdapter? = null


    companion object {
        fun newInstance(
            rocketId: String,
            rocketName: String,
            rocketDesc: String
        ): LaunchDetailsFragment {
            return LaunchDetailsFragment().apply {
                val bundle = Bundle()
                bundle.putString("ROCKET_ID", rocketId)
                bundle.putString("ROCKET_NAME", rocketName)
                bundle.putString("ROCKET_DESC", rocketDesc)
                arguments = bundle
            }
        }
    }

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)

        rocketId = arguments?.getString("ROCKET_ID") ?: throw RuntimeException("Missing Rocket_ID argument")

        viewModel.getLaunches().observe(this, Observer { updateLaunchesList(it) })
        viewModel.getNetworkStatus().observe(this, Observer { onNetworkStateChange(it!!) })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.launch_details_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val description = arguments?.getString("ROCKET_DESC") ?: throw RuntimeException("Missing Rocket_Desc argument")

        initUI(description)

        viewModel.loadLaunches(rocketId, false)
    }

    fun initUI(description: String) {
        launchDesc.text = description
        adapter = LaunchDetailsAdapter(mutableListOf())
        launchRecycler.layoutManager = LinearLayoutManager(context!!, RecyclerView.VERTICAL, false)
        launchRecycler.adapter = adapter
    }

    fun updateLaunchesList(launchesList: List<LaunchEntity>) {
        adapter?.updateLaunches(launchesList)
    }

    fun onNetworkStateChange(status: NetworkStatus) {
        updateState(status)
    }

    fun updateState(networkState: NetworkStatus) =
        when (networkState.status) {
            Status.RUNNING -> showProgress()
            Status.SUCCESS -> hideProgress()
            Status.FAILED -> {
                hideProgress()
                Toast.makeText(context, networkState.msg!!, Toast.LENGTH_SHORT)
            }
        }

    fun showProgress() {
        launchProgress.visibility = View.VISIBLE
    }

    fun hideProgress() {
        launchProgress.visibility = View.INVISIBLE
    }

}
