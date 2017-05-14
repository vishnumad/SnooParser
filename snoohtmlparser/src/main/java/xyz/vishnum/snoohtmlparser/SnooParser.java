package xyz.vishnum.snoohtmlparser;

import android.text.Spannable;
import android.text.Spanned;
import java.util.ArrayList;
import java.util.List;
import net.nightwhistler.htmlspanner.HtmlSpanner;
import net.nightwhistler.htmlspanner.TagNodeHandler;
import net.nightwhistler.htmlspanner.handlers.BoldHandler;
import net.nightwhistler.htmlspanner.handlers.ItalicHandler;
import net.nightwhistler.htmlspanner.handlers.PreHandler;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;
import xyz.vishnum.snoohtmlparser.blocks.CodeBlock;
import xyz.vishnum.snoohtmlparser.blocks.HrBlock;
import xyz.vishnum.snoohtmlparser.blocks.RedditBlock;
import xyz.vishnum.snoohtmlparser.blocks.TableBlock;
import xyz.vishnum.snoohtmlparser.blocks.TextBlock;
import xyz.vishnum.snoohtmlparser.handlers.BlockquoteHandler;
import xyz.vishnum.snoohtmlparser.handlers.CodeHandler;
import xyz.vishnum.snoohtmlparser.handlers.ListHandler;
import xyz.vishnum.snoohtmlparser.handlers.StrikethroughHandler;

/**
 * Author:  vishnu
 * Created: 5/5/17, 10:27 PM
 * Purpose:
 */

public class SnooParser {
    private static final String TAG = SnooParser.class.getSimpleName();
    private HtmlSpanner spanner;

    public SnooParser() {
        spanner = new HtmlSpanner();
        // Unregister incorrectly formatted handlers
        unregisterHandlers();
        // Register custom handlers for Reddit formatting
        registerNewHandlers();
    }

    /**
     * Replace the default handler for a tag with a custom handler that extends TagNodeHandler
     *
     * @param tag An HTML tag
     * @param handler A handler that decides how the specified tag is formatted
     */
    public void replaceHandler(String tag, TagNodeHandler handler) {
        spanner.unregisterHandler(tag);
        spanner.registerHandler(tag, handler);
    }

    /**
     * @param escapedHtml The body HTML from a Reddit comment or self-text
     * @return Returns a list of RedditBlock items that can be parsed and put into views
     */
    public List<RedditBlock> getBlocks(String escapedHtml) {
        String unescapedHtml = Parser.unescapeEntities(escapedHtml, false);
        unescapedHtml = unescapedHtml.replace("href=\"/u/", "href=\"http://www.reddit.com/u/")
                .replace("href=\"/r/", "href=\"http://www.reddit.com/r/");
        Document document = Jsoup.parseBodyFragment(unescapedHtml);

        String buffer = "";
        List<RedditBlock> blocks = new ArrayList<>();
        Elements children = document.body().getElementsByTag("div").first().children();
        for (int i = 0; i < children.size(); i++) {
            Element child = children.get(i);
            if (child.tagName().equalsIgnoreCase("pre")) {
                // Ran into codeblock
                // Add buffer to blocks
                if (!buffer.isEmpty()) blocks.add(new TextBlock(parse(buffer)));
                buffer = "";
                // Add codeblock to blocks
                blocks.add(new CodeBlock(parse(child.outerHtml(), true)));
            } else if (child.tagName().equalsIgnoreCase("table")) {
                // Ran into table
                // Add buffer to blocks
                if (!buffer.isEmpty()) blocks.add(new TextBlock(parse(buffer)));
                buffer = "";
                // Add table to blocks
                blocks.add(formatTableBlock(child));
            } else if (child.tagName().equalsIgnoreCase("hr")) {
                // Ran into horizontal rule
                // Add buffer to blocks
                if (!buffer.isEmpty()) blocks.add(new TextBlock(parse(buffer)));
                buffer = "";
                // Add hr block to blocks
                blocks.add(new HrBlock());
            } else if (i == children.size() - 1) {
                // Last element in div (Not table or pre)
                // Add buffer to blocks
                buffer = buffer.concat(child.outerHtml());
                blocks.add(new TextBlock(parse(buffer)));
            } else {
                // Add element to buffer
                buffer = buffer.concat(child.outerHtml());
            }
        }
        return blocks;
    }

    private Spannable parse(String bufferHtml) {
        Spanned spanned = spanner.fromHtml(bufferHtml);
        return (Spannable) spanned.subSequence(0, spanned.length() - 2);
    }

    private Spannable parse(String bufferHtml, boolean removeWhitespace) {
        if (removeWhitespace) {
            Spanned spanned = spanner.fromHtml(bufferHtml);
            return (Spannable) spanned.subSequence(0, spanned.length() - 3);
        } else {
            return parse(bufferHtml);
        }
    }

    private TableBlock formatTableBlock(Element table) {
        Elements tableRows = table.getElementsByTag("tr");
        Elements headerItems = table.getElementsByTag("th");

        String[] headerRow = new String[headerItems.size()];
        for (int col = 0; col < headerItems.size(); col++) {
            headerRow[col] = headerItems.get(col).text();
        }

        String[][] tableBody = new String[tableRows.size() - 1][headerItems.size()];
        for (int row = 1; row < tableRows.size(); row++) {
            Element tableRow = tableRows.get(row);
            if (tableRow.child(0).tagName().equals("td")) {
                for (int column = 0; column < tableRow.children().size(); column++) {
                    tableBody[row - 1][column] = tableRow.child(column).text();
                }
            }
        }
        return new TableBlock(headerRow, tableBody);
    }

    private void registerNewHandlers() {
        spanner.registerHandler("strong", new BoldHandler());
        spanner.registerHandler("em", new ItalicHandler());
        spanner.registerHandler("del", new StrikethroughHandler());
        spanner.registerHandler("blockquote", new BlockquoteHandler());
        spanner.registerHandler("pre", new PreHandler());
        spanner.registerHandler("code", new CodeHandler());
        spanner.registerHandler("li", new ListHandler());
    }

    private void unregisterHandlers() {
        spanner.unregisterHandler("strong");
        spanner.unregisterHandler("em");
        spanner.unregisterHandler("blockquote");
        spanner.unregisterHandler("pre");
        spanner.unregisterHandler("li");
        spanner.unregisterHandler("ul");
        spanner.unregisterHandler("ol");
    }
}