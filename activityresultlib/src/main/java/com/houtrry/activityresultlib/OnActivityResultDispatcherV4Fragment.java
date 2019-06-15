package com.houtrry.activityresultlib;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.SparseArray;

/**
 * @author: houtrry
 * @date: 2018/9/13 13:46
 * @version: $Rev$
 * @description: ${TODO}
 */

public class OnActivityResultDispatcherV4Fragment extends android.support.v4.app.Fragment {

    private SparseArray<OnActivityResultCallback> mCallbacks = new SparseArray<>();

    public static OnActivityResultDispatcherV4Fragment newInstance() {
        return new OnActivityResultDispatcherV4Fragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        OnActivityResultCallback callback = mCallbacks.get(requestCode);
        mCallbacks.remove(requestCode);
        if (callback != null && requestCode == callback.hashCode()) {
            callback.onActivityResult(resultCode, data, resultCode == Activity.RESULT_OK);
        }
    }

    public void startActivityForResult(Intent intent, OnActivityResultCallback callback) {
        mCallbacks.put(callback.hashCode(), callback);
        this.startActivityForResult(intent, callback.hashCode());
    }
}
