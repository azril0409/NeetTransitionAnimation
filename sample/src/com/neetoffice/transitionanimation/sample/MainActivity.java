package com.neetoffice.transitionanimation.sample;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class MainActivity extends FragmentActivity implements OnItemClickListener {
    private static String[] from = new String[]{"title"};
    private static int[] to = new int[]{R.id.titleView};
    ListView listView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.activity_main);
    	listView = (ListView) findViewById(R.id.listView);
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
        HashMap<String,String> title = new HashMap<String,String>();
        title.put(from[0],"Activity => Activity");
        list.add(title);
        title = new HashMap<String,String>();
        title.put(from[0],"Fragment => Activity");
        list.add(title);
        title = new HashMap<String,String>();
        title.put(from[0],"Fragment => Fragment");
        list.add(title);
        SimpleAdapter adapter = new SimpleAdapter(this,list,R.layout.main_adapter,from,to);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }
    
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
        if(position == 0){
        	Intent intent = new Intent(this,ActivityToActivity.class);
        	startActivity(intent);
        }else if(position == 1){
        	Intent intent = new Intent(this,FragmentToActivity.class);
        	startActivity(intent);
        }else if(position ==2){
        	Intent intent = new Intent(this,FragmentToFragment.class);
        	startActivity(intent);
        }
	}
}
