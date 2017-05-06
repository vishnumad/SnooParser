package xyz.vishnum.snoohtmlparser.blocks;

import android.text.Spannable;

/**
 * Author:  vishnu
 * Created: 5/5/17, 10:29 PM
 * Purpose:
 */

public class TextBlock extends RedditBlock {
    private Spannable text;

    public TextBlock(Spannable text) {
        super(Type.TEXT);
        this.text = text;
    }

    public Spannable getText() {
        return text;
    }
}
