package xyz.vishnum.snoohtmlparser.handlers;

import android.graphics.Color;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import net.nightwhistler.htmlspanner.TagNodeHandler;
import org.htmlcleaner.TagNode;
import xyz.vishnum.snoohtmlparser.spans.BlockquoteSpan;

/**
 * Author:  vishnu
 * Created: 5/5/17, 10:46 PM
 * Purpose:
 */

public class BlockquoteHandler extends TagNodeHandler {
    private int backgroundColor = Color.TRANSPARENT;
    private int barColor = Color.BLUE;
    private int barWidth = 6 /*px*/;
    private int gap = 16 /*px*/;
    private int padding = 4 /*px*/;

    public BlockquoteHandler() {
    }

    public BlockquoteHandler(int backgroundColor, int barColor, int barWidth, int gap,
            int padding) {
        this.backgroundColor = backgroundColor;
        this.barColor = barColor;
        this.barWidth = barWidth;
        this.gap = gap;
        this.padding = padding;
    }

    @Override
    public void handleTagNode(TagNode node, SpannableStringBuilder builder, int start, int end) {
        int last = end;
        if (start < end) last--;
        builder.setSpan(new BlockquoteSpan(backgroundColor, barColor, barWidth, gap, padding),
                start, last, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }
}
