package com.custom.view.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class UnScrollListView extends ListView {

	public UnScrollListView(Context context) {
		this(context,null,0);
	}

	public UnScrollListView(Context context, AttributeSet attrs) {
		this(context, attrs,0);
	}

	public UnScrollListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
}