package com.uestc.snnd;

import android.app.Application;

public class RawEegData extends Application {
	
	/*���ø�ȫ�ֱ�����
	 * �����豸��Լÿ0.002�봫��һ���Ե����ݣ�����ٶ�̫�죬�����ڴ�����
	 * ����������������������ռ��Ե����ݣ��ȵ��ʵ�����������һ���Դ�����
	 * Ϊ�����ӻ�ͼ�������ԣ�����ѡ���ռ���100���Ե����ݣ�����һ�λ�ͼ��
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
	private int first, last;										//������ף���β
	private int length;												//�������鳤��
	
	//���20����������
	public float[] get_Raw_EegData_list(){
		return Raw_EegData_list;
	}
	
	//���ָ���±������
	public float get_Raw_EegData(int index){
		return Raw_EegData_list[index];
	}
	
	//������鳤��
	public int get_length(){
		return length;
	}
	
	//��ö����±�
	public int get_first_index(){
		return first;
	}
	
	//������飬ȫ����ֵΪ0
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
	
	//��¼�Ե�����
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