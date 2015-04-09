package com.neetoffice.transitionanimation.sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.neetoffice.transitionanimation.NeetTransitionManager;
import com.neetoffice.transitionanimation.NeetTransitionSystem;

/**
 * Created by Deo on 2015/3/30.
 */
public class MainFragment extends Fragment implements OnClickListener{
    NeetTransitionManager manager;
    ImageView imageView;
    TextView textView;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        manager = NeetTransitionSystem.createInstance(this);
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater,@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    	View view =inflater.inflate(R.layout.fragment_main, container);
    	textView = (TextView) view.findViewById(R.id.textView1);
    	imageView = (ImageView) view.findViewById(R.id.imageView1);
    	imageView.setOnClickListener(this);
    	return view;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    	super.onActivityCreated(savedInstanceState);
    	int id = getId();
    	if(id==R.id.fragment1){
    		imageView.setImageResource(R.drawable.cupcake);
    		textView.setText("cupcake");
    	}else if(id==R.id.fragment2){
    		imageView.setImageResource(R.drawable.donut);
    		textView.setText("donut");
    	}else if(id==R.id.fragment3){
    		imageView.setImageResource(R.drawable.eclair);
    		textView.setText("eclair");
    	}else if(id==R.id.fragment4){
    		imageView.setImageResource(R.drawable.froyo);
    		textView.setText("froyo");
    	}else if(id==R.id.fragment5){
    		imageView.setImageResource(R.drawable.gingerbread);
    		textView.setText("gingerbread");
    	}else if(id==R.id.fragment6){
    		imageView.setImageResource(R.drawable.honeycomb);
    		textView.setText("honeycomb");
    	} 
        manager.onAfterCreateView(savedInstanceState);
    }

	@Override
	public void onClick(View v) {
        manager.startActivity(new Intent(getActivity(), FragmentToActivity2.class),R.id.imageView1,R.id.textView1);
    }
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		manager.onActivityResult(requestCode, resultCode, data);
	}
}
