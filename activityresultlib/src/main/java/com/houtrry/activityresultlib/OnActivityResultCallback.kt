package com.houtrry.activityresultlib

import android.content.Intent

/**
 * @author: houtrry
 * @date: 2018/9/13 13:48
 * @version: $Rev$
 * @description: ${TODO}
 */
interface OnActivityResultCallback {
    /**
     * 处理onActivityForResult的结果
     *
     * @param resultCode 结果code
     * @param resultIntent
     * @param isOk code is REQUEST_OK
     */
    fun onActivityResult(resultCode: Int, resultIntent: Intent, isOk: Boolean)
}