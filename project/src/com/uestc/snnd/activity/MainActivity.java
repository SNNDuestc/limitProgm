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
        
        //��ʼ��state�ı���
        state.setMovementMethod(ScrollingMovementMethod.getInstance());
        state.setText("");
        state.append("Android version: " + Integer.valueOf(android.os.Build.VERSION.SDK) + "\n" );
        
        //��ʼ��raw_data�ı���
        raw_data.setMovementMethod(ScrollingMovementMethod.getInstance());
        
        //��֤�����豸��״̬�������豸��������
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(bluetoothAdapter == null) 
        {
        	// ��ʾ�û��豸��֧������ģ��
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
        		 // �����Ե��豸��TGDevice������������
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
            case TGDevice.MSG_STATE_CHANGE:  //TGD��״̬�ı�

                switch (msg.arg1) {
	                case TGDevice.STATE_IDLE://��ʼ��TGD��û������ͷ���豸
	                    break;
	                case TGDevice.STATE_CONNECTING://Attempting a connection to the headset		                	
	                	state.append("Connecting...\n");
	                	break;		                    
	                case TGDevice.STATE_CONNECTED://���õ��豸�ҵ��ˣ��������ڱ�����
	                	state.append("Connected.\n");
	                	tgDevice.start();
	                    break;
	                case TGDevice.STATE_NOT_FOUND://�޷����ӵ��豸
	                	state.append("Can't find\n");
	                	break;
	                case TGDevice.STATE_NOT_PAIRED://�Ҳ��������豸
	                	state.append("not paired\n");
	                	break;
	                case TGDevice.STATE_DISCONNECTED://��ʧ����
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