package conma.studio.hamburgerbutton;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.*;
import android.os.Build;
import android.os.Parcelable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.Transformation;
import android.widget.ImageButton;

/**
 * Created by Long Dinh on 13/2/17.
 */

public class HBButton extends ImageButton {

	public HBButtonState getCurrentState() {
		return mCurrentState;
	}

	public int getBorderThickness() {
		return mBorderThickness;
	}

	public void setBorderThickness(int borderThickness) {
		if (this.mBorderThickness == borderThickness)
			return;
		this.mBorderThickness = borderThickness;
		if (null == mPaintBorder) {
			mPaintBorder = new Paint(Paint.ANTI_ALIAS_FLAG);
		}
		mPaintBorder.setStrokeWidth(mBorderThickness);
		verifyingRefreshUI();
	}

	public int getBorderColor() {
		return mBorderColor;
	}

	public void setBorderColor(int borderColor) {
		if (this.mBorderColor == borderColor)
			return;
		this.mBorderColor = borderColor;
		if (null == mPaintBorder) {
			mPaintBorder = new Paint(Paint.ANTI_ALIAS_FLAG);
		}
		mPaintBorder.setColor(borderColor);
		verifyingRefreshUI();
	}

	public int getBorderCornersRadius() {
		return mBorderCornersRadius;
	}

	public void setBorderCornersRadius(int borderCornersRadius) {
		if (this.mBorderCornersRadius == borderCornersRadius)
			return;
		this.mBorderCornersRadius = borderCornersRadius;
		verifyingRefreshUI();
	}

	public int getBackgroundColor() {
		return mBackgroundColor;
	}

	public void setBackgroundColor(int backgroundColor) {
		if (this.mBackgroundColor == backgroundColor)
			return;
		this.mBackgroundColor = backgroundColor;
		if (null == mPaintBackground) {
			mPaintBackground = new Paint(Paint.ANTI_ALIAS_FLAG);
		}
		mPaintBackground.setColor(backgroundColor);
		verifyingRefreshUI();
	}

	public int getLineThickness() {
		return mLineThickness;
	}

	public void setLineThickness(int lineThickness) {
		if (mLineThickness == lineThickness)
			return;
		this.mLineThickness = lineThickness;
		verifyingRefreshUI();
	}

	public int getLineColor() {
		return mLineColor;
	}

	public void setLineColor(int lineColor) {
		if (this.mLineColor == lineColor)
			return;
		this.mLineColor = lineColor;
		if (null == mPaintLine) {
			mPaintLine = new Paint(Paint.ANTI_ALIAS_FLAG);
		}
		mPaintLine.setColor(lineColor);
		verifyingRefreshUI();
	}

	public int getLineCornersRadius() {
		return mLineCornersRadius;
	}

	public void setLineCornersRadius(int lineCornersRadius) {
		if (this.mLineCornersRadius == lineCornersRadius)
			return;
		this.mLineCornersRadius = lineCornersRadius;
		verifyingRefreshUI();
	}

	public int getLineWidthPadding() {
		return mLineWidthPadding;
	}

	public void setLineWidthPadding(int lineWidthPadding) {
		if (this.mLineWidthPadding == lineWidthPadding) {
			return;
		}
		this.mLineWidthPadding = lineWidthPadding;
		verifyingRefreshUI();
	}

	public int getAnimationDuration() {
		return mAnimationDuration;
	}

	public void setAnimationDuration(int animationDuration) {
		this.mAnimationDuration = animationDuration;
	}

	public boolean isSlideLeftToRight() {
		return mSlideLeftToRight;
	}

	public void setSlideLeftToRight(boolean slideLeftToRight) {
		if (this.mSlideLeftToRight == slideLeftToRight)
			return;
		if (getCurrentState() == HBButtonState.ANIMATED && mCanRunAnimationOnDirectionChange) {
			mCanRunAnimationOnDirectionChange = false;
			mCanInvalidate = 0;
			animateChangeDirection();
		} else {
			mSlideLeftToRight = !mSlideLeftToRight;
			verifyingRefreshUI();
		}
	}

