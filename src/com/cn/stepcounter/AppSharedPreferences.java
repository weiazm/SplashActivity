package com.cn.stepcounter;

import android.content.Context;
import android.content.SharedPreferences;

public class AppSharedPreferences {

	private static SharedPreferences sharedPreferences;
	private static SharedPreferences.Editor editor;

	private AppSharedPreferences(Context context) {
		sharedPreferences = context.getSharedPreferences("userdata", Context.MODE_PRIVATE);
	}

	public static SharedPreferences getInstance() {
		if (sharedPreferences == null) {
			new AppSharedPreferences(AppContext.getInstance().getAppContext());
		}
		return sharedPreferences;
	}

	public static void editorPutInt(String arg0, int arg1) {
		editor = AppSharedPreferences.getInstance().edit();
		editor.putInt(arg0, arg1);
		editor.commit();
	}

	public static void editorPutString(String arg0, String arg1) {
		editor = AppSharedPreferences.getInstance().edit();
		editor.putString(arg0, arg1);
		editor.commit();
	}

	public static void editorPutBoolean(String arg0, boolean arg1) {
		editor = AppSharedPreferences.getInstance().edit();
		editor.putBoolean(arg0, arg1);
		editor.commit();
	}

	public static int getInt(String arg0, int arg1) {
		return AppSharedPreferences.getInstance().getInt(arg0, arg1);
	}

	public static String getString(String arg0, String arg1) {
		return AppSharedPreferences.getInstance().getString(arg0, arg1);
	}

	public static boolean getBoolean(String arg0, boolean arg1) {
		return AppSharedPreferences.getInstance().getBoolean(arg0, arg1);
	}
}
