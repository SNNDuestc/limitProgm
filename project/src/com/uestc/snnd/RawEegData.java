package com.uestc.snnd;

import android.app.Application;

public class RawEegData extends Application {
	
	/*设置该全局变量：
	 * 由于设备大约每0.002秒传回一个脑电数据，这个速度太快，不便于处理。
	 * 所以我们设置这个变量来收集脑电数据，等到适当的数量后再一次性处理，
	 * 为了增加绘图的流畅性，我们选择收集到100个脑电数据，进行一次绘图，
	 * 
	 */
	
	private float[] Raw_EegData_list = {0,0,0,0,0,0,0,0,0,0,
								   		0,0,0,0,0,0,0,0,0,0,
								   		0,0,0,0,0,0,0,0,0,0,
								   		0,0,0,0,0,0,0,0,0,0,
								   		0,0,0,0,0,0,0,0,0,0,
								   		0,0,0,0,0,0,0,0,0,0,
								   		0,0,0,0,0,0,0,0,0,0,
								   		0,0,0,0,0,0,0,0,0,0,
								   		0,0,0,0,0,0,0,0,0,0,
								   		0,0,0,0,0,0,0,0,0,0};
	private int first, last;										//定义队首，队尾
	private int length;												//定义数组长度
	
	//获得20个数据数组
	public float[] get_Raw_EegData_list(){
		return Raw_EegData_list;
	}
	
	//获得指定下标的数据
	public float get_Raw_EegData(int index){
		return Raw_EegData_list[index];
	}
	
	//获得数组长度
	public int get_length(){
		return length;
	}
	
	//获得队首下标
	public int get_first_index(){
		return first;
	}
	
	//清空数组，全部赋值为0
	public boolean clear_Raw_EegData_list(){
		int i;
		boolean check = true;
		for(i=0 ; i<length ; i++ ){
			this.Raw_EegData_list[i] = 0;
		}
		
		for(i=0 ; i<length ; i++ ){
			if(this.Raw_EegData_list[i] != 0)
				check = false;
		}
		
		return check;
	}
	
	//记录脑电数据
	public void add_Raw_EegData_list(float data){
		int i;
		for(i=1;i<length;i++){
		this.Raw_EegData_list[i-1] = this.Raw_EegData_list[i]; 
		}
		this.Raw_EegData_list[this.last] = data;
		if(first>0)
			first--;
	}
	
	 public void onCreate() {
	        // TODO Auto-generated method stub
	        super.onCreate();
	        
	        length = 100;
	        Raw_EegData_list = new float[length];
	        first = 99;
	        last = 99;
	    	 
	    	 for(int i=0; i<length; i++){
	    		 Raw_EegData_list[i] = 0;
	    	 }
	 }

}
