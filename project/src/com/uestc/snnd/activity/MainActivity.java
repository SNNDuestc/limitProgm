package com.uestc.snnd.activity;

//import com.neurosky.thinkgear.TGDevice;

import java.util.ArrayList;
import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.SeriesSelection;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import org.achartengine.tools.PanListener;
import org.achartengine.tools.ZoomEvent;
import org.achartengine.tools.ZoomListener;

import android.R.color;
import android.app.Activity;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.SmsManager;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.neurosky.thinkgear.*;
import com.uestc.snnd.R;
import com.uestc.snnd.DataSet;
import com.uestc.snnd.graphic.AbnormalEegPieChart;

public class MainActivity extends Activity {
	
	private static final int EEG_DATA_MAX = 32767;
	
	BluetoothAdapter bluetoothAdapter;	
	private TextView state;
	private TextView user_id;
	private Spinner graphic_spinner;
	private Button set;
	private Button exit;
	private Button send;
	private DataSet dataSet;
	private boolean isRunning = false;
	private int count = -1;
//	private EegGraphics eegGraphics = null;																	//������ͼ��view
	
	private int x_up_left, y_up_left, x_up_right, y_up_right;												//��ͼ�����Ϸ��ؼ����½�, ���½ǵ�����
	private int x_down_left, y_down_left;										//��ͼ�����·��ռ����Ͻǣ� ���Ͻǵ�����
	private float [] paint_data;																				//��ͼʹ�õ����꣬����ԭ���ݴ���õ�
	private int window_width;
	TGDevice tgDevice;
	
	private int counter = 0;  //��¼�����Ե����ݵĴ���	
	final boolean rawEnabled = false;
	private static final int REQUEST_ENABLE_BT = 1;
	
	public static final String TYPE = "type";  
	  
	private XYMultipleSeriesDataset mDataset = new XYMultipleSeriesDataset();   
	private XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();  
	private XYSeries mCurrentSeries;  
	private XYSeriesRenderer mCurrentRenderer;   
	private String mDateFormat;  
	private GraphicalView mChartView;   
	@Override  
	  protected void onRestoreInstanceState(Bundle savedState) {  
	    super.onRestoreInstanceState(savedState);  
	    mDataset = (XYMultipleSeriesDataset) savedState.getSerializable("dataset");  
	    mRenderer = (XYMultipleSeriesRenderer) savedState.getSerializable("renderer");  
	    mCurrentSeries = (XYSeries) savedState.getSerializable("current_series");  
	    mCurrentRenderer = (XYSeriesRenderer) savedState.getSerializable("current_renderer");  
	    mDateFormat = savedState.getString("date_format");  
	  }  
	  
	  @Override  
	  protected void onSaveInstanceState(Bundle outState) {  
	    super.onSaveInstanceState(outState);  
	    outState.putSerializable("dataset", mDataset);  
	    outState.putSerializable("renderer", mRenderer);  
	    outState.putSerializable("current_series", mCurrentSeries);  
	    outState.putSerializable("current_renderer", mCurrentRenderer);  
	    outState.putString("date_format", mDateFormat);  
	  }  
	  
