package com.neetoffice.transitionanimation.sample;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.neetoffice.transitionanimation.NeetTransitionManager;
import com.neetoffice.transitionanimation.NeetTransitionSystem;

/**
 * Created by Deo on 2015/4/1.
 */
public class FragmentToActivity2 extends FragmentActivity {
    NeetTransitionManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_activity2);
        TextView textView = (TextView) findViewById(R.id.textView1);
        ImageView imageView = (ImageView) findViewById(R.id.imageView1);
        manager = NeetTransitionSystem.createInstance(this);
        textView.setText(String.format("Hello I am %s", manager.getTextFromId(R.id.textView1)));
        imageView.setImageDrawable(manager.getImageFromId(R.id.imageView1));
        manager.onAfterCreateView(savedInstanceState);
    }

    @Override
    public void onBackPressed() {
        manager.finish();
    }
}
