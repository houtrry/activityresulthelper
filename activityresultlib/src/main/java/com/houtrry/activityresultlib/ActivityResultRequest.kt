package com.houtrry.activityresultlib

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import java.io.Serializable
import java.util.*

class ActivityResultRequest {

    private val FRAGMENT_TAG = "on_activity_result_dispatcher_fragment"

    private lateinit var mBuilder: Builder

    private constructor() {}

    private constructor(builder: Builder) {
        mBuilder = builder
    }

    fun start() {
        var fragment: DispatcherV4Fragment? =
            mBuilder.fragmentManager?.findFragmentByTag(FRAGMENT_TAG) as DispatcherV4Fragment
        if (fragment == null) {
            fragment = DispatcherV4Fragment()
            mBuilder.fragmentManager?.beginTransaction()?.add(fragment, FRAGMENT_TAG)?.commitAllowingStateLoss()
            mBuilder.fragmentManager?.executePendingTransactions()
        }
        fragment.startActivityForResult(mBuilder.getIntent(), mBuilder.onResultCallback)
    }

    class Builder {
        lateinit var context: Context
        lateinit var targetActivityCls: Class<out Activity>
        lateinit var onResultCallback: OnActivityResultCallback

        var fragmentManager: FragmentManager? = null

        private val bundle: Bundle = Bundle()

        fun context(context: Context): Builder {
            this.context = context
            return this
        }

        fun fromActivity(activity: FragmentActivity): Builder {
            this.context = activity
            this.fragmentManager = activity.supportFragmentManager
            return this
        }

        fun toActivity(cls: Class<out Activity>): Builder {
            this.targetActivityCls = cls
            return this
        }

        fun onActivityResultCallback(onResultCallback: OnActivityResultCallback): Builder {
            this.onResultCallback = onResultCallback
            return this
        }

        fun withInt(key: String, value: Int, needAdd: Boolean = true): Builder {
            if (needAdd) {
                bundle.putInt(key, value)
            }
            return this
        }

        fun withString(key: String, value: String?, needAdd: Boolean = true): Builder {
            if (needAdd) {
                bundle.putString(key, value)
            }
            return this
        }

        fun withBoolean(key: String, value: Boolean, needAdd: Boolean = true): Builder {
            if (needAdd) {
                bundle.putBoolean(key, value)
            }
            return this
        }

        fun withLong(key: String, value: Long, needAdd: Boolean = true): Builder {
            if (needAdd) {
                bundle.putLong(key, value)
            }
            return this
        }

        fun withParcelable(key: String, value: Parcelable?, needAdd: Boolean = true): Builder {
            if (needAdd) {
                bundle.putParcelable(key, value)
            }
            return this
        }

        fun withSerializable(key: String, value: Serializable?, needAdd: Boolean = true): Builder {
            if (needAdd) {
                bundle.putSerializable(key, value)
            }
            return this
        }

        fun withDouble(key: String, value: Double, needAdd: Boolean = true): Builder {
            if (needAdd) {
                bundle.putDouble(key, value)
            }
            return this
        }

        fun withParcelableList(key: String, value: ArrayList<out Parcelable>, needAdd: Boolean = true): Builder {
            if (needAdd) {
                bundle.putParcelableArrayList(key, value)
            }
            return this
        }

        fun getIntent(): Intent {
            val intent = Intent(context, targetActivityCls)
            intent.putExtras(bundle)
            return intent
        }

        fun build(): ActivityResultRequest {
            return ActivityResultRequest(this)
        }
    }
}
