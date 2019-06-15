package com.houtrry.activityresultlib

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.SparseArray

class DispatcherV4Fragment : Fragment() {

    private val mCallbacks = SparseArray<OnActivityResultCallback>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        var callback: OnActivityResultCallback? = mCallbacks.get(requestCode)
        mCallbacks.remove(requestCode)
        if (callback?.hashCode() == requestCode) {
            callback.onActivityResult(resultCode, data, resultCode == Activity.RESULT_OK)
        }
    }

    fun startActivityForResult(intent: Intent, callback: OnActivityResultCallback): Unit {
        mCallbacks.put(callback.hashCode(), callback)
        this.startActivityForResult(intent, callback.hashCode())
    }
}