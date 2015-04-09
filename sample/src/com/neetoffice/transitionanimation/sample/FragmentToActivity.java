package com.neetoffice.transitionanimation.sample;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

/**
 * Created by Deo on 2015/4/1.
 */
public class FragmentToActivity extends FragmentActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_activity);
	}
}
