package com.neetoffice.transitionanimation.sample;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.neetoffice.transitionanimation.NeetTransitionManager;
import com.neetoffice.transitionanimation.NeetTransitionSystem;

/**
 * Created by Deo on 2015/3/30.
 */
public class FragmentToFragmentFragment extends Fragment implements
		OnClickListener {
	NeetTransitionManager manager;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		manager = NeetTransitionSystem.createInstance(this);
	}

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_fragment_fragment, null);
		view.setOnClickListener(this);
		return view;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		manager.onAfterCreateView(savedInstanceState);
	}

	@Override
	public void onClick(View v) {
		manager.addFragment(R.id.fragment2, new FragmentToFragmentFragment2(),R.id.imageView1, R.id.textView1);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		manager.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onDestroyView() {
		manager.finish();
	}
}