	private void verifyingRefreshUI() {
		validateInputValues();
		mIsForceInvalidate = true;
		if (mCanInvalidate == 1) {
			invalidate();
			requestLayout();
		}
	}

	public enum HBButtonState {
		NORMAL,
		ANIMATED
	}

	private final float DPTOPX_SCALE = getResources().getDisplayMetrics().density;

	private static final int MIN_SIZE = 50;
	private static int BORDER_THICKNESS = 10;
	private static int BORDER_COLOR = Color.BLACK;
	private static int BORDER_CORNERS_RADIUS = 10;
	private static int BACKGROUND_COLOR = Color.parseColor("#f8888b");
	private static int LINE_THICKNESS = 8;
	private static int LINE_COLOR = Color.BLUE;
	private static int LINE_CORNERS_RADIUS = 15;
	private static int LINE_WIDTH_PADDING = 10;
	private static int ANIMATION_DURATION = 300;
	private static boolean SLIDE_LEFT_TO_RIGHT = true;
	private static int OFFSET_FIRST = 30;

	private int mBorderThickness;
	private int mBorderColor;
	private int mBorderCornersRadius;
	private int mBackgroundColor;
	private int mLineThickness;
	private int mLineColor;
	private int mLineCornersRadius;
	private int mLineWidthPadding;
	private int mAnimationDuration;
	private boolean mSlideLeftToRight;
	private HBButtonState mCurrentState;

	private int mCanInvalidate;
	private boolean mCanRunAnimationOnDirectionChange;

	private Paint mPaintBorder;
	private Paint mPaintBackground;
	private Paint mPaintSlider;
	private Paint mPaintLine;
	private RectF mRFBorder;
	private RectF mRFBackground;
	private RectF mRFSlider;
	private RectF mLineCenter;
	private RectF mLineTop;
	private RectF mLineBottom;
	private Path mClipPath;

	private int mWidth, mHeight;
	private boolean mIsForceInvalidate;

	public HBButton(Context context) {
		super(context);
	}

