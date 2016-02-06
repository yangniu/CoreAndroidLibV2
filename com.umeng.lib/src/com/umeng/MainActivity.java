package com.umeng;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.umeng.analytics.MobclickAgent;
import com.umeng.lib.R;
import com.umeng.update.UmengUpdateAgent;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.layout_main);

		MobclickAgent.setDebugMode(true);
		// SDK在统计Fragment时，需要关闭Activity自带的页面统计，
		// 然后在每个页面中重新集成页面统计的代码(包括调用了 onResume 和 onPause 的Activity)。
		MobclickAgent.openActivityDurationTrack(false);
		// MobclickAgent.setAutoLocation(true);
		// MobclickAgent.setSessionContinueMillis(1000);

		findViewById(R.id.button1).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				MobclickAgent.onEvent(MainActivity.this, "click");
				MobclickAgent.onEvent(MainActivity.this, "click", "button");
			}
		});

		findViewById(R.id.button2).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				UmengUpdateAgent.setUpdateAutoPopup(false);
				UmengUpdateAgent.setUpdateOnlyWifi(false);
				UmengUpdateAgent.update(MainActivity.this);
			}
		});
	}

	@Override
	public void onResume() {
		super.onResume();
		MobclickAgent.onPageStart(getClass().getName());
		MobclickAgent.onResume(this);
	}

	@Override
	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd(getClass().getName());
		MobclickAgent.onPause(this);
	}

}
