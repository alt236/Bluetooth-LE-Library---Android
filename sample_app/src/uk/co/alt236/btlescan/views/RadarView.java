package uk.co.alt236.btlescan.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class RadarView extends View{
	private Paint mRadarBackgroundPaint;
	private Paint mViewBackgroundPaint;
	private int mWidth;
	private int mHeight;
	
	public RadarView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	private void init() {
		mRadarBackgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mRadarBackgroundPaint.setColor(0xff101010);
		mRadarBackgroundPaint.setStyle(Style.FILL);
		
		mViewBackgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mViewBackgroundPaint.setColor(Color.BLACK);
		mViewBackgroundPaint.setStyle(Style.FILL);
	}

	public RadarView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public RadarView(Context context) {
		super(context);
		init();
	}


	private int getSmallestDimension(){
		if(mWidth > mHeight){
			return mHeight;
		} else {
			return mWidth;
		}
	}

	 @Override
	    public void onSizeChanged (int w, int h, int oldw, int oldh){
	        super.onSizeChanged(w, h, oldw, oldh);
	        mWidth = w;
	        mHeight = h;
	    }
	 
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		Log.d("TAG", "~ ONDRAW!");
		
		Log.d("TAG", "~ getLeft! " + getLeft());
		Log.d("TAG", "~ getTop! " + getTop());
		Log.d("TAG", "~ getRight! " + getRight());
		Log.d("TAG", "~ getBottom! " + getBottom());
		Log.d("TAG", "~ width! " + mWidth);
		Log.d("TAG", "~ height! " + mHeight);
		
		final int smallestDim = getSmallestDimension();


		canvas.drawRect(
				getLeft(), 
				getTop(), 
				getRight(), 
				getBottom(),
				mViewBackgroundPaint);
		
		canvas.drawCircle(
				getLeft() + (mWidth / 2f),
				getTop() + (mHeight / 2f),
				smallestDim / 2f,
				mRadarBackgroundPaint);
	}
}
