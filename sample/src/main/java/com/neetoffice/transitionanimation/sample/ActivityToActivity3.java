package com.neetoffice.transitionanimation.sample;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.neetoffice.transitionanimation.NeetTransitionManager;
import com.neetoffice.transitionanimation.NeetTransitionSystem;

/**
 * Created by Deo on 2015/4/1.
 */
public class ActivityToActivity3 extends FragmentActivity {
    NeetTransitionManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity3);
        manager = NeetTransitionSystem.createInstance(this);
        manager.onAfterCreateView(savedInstanceState);
    }

    @Override
    public void onBackPressed() {
        manager.finish();
    }
}
