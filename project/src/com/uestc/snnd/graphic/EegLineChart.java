package com.uestc.snnd.graphic;

import java.util.ArrayList;
import java.util.List;

import org.achartengine.GraphicalView;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import com.uestc.snnd.RawEegData;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class EegLineChart extends Activity {
	

	public static final String TYPE = "type";  
	  
	  private XYMultipleSeriesDataset mDataset = new XYMultipleSeriesDataset();  
	  
	  private XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();  
	  
	  private XYSeries mCurrentSeries;  
	  
	  private XYSeriesRenderer mCurrentRenderer;  
	  
	  private String mDateFormat;   
	  
	  private GraphicalView mChartView;  
	    
	  private int index = 0;  
	  
	  
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
	  
	  
	     
	
	

}
