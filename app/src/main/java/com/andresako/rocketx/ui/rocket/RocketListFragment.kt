package com.andresako.rocketx.ui.rocket

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.andresako.rocketx.R
import com.andresako.rocketx.data.room.entity.RocketEntity
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.rocket_list_fragment.*
import javax.inject.Inject


class RocketListFragment : Fragment() {

    @Inject
    lateinit var viewModel: RocketListViewModel

    private var listAdapter: RocketListAdapter? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)

        viewModel.getRockets().observe(this, Observer { updateRocketList(it!!) })
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
}
