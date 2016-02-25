package com.custom.core;

import com.custom.core.exception.LogUtil;
import com.custom.core.volley.request.VolleyRequestManager;

import android.app.Application;
import android.util.Log;

/**
 * 用来控制所有application中的activity
 * @author niufei
 *
 */
public abstract class AbsApplication extends Application { 
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		LogUtil.init(getApplicationContext());
	}
	
	@Override  
    public void onTerminate(){  
        super.onTerminate();  
    }  
  
    public void onConfigurationChanged(){  
        Log.e("ConstantApplication","onConfigurationChanged");  
    }  
}
