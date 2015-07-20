package com.neetoffice.transitionanimation.sample;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.neetoffice.transitionanimation.NeetTransitionManager;
import com.neetoffice.transitionanimation.NeetTransitionSystem;

/**
 * Created by Deo on 2015/3/30.
 */
public class FragmentToFragmentFragment2 extends Fragment {
	NeetTransitionManager manager;
	TextView textView;
	ImageView imageView1;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		manager = NeetTransitionSystem.createInstance(this);
	}

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_fragment_fragment2, null);
		textView = (TextView) view.findViewById(R.id.textView1);
		imageView1 = (ImageView) view.findViewById(R.id.imageView1);
		return view;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		textView.setText(String.format("Hello I am %s",manager.getTextFromId(R.id.textView1)));
		imageView1.setImageDrawable(manager.getImageFromId(R.id.imageView1));
		manager.onAfterCreateView(savedInstanceState);
	}

	@Override
	public void onDestroyView() {
		manager.finish();
	}
}
