package conma.studio.hamburgerbutton;

import android.annotation.TargetApi;
import android.graphics.RectF;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

/**
 * Created by Long Dinh on 17/2/17.
 */

class HBButtonSavedState extends View.BaseSavedState {

	int borderThickness;
	int borderColor;
	int borderCornersRadius;
	int backgroundColor;
	int lineThickness;
	int lineColor;
	int lineCornersRadius;
	int lineWidthPadding;
	int animationDuration;
	boolean slideLeftToRight;
	HBButton.HBButtonState currentState;

	RectF rfBorder;
	RectF rfBackground;
	RectF rfSlider;
	RectF lineCenter;
	RectF lineTop;
	RectF lineBottom;

	HBButtonSavedState(Parcelable superState) {
		super(superState);
	}

	HBButtonSavedState(Parcel in) {
		super(in);
		restoreData(in);
	}

	@TargetApi(Build.VERSION_CODES.N)
	HBButtonSavedState(Parcel in, ClassLoader loader) {
		super(in, loader);
		restoreData(in);
	}

	private void restoreData(Parcel in) {
		borderThickness = in.readInt();
		borderColor = in.readInt();
		borderCornersRadius = in.readInt();
		backgroundColor = in.readInt();
		lineThickness = in.readInt();
		lineColor = in.readInt();
		lineCornersRadius = in.readInt();
		lineWidthPadding = in.readInt();
		animationDuration = in.readInt();
		slideLeftToRight = in.readByte() == 1;
		currentState = HBButton.HBButtonState.values()[in.readInt()];
		if (null == rfBorder) {
			rfBorder = new RectF();
		}
		rfBorder.left = in.readFloat();
		rfBorder.top = in.readFloat();
		rfBorder.right = in.readFloat();
		rfBorder.bottom = in.readFloat();
		if (null == rfBackground) {
			rfBackground = new RectF();
		}
		rfBackground.left = in.readFloat();
		rfBackground.top = in.readFloat();
		rfBackground.right = in.readFloat();
		rfBackground.bottom = in.readFloat();
		if (null == rfSlider) {
			rfSlider = new RectF();
		}
		rfSlider.left = in.readFloat();
		rfSlider.top = in.readFloat();
		rfSlider.right = in.readFloat();
		rfSlider.bottom = in.readFloat();
		if (null == lineCenter) {
			lineCenter = new RectF();
		}
		lineCenter.left = in.readFloat();
		lineCenter.top = in.readFloat();
		lineCenter.right = in.readFloat();
		lineCenter.bottom = in.readFloat();
		if (null == lineTop) {
			lineTop = new RectF();
		}
		lineTop.left = in.readFloat();
		lineTop.top = in.readFloat();
		lineTop.right = in.readFloat();
		lineTop.bottom = in.readFloat();
		if (null == lineBottom) {
			lineBottom = new RectF();
		}
		lineBottom.left = in.readFloat();
		lineBottom.top = in.readFloat();
		lineBottom.right = in.readFloat();
		lineBottom.bottom = in.readFloat();
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		super.writeToParcel(out, flags);
		out.writeInt(borderThickness);
		out.writeInt(borderColor);
		out.writeInt(borderCornersRadius);
		out.writeInt(backgroundColor);
		out.writeInt(lineThickness);
		out.writeInt(lineColor);
		out.writeInt(lineCornersRadius);
		out.writeInt(lineWidthPadding);
		out.writeInt(animationDuration);
		out.writeByte((byte) (slideLeftToRight ? 1 : 0));
		out.writeInt(currentState.ordinal());
		out.writeFloat(rfBorder.left);
		out.writeFloat(rfBorder.top);
		out.writeFloat(rfBorder.right);
		out.writeFloat(rfBorder.bottom);
		out.writeFloat(rfBackground.left);
		out.writeFloat(rfBackground.top);
		out.writeFloat(rfBackground.right);
		out.writeFloat(rfBackground.bottom);
		out.writeFloat(rfSlider.left);
		out.writeFloat(rfSlider.top);
		out.writeFloat(rfSlider.right);
		out.writeFloat(rfSlider.bottom);
		out.writeFloat(lineCenter.left);
		out.writeFloat(lineCenter.top);
		out.writeFloat(lineCenter.right);
		out.writeFloat(lineCenter.bottom);
		out.writeFloat(lineTop.left);
		out.writeFloat(lineTop.top);
		out.writeFloat(lineTop.right);
		out.writeFloat(lineTop.bottom);
		out.writeFloat(lineBottom.left);
		out.writeFloat(lineBottom.top);
		out.writeFloat(lineBottom.right);
		out.writeFloat(lineBottom.bottom);
	}

	public static final Parcelable.Creator<HBButtonSavedState> CREATOR
			= new Parcelable.Creator<HBButtonSavedState>() {
		public HBButtonSavedState createFromParcel(Parcel in) {
			return new HBButtonSavedState(in);
		}

		public HBButtonSavedState[] newArray(int size) {
			return new HBButtonSavedState[size];
		}
	};
}
