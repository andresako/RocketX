package com.andresako.rocketx.ui.rocket

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.andresako.rocketx.R
import com.andresako.rocketx.data.NetworkStatus
import com.andresako.rocketx.data.Status
import com.andresako.rocketx.data.room.entity.RocketEntity
import com.andresako.rocketx.ui.base.BaseFragment
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.rocket_list_fragment.*
import javax.inject.Inject


class RocketListFragment : BaseFragment() {

    @Inject
    lateinit var viewModel: RocketListViewModel

    private var listAdapter: RocketListAdapter? = null

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)

        viewModel.getRockets().observe(this, Observer { updateRocketList(it!!) })
        viewModel.getNetworkStatus().observe(this, Observer { onNetworkStateChange(it!!) })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.rocket_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()

        rocketsSwipeRefresh.setOnRefreshListener { viewModel.loadRockets(true) }

        viewModel.loadRockets(false)
    }

    private fun initUI() {
        listAdapter = RocketListAdapter(mutableListOf(), this::goToLaunch)
        rocketsRecycler.layoutManager = LinearLayoutManager(context!!, RecyclerView.VERTICAL, false)
        rocketsRecycler.adapter = listAdapter
    }

    fun goToLaunch(rocketId: String, rocketName: String, description: String) {
//        goToLaunchPage
    }

    fun updateRocketList(rockets: List<RocketEntity>) {
        listAdapter?.updateList(rockets)
    }

    fun onNetworkStateChange(status: NetworkStatus) {
        if (rocketsSwipeRefresh.isRefreshing) updateStateWhenRefreshing(status)
        else updateState(status)
    }

    fun updateStateWhenRefreshing(networkStatus: NetworkStatus) {
        if (networkStatus.status != Status.RUNNING) {
            rocketsSwipeRefresh.isRefreshing = false
//            if (networkStatus.status == Status.FAILED)
//                display some failure
        }
    }

    fun updateState(networkState: NetworkStatus) =
        when (networkState.status) {
            Status.RUNNING -> showProgress()
            Status.SUCCESS -> hideProgress()
            Status.FAILED -> {
                hideProgress()
//                container.displayErrorDialog(context!!, networkState.msg!!)
            }
        }

    fun showProgress() {
        rocketsProgress.visibility = View.VISIBLE
    }

    fun hideProgress() {
        rocketsProgress.visibility = View.INVISIBLE
    }
}
