package com.houtrry.activityresulthelper

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.houtrry.activityresultlib.ActivityResultRequest
import com.houtrry.activityresultlib.OnActivityResultCallback

class MainActivity : AppCompatActivity(), OnActivityResultCallback {
    override fun onActivityResult(resultCode: Int, resultIntent: Intent, isOk: Boolean) {
        Log.d("", "resultCode: $resultCode, isOk: $isOk, resultIntent: $resultIntent")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        test()
    }

    private fun test() {
        val builder = ActivityResultRequest.Builder()
        builder
            .apply {
                context = this@MainActivity
                targetActivityCls = Main2Activity::class.java
                fragmentManager = this@MainActivity.supportFragmentManager
                onResultCallback = this@MainActivity
            }
            .withBoolean(KEY_NAME, true, false)
            .withInt("abc", 12)
            .withString("abc", "efg")
            .build()
            .start()
    }

    private fun test1() {
        val builder = ActivityResultRequest.Builder()
        builder.fromActivity(this)
            .toActivity(Main2Activity::class.java)
            .onActivityResultCallback(this)
            .withString("abc", "efg")
            .build()
            .start()
    }
}
