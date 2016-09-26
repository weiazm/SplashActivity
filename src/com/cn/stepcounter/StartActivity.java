package com.cn.stepcounter;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

public class StartActivity extends Activity implements SensorListener {

	private TextView runTextView;
	private TextView strongTextView;
	private TextView settingTxt;
	private TextView phoneNumTxt;
	private TextView stumbleNumTxt;
	private SensorManager sm;
	private Sensor mGSensor;
	private Sensor gyroscopeSensor;
	public static final String TAG = "StartActivity";
	private boolean isR = false;
	private boolean isW = false;

	@TargetApi(Build.VERSION_CODES.CUPCAKE)
	@SuppressLint("InlinedApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.start);

		runTextView = (TextView) findViewById(R.id.run);
		strongTextView = (TextView) findViewById(R.id.strong);
		settingTxt = (TextView) findViewById(R.id.settingTxt);
		phoneNumTxt = (TextView) findViewById(R.id.phoneNumTxt);
		stumbleNumTxt = (TextView) findViewById(R.id.stumbleNumTxt);

		runTextView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(StartActivity.this, StepCounterActivity.class);
				Bundle bundle = new Bundle();
				bundle.putBoolean("run", false);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});

		strongTextView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(StartActivity.this, StepCounterActivity.class);
				Bundle bundle = new Bundle();
				bundle.putBoolean("run", true);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
		settingTxt.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(StartActivity.this, SetPhoneNumActivity.class);
				startActivity(intent);
			}
		});
		if (AppSharedPreferences.getBoolean("isSetting", false)) {
			phoneNumTxt.setVisibility(View.VISIBLE);
			phoneNumTxt.setText(AppSharedPreferences.getString("phoneNumSTr", ""));
		} else {
			phoneNumTxt.setVisibility(View.GONE);
		}

		sm = (SensorManager) getSystemService(SENSOR_SERVICE);
		// 获取Sensor对象
		gyroscopeSensor = sm.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
		if (gyroscopeSensor == null) {
			// GyroscopeValue.setText("不支持陀螺仪");
		}
		sm.registerListener(new MySensorListener(), gyroscopeSensor, SensorManager.SENSOR_DELAY_NORMAL);
		sm.registerListener(this, SensorManager.SENSOR_ACCELEROMETER, SensorManager.SENSOR_DELAY_NORMAL);
	}

	@SuppressLint("NewApi")
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		stumbleNumTxt.setText("当月跌倒次数为：" + AppSharedPreferences.getInt("stumbleNumTxt", 0));
		if (AppSharedPreferences.getBoolean("isSetting", false)) {
			phoneNumTxt.setVisibility(View.VISIBLE);
			phoneNumTxt.setText("当前紧急联系人号码为：" + AppSharedPreferences.getString("phoneNumSTr", ""));

		} else {
			phoneNumTxt.setVisibility(View.GONE);
		}
		sm.registerListener(new MySensorListener(), gyroscopeSensor, SensorManager.SENSOR_DELAY_NORMAL);
		sm.registerListener(this, SensorManager.SENSOR_ACCELEROMETER, SensorManager.SENSOR_DELAY_NORMAL);
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void onStop() {
		// unregister listener
		sm.unregisterListener(this);
		super.onStop();
	}

	@SuppressLint("NewApi")
	@Override
	public void onSensorChanged(int sensor, float[] values) {
		// TODO Auto-generated method stub
		if (sensor == SensorManager.SENSOR_ACCELEROMETER) {
			float x = values[0];
			float y = values[1];
			float z = values[2];
			float ax = (float) (Math.abs(x) * Math.abs(x));
			float ay = (float) (Math.abs(y) * Math.abs(y));
			float az = (float) (Math.abs(z) * Math.abs(z));
			double R = Math.sqrt((double) (ax + ay + az));
			Log.v(TAG, "R-----" + R);
			if (R > 20) {
				isR = true;
			}
			if (isR && isW) {
				isR = false;
				isW = false;
				if (AppSharedPreferences.getBoolean("isSetting", false)) {
					AppSharedPreferences.editorPutInt("stumbleNumTxt", 1 + AppSharedPreferences.getInt("stumbleNumTxt", 0));
					Uri uri = Uri.parse("tel:" + AppSharedPreferences.getString("phoneNumSTr", ""));
					Intent intent = new Intent(Intent.ACTION_CALL, uri);
					startActivity(intent);
					SmsManager sms = SmsManager.getDefault();
					sms.sendTextMessage(AppSharedPreferences.getString("phoneNumSTr", ""), null, "我跌倒了，需要你的帮助！", null, null);
				} else {
					Toast.makeText(StartActivity.this, "您还未设置联系人", 1).show();
				}
			}
		}
	}

	@Override
	public void onAccuracyChanged(int sensor, int accuracy) {
		// TODO Auto-generated method stub

	}

	@SuppressLint("NewApi")
	public class MySensorListener implements SensorEventListener {

		public void onAccuracyChanged(Sensor sensor, int accuracy) {

		}

		public void onSensorChanged(SensorEvent event) {
			if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
				float x = event.values[0];
				float y = event.values[1];
				float z = event.values[2];
				float ax = (float) (Math.abs(x) * Math.abs(x));
				float ay = (float) (Math.abs(y) * Math.abs(y));
				float az = (float) (Math.abs(z) * Math.abs(z));
				double W = Math.sqrt((double) (ax + ay + az));
				Log.v(TAG, "W-----" + W);
				if (W > 4) {
					isW = true;
					// Toast.makeText(StartActivity.this, "w", 1).show();
				}
			}
		}

	}

}
