package xyz.vishnum.snoohtmlparser.blocks;

/**
 * Author:  vishnu
 * Created: 5/5/17, 10:28 PM
 * Purpose:
 */

public class RedditBlock {
    private Type blockType;

    public RedditBlock(Type blockType) {
        this.blockType = blockType;
    }

    public RedditBlock.Type getBlockType() {
        return blockType;
    }

    public void setBlockType(RedditBlock.Type type) {
        this.blockType = type;
    }

    public enum Type {TEXT, HR, CODE, TABLE}
}
