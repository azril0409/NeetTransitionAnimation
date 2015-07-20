package com.neetoffice.transitionanimation;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

/**
 * Created by Deo on 2015/3/30.
 */
public class NeetTransitionSystem {

    public static NeetTransitionManager createInstance(Fragment fragment) {
        return new NeetTransitionFragmentManager(fragment);
    }

    public static NeetTransitionManager createInstance(FragmentActivity activity) {
        return new NeetTransitionActivityV4Manager(activity);
    }
    
    public static NeetTransitionManager createInstance(Activity activity) {
        return new NeetTransitionActivityManager(activity);
    }
}