	    private Thread GetDataThread = new Thread (new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				while(isRunning){
					try{
//						rawEegData.add_Raw_EegData_list(msg.arg1);
						if(tgDevice == null)
							throw new InterruptedException();
		            	
		            	mDataset.removeSeries(mCurrentSeries);
		                
		                int length = mCurrentSeries.getItemCount();
		                if(length > dataSet.get_length()){
		                	length = dataSet.get_length(); 
		                }
		                
		                mCurrentSeries.clear();
		                for(int i = 0; i < dataSet.get_length();i++)
		                mCurrentSeries.add(i, dataSet.get_Raw_EegData(i));
		                
		                mDataset.addSeries(mCurrentSeries);
		                
		                if(counter >= dataSet.get_length())
		                	counter = 0;
		                
		            	counter++; 
		            	
		            	if(counter % 10 == 0)
		            	mChartView.invalidate();
					}catch(InterruptedException e){
						e.printStackTrace();
					}
				}
				
			}
	    	
	    });
	  
	  
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        
        setContentView(R.layout.main_activity);
        
        dataSet = (DataSet)getApplication();      
        user_id    = (TextView)findViewById(R.id.main_tv_userId);
        state 	   = (TextView)findViewById(R.id.main_tv_state);
        graphic_spinner   = (Spinner)findViewById(R.id.main_spinner_graphic);
        set        = (Button)findViewById(R.id.main_but_set);
        exit       = (Button)findViewById(R.id.main_but_exit);
        send	   = (Button)findViewById(R.id.main_but_send);
        //graphic_list = (ListView)findViewById(R.id.main_lv_graphicList);
        
        window_width = this.getWindow().getWindowManager().getDefaultDisplay().getWidth();
        
        x_up_left = user_id.getLeft();
    	y_up_left = user_id.getTop() + user_id.getHeight();
    	
    	x_up_right = user_id.getLeft() + user_id.getWidth();
    	y_up_right = user_id.getTop() + user_id.getHeight();
    	
    	x_down_left = graphic_spinner.getLeft();
    	y_down_left = graphic_spinner.getTop();
    	
    	x_down_left = graphic_spinner.getLeft() + graphic_spinner.getWidth();
    	y_down_left = graphic_spinner.getTop();
    	
    	
    	//��ʼ����ͼ�ؼ�
    	mRenderer.setApplyBackgroundColor(true);//�����Ƿ���ʾ����ɫ    
        mRenderer.setAxisTitleTextSize(16); //������������ֵĴ�С  
        mRenderer.setChartTitleTextSize(20);//?��������ͼ��������ִ�С  
        mRenderer.setLabelsTextSize(15);//���ÿ̶���ʾ���ֵĴ�С(XY�ᶼ�ᱻ����)  
        mRenderer.setLegendTextSize(20);//ͼ�����ִ�С  
        mRenderer.setMargins(new int[] { 0, 20, 0, 20 });//����ͼ�����߿�(��/��/��/��)  
        mRenderer.setZoomButtonsVisible(true);//�Ƿ���ʾ�Ŵ���С��ť  
        mRenderer.setPointSize(3);//���õ�Ĵ�С(ͼ����ʾ�ĵ�Ĵ�С��ͼ���е�Ĵ�С���ᱻ����) 
