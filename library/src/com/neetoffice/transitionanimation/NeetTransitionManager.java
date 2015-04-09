package com.neetoffice.transitionanimation;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by Deo-chainmeans on 2015/3/30.
 */
public interface NeetTransitionManager {
    public final static int REQUEST_CODE = 0x1547;

    void onAfterCreateView(Bundle savedInstanceState);

    void onActivityResult(int requestCode, int resultCode, Intent data);

    void startActivity(Intent intent, int... rids);

    void addFragment(int rid,Fragment fragment,int... rids);

    void beforeFinish();
    
    void setDuration(long duration);
    
    Drawable getImageFromId(int viewId);
    
    String getTextFromId(int viewId);
}
