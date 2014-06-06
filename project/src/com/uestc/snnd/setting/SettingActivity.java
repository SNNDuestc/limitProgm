package com.uestc.snnd.setting;


import java.util.ArrayList;
import java.util.List;

import com.uestc.snnd.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SettingActivity extends Activity {
	
	private ListView setting_list;
	
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.setting_activity);
        
        setting_list = (ListView)findViewById(R.id.setting_list);
        
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(SettingActivity.this, 
				android.R.layout.simple_list_item_1, getData());
        setting_list.setAdapter(arrayAdapter);
        
        setting_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				
			}
        	
        });

	}
	
	
	
	private List<String> getData(){
		List<String> data = new ArrayList<String>();
        data.add("提示");
        data.add("报警等级");
        data.add("历史数据");
        data.add("帮助");
         
        return data;
	}

}
