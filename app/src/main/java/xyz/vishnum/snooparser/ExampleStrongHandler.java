package xyz.vishnum.snooparser;

import android.graphics.Color;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import net.nightwhistler.htmlspanner.TagNodeHandler;
import org.htmlcleaner.TagNode;

/**
 * Author:  vishnu
 * Created: 5/6/17, 1:30 PM
 * Purpose:
 */

public class ExampleStrongHandler extends TagNodeHandler {
    @Override
    public void handleTagNode(TagNode node, SpannableStringBuilder builder, int start, int end) {
        builder.setSpan(new ForegroundColorSpan(Color.BLUE), start, end,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }
}
