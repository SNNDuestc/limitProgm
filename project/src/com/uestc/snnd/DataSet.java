package com.uestc.snnd;

import android.app.Application;

public class DataSet extends Application {
	
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
	String name;
	String Tel;
	String Message;
	
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
	
	public Boolean setName(String name){
		if(name != null){
		this.name = name;
		return true;
		}else{
			return false;
		}
	}
	
	public Boolean setTel(String tel){
		if(tel != null){
		this.Tel = tel;
		return true;
		}else{
			return false;
		}
	}
	
	public Boolean setMessage(String Mes){
		if(Mes != null){
		this.Message = Mes;
		return true;
		}else{
			return false;
		}
	}
	
	public String getName(){
		return this.name;
	}
	
	public String getTel(){
		return this.Tel;
	}
	
	public String getMessage(){
		return this.Message;
	}
	
	 public void onCreate() {
	        // TODO Auto-generated method stub
	        super.onCreate();
	        
	        length = 100;
	        Raw_EegData_list = new float[length];
	        first = 99;
	        last = 99;
	        Message = "我现在处于危险状态";
	    	 
	    	 for(int i=0; i<length; i++){
	    		 Raw_EegData_list[i] = 0;
	    	 }
	 }

}
