package com.neetoffice.transitionanimation;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Deo on 2015/4/2.
 */
class Scene {
    Transition transition;
    private ViewGroup layout;
    private Animator.AnimatorListener listener;
    
    public Scene(Transition transition) {
        this.transition = transition;
    }
    
    void setListener(Animator.AnimatorListener listener) {
		this.listener = listener;
	}

	void createView(Context context) {
        layout = new RelativeLayout(context.getApplicationContext());
        try {
            Class<?>[] params = {Context.class};
            Constructor<? extends View> constructor = transition.viewClass.getConstructor(params);
            Object[] paramObjs = {context.getApplicationContext()};
            View view = (View) constructor.newInstance(paramObjs);
            view.setId(transition.id);
            view.setPadding(transition.pl, transition.pt, transition.pr, transition.pb);
            if (view instanceof TextView) {
                TextView textView = (TextView) view;
                textView.setText(transition.text);
                textView.setTextSize(TypedValue.COMPLEX_UNIT_PX,transition.textSize);
                textView.setTextColor(transition.textColor);
                textView.setGravity(transition.textGravity);
            } else if (view instanceof ImageView) {
                ImageView imageView = (ImageView) view;
                imageView.setImageBitmap(BitmapFactory.decodeByteArray(transition.image, 0, transition.image.length));
                imageView.setScaleType(transition.scaleType);
            }
            RelativeLayout.LayoutParams imageViewParams = new RelativeLayout.LayoutParams(transition.r-transition.l, transition.b-transition.t);
            imageViewParams.setMargins(transition.l, transition.t, 0, 0);
            layout.addView(view, imageViewParams);
        } catch (NoSuchMethodException e) {
        } catch (InvocationTargetException e) {
        } catch (InstantiationException e) {
        } catch (IllegalAccessException e) {
        } catch (Exception e) {
        }
    }

    public void add(Context context) {
        final WindowManager manager = (WindowManager) context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        manager.addView(layout, getWindowLayoutParams());
    }

    public void reomve(Context context) {
        try {
            WindowManager manager = (WindowManager) context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
            manager.removeView(layout);
        }catch (Exception e){        	
        }
    }

    @SuppressWarnings("deprecation")
	@SuppressLint("NewApi") 
    public void runAnimation(final @NonNull Context context, final @NonNull View contentView,long duration) {
        final WindowManager manager = (WindowManager) context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(metrics);
        View moveView = layout.findViewById(transition.id);
        if(moveView==null){
        	reomve(context);
        	return;
        }
        final View nowView = contentView.findViewById(transition.id);
        if(nowView==null){
        	reomve(context);
        	return;
        }
        if(nowView.getPaddingLeft()!=0||nowView.getPaddingTop()!=0||nowView.getPaddingRight()!=0||nowView.getPaddingBottom()!=0){
        	reomve(context);
        	return;
        }
        Rect newViewRect = BaseManager.getWindowRect((Activity)context,nowView);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            moveView.setBackground(nowView.getBackground());
        } else {
            moveView.setBackgroundDrawable(nowView.getBackground());
        }
        AnimatorSet set = new AnimatorSet();
        int formLeft = (int) (moveView.getLeft());
        int toLeft = newViewRect.left;
        int formRight = (int) (moveView.getRight());
        int toRight = newViewRect.right;
        int formTop = (int) (moveView.getTop());
        int toTop = newViewRect.top;
        int formBottom = (int) (moveView.getBottom());
        int toBottom = newViewRect.bottom;
        ObjectAnimator al = ObjectAnimator.ofInt(moveView, "left", formLeft, toLeft).setDuration(duration);
        ObjectAnimator ar = ObjectAnimator.ofInt(moveView, "right", formRight, toRight).setDuration(duration);
        ObjectAnimator at = ObjectAnimator.ofInt(moveView, "top", formTop, toTop).setDuration(duration);
        ObjectAnimator ab = ObjectAnimator.ofInt(moveView, "bottom", formBottom, toBottom).setDuration(duration);
        if (moveView instanceof ImageView) {
        	((ImageView)moveView).setScaleType(((ImageView)nowView).getScaleType());
            Bitmap bitmap = ((BitmapDrawable)((ImageView)nowView).getDrawable()).getBitmap();
            ObjectAnimator ai = ObjectAnimator.ofObject(moveView, "imageBitmap", new TypeEvaluator<Bitmap>() {
                @Override
                public Bitmap evaluate(float fraction, Bitmap startValue, Bitmap endValue) {
                    return fraction > 0.5 ? endValue : startValue;
                }
            }, bitmap, bitmap).setDuration(duration);
            set.play(al).with(ar).with(at).with(ab).with(ai);
        } else if (moveView instanceof TextView) {
            TextView textView = (TextView) moveView;
            ObjectAnimator aTextColor = ObjectAnimator.ofObject(textView, "textColor", new TypeEvaluator<Integer>() {
                @Override
                public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
                    try {
                        int sa = Color.alpha(startValue);
                        int sr = Color.red(startValue);
                        int sg = Color.green(startValue);
                        int sb = Color.blue(startValue);
                        int ea = Color.alpha(endValue);
                        int er = Color.red(endValue);
                        int eg = Color.green(endValue);
                        int eb = Color.blue(endValue);
                        int a = (int) (sa + (ea - sa) * fraction);
                        int r = (int) (sr + (er - sr) * fraction);
                        int g = (int) (sg + (eg - sg) * fraction);
                        int b = (int) (sb + (eb - sb) * fraction);
                        int color = Color.argb(a, r, g, b);
                        return color;
                    } catch (Exception e) {
                    }
                    return fraction < 0.5 ? startValue : endValue;
                }
            }, transition.textColor, ((TextView) nowView).getCurrentTextColor()).setDuration(duration);
            ObjectAnimator moveAlpha = ObjectAnimator.ofFloat(moveView, "alpha", 1f,0f).setDuration(300);
            ObjectAnimator nowAlpha = ObjectAnimator.ofFloat(nowView, "alpha", 0f,1f).setDuration(300);
            set.play(al).with(ar).with(at).with(ab).with(aTextColor).before(moveAlpha).before(nowAlpha);
            nowView.setAlpha(0f);
            nowView.setVisibility(View.VISIBLE);
        }
        set.addListener(listener);
        set.start();
    }

    @SuppressLint("RtlHardcoded") 
    static ViewGroup.LayoutParams getWindowLayoutParams() {
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.type = WindowManager.LayoutParams.TYPE_PHONE;
        params.format = PixelFormat.RGBA_8888;
        params.flags = WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD|WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED|WindowManager.LayoutParams.FLAG_FULLSCREEN;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        params.gravity = Gravity.LEFT | Gravity.TOP;
        return params;
    }
}
