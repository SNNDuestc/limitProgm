package com.uestc.snnd.main;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.view.View;

public class EegGraphics extends View implements Runnable{
	
	private Paint paint = null;
	
	public EegGraphics (Context context){
		
		super(context);
		
		paint = new Paint();
		
		new Thread(this).start();
	}
	
	protected void onDraw(Canvas canvas){
		
		super.onDraw(canvas);
		paint.setColor(Color.BLUE);
		paint.setStyle(Style.FILL);
		canvas.drawColor(Color.CYAN);
		//canvas.drawCircle();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