	public HBButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}

	public HBButton(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context, attrs);
	}

	@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
	public HBButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		init(context, attrs);
	}

	private void init(Context context, AttributeSet attrs) {
		setSaveEnabled(true);
		setBackground(null);
		TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.HBButton);

		try {
			mBorderThickness = typedArray.getInt(R.styleable.HBButton_border_thickness, BORDER_THICKNESS);
			mBorderColor = typedArray.getColor(R.styleable.HBButton_border_color, BORDER_COLOR);
			mBorderCornersRadius = typedArray.getInt(R.styleable.HBButton_border_corners_radius, BORDER_CORNERS_RADIUS);
			mBackgroundColor = typedArray.getColor(R.styleable.HBButton_background_color, BACKGROUND_COLOR);
			mLineThickness = typedArray.getInt(R.styleable.HBButton_line_thickness, LINE_THICKNESS);
			mLineColor = typedArray.getColor(R.styleable.HBButton_line_color, LINE_COLOR);
			mLineCornersRadius = typedArray.getInt(R.styleable.HBButton_line_corners_radius, LINE_CORNERS_RADIUS);
			mLineWidthPadding = typedArray.getInt(R.styleable.HBButton_line_width_padding, LINE_WIDTH_PADDING);
			mLineWidthPadding = mLineWidthPadding > LINE_WIDTH_PADDING + 5 ? LINE_WIDTH_PADDING + 5 : mLineWidthPadding;
			mAnimationDuration = typedArray.getInt(R.styleable.HBButton_animation_duration, ANIMATION_DURATION);
			mSlideLeftToRight = typedArray.getBoolean(R.styleable.HBButton_slide_left_to_right, SLIDE_LEFT_TO_RIGHT);
		} finally {
			typedArray.recycle();
		}
		mCanInvalidate = 1;
		mCurrentState = HBButtonState.NORMAL;
		mPaintBorder = new Paint(Paint.ANTI_ALIAS_FLAG);
		mPaintBorder.setColor(getBorderColor());
		mPaintBackground = new Paint(Paint.ANTI_ALIAS_FLAG);
		mPaintBackground.setColor(getBackgroundColor());
		mPaintSlider = new Paint(Paint.ANTI_ALIAS_FLAG);
		mPaintSlider.setColor(Color.parseColor("#40000000"));
		mPaintLine = new Paint(Paint.ANTI_ALIAS_FLAG);
		mPaintLine.setColor(getLineColor());
		mIsForceInvalidate = false;
		mCanRunAnimationOnDirectionChange = true;
		validateInputValues();
	}

	private void validateInputValues() {
		if (mBorderThickness < 0) {
			throw new IllegalArgumentException("Border thickness cannot be negative");
		}
		if (mBorderCornersRadius < 0) {
			throw new IllegalArgumentException("Border corner radius cannot be negative");
		}
		if (mLineThickness < 0) {
			throw new IllegalArgumentException("Line thickness cannot be negative");
		}
		if (mLineCornersRadius < 0) {
			throw new IllegalArgumentException("Line corner radius cannot be negative");
		}
		if (mLineWidthPadding < 0) {
			throw new IllegalArgumentException("Line padding cannot be negative");
		}
		if (mAnimationDuration < 0) {
			throw new IllegalArgumentException("Animation duration cannot be negative");
		}
	}

	@Override
	public Parcelable onSaveInstanceState() {
		Parcelable superState = super.onSaveInstanceState();
		HBButtonSavedState hbButtonSavedState = new HBButtonSavedState(superState);
		hbButtonSavedState.borderThickness = getBorderThickness();
		hbButtonSavedState.borderColor = getBorderColor();
		hbButtonSavedState.borderCornersRadius = getBorderCornersRadius();
		hbButtonSavedState.backgroundColor = getBackgroundColor();
		hbButtonSavedState.lineThickness = getLineThickness();
		hbButtonSavedState.lineColor = getLineColor();
		hbButtonSavedState.lineCornersRadius = getLineCornersRadius();
		hbButtonSavedState.animationDuration = getAnimationDuration();
		hbButtonSavedState.lineWidthPadding = getLineWidthPadding();
		hbButtonSavedState.slideLeftToRight = isSlideLeftToRight();
		hbButtonSavedState.currentState = getCurrentState();
		hbButtonSavedState.rfBorder = mRFBorder;
		hbButtonSavedState.rfBackground = mRFBackground;
		hbButtonSavedState.rfSlider = mRFSlider;
		hbButtonSavedState.lineCenter = mLineCenter;
		hbButtonSavedState.lineTop = mLineTop;
		hbButtonSavedState.lineBottom = mLineBottom;
		return hbButtonSavedState;
	}

	@Override
	public void onRestoreInstanceState(Parcelable state) {
		mCanInvalidate = 0;
		HBButtonSavedState hbButtonSavedState = (HBButtonSavedState) state;
		super.onRestoreInstanceState(hbButtonSavedState.getSuperState());
		mBorderThickness = hbButtonSavedState.borderThickness;
		mBorderColor = hbButtonSavedState.borderColor;
		mBorderCornersRadius = hbButtonSavedState.borderCornersRadius;
		mBackgroundColor = hbButtonSavedState.backgroundColor;
		mLineThickness = hbButtonSavedState.lineThickness;
		mLineColor = hbButtonSavedState.lineColor;
		mLineCornersRadius = hbButtonSavedState.lineCornersRadius;
		mLineWidthPadding = hbButtonSavedState.lineWidthPadding;
		mAnimationDuration = hbButtonSavedState.animationDuration;
		mSlideLeftToRight = hbButtonSavedState.slideLeftToRight;
		mCurrentState = hbButtonSavedState.currentState;
		mRFBorder = hbButtonSavedState.rfBorder;
		mRFBackground = hbButtonSavedState.rfBackground;
		mRFSlider = hbButtonSavedState.rfSlider;
		mLineCenter = hbButtonSavedState.lineCenter;
		mLineTop = hbButtonSavedState.lineTop;
		mLineBottom = hbButtonSavedState.lineBottom;
		mCanInvalidate = 1;
	}

	@Override
	public boolean performClick() {
		if (isSlideLeftToRight()) {
			animateLeftToRight();
		} else {
			animateRightToLeft();
		}
		return super.performClick();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		int heightSize = MeasureSpec.getSize(heightMeasureSpec);

		if (widthMode == MeasureSpec.EXACTLY)
			mWidth = widthSize;
		else if (widthMode == MeasureSpec.AT_MOST)
			mWidth = (int) Math.min(MIN_SIZE * DPTOPX_SCALE, widthSize);
		else
			mWidth = widthSize;

		if (heightMode == MeasureSpec.EXACTLY)
			mHeight = heightSize;
		else if (heightMode == MeasureSpec.AT_MOST)
			mHeight = (int) Math.min(MIN_SIZE * DPTOPX_SCALE, heightSize);
		else
			mHeight = heightSize;

		initComponentsDimen();

		setMeasuredDimension(mWidth, mHeight);
	}

	private void initComponentsDimen() {
		if (null == mRFBorder || mIsForceInvalidate)
			mRFBorder = new RectF(0, 0, mWidth, mHeight);
		if (null == mRFBackground || mIsForceInvalidate)
			mRFBackground = new RectF(getBorderThickness() / 2, getBorderThickness() / 2, mWidth - getBorderThickness() / 2, mHeight - getBorderThickness() / 2);
		if (null == mRFSlider || mIsForceInvalidate)
			mRFSlider = isSlideLeftToRight() ? new RectF(0, 0, 0, mHeight) : new RectF(mWidth, 0, mWidth, mHeight);
		if (null == mLineCenter || mIsForceInvalidate)
			mLineCenter = new RectF(mWidth / 6 + getLineWidthPadding(), (mWidth - mLineThickness) / 2, mWidth * 5 / 6 - getLineWidthPadding(), (mWidth + mLineThickness) / 2);
		if (null == mLineTop || mIsForceInvalidate)
			mLineTop = new RectF(mWidth / 6 + getLineWidthPadding(), mHeight / 3 - mLineThickness, mWidth * 5 / 6 - getLineWidthPadding(), mHeight / 3);
		if (null == mLineBottom || mIsForceInvalidate)
			mLineBottom = new RectF(mWidth / 6 + getLineWidthPadding(), mHeight * 2 / 3, mWidth * 5 / 6 - getLineWidthPadding(), mHeight * 2 / 3 + mLineThickness);

		mClipPath = new Path();
		//if (isSlideLeftToRight()) {
		mClipPath.addRoundRect(mRFBackground, getBorderCornersRadius(), getBorderCornersRadius(), Path.Direction.CW);
		//} else {
		//	mClipPath.addRoundRect(mRFBorder, getBorderCornersRadius(), getBorderCornersRadius(), Path.Direction.CCW);
		//}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawRoundRect(mRFBorder, getBorderCornersRadius(), getBorderCornersRadius(), mPaintBorder);
		canvas.drawRoundRect(mRFBackground, getBorderCornersRadius(), getBorderCornersRadius(), mPaintBackground);
		canvas.save();
		canvas.clipPath(mClipPath);
		canvas.drawRect(mRFSlider, mPaintSlider);
		canvas.drawRoundRect(mLineTop, getLineCornersRadius(), getLineCornersRadius(), mPaintLine);
		canvas.drawRoundRect(mLineCenter, getLineCornersRadius(), getLineCornersRadius(), mPaintLine);
		canvas.drawRoundRect(mLineBottom, getLineCornersRadius(), getLineCornersRadius(), mPaintLine);
		canvas.restore();
	}

	private void animateLeftToRight() {

		setClickable(false);

		final float finalRight;
		final float origRight;

		if (getCurrentState() == HBButtonState.NORMAL) {
			origRight = mRFSlider.right;
			finalRight = mRFSlider.right + mWidth / 3 * 2;
		} else {
			origRight = mRFSlider.right;
			finalRight = mRFSlider.right;
		}

		Animation animation = new Animation() {
			@Override
			protected void applyTransformation(float interpolatedTime, Transformation t) {
				super.applyTransformation(interpolatedTime, t);
				if (getCurrentState() == HBButtonState.NORMAL) {
					float currentPos = finalRight * interpolatedTime;
					if (currentPos < origRight) {
						currentPos = origRight;
					}
					mRFSlider.set(mRFSlider.left, mRFSlider.top, currentPos, mRFSlider.bottom);
				} else {
					mRFSlider.set(mRFSlider.left, mRFSlider.top, finalRight - (finalRight * interpolatedTime), mRFSlider.bottom);
				}
				invalidate();
			}
		};

		animation.setDuration(getAnimationDuration() + OFFSET_FIRST * 3);
		animation.setAnimationListener(stateChangeListener);

		Animation animationLineTop = generateLineLTRAnimation(mLineTop, 0);
		Animation animationLineCenter = generateLineLTRAnimation(mLineCenter, OFFSET_FIRST);
		Animation animationLineBottom = generateLineLTRAnimation(mLineBottom, OFFSET_FIRST * 2);
		AnimationSet animationSet = new AnimationSet(false);
		animationSet.addAnimation(animation);
		animationSet.addAnimation(animationLineTop);
		animationSet.addAnimation(animationLineCenter);
		animationSet.addAnimation(animationLineBottom);
		startAnimation(animationSet);
	}

	private Animation generateLineLTRAnimation(final RectF rectF, long offset) {

		final float currentLeft = rectF.left;
		final float currentRight = rectF.right;

		Animation animation = new Animation() {
			@Override
			protected void applyTransformation(float interpolatedTime, Transformation t) {
				super.applyTransformation(interpolatedTime, t);
				if (getCurrentState() == HBButtonState.NORMAL) {
					rectF.set(rectF.left, rectF.top, currentRight - (currentRight - currentLeft) / 2 * interpolatedTime, rectF.bottom);
				} else {
					rectF.set(rectF.left, rectF.top, currentRight + (currentRight - currentLeft) * interpolatedTime, rectF.bottom);
				}
				invalidate();
			}
		};
		animation.setDuration(getAnimationDuration());
		animation.setStartOffset(offset);
		return animation;
	}

	private void animateRightToLeft() {

		setClickable(false);

		final float finalLeft;
		final float origLeft;

		if (getCurrentState() == HBButtonState.NORMAL) {
			origLeft = mRFSlider.left;
			finalLeft = mRFSlider.left - mWidth / 3 * 2;
		} else {
			origLeft = mRFSlider.left;
			finalLeft = mRFSlider.left + mWidth / 3 * 2;
		}

		Animation animation = new Animation() {
			@Override
			protected void applyTransformation(float interpolatedTime, Transformation t) {
				super.applyTransformation(interpolatedTime, t);
				if (getCurrentState() == HBButtonState.NORMAL) {
					mRFSlider.set(origLeft - (origLeft - finalLeft) * interpolatedTime, mRFSlider.top, mRFSlider.right, mRFSlider.bottom);
				} else {
					mRFSlider.set(origLeft + (finalLeft - origLeft) * interpolatedTime, mRFSlider.top, mRFSlider.right, mRFSlider.bottom);
				}
				invalidate();
			}
		};

		animation.setDuration(getAnimationDuration() + OFFSET_FIRST * 3);
		animation.setAnimationListener(stateChangeListener);

		Animation animationLineTop = generateLineRTLAnimation(mLineTop, 0);
		Animation animationLineCenter = generateLineRTLAnimation(mLineCenter, OFFSET_FIRST);
		Animation animationLineBottom = generateLineRTLAnimation(mLineBottom, OFFSET_FIRST * 2);
		AnimationSet animationSet = new AnimationSet(false);
		animationSet.addAnimation(animation);
		animationSet.addAnimation(animationLineTop);
		animationSet.addAnimation(animationLineCenter);
		animationSet.addAnimation(animationLineBottom);
		startAnimation(animationSet);
	}

	private Animation generateLineRTLAnimation(final RectF rectF, long offset) {

		final float currentLeft = rectF.left;
		final float currentRight = rectF.right;

		Animation animation = new Animation() {
			@Override
			protected void applyTransformation(float interpolatedTime, Transformation t) {
				super.applyTransformation(interpolatedTime, t);
				if (getCurrentState() == HBButtonState.NORMAL) {
					rectF.set(currentLeft + (currentRight - currentLeft) / 2 * interpolatedTime, rectF.top, rectF.right, rectF.bottom);
				} else {
					rectF.set(currentLeft - (currentRight - currentLeft) * interpolatedTime, rectF.top, rectF.right, rectF.bottom);
				}
				invalidate();
			}
		};
		animation.setDuration(getAnimationDuration());
		animation.setStartOffset(offset);
		return animation;
	}

	private void animateChangeDirection() {

		setClickable(false);
		final float origLeft;
		final float finalLeft;
		final float origRight;
		final float finalRight;
		final float rangeOfLeft;
		final float rangeOfRight;

		if (isSlideLeftToRight()) {

			origRight = mRFSlider.right;
			finalRight = mWidth;

			rangeOfRight = finalRight - origRight;
			rangeOfLeft = rangeOfRight + mWidth / 3;

			mRFSlider.left -= rangeOfRight;
			origLeft = mRFSlider.left;
		} else {
			origLeft = mRFSlider.left;
			finalLeft = 0;

			rangeOfLeft = finalLeft - origLeft;
			rangeOfRight = rangeOfLeft - mWidth / 3;

			mRFSlider.right += -rangeOfLeft;
			origRight = mRFSlider.right;
		}

		Animation animation = new Animation() {
			@Override
			protected void applyTransformation(float interpolatedTime, Transformation t) {
				super.applyTransformation(interpolatedTime, t);
				mRFSlider.set(origLeft + rangeOfLeft * interpolatedTime, mRFSlider.top, origRight + rangeOfRight * interpolatedTime, mRFSlider.bottom);
				invalidate();
			}
		};

		animation.setDuration(getAnimationDuration() / 2);

		AnimationSet animationSet = new AnimationSet(true);
		animationSet.addAnimation(animation);
		animationSet.setInterpolator(new AccelerateDecelerateInterpolator());

		Animation animationLineTop1;
		Animation animationLineCenter1;
		Animation animationLineBottom1;

		Animation animationLineTop2;
		Animation animationLineCenter2;
		Animation animationLineBottom2;

		if (isSlideLeftToRight()) {
			animationLineTop1 = generateLineBackLTRStep1(mLineTop);
			animationLineCenter1 = generateLineBackLTRStep1(mLineCenter);
			animationLineBottom1 = generateLineBackLTRStep1(mLineBottom);

			animationLineTop2 = generateLineBackLTRStep2(mLineTop);
			animationLineCenter2 = generateLineBackLTRStep2(mLineCenter);
			animationLineBottom2 = generateLineBackLTRStep2(mLineBottom);
		} else {
			animationLineTop1 = generateLineBackRTLStep1(mLineTop);
			animationLineCenter1 = generateLineBackRTLStep1(mLineCenter);
			animationLineBottom1 = generateLineBackRTLStep1(mLineBottom);

			animationLineTop2 = generateLineBackRTLStep2(mLineTop);
			animationLineCenter2 = generateLineBackRTLStep2(mLineCenter);
			animationLineBottom2 = generateLineBackRTLStep2(mLineBottom);
		}

		animationSet.addAnimation(animationLineTop1);
		animationSet.addAnimation(animationLineCenter1);
		animationSet.addAnimation(animationLineBottom1);

		animationSet.addAnimation(animationLineTop2);
		animationSet.addAnimation(animationLineCenter2);
		animationSet.addAnimation(animationLineBottom2);

		animationSet.setAnimationListener(directionChangeListener);

		startAnimation(animationSet);
	}

	private Animation generateLineBackLTRStep1(final RectF rectF) {
		final float currentLeft = rectF.left;
		final float currentRight = rectF.right;

		Animation animation = new Animation() {
			@Override
			protected void applyTransformation(float interpolatedTime, Transformation t) {
				super.applyTransformation(interpolatedTime, t);
				rectF.set(rectF.left, rectF.top, currentRight + (currentRight - currentLeft) * interpolatedTime, rectF.bottom);
				invalidate();
			}
		};
		animation.setDuration(getAnimationDuration() / 4);
		return animation;
	}

	private Animation generateLineBackLTRStep2(final RectF rectF) {
		final float currentLeft = rectF.left;
		final float currentRight = rectF.right;

		Animation animation = new Animation() {
			@Override
			protected void applyTransformation(float interpolatedTime, Transformation t) {
				super.applyTransformation(interpolatedTime, t);
				rectF.set(currentLeft + (currentRight - currentLeft) * interpolatedTime, rectF.top, rectF.right, rectF.bottom);
				invalidate();
			}
		};
		animation.setDuration(getAnimationDuration() / 4);
		return animation;
	}

	private Animation generateLineBackRTLStep1(final RectF rectF) {
		final float currentLeft = rectF.left;
		final float currentRight = rectF.right;

		Animation animation = new Animation() {
			@Override
			protected void applyTransformation(float interpolatedTime, Transformation t) {
				super.applyTransformation(interpolatedTime, t);
				rectF.set(currentLeft - (currentRight - currentLeft) * interpolatedTime, rectF.top, rectF.right, rectF.bottom);
				invalidate();
			}
		};
		animation.setDuration(getAnimationDuration() / 4);
		return animation;
	}

	private Animation generateLineBackRTLStep2(final RectF rectF) {
		final float currentLeft = rectF.left;
		final float currentRight = rectF.right;

		Animation animation = new Animation() {
			@Override
			protected void applyTransformation(float interpolatedTime, Transformation t) {
				super.applyTransformation(interpolatedTime, t);
				rectF.set(rectF.left, rectF.top, currentRight - (currentRight - currentLeft) * interpolatedTime, rectF.bottom);
				invalidate();
			}
		};
		animation.setDuration(getAnimationDuration() / 4);
		return animation;
	}

	private Animation.AnimationListener stateChangeListener = new Animation.AnimationListener() {
		@Override
		public void onAnimationStart(Animation animation) {

		}

		@Override
		public void onAnimationEnd(Animation animation) {
			postDelayed(new Runnable() {
				@Override
				public void run() {
					clearAnimation();
					if (getCurrentState() == HBButtonState.NORMAL) {
						mCurrentState = HBButtonState.ANIMATED;
					} else {
						mCurrentState = HBButtonState.NORMAL;
					}
					setClickable(true);
				}
			}, 100);
		}

		@Override
		public void onAnimationRepeat(Animation animation) {

		}
	};

	private Animation.AnimationListener directionChangeListener = new Animation.AnimationListener() {
		@Override
		public void onAnimationStart(Animation animation) {

		}

		@Override
		public void onAnimationEnd(Animation animation) {
			postDelayed(new Runnable() {
				@Override
				public void run() {
					mCanRunAnimationOnDirectionChange = true;
					mCanInvalidate = 1;
					mSlideLeftToRight = !mSlideLeftToRight;
					setClickable(true);
				}
			}, 100);
		}

		@Override
		public void onAnimationRepeat(Animation animation) {

		}
	};
}
