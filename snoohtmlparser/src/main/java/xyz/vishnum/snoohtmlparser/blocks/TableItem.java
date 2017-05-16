package xyz.vishnum.snoohtmlparser.blocks;

import android.text.Spannable;

/**
 * Author:  vishnu
 * Created: 5/15/17, 4:44 PM
 * Purpose:
 */

public class TableItem {

    public Align alignment = Align.LEFT;
    public Spannable text;

    public TableItem(Align alignment, Spannable text) {
        this.alignment = alignment;
        this.text = text;
    }

    public TableItem(Spannable text) {
        this.text = text;
    }

    public enum Align {LEFT, RIGHT, CENTER}
}
