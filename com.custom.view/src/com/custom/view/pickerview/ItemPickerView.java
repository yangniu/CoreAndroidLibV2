package com.custom.view.pickerview;

import com.custom.view.pickerview.adapter.WheelAdapter;
import com.custom.view.pickerview.lib.WheelView;
import com.custom.view.pickerview.listener.OnItemSelectedListener;

import com.custom.view.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * 设置项选择器
 * 
 * @author zhangtao
 * 
 */
public class ItemPickerView extends BasePickerView {

	private View btnSubmit, btnCancel;
	private WheelView mWheelView;

	public ItemPickerView(Context context) {
		super(context);
		LayoutInflater.from(context).inflate(R.layout.pickerview_wheel,
				contentContainer);
		btnSubmit = findViewById(R.id.btnSubmit);
		btnCancel = findViewById(R.id.btnCancel);
		mWheelView = (WheelView) findViewById(R.id.wheel_view);
	}

	public void setCurrentItem(int currentItem) {
		mWheelView.setCurrentItem(currentItem);
	}

	public void setCyclic(boolean cyclic) {
		mWheelView.setCyclic(cyclic);
	}

	public void setAdapter(WheelAdapter adapter) {
		mWheelView.setAdapter(adapter);
	}

	public void setOnItemSelectedListener(
			OnItemSelectedListener OnItemSelectedListener) {
		mWheelView.setOnItemSelectedListener(OnItemSelectedListener);
	}
	
	public void setOnSubmitClickListener(OnClickListener listener) {
		btnSubmit.setOnClickListener(listener);
	}
	
	public void setOnCancelClickListener(OnClickListener listener) {
		btnCancel.setOnClickListener(listener);
	}

}
