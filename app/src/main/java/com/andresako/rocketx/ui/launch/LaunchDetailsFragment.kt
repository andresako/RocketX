package com.andresako.rocketx.ui.launch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.andresako.rocketx.R

class LaunchDetailsFragment : Fragment() {

    companion object {
        fun newInstance() = LaunchDetailsFragment()
    }

    private lateinit var viewModel: LaunchDetailsViewModel

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
