package com.cn.stepcounter;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * ������ϵ�˺���
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
					Toast.makeText(SetPhoneNumActivity.this, "�������ֻ�����", 1).show();
				} else {
					if (!VerifyFormat.isMobileNO(phoneNumSTr)) {
						Toast.makeText(SetPhoneNumActivity.this, "�ֻ������ʽ����ȷ", 1).show();
					} else {
						AppSharedPreferences.editorPutString("phoneNumSTr", phoneNumSTr);
						AppSharedPreferences.editorPutBoolean("isSetting", true);
						Toast.makeText(SetPhoneNumActivity.this, "���óɹ�", 1).show();
						finish();
					}

				}
			}
		});
	}
}
