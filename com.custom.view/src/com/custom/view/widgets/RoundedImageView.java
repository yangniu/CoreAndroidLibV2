package com.custom.view.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * 带圆角的ImageView
 * 
 * @author zhangtao
 * 
 */
public class RoundedImageView extends ImageView {

	private float[] mRadii;

	public RoundedImageView(Context context) {
		super(context);
	}

	public RoundedImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public RoundedImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public void setCornerRadiiDP(float leftTop, float rightTop,
			float leftBottom, float rightBottom) {
		final float density = getResources().getDisplayMetrics().density;

		final float lt = leftTop * density;
		final float rt = rightTop * density;
		final float lb = leftBottom * density;
		final float rb = rightBottom * density;

		mRadii = new float[] { lt, lt, rt, rt, rb, rb, lb, lb };
	}

	@Override
	protected void onDraw(Canvas canvas) {
		if (mRadii != null) {
			Path clipPath = new Path();
			int w = this.getWidth();
			int h = this.getHeight();
			clipPath.addRoundRect(new RectF(0, 0, w, h), mRadii,
					Path.Direction.CW);
			canvas.clipPath(clipPath);
		}
		super.onDraw(canvas);
	}
}
