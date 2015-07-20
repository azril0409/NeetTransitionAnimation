package com.neetoffice.transitionanimation.sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;

import com.neetoffice.transitionanimation.NeetTransitionManager;
import com.neetoffice.transitionanimation.NeetTransitionSystem;

/**
 * Created by Deo on 2015/4/1.
 */
public class ActivityToActivity2 extends FragmentActivity implements OnClickListener {
    NeetTransitionManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity2);
        findViewById(R.id.textView2).setOnClickListener(this);
        manager = NeetTransitionSystem.createInstance(this);
        manager.onAfterCreateView(savedInstanceState);
    }
    
    public void onClick(View view) {
        manager.startActivity(new Intent(this,ActivityToActivity3.class),R.id.imageView1,R.id.textView2);
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	super.onActivityResult(requestCode, resultCode, data);
    	manager.onActivityResult(requestCode, resultCode, data); 
    }

    @Override
    public void onBackPressed() {
        manager.finish();
    }
}
