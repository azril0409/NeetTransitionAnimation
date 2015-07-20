package com.neetoffice.transitionanimation;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Deo on 2015/4/1.
 */
abstract class BaseManager implements NeetTransitionManager {
	final static String DATAS = "com.neetoffice.transitionanimation_bnjvgydvtge_";
    final static String FRAGMENTID = "com.neetoffice.transitionanimation_nhht5wedbde_";
    protected static ArrayList<Scene> scenes;
    private final Handler handler = new Handler();
    private long duration=1000;
    
    abstract Bundle getBundle();
    abstract Resources getResources();
    
    public void setDuration(long duration){
    	this.duration = duration;
    };

    void runAnimation(final @NonNull Context context, @NonNull final View contentView){
    	if(scenes==null||scenes.size()==0){return;}
    	for(Scene scene : scenes){
    		View v = contentView.findViewById(scene.transition.id);
    		if(v!=null){v.setVisibility(View.INVISIBLE);}
    	}
    	handler.post(new Runnable() {
            @Override
            public void run() {
                if(contentView.getMeasuredHeight()>0){
                	for(Scene scene : scenes){
                    	scene.setListener(new Listener(context.getApplicationContext(),scene,contentView));
                        scene.runAnimation(context,contentView,duration);
                    }
                }else{
                    handler.post(this);
                }
            }
        });
    }
    
    
    @Override
    public Drawable getImageFromId(int viewId) {
    	Drawable drawable = null;
		@SuppressWarnings("unchecked")
		ArrayList<Data> datas = (ArrayList<Data>) getBundle().getSerializable(DATAS);
        if(datas!=null){
        	for(Data data:datas){
        		if(data.id == viewId){
        			if(data.image!=null){
        				drawable = new BitmapDrawable(getResources(),BitmapFactory.decodeByteArray(data.image, 0, data.image.length));
            		}
            		break;
            	}
            }    
        }	
    	return drawable;
    }

	@Override
	public String getTextFromId(int viewId) {
		String text = "";
		@SuppressWarnings("unchecked")
		ArrayList<Data> datas = (ArrayList<Data>) getBundle().getSerializable(DATAS);
		if(datas!=null){
        	for(Data data:datas){
            	if(data.id == viewId){
            		text = data.text;
            		break;
            	}
            }    
        }	
		return text;
	}
    
    static class Listener implements AnimatorListener{
    	Scene scene;
    	Context context;
    	View contentView;
    	Listener(Context context,Scene scene,View contentView){
    		this.context = context;
    		this.scene = scene;
    		this.contentView = contentView;            
    	}    	
		@Override
		public void onAnimationStart(Animator animation) {
		}
		@Override
		public void onAnimationEnd(Animator animation) {
			scenes.remove(scene);
			scene.reomveWindow(context);
			View v = contentView.findViewById(scene.transition.id);
			if(v!=null){v.setVisibility(View.VISIBLE);}
		}
		@Override
		public void onAnimationCancel(Animator animation) {			
		}
		@Override
		public void onAnimationRepeat(Animator animation) {			
		}
    };
    
    static void addWindowView(@NonNull Context context,@NonNull View contentView,@NonNull ArrayList<Transition> transitions){    	
        scenes = new ArrayList<Scene>();
        for(Transition transition:transitions){
            Scene scene = new Scene(transition);
            scene.createView(context);
            scene.addWindow(context);
            scenes.add(scene);
        }
    }

    static Rect getWindowRect(@NonNull Activity activity,@NonNull View chileView) {
        int defaultHeight = 0;
        int defaultWidth = 0;
        ArrayList<View> views = new ArrayList<View>();
        View decorView = activity.getWindow().getDecorView();
        View contentView = decorView.findViewById(android.R.id.content);
        ViewParent parent = chileView.getParent();
    	while(parent instanceof View){
    		View v  = (View) parent;
    		if(v.getId()==android.R.id.content){break;}
			views.add(v);
    		parent = v.getParent();
    	}
        int resId = activity.getResources().getIdentifier("action_bar_container", "id", "android");
        View actionBarContainer = decorView.findViewById(resId);
		int barTop = 0;
		if(actionBarContainer!=null){
			barTop = actionBarContainer.getTop();
		}
		int contentTop = 0;
		if(contentView!=null){
			contentTop = contentView.getTop();
		}
		defaultHeight = contentTop-barTop;
    	for(View view : views){
    		defaultHeight+=view.getTop();
    		defaultWidth+=view.getLeft();
    	}    	
        int left = chileView.getLeft()+defaultWidth;
        int right = chileView.getRight()+defaultWidth;
        int top = chileView.getTop()+defaultHeight;
        int bottom = chileView.getBottom()+defaultHeight;
        return new Rect(left, top, right, bottom);
    }

    static Transition createTransition(@NonNull Activity activity,@NonNull View chileView) {
        Transition transition = new Transition();
        Rect rect = getWindowRect(activity,chileView);
        transition.id = chileView.getId();
        transition.viewClass = chileView.getClass();
        transition.l = rect.left;
        transition.t = rect.top;
        transition.r = rect.right;
        transition.b = rect.bottom;
        transition.pl = chileView.getPaddingLeft();
        transition.pt = chileView.getPaddingTop();
        transition.pr = chileView.getPaddingRight();
        transition.pb = chileView.getPaddingBottom();
        if (chileView instanceof ImageView) {
        	Bitmap bitmap = ((BitmapDrawable)((ImageView)chileView).getDrawable()).getBitmap();
            transition.image = bitmap2ByteArrayBitmap(bitmap);
            transition.scaleType = ((ImageView)chileView).getScaleType();
            return transition;
        } else if (chileView instanceof TextView) {
            transition.text = ((TextView) chileView).getText().toString();
            transition.textSize = ((TextView) chileView).getTextSize();
            transition.textColor = ((TextView) chileView).getCurrentTextColor();
            transition.textGravity = ((TextView) chileView).getGravity();
            return transition;
        }
        return null;
    }

	static ArrayList<Transition> beforeFinish(@NonNull Activity activity,@NonNull Bundle bundle,@NonNull View contentView) {
        ArrayList<Transition> ts = new ArrayList<Transition>();
        @SuppressWarnings("unchecked")
        ArrayList<Data> datas = (ArrayList<Data>) bundle.getSerializable(DATAS);
        if(datas!=null){
        	for (Data data : datas) {
        		final View v = contentView.findViewById(data.id);
        		if(v==null){continue;}
        		Transition t = createTransition(activity, v);
        		if(t !=null){
        			ts.add(t);
        		}
        	}            
        }
        return ts;
    }

    static byte[] bitmap2ByteArrayBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }
}
