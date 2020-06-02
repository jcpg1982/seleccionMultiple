package pe.com.dms.movilasist.util.drawables;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;

import androidx.annotation.ColorInt;

import pe.com.dms.movilasist.R;

public class TextDrawable extends Drawable {

    private Paint mBadgePaint;
    private TextPaint mTextPaint;
    private Rect mTxtRect = new Rect();
    private int mBackgroundColor;
    private int mTextColor;
    private float mTextSize;

    private String mText = "";
    private boolean mWillDraw = false;

    public TextDrawable(Context context) {
        this(context, Color.CYAN, Color.WHITE);
    }

    public TextDrawable(Context context, @ColorInt int backgroundColor, @ColorInt int textColor) {
        this(context, backgroundColor, null, textColor,
                context.getResources().getDimension(R.dimen.static_display_text_size));
    }

    public TextDrawable(Context context, @ColorInt int backgroundColor, String text,
                        @ColorInt int textColor, float textSize) {
        mBackgroundColor = backgroundColor;
        mTextColor = textColor;
        mText = text;
        mWillDraw = text != null;

        mTextSize = textSize;

        mBadgePaint = new Paint();
        mBadgePaint.setColor(mBackgroundColor);
        mBadgePaint.setAntiAlias(true);
        mBadgePaint.setStyle(Paint.Style.FILL);

        mTextPaint = new TextPaint();
        mTextPaint.setColor(textColor);
       /* mTextPaint.setTypeface(FontUtils.getTypeFace(context,
                FontUtils.getFontRes(CustomFontEnum.POPPING_MEDIUM)));*/
        mTextPaint.setTextSize(textSize);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    public void draw(Canvas canvas) {
        if (!mWillDraw) {
            return;
        }

        Rect bounds = getBounds();
        float width = bounds.right - bounds.left;
        float height = bounds.bottom - bounds.top;

        float radius = ((Math.min(width, height) / 2) - 1);
        float centerX = width - radius - 1;
        float centerY = radius + 1;

        canvas.drawCircle(centerX, centerY, radius, mBadgePaint);

        mTextPaint.getTextBounds(mText.toUpperCase(), 0, mText.length(), mTxtRect);
        float textHeight = mTxtRect.bottom - mTxtRect.top;
        float textY = centerY + (textHeight / 2f);
        canvas.drawText(mText.toUpperCase(), centerX, textY, mTextPaint);
    }

    @Override
    public void setAlpha(final int alpha) {
        mBadgePaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(final ColorFilter cf) {
        mBadgePaint.setColorFilter(cf);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    public void setBackgroundColor(@ColorInt int color) {
        mBackgroundColor = color;
        mBadgePaint.setColor(color);
        invalidateSelf();
    }

    public void setTextColor(@ColorInt int color) {
        mTextColor = color;
        mTextPaint.setColor(color);
        invalidateSelf();
    }

    public void setTextSize(float textSize) {
        mTextSize = textSize;
        mTextPaint.setTextSize(textSize);
        invalidateSelf();
    }

    public void setText(String text) {
        mText = text;
        mWillDraw = text != null;
        invalidateSelf();
    }
}