package com.custom.view.widgets;

import com.custom.core.util.android.DensityUtil;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Adapter;
import android.widget.LinearLayout;

/**
 * 模拟ListView的LinearLayout
 * 
 * @author zhangtao
 * 
 */
public class LinearLayoutForListView extends LinearLayout {

	private Adapter mAdapter;
	private Drawable mDivider;
	private int mDividerId;
	private OnItemClickListener mOnItemClickListener;

	private int mDividerHeight;
	private boolean mTopAndBottomDividerShow;

	public LinearLayoutForListView(Context context) {
		this(context, null);
	}

	public LinearLayoutForListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setOrientation(LinearLayout.VERTICAL);
		mDividerHeight = DensityUtil.dip2px(getContext(), 1);
	}

	public void setAdapter(Adapter adapter) {
		mAdapter = adapter;
		bindView();
	}

	private void bindView() {
		if (mAdapter == null) {
			return;
		}

		removeAllViews();
		int count = mAdapter.getCount();
		for (int i = 0; i < count; i++) {
			final int position = i;
			final View view = mAdapter.getView(i, null, this);
			view.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					if (mOnItemClickListener != null) {
						mOnItemClickListener.onItemClick(
								LinearLayoutForListView.this, view, position,
								mAdapter.getItemId(position));
					}
				}
			});
			if (i == 0 && mTopAndBottomDividerShow) {
				addDivider();
			}
			addView(view);
			if (i != count - 1) {
				addDivider();
			}
		}
	}

	private void addDivider() {
		View divider = new View(getContext());
		divider.setBackgroundResource(mDividerId);
		LinearLayout.LayoutParams params = new LayoutParams(
				LayoutParams.MATCH_PARENT, mDividerHeight);
		addView(divider, params);
	}

	public void setDivider(int id) {
		mDividerId = id;
	}

	public void setDivider(Drawable divider) {
		mDivider = divider;
	}

	public void setDividerHeight(float height) {
		mDividerHeight = DensityUtil.dip2px(getContext(), height);
	}

	public void setTopAndBottomDivider(boolean shown) {
		mTopAndBottomDividerShow = shown;
	}

	public void setOnItemClickListener(OnItemClickListener listener) {
		mOnItemClickListener = listener;
	}

	public interface OnItemClickListener {
		void onItemClick(LinearLayoutForListView parent, View view,
				int position, long id);
	}

}
