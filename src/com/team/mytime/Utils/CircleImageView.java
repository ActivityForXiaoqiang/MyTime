package com.team.mytime.Utils;



import com.team.mytime.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

public class CircleImageView extends ImageView {

	private Bitmap mask;
	private Paint paint;

	private int borderColor = 0xffececec;
	private int roundWidth = 0;
	private int roundHeight = 0;
	private int borderWidth = 0;

	private static final Xfermode MASK_XFERMODE;
	static {
		PorterDuff.Mode localMode = PorterDuff.Mode.DST_IN;
		MASK_XFERMODE = new PorterDuffXfermode(localMode);
	}

	public CircleImageView(Context paramContext) {
		super(paramContext);
		this.setScaleType(ScaleType.CENTER_CROP);
	}

	public CircleImageView(Context paramContext, AttributeSet paramAttributeSet) {
		super(paramContext, paramAttributeSet);
		this.setScaleType(ScaleType.CENTER_CROP);
	}

	public CircleImageView(Context paramContext,
			AttributeSet paramAttributeSet, int paramInt) {
		super(paramContext, paramAttributeSet, paramInt);
		this.setScaleType(ScaleType.CENTER_CROP);
	}

	private Bitmap makeDst(int w, int h) {
		Bitmap bm = Bitmap.createBitmap(getWidth(), getHeight(),
				Bitmap.Config.ARGB_4444);
		Canvas c = new Canvas(bm);
		Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
		p.setColor(Color.parseColor("#ffffffff"));
		// c.drawOval(new RectF(0, 0, w, h), p);
		c.drawRoundRect(new RectF(0, 0, getWidth(), getHeight()), w, h, p);
		return bm;
	}

	@Override
	protected void onDraw(Canvas paramCanvas) {
		Drawable localDrawable = getDrawable();
		if (localDrawable == null)
			return;
		try {
			initPaint();
			// 鑾峰彇drawable鐨勫鍜岄珮
			int dWidth = localDrawable.getIntrinsicWidth();
			int dHeight = localDrawable.getIntrinsicHeight();

			// 鍒涘缓bitmap
			// Bitmap bitmap = Bitmap.createBitmap(getWidth(),
			// getHeight(),Config.ARGB_8888);
			// 鍒涘缓鐢诲竷
			// Canvas drawCanvas = new Canvas(bitmap);
			// 缂╂斁姣斾緥
			float scale = 1.0f;
			// 鎸夌収bitmap鐨勫楂橈紝浠ュ強view鐨勫楂橈紝璁＄畻缂╂斁姣斾緥锛涘洜涓鸿缃殑src瀹介珮姣斾緥鍙兘鍜宨mageview鐨勫楂樻瘮渚嬩笉鍚岋紝杩欓噷鎴戜滑涓嶅笇鏈涘浘鐗囧け鐪燂紱
			// if (dWidth == dHeight)
			// {
			// //
			// 濡傛灉鍥剧墖鐨勫鎴栬�楂樹笌view鐨勫楂樹笉鍖归厤锛岃绠楀嚭闇�缂╂斁鐨勬瘮渚嬶紱缂╂斁鍚庣殑鍥剧墖鐨勫楂橈紝涓�畾瑕佸ぇ浜庢垜浠瑅iew鐨勫楂橈紱鎵�互鎴戜滑杩欓噷鍙栧ぇ鍊硷紱
			// scale = Math.max(getWidth() * 1.0f / dWidth, getHeight()
			// * 1.0f / dHeight);
			// } else
			// {
			// scale = getWidth() * 1.0F / Math.min(dWidth, dHeight);
			// }
			scale = Math.max(getWidth() * 1.0f / dWidth, getHeight() * 1.0f
					/ dHeight);
			// 鏍规嵁缂╂斁姣斾緥锛岃缃産ounds锛岀浉褰撲簬缂╂斁鍥剧墖浜�
			localDrawable.setBounds(0, 0, (int) (scale * dWidth),
					(int) (scale * dHeight));
			// localDrawable.draw(drawCanvas);

			float f1 = getWidth();
			float f2 = getHeight();
			int i = paramCanvas.saveLayer(0.0F, 0.0F, f1, f2, null, 31);
			int j = getWidth();
			int k = getHeight();
			// localDrawable.setBounds(0, 0, j, k);
			localDrawable.draw(paramCanvas);
			if ((this.mask == null) || (this.mask.isRecycled())) {
				if (this.roundHeight == 0 && this.roundWidth == 0) {
					this.roundHeight = getHeight();
					this.roundWidth = getWidth();
				}
				this.mask = makeDst(roundWidth, roundHeight);
			}
			paramCanvas.drawBitmap(this.mask, 0.0F, 0.0F, this.paint);
			drawBorder(paramCanvas, j, k);
			drawPercent(paramCanvas, paint);
			paramCanvas.restoreToCount(i);
			return;
		} catch (Exception localException) {
			localException.printStackTrace();
		}
	}

