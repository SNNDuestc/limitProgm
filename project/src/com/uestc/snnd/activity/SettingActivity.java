package com.uestc.snnd.activity;


import com.uestc.snnd.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class SettingActivity extends Activity {
	
	private TextView help_set;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.setting_activity);
        
        help_set = (TextView)findViewById(R.id.setting_tv_help);
        
        help_set.setOnClickListener(new OnClickListener(){
        	
			@Override
			public void onClick(View v) {
				Intent intent_ToHelpSetting = new Intent();
				intent_ToHelpSetting.setClass(SettingActivity.this, HelpSettingDialog.class);
				startActivity(intent_ToHelpSetting);
				
			}
			
		});
        

        
       
	}

}
