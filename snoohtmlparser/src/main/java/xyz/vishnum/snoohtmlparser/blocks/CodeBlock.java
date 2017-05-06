package xyz.vishnum.snoohtmlparser.blocks;

import android.text.Spannable;

/**
 * Author:  vishnu
 * Created: 5/5/17, 10:30 PM
 * Purpose:
 */

public class CodeBlock extends RedditBlock {
    private Spannable text;

    public CodeBlock(Spannable text) {
        super(Type.CODE);
        this.text = text;
    }

    public Spannable getText() {
        return text;
    }
}
