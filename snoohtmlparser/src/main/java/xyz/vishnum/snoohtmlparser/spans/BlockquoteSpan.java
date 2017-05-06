package xyz.vishnum.snoohtmlparser.spans;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.Layout;
import android.text.style.LeadingMarginSpan;
import android.text.style.LineBackgroundSpan;

/**
 * Author:  vishnu
 * Created: 5/5/17, 10:47 PM
 * Purpose:
 */

public class BlockquoteSpan implements LeadingMarginSpan, LineBackgroundSpan {
    private final int backgroundColor;
    private final int stripeColor;
    private final float stripeWidth;
    private final float gap;
    private final int padding;

    public BlockquoteSpan(int backgroundColor, int stripeColor, float stripeWidth, float gap,
            int padding) {
        this.backgroundColor = backgroundColor;
        this.stripeColor = stripeColor;
        this.stripeWidth = stripeWidth;
        this.gap = gap;
        this.padding = padding;
    }

    @Override
    public int getLeadingMargin(boolean first) {
        return (int) (stripeWidth + gap);
    }

    @Override
    public void drawLeadingMargin(Canvas c, Paint p, int x, int dir, int top, int baseline,
            int bottom, CharSequence text, int start, int end, boolean first, Layout layout) {
        Paint.Style style = p.getStyle();
        int paintColor = p.getColor();

        p.setStyle(Paint.Style.FILL);
        p.setColor(stripeColor);

        c.drawRect(x, top - padding, x + dir * stripeWidth, bottom + padding, p);

        p.setStyle(style);
        p.setColor(paintColor);
    }

    @Override
    public void drawBackground(Canvas c, Paint p, int left, int right, int top, int baseline,
            int bottom, CharSequence text, int start, int end, int lnum) {
        int paintColor = p.getColor();
        p.setColor(backgroundColor);
        c.drawRect(left - padding, top - padding, right + padding, bottom + padding, p);
        p.setColor(paintColor);
    }
}
