package com.uestc.snnd.activity;

import com.uestc.snnd.DataSet;
import com.uestc.snnd.R;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class HelpSettingDialog extends Activity {
	
	private EditText et_name;
	private EditText et_tel;
	private EditText et_mes;
	private Button confirm;
	private Button cancel;
	private DataSet dataSet;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.help_setting_dialog);
     
        et_name = (EditText)findViewById(R.id.helpSetting_et_name);
        et_tel = (EditText)findViewById(R.id.helpSetting_et_tel);
        et_mes = (EditText)findViewById(R.id.helpSetting_et_message);
        confirm = (Button)findViewById(R.id.helpSetting_but_confirm);
        cancel = (Button)findViewById(R.id.helpSetting_but_cancel);
        dataSet = (DataSet)getApplication();
        
        et_name.setHint("姓名");
        et_tel.setHint("收件人电话");
        et_mes.setHint("发送信息");
        
        confirm.setOnClickListener(new View.OnClickListener() {
			
        	int count = 3;
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!dataSet.setMessage(et_mes.getText().toString()))
				{
					Toast.makeText(HelpSettingDialog.this, "发送内容为空，使用默认短信内容", Toast.LENGTH_SHORT)  
                    .show();
					et_mes.setText("我现在处于危险状态！");
				}
				if(!dataSet.setName(et_name.getText().toString()) || et_name.getText().toString()=="姓名")
				{
					Toast.makeText(HelpSettingDialog.this, "姓名栏输入为空", Toast.LENGTH_SHORT)  
                    .show();
					count--;
				}
				if(!dataSet.setTel(et_tel.getText().toString()) || et_tel.getText().toString()=="收件人电话");
				{
					Toast.makeText(HelpSettingDialog.this, "电话号码栏输入为空", Toast.LENGTH_SHORT)  
                    .show();
					count--;
				}
				
				if(count == 3){
					Log.e(USER_SERVICE, "success");
					finish();
				}
			}
		});
        
        
        	cancel.setOnClickListener(new View.OnClickListener(){
        		@Override
    			public void onClick(View v) {
    				// TODO Auto-generated method stub
        			finish();
        		}
        	});
        
	}
	

}
