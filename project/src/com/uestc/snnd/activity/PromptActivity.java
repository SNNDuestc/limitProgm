package com.uestc.snnd.activity;


import java.util.ArrayList;
import java.util.List;

import com.uestc.snnd.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class PromptActivity extends Activity{
	
	private ListView prompt_list;
	
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.prompt_activity);
        prompt_list = (ListView)findViewById(R.id.prompt_list);
        
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(PromptActivity.this, 
				android.R.layout.simple_list_item_1, getData());
        prompt_list.setAdapter(arrayAdapter);

	}
	
	
	
	private List<String> getData(){
		List<String> data = new ArrayList<String>();
        data.add("短信提示");
        data.add("连接提示");
        data.add("提示铃声");
         
        return data;
	}

}