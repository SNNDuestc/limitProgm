package com.uestc.snnd.scroll;

import com.uestc.snnd.listenner.OnViewChangeListener;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

public class ScrollLayout extends ViewGroup {

	private int mCurScreen;    						
	private int mDefaultScreen = 0; 
	
	private float mLastMotionX;
	
	private Scroller  mScroller;						// sliding controller
	
	private VelocityTracker mVelocityTracker;  			// judging gesture
    private static final int SNAP_VELOCITY = 600; 
    
	private static final String TAG = "ScrollLayout"; 
	private OnViewChangeListener mOnViewChangeListener; 
	
	public ScrollLayout(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init(context);		
	}

	public ScrollLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init(context);
	}

	public ScrollLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		init(context);
	}

	private void init(Context context) {
		mCurScreen 	= mDefaultScreen;
		mScroller 	= new Scroller(context);
	}
	
	@Override
	protected void onLayout(boolean changed, int l, int u, int r, int b) {
		// TODO Auto-generated method stub
		
		if(changed) {
			int childLeft = 0;
			final int childCount = getChildCount();
			
			for(int i=0; i <childCount; i++) {
				final View childView = getChildAt(i);
				
				if(childView.getVisibility() != View.GONE) {
					final int childWidth = childView.getMeasuredWidth();
					childView.layout(childLeft, 0, childLeft+childWidth, 
									 childView.getMeasuredHeight());
					childLeft += childWidth;
				}	
			}	
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		final int action = event.getAction();
		final float x = event.getX();
		final float y = event.getY();
		
		switch(action) {
		case MotionEvent.ACTION_DOWN:
			
			Log.i("ACTION_DOWN", "onTouchEvent ACTION_DOWN");
			
			if(mVelocityTracker == null) {
				mVelocityTracker = VelocityTracker.obtain();
				mVelocityTracker.addMovement(event);
			}
			
			if(!mScroller.isFinished()) {
				mScroller.abortAnimation();
			}
			
			mLastMotionX = x;
			break;
			
		case MotionEvent.ACTION_MOVE:  
	       
			int deltaX = (int)(mLastMotionX - x);
	           
     	   	if (IsCanMove(deltaX))
     	   	{
     	   		if (mVelocityTracker != null)
     	   		{
		            	mVelocityTracker.addMovement(event); 
     	   		}   

	            mLastMotionX = x;    

	            scrollBy(deltaX, 0);	
     	   	}
      
     	   	break;
     	 
		case MotionEvent.ACTION_UP:
			
			int velocityX = 0;
			if(mVelocityTracker != null) {
				mVelocityTracker.addMovement(event);
				mVelocityTracker.computeCurrentVelocity(1000);
				velocityX = (int)mVelocityTracker.getXVelocity();
			}
			
			if(velocityX > SNAP_VELOCITY && mCurScreen > 0) {
				
			}
		}
				
		return super.onTouchEvent(event);
	}
	
	private boolean IsCanMove(int deltaX) {
		if(getScrollX() <= 0 && deltaX < 0)
		{
			return false;
		}
		if(getScrollX() >= (getChildCount() - 1) * getWidth() && deltaX > 0)
		{
			return false;
		}
		
		return true;
	}
	
	public void SetOnViewChangeListener(OnViewChangeListener listener) {
		mOnViewChangeListener = listener;
	}

}
