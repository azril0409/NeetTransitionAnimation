package com.neetoffice.transitionanimation;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.View;

/**
 * Created by Deo on 2015/3/25.
 */
public class NeetTransitionActivityManager extends BaseManager {
    private Activity activity;
    
    NeetTransitionActivityManager(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void startActivity(@NonNull Intent intent,int... rids) {
        ArrayList<Transition> ts = new ArrayList<Transition>();
        ArrayList<Data> datas = new ArrayList<Data>();
        for (int rid : rids) {
            View v = activity.findViewById(rid);
            if(v==null){continue;}
        	Transition t = createTransition(activity,v);
            if(t !=null){
            	ts.add(t);
            	Data data = new Data();
            	data.id = t.id;
            	data.image = t.image;
            	data.text = t.text;
            	datas.add(data);
            }
        }
        addWindowView(activity, activity.findViewById(android.R.id.content), ts);
        intent.putExtra(DATAS, datas);
        activity.startActivityForResult(intent, REQUEST_CODE);
        activity.overridePendingTransition(0, 0);
    }

    @Override
    public void addFragment(int rid,Fragment fragment,int... rids){
    }

    @Override
    public void onAfterCreateView(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            return;
        }
        runAnimation(activity,activity.findViewById(android.R.id.content));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    	if(requestCode==REQUEST_CODE){
    		runAnimation(activity,activity.findViewById(android.R.id.content));
    	}
    }

    @Override
    public void finish() {
        @SuppressWarnings("unchecked")
		ArrayList<Data> datas = (ArrayList<Data>) activity.getIntent().getSerializableExtra(DATAS);
        if(datas!=null){
            ArrayList<Transition> ts = new ArrayList<Transition>();
            for(Data data:datas){
            	View v = activity.findViewById(data.id);
                if(v==null){continue;}
            	Transition t=createTransition(activity,v);
            	if(t!=null){
            		ts.add(t);
                	data.id = t.id;
                	data.image = t.image;
                	data.text = t.text;
                	datas.add(data);
            	}
            }
            addWindowView(activity, activity.findViewById(android.R.id.content), ts);
            Intent intent = new Intent();
            intent.putExtra(DATAS, datas);
            activity.setResult(Activity.RESULT_OK, intent);
            activity.finish();
            activity.overridePendingTransition(0, 0);
        }
    }

	@Override
	Bundle getBundle() {
		return activity.getIntent().getExtras();
	}

	@Override
	Resources getResources() {
		return activity.getResources();
	}
}
