package com.andresako.rocketx.ui.base

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        try {
//            activityCallback = activity as MainActivityCallback
        } catch (e: ClassCastException) {
            throw ClassCastException(this.javaClass.simpleName + " must implement MainActivityCallback")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        activityCallback.updateToolbarTitle(barTitle)
    }
}