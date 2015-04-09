package com.neetoffice.transitionanimation;

import java.util.ArrayList;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

/**
 * Created by Deo-chainmeans on 2015/3/25.
 */
public class NeetTransitionActivityV4Manager extends BaseManager {
    private FragmentActivity activity;
    NeetTransitionActivityV4Manager(FragmentActivity activity) {
        this.activity = activity;
    }

    @Override
    public void startActivity(Intent intent, int... rids) {
        ArrayList<Transition> ts = new ArrayList<Transition>();
        ArrayList<Data> datas = new ArrayList<Data>();
        for (int rid : rids) {
            View v = activity.findViewById(rid);
            if(v==null){continue;}
            if(v.getPaddingLeft()!=0||v.getPaddingTop()!=0||v.getPaddingRight()!=0||v.getPaddingBottom()!=0){continue;}
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
        addWindowView(activity,activity.findViewById(android.R.id.content),ts);
        intent.putExtra(DATAS, datas);
        activity.overridePendingTransition(0, 0);
        activity.startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    public void addFragment(int rid,Fragment fragment,int... rids){
        ArrayList<Transition> ts = new ArrayList<Transition>();
        ArrayList<Data> datas = new ArrayList<Data>();
        for(int id : rids){
            View v = activity.findViewById(id);
            if(v==null){continue;}
            if(v.getPaddingLeft()!=0||v.getPaddingTop()!=0||v.getPaddingRight()!=0||v.getPaddingBottom()!=0){
            	continue;
            }
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
        addWindowView(activity,activity.findViewById(android.R.id.content), ts);
        FragmentManager manager = activity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putSerializable(DATAS,datas);
        fragment.setArguments(bundle);
        fragmentTransaction.add(rid, fragment);
        fragmentTransaction.setCustomAnimations(0,0);
        fragmentTransaction.addToBackStack(fragment.toString());
        fragmentTransaction.commit();
    }

    @Override
    public void onAfterCreateView(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            return;
        }
        View contentView = null;
    	int fragmentid = activity.getIntent().getIntExtra(FRAGMENTID, -1);
    	if(fragmentid>0){
    		Fragment fragment = activity.getSupportFragmentManager().findFragmentById(fragmentid);
    		contentView = fragment.getView();
    	}else{
    		contentView = activity.findViewById(android.R.id.content);
    	}
        runAnimation(activity,contentView);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    	if(requestCode==REQUEST_CODE){
            View contentView = null;
        	int fragmentid = activity.getIntent().getIntExtra(FRAGMENTID, -1);
        	if(fragmentid>0){
        		Fragment fragment = activity.getSupportFragmentManager().findFragmentById(fragmentid);
        		contentView = fragment.getView();
        	}else{
        		contentView = activity.findViewById(android.R.id.content);
        	}
    		runAnimation(activity,contentView);
    	}
    }

    @Override
    public void beforeFinish() {
        @SuppressWarnings("unchecked")
		ArrayList<Data> datas = (ArrayList<Data>) activity.getIntent().getSerializableExtra(DATAS);
        int fragmentid = activity.getIntent().getIntExtra(FRAGMENTID,-1);
        if(datas!=null){
            ArrayList<Transition> ts = new ArrayList<Transition>();
            for(Data data:datas){
            	View v = activity.findViewById(data.id);
            	if(v==null){continue;}
            	if(v.getPaddingLeft()!=0||v.getPaddingTop()!=0||v.getPaddingRight()!=0||v.getPaddingBottom()!=0){continue;}
                Transition t=createTransition(activity,v);
            	if(t!=null){
            		ts.add(t);
                }
            }
            addWindowView(activity,activity.findViewById(android.R.id.content),ts);
            Intent intent = new Intent();
            intent.putExtra(DATAS, datas);
            intent.putExtra(FRAGMENTID, fragmentid);
            activity.overridePendingTransition(0, 0);
            activity.setResult(FragmentActivity.RESULT_OK, intent);
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
