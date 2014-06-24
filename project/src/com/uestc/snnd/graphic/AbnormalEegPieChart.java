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
    double[] values = new double[] { 12, 14, 11, 10, 19 };//饼图分层5块,每块代表的数值  
    int[] colors = new int[] { Color.BLUE, Color.GREEN, Color.MAGENTA, Color.YELLOW, Color.CYAN };//每块饼图的颜色  
    DefaultRenderer renderer = buildCategoryRenderer(colors);  
    renderer.setZoomButtonsVisible(true);//设置显示放大缩小按钮  
    renderer.setZoomEnabled(true);//设置允许放大缩小.  
    renderer.setChartTitleTextSize(20);//设置图表标题的文字大小  
    return ChartFactory.getPieChartIntent(context, buildCategoryDataset("Project budget", values),  
        renderer, "Budget");//构建Intent, buildCategoryDataset是调用AbstraDemoChart的构建方法.    
  }  
  
}  