//      mRenderer.setXLabels(rawEegData.get_length());
//      mRenderer.setYLabels(rawEegData.get_length());
        mRenderer.setXTitle("ʱ��");//����ΪX��ı���  
        mRenderer.setYTitle("�Ե�");//����y��ı��� 
        mRenderer.setYAxisMax(EEG_DATA_MAX);
        mRenderer.setYAxisMin(-EEG_DATA_MAX);
        mRenderer.setXAxisMax(dataSet.get_length());
        mRenderer.setXAxisMin(0);
        mRenderer.setShowGrid(true);
        mRenderer.setGridColor(Color.GREEN);
    	
    	paint_data = new float[dataSet.get_length()*2]; 
    	
    	String seriesTitle = "�Ե�����";//ͼ��    
        XYSeries series = new XYSeries(seriesTitle);//����XYSeries  
        mDataset.addSeries(series);//��XYMultipleSeriesDataset�����XYSeries  
        mCurrentSeries = series;//���õ�ǰ��Ҫ������XYSeries  
        XYSeriesRenderer renderer = new XYSeriesRenderer();//����XYSeriesRenderer  
        mRenderer.addSeriesRenderer(renderer);//������XYSeriesRenderer���ӵ�XYMultipleSeriesRenderer  
        renderer.setPointStyle(PointStyle.CIRCLE);//���������Բ��  
        renderer.setColor(Color.GREEN);
        renderer.setFillPoints(true);//���õ��Ƿ�ʵ��  
        mCurrentRenderer = renderer;  
        //setSeriesEnabled(true);  

        
        //��ʼ��state�ı���
        state.setText("Android version: " + Integer.valueOf(android.os.Build.VERSION.SDK) + "\n" );
        
        //��ʼ��graphic_spinner
        String []  mGraphics = {"�Ե�ͼ", "�Բ��쳣����ͳ��", "רע��"};
        ArrayList<String> allGraphics = new ArrayList<String>();
        for(int i=0;i<mGraphics.length;i++)
        {
        	allGraphics.add(mGraphics[i]);
        	mCurrentSeries.add(i, dataSet.get_Raw_EegData(i));
        }
        
        ArrayAdapter<String> aspnGraphics = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, allGraphics);
        aspnGraphics.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        
        graphic_spinner.setAdapter(aspnGraphics);
        //graphic_spinner.setPromptId();
        graphic_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				String str = parent.getItemAtPosition(position).toString();
				switch(str){
				case "�Ե�ͼ":
					{
						Toast.makeText(MainActivity.this, "��ǰҳ��", Toast.LENGTH_SHORT).show();
						break;
					}
				case "�Բ��쳣ͳ��":
					{
						
						Intent intent_PieChart = new AbnormalEegPieChart().execute(MainActivity.this);
						startActivity(intent_PieChart);
						break;
					}
				case "רע��":
					{
					
					}
				}
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
        	
		});
        //��set��ť���¼�����
        set.setOnClickListener(new OnClickListener(){
        	
			@Override
			public void onClick(View v) {
				Intent intent_MainToSet = new Intent();
				intent_MainToSet.setClass(MainActivity.this, SettingActivity.class);
				startActivity(intent_MainToSet);
				
			}
			
		});
        
        //���Է��Ͷ��Ź���
        send.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(dataSet.getTel() == null)
					Toast.makeText(MainActivity.this, "�����÷��Ͷ�Ϣ����", Toast.LENGTH_SHORT)  
                    .show(); 
				else{
					SmsManager sms = SmsManager.getDefault();
					if(sms != null){
					String mes = dataSet.getMessage().toString();
					String desnm = dataSet.getTel().toString();
					PendingIntent mPI = PendingIntent.getBroadcast(MainActivity.this, 0, new Intent(), 0);
					sms.sendTextMessage(desnm, null, mes, mPI, null);
					}else{
						Toast.makeText(MainActivity.this, "���Ź��ܲ�����", Toast.LENGTH_SHORT)  
	                    .show(); 
					}
				}
			}
		});

        
        //��֤�����豸��״̬�������豸��������
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(bluetoothAdapter == null) 
        {
        	// ��ʾ�û��豸��֧������ģ��
        	//Toast.makeText(this, "Bluetooth is not available", Toast.LENGTH_LONG).show();
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
    
    
    private List<String> getData(){
        
        List<String> data = new ArrayList<String>();
        data.add("�Ե�ͼ");
        data.add("�Բ��쳣ͳ��");
        data.add("���ɶ�");
         
        return data;
    }
    
    
    @Override
    public void onDestroy() {
    	tgDevice.close();
        super.onDestroy();
    }
    
    protected void onResume() {  
        super.onResume();  
        if (mChartView == null) {  
          LinearLayout layout = (LinearLayout) findViewById(R.id.main_linear_chart);  
          mChartView = ChartFactory.getLineChartView(this, mDataset, mRenderer);  
          mRenderer.setClickEnabled(true);//����ͼ���Ƿ�������  
          mRenderer.setSelectableBuffer(100);//���õ�Ļ���뾶ֵ(��ĳ�㸽�����ʱ,���Χ�ڶ����������)  
//          mChartView.setOnClickListener(new View.OnClickListener() {  
//            @Override  
//            public void onClick(View v) {  
//                //��δ��봦����һ�����,���������ĵ����ĸ��������Լ��������.  
//                //-->start  
//              SeriesSelection seriesSelection = mChartView.getCurrentSeriesAndPoint();  
//              double[] xy = mChartView.toRealPoint(0);  
//              if (seriesSelection == null) {  
//                Toast.makeText(MainActivity.this, "No chart element was clicked", Toast.LENGTH_SHORT)  
//                    .show();  
//              } else {  
//                Toast.makeText(  
//                		MainActivity.this,  
//                    "Chart element in series index " + seriesSelection.getSeriesIndex()  
//                        + " data point index " + seriesSelection.getPointIndex() + " was clicked"  
//                        + " closest point value X=" + seriesSelection.getXValue() + ", Y=" + seriesSelection.getValue()  
//                        + " clicked point value X=" + (float) xy[0] + ", Y=" + (float) xy[1], Toast.LENGTH_SHORT).show();  
//              }  
//              //-->end  
//            }  
//          });  
//          mChartView.setOnLongClickListener(new View.OnLongClickListener() {  
//            @Override  
//            public boolean onLongClick(View v) {  
//              SeriesSelection seriesSelection = mChartView.getCurrentSeriesAndPoint();  
//              if (seriesSelection == null) {  
//                Toast.makeText(MainActivity.this, "No chart element was long pressed",  
//                    Toast.LENGTH_SHORT);  
//                return false; // no chart element was long pressed, so let something  
//                // else handle the event  
//              } else {  
//                Toast.makeText(MainActivity.this, "Chart element in series index "  
//                    + seriesSelection.getSeriesIndex() + " data point index "  
//                    + seriesSelection.getPointIndex() + " was long pressed", Toast.LENGTH_SHORT);  
//                return true; // the element was long pressed - the event has been  
//                // handled  
//              }  
//            }  
//          });  
          //��δ��봦��Ŵ���С  
          //-->start  
          mChartView.addZoomListener(new ZoomListener() {  
            public void zoomApplied(ZoomEvent e) {  
              String type = "out";  
              if (e.isZoomIn()) {  
                type = "in";  
              }  
              System.out.println("Zoom " + type + " rate " + e.getZoomRate());  
            }  
              
            public void zoomReset() {  
              System.out.println("Reset");  
            }  
          }, true, true);  
          //-->end  
          //�����϶�ͼ��ʱ��̨��ӡ��ͼ������������Сֵ.  
          mChartView.addPanListener(new PanListener() {  
            public void panApplied() {  
              System.out.println("New X range=[" + mRenderer.getXAxisMin() + ", " + mRenderer.getXAxisMax()  
                  + "], Y range=[" + mRenderer.getYAxisMax() + ", " + mRenderer.getYAxisMax() + "]");  
            }  
          });  
          layout.addView(mChartView, new LayoutParams(LayoutParams.FILL_PARENT,  
              LayoutParams.FILL_PARENT));   
        } else {  
          mChartView.repaint();  
        }  
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
	                	state.setText("Connecting...\n");
	                	break;		                    
	                case TGDevice.STATE_CONNECTED://���õ��豸�ҵ��ˣ��������ڱ�����
	                	state.setText("Connected.\n");
	                	tgDevice.start();
	                    break;
	                case TGDevice.STATE_NOT_FOUND://�޷����ӵ��豸
	                	state.setText("Can't find\n");
	                	break;
	                case TGDevice.STATE_NOT_PAIRED://�Ҳ��������豸
	                	state.setText("not paired\n");
	                	break;
	                case TGDevice.STATE_DISCONNECTED://��ʧ����
	                	state.setText("Disconnected mang\n");
                }

                break;
            case TGDevice.MSG_POOR_SIGNAL:
            		//signal = msg.arg1;
            	//state.setText("PoorSignal: " + msg.arg1 + "\n");
                break;
            case TGDevice.MSG_RAW_DATA:	//Raw EEG data  
            {	count++;
            	if(count % 64 == 0){
            		if(count >= 2048)
            			count = 0;
            		dataSet.add_Raw_EegData_list(msg.arg1);
            	
            	mDataset.removeSeries(mCurrentSeries);
                
                int length = mCurrentSeries.getItemCount();
                if(length > dataSet.get_length()){
                	length = dataSet.get_length(); 
                }
                
                mCurrentSeries.clear();
                for(int i = 0; i < dataSet.get_length();i++)
                mCurrentSeries.add(i, dataSet.get_Raw_EegData(i));
                
                mDataset.addSeries(mCurrentSeries);
                
//                if(counter >= rawEegData.get_length())
//                	counter = 0;
//                
//            	counter++; 
//            	
//            	if(counter % 10 == 0)
            	mChartView.invalidate();
            	}
            }
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


