package xyz.vishnum.snoohtmlparser.blocks;

import java.util.List;

/**
 * Author:  vishnu
 * Created: 5/5/17, 10:31 PM
 * Purpose: Table block
 */

public class TableBlock extends RedditBlock {

    private List<TableItem> headerRow;
    private List<List<TableItem>> bodyRows;

    public TableBlock(List<TableItem> headerRow, List<List<TableItem>> bodyRows) {
        super(Type.TABLE);
        this.headerRow = headerRow;
        this.bodyRows = bodyRows;
    }

    public List<TableItem> getHeaderRow() {
        return headerRow;
    }

    public List<List<TableItem>> getBodyRows() {
        return bodyRows;
    }
}

