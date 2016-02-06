package com.custom.view.dialog;

/**
 * dialogActivity.java
 * author      date          time      
 * ─────────────────────────────────────────────
 * niufei     2012-6-5       下午12:33:02
 * Copyright (c) 2012, TNT All Rights Reserved.
 */

import com.custom.view.R;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public abstract class AbsBaseDialog extends Dialog {

	public static final int DIALOG_BUTTON_STYLE_NONE = 0;

	public static final int DIALOG_BUTTON_STYLE_ONE = 1;

	public static final int DIALOG_BUTTON_STYLE_TWO = 2;

	public static final int DIALOG_BUTTON_STYLE_THREE = 3;

	private int currentBtnStyle = DIALOG_BUTTON_STYLE_TWO;

	protected Button btn_up, btn_left, btn_right;
	protected TextView tv_title;
	protected ImageView iv_title;

	private OnDialogBtnClickListener upBtnListener;
	private OnDialogBtnClickListener leftBtnListener;
	private OnDialogBtnClickListener rightBtnListener;
	protected LinearLayout titleLayout, contentLayout;
	protected LinearLayout btnLayout, bottomBtnLayout;

	protected AbsBaseDialog(Context context) {
		super(context, R.style.expand_base_dialog_style);
	}

	protected AbsBaseDialog(Context context, int currentBtnStyle) {
		this(context);
		this.currentBtnStyle = currentBtnStyle;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.view_base_dialog);

		tv_title = (TextView) findViewById(R.id.expand_dialog_title_view);
		iv_title = (ImageView) findViewById(R.id.expand_dialog_title_image);
		titleLayout = (LinearLayout) findViewById(R.id.expand_dialog_title_layout);
		contentLayout = (LinearLayout) findViewById(R.id.expand_dialog_content_layout);
		setContentLayoutView(contentLayout);
		btnLayout = (LinearLayout) findViewById(R.id.expand_dialog_btn_layout);
		btn_up = (Button) findViewById(R.id.expand_dialog_btn_up);
		bottomBtnLayout = (LinearLayout) findViewById(R.id.expand_dialog_btn_bottom_layout);
		btn_left = (Button) findViewById(R.id.expand_dialog_btn_left);
		btn_right = (Button) findViewById(R.id.expand_dialog_btn_right);
		setBtnName(getContext().getResources().getString(R.string.expand_base_dialog_left_btn_txt)
				, "取消", "确定");
		setButtonVisibilityStyle();
		btn_up.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (upBtnListener != null) {
					upBtnListener.onButtonClick(v);
				}
				dismiss();
			}
		});
		btn_left.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (leftBtnListener != null) {
					leftBtnListener.onButtonClick(v);
				}
				dismiss();
			}
		});
		btn_right.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (rightBtnListener != null) {
					rightBtnListener.onButtonClick(v);
				}
				dismiss();
			}
		});

	}

	
	private String getStringFromId(int resId){
		
		return getContext().getResources().getString(resId);
	}
	@Override
	public void show() {
		// 点击对话框外部取消对话框显示
		setCanceledOnTouchOutside(true);
		super.show();
	}

	/***
	 * Override show() default cancelTouchOutside is true
	 * 
	 * @param cancelTouchOutside
	 * @param niufei
	 * @param 2014-3-11 下午12:04:54
	 * @return void
	 * @throws
	 */
	public void show(boolean cancelTouchOutside) {
		// 点击对话框外部取消对话框显示
		setCanceledOnTouchOutside(cancelTouchOutside);
		super.show();
	}

	/**
	 * 设置显示内容布局
	 */
	protected abstract void setContentLayoutView(LinearLayout contentLayout);

	/**
	 * 设置标题样式
	 * 
	 * @param imgResId
	 * @param title
	 */
	public void setTitle(int imgResId, String title) {
		setTitleImg(imgResId);
		setTitleText(title);
	}

	/**
	 * 设置按钮显示状态
	 */
	protected void setButtonVisibilityStyle() {
		getButtonVisibilityStyle(currentBtnStyle);
	}

	public void getButtonVisibilityStyle(int btnStyle) {
		switch (btnStyle) {

		case DIALOG_BUTTON_STYLE_NONE:
			if (btnLayout != null) {
				btnLayout.setVisibility(View.GONE);
			}
			break;
		case DIALOG_BUTTON_STYLE_ONE:
			if (bottomBtnLayout != null) {
				bottomBtnLayout.setVisibility(View.GONE);
			}
			if (btn_up != null) {
				btn_up.setVisibility(View.VISIBLE);
			}
			break;
		case DIALOG_BUTTON_STYLE_TWO:
			if (btn_up != null) {
				btn_up.setVisibility(View.GONE);
			}
			if (bottomBtnLayout != null) {
				bottomBtnLayout.setVisibility(View.VISIBLE);
			}
			break;
		case DIALOG_BUTTON_STYLE_THREE:
			if (btn_up != null) {
				btn_up.setVisibility(View.VISIBLE);
			}
			if (bottomBtnLayout != null) {
				bottomBtnLayout.setVisibility(View.VISIBLE);
			}
			break;
		}
	}

	/**
	 * 设置标题栏图片
	 * 
	 * @param image
	 */
	public void setTitleImg(int image) {

		iv_title.setImageResource(image);
	}

	/**
	 * 设置标题栏标题
	 * 
	 * @param title
	 */
	public void setTitleText(String title) {

		tv_title.setText(title);
	}

	public void setUpBtnVisible(int visible) {
		btn_up.setVisibility(visible);
	}

	/**
	 * 设置按钮的名称
	 * 
	 * @param title
	 */
	public void setBtnName(String upBtnName, String leftBtnName, String rightBtnName) {
		if (btn_up != null) {
			btn_up.setText(upBtnName);
		}
		if (btn_left != null) {

			btn_left.setText(leftBtnName);
		}
		if (btn_right != null) {

			btn_right.setText(rightBtnName);
		}

	}

	public void setUpBtnClickListener(OnDialogBtnClickListener clickListener) {
		this.upBtnListener = clickListener;
	}

	public void setLeftBtnClickListener(OnDialogBtnClickListener clickListener) {
		this.leftBtnListener = clickListener;
	}

	public void setRightBtnClickListener(OnDialogBtnClickListener clickListener) {
		this.rightBtnListener = clickListener;
	}

	public interface OnDialogBtnClickListener {

		public void onButtonClick(View view);
	}

}
