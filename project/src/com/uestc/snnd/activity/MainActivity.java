package com.uestc.snnd.activity;

//import com.neurosky.thinkgear.TGDevice;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.neurosky.thinkgear.*;
import com.uestc.snnd.R;

public class MainActivity extends Activity {
	
	BluetoothAdapter bluetoothAdapter;	
	private TextView state;
	private TextView user_id;
	private TextView raw_data;
	private Button connect;
	private Button set;
	private Button exit;
	
	TGDevice tgDevice;
	final boolean rawEnabled = false;
	private static final int REQUEST_ENABLE_BT = 1;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.main_activity);
        
        user_id = (TextView)findViewById(R.id.main_tv_userId);
        state = (TextView)findViewById(R.id.main_tv_state);
        raw_data = (TextView)findViewById(R.id.main_tv_data);
        
        //初始化state文本框
        state.setMovementMethod(ScrollingMovementMethod.getInstance());
        state.setText("");
        state.append("Android version: " + Integer.valueOf(android.os.Build.VERSION.SDK) + "\n" );
        
        //初始化raw_data文本框
        raw_data.setMovementMethod(ScrollingMovementMethod.getInstance());
        
        //验证蓝牙设备的状态，并与设备建立连接
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(bluetoothAdapter == null) 
        {
        	// 提示用户设备不支持蓝牙模块
        	Toast.makeText(this, "Bluetooth is not available", Toast.LENGTH_LONG).show();
        }
        else
        {
        	
        	 if(!bluetoothAdapter.isEnabled())
        	 {
         		Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
         		startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
        	 }
        	 else
        	 {    
        		 // 创建脑电设备类TGDevice，并建立连接
        		 tgDevice = new TGDevice(bluetoothAdapter, handler);
        		 tgDevice.connect(true); 
         	 }
        }      
    }
    
    @Override
    public void onDestroy() {
    	tgDevice.close();
        super.onDestroy();
    }
    /**
     * Handles messages from TGDevice
     */
    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
        	switch (msg.what) {
            case TGDevice.MSG_STATE_CHANGE:  //TGD的状态改变

                switch (msg.arg1) {
	                case TGDevice.STATE_IDLE://初始化TGD，没有连到头部设备
	                    break;
	                case TGDevice.STATE_CONNECTING://Attempting a connection to the headset		                	
	                	state.append("Connecting...\n");
	                	break;		                    
	                case TGDevice.STATE_CONNECTED://可用的设备找到了，数据正在被接受
	                	state.append("Connected.\n");
	                	tgDevice.start();
	                    break;
	                case TGDevice.STATE_NOT_FOUND://无法连接到设备
	                	state.append("Can't find\n");
	                	break;
	                case TGDevice.STATE_NOT_PAIRED://找不到可用设备
	                	state.append("not paired\n");
	                	break;
	                case TGDevice.STATE_DISCONNECTED://丢失连接
	                	state.append("Disconnected mang\n");
                }

                break;
            case TGDevice.MSG_POOR_SIGNAL:
            		//signal = msg.arg1;
            	//state.setText("PoorSignal: " + msg.arg1 + "\n");
                break;
            case TGDevice.MSG_RAW_DATA:	//Raw EEG data  
            		//raw1 = msg.arg1;
            		raw_data.append("Got raw: " + msg.arg1 + "\n");
            	break;
            case TGDevice.MSG_HEART_RATE://Heart rate data
            	//state.setText("Heart rate: " + msg.arg1 + "\n");
                break;
            case TGDevice.MSG_ATTENTION:  //Attention level data
            		//att = msg.arg1;
            	//state.setText("Attention: " + msg.arg1 + "\n");
            		//Log.v("HelloA", "Attention: " + att + "\n");
            	break;
            case TGDevice.MSG_MEDITATION:

            	break;
            case TGDevice.MSG_BLINK://Strength of detected blink
            	//state.setText("Blink: " + msg.arg1 + "\n");
            	break;
            case TGDevice.MSG_RAW_COUNT:
            		//tv.append("Raw Count: " + msg.arg1 + "\n");
            	break;
            case TGDevice.MSG_LOW_BATTERY:
            	Toast.makeText(getApplicationContext(), "Low battery!", Toast.LENGTH_LONG).show();
            	break;
            case TGDevice.MSG_RAW_MULTI:  //Multi-channel raw data
            	//TGRawMulti rawM = (TGRawMulti)msg.obj;
            	//tv.append("Raw1: " + rawM.ch1 + "\nRaw2: " + rawM.ch2);
            default:
            	break;
        }
        }
    };
    
    public void doStuff(View view) {
    	if(tgDevice.getState() != TGDevice.STATE_CONNECTING && tgDevice.getState() != TGDevice.STATE_CONNECTED)
    		tgDevice.connect(rawEnabled);   
    	//tgDevice.ena
    }
}