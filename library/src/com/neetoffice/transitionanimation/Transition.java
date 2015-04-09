package com.neetoffice.transitionanimation;

import android.view.View;
import android.widget.ImageView;

/**
 * Created by Deo on 2015/3/27.
 */
class Transition extends Data{	
	/**
	 * 
	 */
	private static final long serialVersionUID = 864053044414729914L;

    Class<? extends View> viewClass;
    float textSize;
    int textColor;
    int textGravity;
    int l;
    int t;
    int r;
    int b;
    int pl;
    int pt;
    int pr;
    int pb;
    ImageView.ScaleType scaleType;
}
