package com.cn.stepcounter;

import java.util.Stack;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.util.Log;

public class AppContext extends Application {

	private static final String TAG = "MyApplication";
	private static Stack<Activity> activityStack = null;
	public static AppContext instance = null;
	/** application context */
	private Context appContext = null;

	@Override
	public void onCreate() {
		Log.d(TAG, "[MyApplication] onCreate");
		super.onCreate();
		activityStack = new Stack<Activity>();
		instance = this;
		appContext = getApplicationContext();
		// 全局初始化
	}

	public static AppContext getInstance() {
		return instance;
	}

	public Context getAppContext() {
		return appContext;
	}
}
