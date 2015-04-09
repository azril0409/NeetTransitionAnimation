package com.neetoffice.transitionanimation;

import java.util.ArrayList;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

/**
 * Created by Deo on 2015/3/25.
 */
public class NeetTransitionFragmentManager extends BaseManager {
    private Fragment fragment;

    NeetTransitionFragmentManager(Fragment fragment) {
        this.fragment = fragment;
    }

    public void startActivity(@NonNull Intent intent, int... rids) {
        ArrayList<Transition> ts = new ArrayList<Transition>();
        ArrayList<Data> datas = new ArrayList<Data>();
        for (int rid : rids) {
            View v = fragment.getView().findViewById(rid);
            if(v==null){continue;}
            if(v.getPaddingLeft()!=0||v.getPaddingTop()!=0||v.getPaddingRight()!=0||v.getPaddingBottom()!=0){continue;}
            Transition t = createTransition(fragment.getActivity(),v);
            if(t !=null){
            	ts.add(t);
            	Data data = new Data();
            	data.id = t.id;
            	data.image = t.image;
            	data.text = t.text;
            	datas.add(data);
            }
        }
        addWindowView(fragment.getActivity(),fragment.getActivity().findViewById(android.R.id.content), ts);
        intent.putExtra(DATAS, datas);
        fragment.getActivity().overridePendingTransition(0, 0);
        fragment.startActivityForResult(intent, REQUEST_CODE);
       
    }

    @Override
    public void addFragment(int rid,@NonNull Fragment fragment, int... rids) {
        ArrayList<Transition> ts = new ArrayList<Transition>();
        ArrayList<Data> datas = new ArrayList<Data>();
        for(int id : rids){
            View v =  this.fragment.getView().findViewById(id);
            if(v==null){continue;}
            if(v.getPaddingLeft()!=0||v.getPaddingTop()!=0||v.getPaddingRight()!=0||v.getPaddingBottom()!=0){continue;}
            Transition t = createTransition(this.fragment.getActivity(),v);
            if(t !=null){
            	ts.add(t);
            	Data data = new Data();
            	data.id = t.id;
            	data.image = t.image;
            	data.text = t.text;
            	datas.add(data);
            }
            v.setVisibility(View.INVISIBLE);
        }
        addWindowView(this.fragment.getActivity(),this.fragment.getActivity().findViewById(android.R.id.content), ts);
        FragmentManager manager = this.fragment.getFragmentManager();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putSerializable(DATAS,datas);
        bundle.putInt(FRAGMENTID,this.fragment.getId());
        fragment.setArguments(bundle);
        fragmentTransaction.add(rid, fragment);
        fragmentTransaction.setCustomAnimations(0,0);
        fragmentTransaction.addToBackStack(fragment.toString());
        fragmentTransaction.commit();
    }

    @Override
    public void onAfterCreateView(Bundle savedInstanceState) {
        if (savedInstanceState != null) {return;}
        View contentView = fragment.getView();
        runAnimation(fragment.getActivity(),contentView);
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    	if(requestCode==REQUEST_CODE){
            View contentView = null;
            Bundle argument = fragment.getArguments();
            if(argument!=null){
            	int fragmentid = argument.getInt(FRAGMENTID, -1);
            	if(fragmentid>0){
            		Fragment f = fragment.getFragmentManager().findFragmentById(fragmentid);
            		contentView = f.getView();
            	}else{
            		contentView = fragment.getView();
            	}
            }else{
            	contentView = fragment.getView();
            }
            runAnimation(fragment.getActivity(), contentView);
    	}
    }

    public void beforeFinish() {
        Bundle argument = fragment.getArguments();
        if(argument!=null){
            @SuppressWarnings("unchecked")
			ArrayList<Data> datas = (ArrayList<Data>) argument.getSerializable(DATAS);
            int fragmentid = argument.getInt(FRAGMENTID,-1);
            if(datas!=null){
                ArrayList<Transition> ts = new ArrayList<Transition>();
                for(Data data:datas){
                	View v = fragment.getView().findViewById(data.id);
                	if(v==null){continue;}
                	Transition t = createTransition(fragment.getActivity(),v);
                	if(t!=null){
                		ts.add(t);
                    }
                }
                addWindowView(fragment.getActivity(),fragment.getActivity().findViewById(android.R.id.content),ts);
                Intent intent = new Intent();
                intent.putExtra(DATAS, datas);
                intent.putExtra(FRAGMENTID, fragmentid);
                fragment.getActivity().overridePendingTransition(0, 0);
                fragment.getActivity().setResult(FragmentActivity.RESULT_OK, intent);
                if(fragmentid>0){
                    Fragment fragment = this.fragment.getFragmentManager().findFragmentById(fragmentid);
                    runAnimation(fragment.getActivity(),fragment.getView());
                }else{
                    runAnimation(fragment.getActivity(),fragment.getView());
                }
            }
        }
    }

	@Override
	Bundle getBundle() {
		Bundle b = fragment.getArguments();
		if(b!=null){return b;}
		return new Bundle();
	}

	@Override
	Resources getResources() {
		return fragment.getResources();
	}
    

}
