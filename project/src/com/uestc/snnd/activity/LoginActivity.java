package com.uestc.snnd.activity;



import com.uestc.snnd.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity{
	
	private EditText account;
	private EditText password;
	
	private Button confirm;
	private Button cancel;

	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.login_activity);
        
        account = (EditText)findViewById(R.id.login_et_account);
        password = (EditText)findViewById(R.id.login_et_password);
        confirm = (Button)findViewById(R.id.login_but_confirm);
        cancel = (Button)findViewById(R.id.login_but_cancel);
        
        confirm.setOnClickListener(new OnClickListener(){
        	public void onClick(View v){
        		
        		Intent intent_login = new Intent();
				intent_login.setClass(LoginActivity.this, MainActivity.class);
        		intent_login.putExtra("id", account.getText().toString());
				startActivity(intent_login);
        	}
        	
        });
        
        cancel.setOnClickListener(new OnClickListener(){
        	public void onClick(View v){
        		
        		Intent intent_cancel = new Intent();
				intent_cancel.setClass(LoginActivity.this, StartActivity.class);
				startActivity(intent_cancel);
        	}
        	
        });
        
        
	}
}
