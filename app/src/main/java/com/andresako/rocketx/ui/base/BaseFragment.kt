package com.andresako.rocketx.ui.base

import android.content.Context
import androidx.fragment.app.Fragment
import com.andresako.rocketx.ui.MainCallback

abstract class BaseFragment : Fragment() {

    lateinit var mainCallback: MainCallback

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        try {
            mainCallback = activity as MainCallback
        } catch (e: ClassCastException) {
            throw ClassCastException(this.javaClass.simpleName + " must implement MainCallback")
        }
    }

}