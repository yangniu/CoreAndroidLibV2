package com.custom.view.widgets;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
/**
 * 
 * ClassName: MyViewPager <br/>
 * Function: 实现不跟左右滑动冲突的viewpager <br/>
 * date: 2014-7-28 下午3:34:18 <br/>
 *
 * @author yangboqing
 * @version
 */
public class MySubViewPager extends ViewPager {

     /**
	 * Position of the last motion event.
	 */
	private float mLastMotionX, mLastMotionY;

	public MySubViewPager(Context context) {
		super(context);
	}

	public MySubViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	private boolean isFirstMoving;
    private boolean isLastMoving;
    private float dx;
    private float dy;
    
    @Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			mLastMotionX = ev.getX();
			mLastMotionY = ev.getY();
			getParent().requestDisallowInterceptTouchEvent(true);
			break;
		case MotionEvent.ACTION_MOVE:
			final float x = ev.getX();
			final float y = ev.getY();
			dx = x - mLastMotionX;
			dy = y - mLastMotionY;
			if (Math.abs(dy) > Math.abs(dx)) {
				getParent().requestDisallowInterceptTouchEvent(false);
				break;
			}
			if (isFistItem() && dx > 0&&isFirstMoving) {
				isFirstMoving = false;
				getParent().requestDisallowInterceptTouchEvent(false);
			}
			if (isLastItem() && dx < 0&&isLastMoving) {
				isLastMoving = false;
				getParent().requestDisallowInterceptTouchEvent(false);
			}
			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL:
			if (isFistItem() && dx > 0) {
				isFirstMoving = true;
			}else{
				isFirstMoving = false;
			}
			if (isLastItem() && dx < 0){
				isLastMoving = true;
			}else{
				isLastMoving = false;
			}
			getParent().requestDisallowInterceptTouchEvent(false);
			break;
		}
		return super.dispatchTouchEvent(ev);
	}

	private boolean isFistItem() {
		return getCurrentItem() == 0;
	}

	private boolean isLastItem() {
		return getCurrentItem() == getChildCount();
	}
}
