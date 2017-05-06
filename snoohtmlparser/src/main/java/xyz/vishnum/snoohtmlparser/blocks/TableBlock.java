package xyz.vishnum.snoohtmlparser.blocks;

/**
 * Author:  vishnu
 * Created: 5/5/17, 10:31 PM
 * Purpose:
 */

public class TableBlock extends RedditBlock {

    private String[] headerRow;
    private String[][] bodyRows;

    public TableBlock(String[] headerRow, String[][] bodyRows) {
        super(Type.TABLE);
        this.headerRow = headerRow;
        this.bodyRows = bodyRows;
    }

    public String[] getHeaderRow() {
        return headerRow;
    }

    public String[][] getBodyRows() {
        return bodyRows;
    }
}

