package pe.com.dms.movilasist.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

import pe.com.dms.movilasist.interfaces.OnItemClickActionListener;

public class ButtonSwipeAction {

    private String text;
    private Bitmap bitmap;
    private int color;
    private int pos;
    private RectF clickRegion;
    private OnItemClickActionListener mListener;

    public ButtonSwipeAction(String text, Bitmap imageResId, int color, OnItemClickActionListener listener) {
        this.text = text;
        this.bitmap = imageResId;
        this.color = color;
        this.mListener = listener;
    }

    public boolean onClick(float x, float y) {
        if (clickRegion != null && clickRegion.contains(x, y)) {
            mListener.onClick(pos);
            return true;
        }

        return false;
    }

    public void onDraw(Canvas c, RectF rect, int pos) {
        Paint p = new Paint();

        // Draw background
        p.setColor(color);
        c.drawRect(rect, p);

        // Draw Text
        p.setColor(Color.WHITE);
        p.setTextSize(40);

        Rect bounds = new Rect();
        float cHeight = rect.height();
        float cWidth = rect.width();
        p.setTextAlign(Paint.Align.LEFT);
        p.getTextBounds(text, 0, text.length(), bounds);
        float x = cWidth / 2f - bounds.width() / 2f - bounds.left;
        float y = cHeight / 2f + bounds.height() / 2f - bounds.bottom;
        c.drawText(text, rect.left + x, rect.top + y, p);

        float spaceHeight = 15; // change to whatever you deem looks better
        float combinedHeight = bitmap.getHeight() + spaceHeight + bounds.height();
        c.drawBitmap(bitmap, rect.centerX() - (bitmap.getWidth() / 2), rect.centerY() - (combinedHeight / 2), null);
        clickRegion = rect;
        this.pos = pos;
    }
}
