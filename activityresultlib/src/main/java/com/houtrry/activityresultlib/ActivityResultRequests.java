package com.houtrry.activityresultlib;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author: houtrry
 * @date: 2018/9/13 13:53
 * @version: $Rev$
 * @description: 通过一个空的Fragment, 来发送startActivity和接收onActivityResult, 避免在每个Activity或者Fragment中单独处理onActivityResult.
 * 当然,动态获取权限也可以这么处理.
 * Android中,Glide, RxPermission, RxLifeCycle 等都是这么处理的.其中,Glide和RxLifeCycle用这种方式来感知生命周期, RxPermission用这个方法来处理回调
 */

public class ActivityResultRequests {

    public static final String FRAGMENT_TAG = "on_activity_result_dispatcher_fragment";

    private OnActivityResultDispatcherFragment mOnActivityResultDispatcherFragment;
    private Builder mBuilder;

    public ActivityResultRequests() {

    }

    private ActivityResultRequests(Builder builder) {
        this.mBuilder = builder;
    }

    public ActivityResultRequests init(@NonNull Activity activity) {
        mOnActivityResultDispatcherFragment = getOnActivityResultDispatcherFragment(activity.getFragmentManager());
        return this;
    }

    public ActivityResultRequests init(@NonNull Fragment fragment) {
        mOnActivityResultDispatcherFragment = getOnActivityResultDispatcherFragment(fragment.getFragmentManager());
        return this;
    }

    public ActivityResultRequests init(@NonNull FragmentManager fragmentManager) {
        mOnActivityResultDispatcherFragment = getOnActivityResultDispatcherFragment(fragmentManager);
        return this;
    }

    private OnActivityResultDispatcherFragment getOnActivityResultDispatcherFragment(@NonNull FragmentManager fragmentManager) {
        OnActivityResultDispatcherFragment fragment = (OnActivityResultDispatcherFragment) fragmentManager.findFragmentByTag(FRAGMENT_TAG);

        if (fragment == null) {
            fragment = OnActivityResultDispatcherFragment.newInstance();
            fragmentManager.beginTransaction()
                    .add(fragment, FRAGMENT_TAG)
                    .commitAllowingStateLoss();
            fragmentManager.executePendingTransactions();
        }
        return fragment;
    }

    public ActivityResultRequests startActivityForResult(Intent intent, OnActivityResultCallback callback) {
        mOnActivityResultDispatcherFragment.startActivityForResult(intent, callback);
        return this;
    }

    public void start() {
        getOnActivityResultDispatcherFragment(mBuilder.fragmentManager).startActivityForResult(mBuilder.getIntent(), mBuilder.onResultCallback);
    }

    public static class Builder {

        private Context context;
        private Class<? extends Activity> targetActivityCls;
        private OnActivityResultCallback onResultCallback;
        private FragmentManager fragmentManager;

        private Bundle bundle = new Bundle();

        public Builder context(@NonNull Context context) {
            this.context = context;
            return this;
        }

        public Builder fromActivity(@NonNull Activity activity) {
            this.fragmentManager = activity.getFragmentManager();
            this.context = activity;
            return this;
        }

        public Builder fromFragment(@NonNull Fragment fragment, @NonNull Context context) {
            this.fragmentManager = fragment.getFragmentManager();
            this.context = context;
            return this;
        }

        public Builder toActivity(@NonNull Class<? extends Activity> targetActivityCls) {
            this.targetActivityCls = targetActivityCls;
            return this;
        }

        public Builder onActivityResultCallback(OnActivityResultCallback onResultCallback) {
            this.onResultCallback = onResultCallback;
            return this;
        }

        public Builder withInt(@NonNull String key, int value) {
            return withInt(key, value, true);
        }

        public Builder withString(@NonNull String key, @Nullable String value) {
            return withString(key, value, true);
        }

        public Builder withBoolean(@NonNull String key, boolean value) {
            return withBoolean(key, value, true);
        }

        public Builder withLong(@NonNull String key, long value) {
            return withLong(key, value, true);
        }

        public Builder withParcelableList(@NonNull String key, @NonNull ArrayList<? extends Parcelable> value) {
            return withParcelableList(key, value, true);
        }

        public Builder withParcelable(@NonNull String key, @Nullable Parcelable value) {
            return withParcelable(key, value, true);
        }

        public Builder withSerializable(@NonNull String key, @Nullable Serializable value) {
            return withSerializable(key, value, true);
        }

        public Builder withFloat(@NonNull String key, float value) {
            return withFloat(key, value, true);
        }

        public Builder withDouble(@NonNull String key, double value) {
            return withDouble(key, value, true);
        }

        public Builder withInt(@NonNull String key, int value, boolean needAdd) {
            if (needAdd) {
                bundle.putInt(key, value);
            }
            return this;
        }

        public Builder withString(@NonNull String key, @Nullable String value, boolean needAdd) {
            if (needAdd) {
                bundle.putString(key, value);
            }
            return this;
        }

        public Builder withBoolean(@NonNull String key, boolean value, boolean needAdd) {
            if (needAdd) {
                bundle.putBoolean(key, value);
            }
            return this;
        }

        public Builder withLong(@NonNull String key, long value, boolean needAdd) {
            if (needAdd) {
                bundle.putLong(key, value);
            }
            return this;
        }

        public Builder withParcelable(@NonNull String key, @Nullable Parcelable value, boolean needAdd) {
            if (needAdd) {
                bundle.putParcelable(key, value);
            }
            return this;
        }

        public Builder withSerializable(@NonNull String key, @Nullable Serializable value, boolean needAdd) {
            if (needAdd) {
                bundle.putSerializable(key, value);
            }
            return this;
        }

        public Builder withFloat(@NonNull String key, float value, boolean needAdd) {
            if (needAdd) {
                bundle.putFloat(key, value);
            }
            return this;
        }

        public Builder withDouble(@NonNull String key, double value, boolean needAdd) {
            if (needAdd) {
                bundle.putDouble(key, value);
            }
            return this;
        }

        public Builder withParcelableList(@NonNull String key, @NonNull ArrayList<? extends Parcelable> value, boolean needAdd) {
            if (needAdd) {
                bundle.putParcelableArrayList(key, value);
            }
            return this;
        }

        private Intent getIntent() {
            final Intent intent = new Intent(context, targetActivityCls);
            intent.putExtras(bundle);
            return intent;
        }

        public ActivityResultRequests build() {
            checkArgs();
            return new ActivityResultRequests(this);
        }

        private void checkArgs() {
            if (fragmentManager == null) {
                throw new IllegalArgumentException("fragmentManager is null, you should call Builder#fromActivity or Builder#fromFragment first");
            }
            if (targetActivityCls == null) {
                throw new IllegalArgumentException("targetActivityCls is null, you should call Builder#toActivity first");
            }
            if (context == null) {
                throw new IllegalArgumentException("context is null");
            }
        }
    }
}
