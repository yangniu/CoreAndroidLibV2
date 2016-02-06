package com.umeng.share;

import com.umeng.share.R;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity);
		findViewById(R.id.button1).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ShareService shareService = new ShareService(MainActivity.this);

				ShareInfoVO shareInfo = new ShareInfoVO();
				shareInfo.setTitle("测试");
				shareInfo.setTargetUrl("http://www.baidu.com");
				shareInfo.setContent("测试测试测试测试");
				shareInfo
						.setImageUrl("http://www.yypt.com/customer/flash/images/ad13.gif");

				shareService.showSharePlantDialog(shareInfo);
			}

		});

	}
}
