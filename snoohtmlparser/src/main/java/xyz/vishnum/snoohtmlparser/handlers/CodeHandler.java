package xyz.vishnum.snoohtmlparser.handlers;

import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.TypefaceSpan;
import net.nightwhistler.htmlspanner.TagNodeHandler;
import org.htmlcleaner.TagNode;

/**
 * Author:  vishnu
 * Created: 5/5/17, 10:49 PM
 * Purpose:
 */

public class CodeHandler extends TagNodeHandler {
    @Override
    public void handleTagNode(TagNode node, SpannableStringBuilder builder, int start, int end) {
        builder.setSpan(new TypefaceSpan("monospace"), start, end,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }
}
