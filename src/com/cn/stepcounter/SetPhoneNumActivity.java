package com.cn.stepcounter;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 设置联系人号码
 */
public class SetPhoneNumActivity extends Activity {
	private TextView confirmTxt;
	private EditText phoneEdit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_activity);
		phoneEdit = (EditText) findViewById(R.id.phoneEdit);
		confirmTxt = (TextView) findViewById(R.id.confirmTxt);
		confirmTxt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String phoneNumSTr = phoneEdit.getText().toString();
				if (phoneNumSTr == null || phoneNumSTr.equals("")) {
					Toast.makeText(SetPhoneNumActivity.this, "请输入手机号码", 1).show();
				} else {
					if (!VerifyFormat.isMobileNO(phoneNumSTr)) {
						Toast.makeText(SetPhoneNumActivity.this, "手机号码格式不正确", 1).show();
					} else {
						AppSharedPreferences.editorPutString("phoneNumSTr", phoneNumSTr);
						AppSharedPreferences.editorPutBoolean("isSetting", true);
						Toast.makeText(SetPhoneNumActivity.this, "设置成功", 1).show();
						finish();
					}

				}
			}
		});
	}
}
