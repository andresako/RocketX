package com.andresako.rocketx.ui.launch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.andresako.rocketx.R
import com.andresako.rocketx.ui.base.BaseFragment
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.launch_details_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(LaunchDetailsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
