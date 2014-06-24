package com.uestc.snnd.graphic;

import org.achartengine.ChartFactory;  
import org.achartengine.renderer.DefaultRenderer;  
  
import android.content.Context;  
import android.content.Intent;  
import android.graphics.Color;  
  
public class AbnormalEegPieChart extends AbstractDemoChart {  
  public String getName() {  
    return "Budget chart";  
  }  
  
  public String getDesc() {  
    return "The budget per project for this year (pie chart)";  
  }  
  public Intent execute(Context context) {  
    double[] values = new double[] { 12, 14, 11, 10, 19 };//��ͼ�ֲ�5��,ÿ��������ֵ  
    int[] colors = new int[] { Color.BLUE, Color.GREEN, Color.MAGENTA, Color.YELLOW, Color.CYAN };//ÿ���ͼ����ɫ  
    DefaultRenderer renderer = buildCategoryRenderer(colors);  
    renderer.setZoomButtonsVisible(true);//������ʾ�Ŵ���С��ť  
    renderer.setZoomEnabled(true);//��������Ŵ���С.  
    renderer.setChartTitleTextSize(20);//����ͼ���������ִ�С  
    return ChartFactory.getPieChartIntent(context, buildCategoryDataset("Project budget", values),  
        renderer, "Budget");//����Intent, buildCategoryDataset�ǵ���AbstraDemoChart�Ĺ�������.    
  }  
  
}  
