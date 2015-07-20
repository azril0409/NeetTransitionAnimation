package com.neetoffice.transitionanimation.sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.neetoffice.transitionanimation.NeetTransitionManager;
import com.neetoffice.transitionanimation.NeetTransitionSystem;


/**
 * Created by Deo on 2015/4/1.
 */
public class ActivityToActivity extends FragmentActivity {
    NeetTransitionManager manager;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity);
        manager = NeetTransitionSystem.createInstance(this);
        manager.onAfterCreateView(savedInstanceState);
    }

    public void onClick(View view) {
        manager.startActivity(new Intent(this,ActivityToActivity2.class),R.id.imageView1,R.id.textView1);
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	super.onActivityResult(requestCode, resultCode, data);
    	manager.onActivityResult(requestCode, resultCode, data); 
    }
}