	private void drawBorder(Canvas canvas, final int width, final int height) {
		if (this.borderWidth == 0) {
			return;
		}
		Paint mBorderPaint = new Paint();
		mBorderPaint.setStyle(Paint.Style.STROKE);
		mBorderPaint.setAntiAlias(true);
		mBorderPaint.setColor(this.borderColor);
		mBorderPaint.setStrokeWidth(borderWidth);
		int step = borderWidth / 2;
		canvas.drawRoundRect(new RectF(0 + step, step, getWidth() - step,
				getHeight() - step), width - borderWidth, height - borderWidth,
				mBorderPaint);
		/**
		 * 鍧愭爣x锛歷iew瀹藉害鐨勪竴鑸�鍧愭爣y锛歷iew楂樺害鐨勪竴鑸�鍗婂緞r锛氬洜涓烘槸view鐨勫搴�border鐨勪竴鍗�
		 */
		// canvas.drawCircle(width >> 1, height >> 1, (width - border) >> 1,
		// mBorderPaint);
	}

	public void setRound(int roundWidthDP, int roundHeightDP) {
		this.roundHeight = roundHeightDP;
		this.roundWidth = roundWidthDP;

		float density = this.getContext().getResources().getDisplayMetrics().density;
		roundWidth = (int) (roundWidth * density);
		roundHeight = (int) (roundHeight * density);
	}

	/**
	 * @Title: setBorderColor
	 * @Description: TODO(璁剧疆杈规棰滆壊鍊�
	 * @param @param color argb鍊�璁惧畾鏂囦欢
	 * @return void 杩斿洖绫诲瀷
	 * @throws
	 */
	public void setBorder(int color, int widthPX) {
		if (color == -1) {
			borderColor = 0xffececec;
		} else {
			this.borderColor = color;
		}
		this.borderWidth = widthPX;
	}

	public void setBorder(int widthPX) {
		borderColor = 0xffececec;
		this.borderWidth = widthPX;
	}

	@Override
	public void setColorFilter(ColorFilter cf) {
		// TODO Auto-generated method stub
		super.setColorFilter(cf);
		initPaint();
		paint.setColorFilter(cf);
	}

	private void initPaint() {
		if (this.paint == null) {
			Paint localPaint1 = new Paint();
			this.paint = localPaint1;
			this.paint.setFilterBitmap(false);
			Paint localPaint2 = this.paint;
			Xfermode localXfermode1 = MASK_XFERMODE;
			@SuppressWarnings("unused")
			Xfermode localXfermode2 = localPaint2.setXfermode(localXfermode1);
		}
	}

	// ///////////////////////////////////////////////////////////////////////////
	public enum Status {
		RUNNING, NONE
	}

	private static final int DEFAULT_TEXTCOLOT = 0xff0074a2;
	private static final int DEFAULT_TEXTSIZE = 32;
	private float mPercent;
	private int percent;
	private Bitmap mScaledBitmap;
	private float mLeft;
	private int mSpeed = 10;
	private int mRepeatCount = 0;
	private Status mFlag = Status.NONE;
	private int mTextColot = DEFAULT_TEXTCOLOT;
	private int mTextSize = DEFAULT_TEXTSIZE;

	public void setTextColor(int color) {
		mTextColot = color;
	}

	public void setTextSize(int size) {
		mTextSize = size;
	}

	public void setPercent(float percent) {
		mFlag = Status.RUNNING;
		mPercent = percent;
		this.percent = (int) (percent * 100);
		postInvalidate();
	}

	public void setStatus(Status status) {
		mFlag = status;
	}

	public void clear() {
		mFlag = Status.NONE;
		if (mScaledBitmap != null) {
			mScaledBitmap.recycle();
			mScaledBitmap = null;
		}
		postInvalidate();
	}

	protected void drawPercent(Canvas canvas, Paint paint) {
		if (mFlag == Status.RUNNING) {
			if (mScaledBitmap == null) {
				Bitmap mBitmap = BitmapFactory.decodeResource(getContext()
						.getResources(), R.drawable.ic_launcher);
				// mScaledBitmap = Bitmap.createScaledBitmap(mBitmap,
				// mBitmap.getWidth(), getHeight(), false);
				mScaledBitmap = Bitmap.createScaledBitmap(mBitmap,
						mBitmap.getWidth(), mBitmap.getHeight(), false);
				mBitmap.recycle();
				mBitmap = null;
				mRepeatCount = (int) Math.ceil(getWidth()
						/ mScaledBitmap.getWidth() + 0.5) + 1;
			}

			for (int idx = 0; idx < mRepeatCount; idx++) {
				canvas.drawBitmap(mScaledBitmap, mLeft + (idx - 1)
						* mScaledBitmap.getWidth(), -mPercent * getHeight(),
						null);
			}
			if (percent <= 100) {
				String str = percent + "%";
				paint.setColor(mTextColot);
				paint.setTextSize(mTextSize);
				canvas.drawText(str, (getWidth() - paint.measureText(str)) / 2,
						getHeight() / 2 + mTextSize / 2, paint);

				mLeft += mSpeed;
				if (mLeft >= mScaledBitmap.getWidth())
					mLeft = 0;
				postInvalidateDelayed(30);
			}

		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	/**
	 * 缁樺埗褰㈢姸
	 * 
	 * @return
	 */
	public Bitmap getBitmap() {
		Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(),
				Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(Color.BLACK);

		if (getWidth() != getHeight()) {
			canvas.drawRoundRect(new RectF(0, 0, getWidth(), getHeight()),
					roundWidth, roundHeight, paint);
		} else {
			canvas.drawCircle(getWidth() / 2, getWidth() / 2, getWidth() / 2,
					paint);
		}

		return bitmap;
	}
}