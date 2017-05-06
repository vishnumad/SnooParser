package xyz.vishnum.snoohtmlparser.handlers;

import android.text.SpannableStringBuilder;
import net.nightwhistler.htmlspanner.TagNodeHandler;
import org.htmlcleaner.TagNode;

/**
 * Author:  vishnu
 * Created: 5/5/17, 10:49 PM
 * Purpose: Properly formatted list items
 */

public class ListHandler extends TagNodeHandler {
    @Override
    public void handleTagNode(TagNode node, SpannableStringBuilder builder, int start, int end) {
        TagNode parent = node.getParent();
        boolean isNested = parent.getParent().getName().equalsIgnoreCase("li");
        int index = getIndex(node, parent);
        boolean isLastItem = index == parent.getChildTags().length;

        if (parent.getName().equalsIgnoreCase("ul")) {
            // Unordered list - use bullet point
            if (isLastItem && !isNested) builder.insert(end, "\n\n");
            builder.insert(start, "• ");
            if (isNested) builder.insert(start, "     ");
            if (index > 1 || isNested) builder.insert(start, "\n");
        } else if (parent.getName().equalsIgnoreCase("ol")) {
            // Ordered list - use numbers
            String indexString = index + ". ";
            if (isLastItem && !isNested) builder.insert(end, "\n\n");
            builder.insert(start, indexString);
            if (isNested) builder.insert(start, "    ");
            if (index > 1 || isNested) builder.insert(start, "\n");
        }
    }

    private int getIndex(TagNode node, TagNode parentNode) {
        int index = parentNode.getChildIndex(node);
        return index / 2 + 1;
    }
}
