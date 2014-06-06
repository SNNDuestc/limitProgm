package com.uestc.snnd.start;



import com.uestc.snnd.R;
import com.uestc.snnd.login.LoginActivity;
import com.uestc.snnd.main.MainActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class StartActivity extends Activity{
	
	Button but_login;
	Button but_start;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.start_activity);
        
        but_login = (Button)findViewById(R.id.start_but_login);
        but_start = (Button)findViewById(R.id.start_but_start);
        
        but_login.setOnClickListener(new OnClickListener(){
        	public void onClick(View v){
        		
        		Intent intent_login = new Intent();
        		intent_login.setClass(StartActivity.this, LoginActivity.class);
				startActivity(intent_login);
        	}
        	
        });
        
        
        but_start.setOnClickListener(new OnClickListener(){
        	public void onClick(View v){

        		Intent intent_start = new Intent();
				intent_start.setClass(StartActivity.this, MainActivity.class);
				startActivity(intent_start);
        	}
        	
        });
        
        
        
	}

}
