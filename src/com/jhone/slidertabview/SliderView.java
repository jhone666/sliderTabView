package com.jhone.slidertabview;

import java.util.ArrayList;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
/**
 * 
 * @author jhone 
 * @time 2016-09-29
 *
 */
public class SliderView extends RelativeLayout {

	private Context context;
	public SliderView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		init(context);
	}

	public SliderView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		// TODO Auto-generated constructor stub
	}

	public SliderView(Context context) {
		this(context, null);
		// TODO Auto-generated constructor stub
	}

	private android.widget.LinearLayout.LayoutParams params_ll_slider;
	private LinearLayout ll_slider,ll_content;
	
	private View slider;//滑块
	private void init(Context context) {
		// TODO Auto-generated method stub
		this.context=context;
		setBackgroundResource(R.drawable.slider_bg);
		
		params_ll_slider=new android.widget.LinearLayout.LayoutParams(android.widget.LinearLayout.LayoutParams.MATCH_PARENT,android.widget.LinearLayout.LayoutParams.MATCH_PARENT);
		ll_slider=new LinearLayout(context);
		ll_slider.setLayoutParams(params_ll_slider);
		ll_slider.setOrientation(LinearLayout.HORIZONTAL);
		
		android.widget.LinearLayout.LayoutParams params_slider=new android.widget.LinearLayout.LayoutParams(0,android.view.ViewGroup.LayoutParams.MATCH_PARENT);
		params_slider.weight=1;
		slider=new View(context);
		slider.setLayoutParams(params_slider);
		slider.setBackgroundResource(R.drawable.slider_view);
		ll_slider.addView(slider);
		
		ll_content=new LinearLayout(context);
		ll_content.setLayoutParams(params_ll_slider);
		ll_content.setOrientation(LinearLayout.HORIZONTAL);
		
		addView(ll_slider);
		addView(ll_content);
	}
	
	private ArrayList<TextView> childs;
	public void setTitles(final ArrayList<String> titles){
		childs=new ArrayList<TextView>();
		ll_slider.setWeightSum(titles.size());
		TextView child=null;
		android.widget.LinearLayout.LayoutParams params=new android.widget.LinearLayout.LayoutParams(android.widget.LinearLayout.LayoutParams.MATCH_PARENT,android.widget.LinearLayout.LayoutParams.MATCH_PARENT,1.0f);
		ColorStateList colorStateList = getResources().getColorStateList(R.color.slider_textcolor);  
		for (int i = 0; i < titles.size(); i++) {
			child=new TextView(context);
			child.setLayoutParams(params);
			child.setGravity(Gravity.CENTER);
			
			child.setText(titles.get(i));
			child.setTextColor(colorStateList);
			child.setTextSize(16);
			child.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View view) {
					// TODO Auto-generated method stub
					doAnimal(view);
				}
			});
			childs.add(child);
			ll_content.addView(child);
		}
		requestLayout();
		mSelectedItem=childs.get(0);
		mSelectedItem.setEnabled(false);
	}
	
	private View mSelectedItem;
	
	protected void doAnimal(final View view) {
		// TODO Auto-generated method stub
		TranslateAnimation animation = new TranslateAnimation(mSelectedItem.getLeft(),
				view.getLeft(), 0, 0);
		animation.setInterpolator(new AccelerateDecelerateInterpolator());
		animation.setFillAfter(true);
		animation.setDuration(200);
		animation.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationRepeat(Animation arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animation arg0) {
				// TODO Auto-generated method stub
				//这里用这种方式而不用mSelectedItem和view来设置是有原因的，试下就知道，后者效果非常不友好(连续点的时候)
				for (int i = 0; i < childs.size(); i++) {
					childs.get(i).setEnabled(true);
				}
				mSelectedItem.setEnabled(false);
			}
		});
		
		
		slider.startAnimation(animation);
		
		mSelectedItem=view;
		if (sliderOnItemClick!=null) {
			sliderOnItemClick.onItemClick(((TextView)view).getText().toString());
		}
	}

	private SliderOnItemClick sliderOnItemClick;
	
	public interface SliderOnItemClick{
		void onItemClick(String title);
	}

	public void setOnItemClick(SliderOnItemClick sliderOnItemClick) {
		// TODO Auto-generated method stub
		this.sliderOnItemClick=sliderOnItemClick;
	}
	//设置当前选中的项
	public void setCurrentItem(int index){
		doAnimal(childs.get(index));
	}
	
	
/*************************************以上是想实现在屏幕上滑动使滑块移动的效果，实验证明，装逼失败*****************************************************/
	
//	@Override  
//	public boolean onInterceptTouchEvent(MotionEvent e) {  
//	    switch (e.getAction()) {  
//	    case MotionEvent.ACTION_DOWN:  
//	        break;  
//	    case MotionEvent.ACTION_MOVE:  
//	                       //必须要在MOVE中return才有效果，在这里return后UP事件也会被拦截  
//	        return true;  
//	    case MotionEvent.ACTION_UP:  
//	    case MotionEvent.ACTION_CANCEL:  
//	        break;  
//	    }  
//	    return super.onInterceptTouchEvent(e);  
//		return true;
//	}  
	
	private int downX;
	private int downY;
	//在屏幕上滑动使滑块移动效果
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			downX=(int) event.getX();
			downY=(int) event.getY();
			Log.e("jhone", "ACTION_DOWN");
			break;
		case MotionEvent.ACTION_MOVE:
			Log.e("jhone", "ACTION_MOVE");
			break;
		case MotionEvent.ACTION_UP:
			Log.e("jhone", "ACTION_UP");
			computerPosition();
			break;

		default:
			break;
		}
		
		return super.onTouchEvent(event);
		
	}
	//计算当前Down在哪个child上面，从这个child开始滑动
	private TextView computerPosition(){
		
		Rect rect=new Rect();
		for (int i = 0; i < childs.size(); i++) {
			childs.get(i).getLocalVisibleRect(rect);
			if (rect.contains(downX, downY)) {
				Log.e("jhone", "computerPosition");
				return childs.get(i);
			}
		}
		return null;
	}
	
	
}
