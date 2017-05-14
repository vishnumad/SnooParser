package xyz.vishnum.snoohtmlparser;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.provider.Browser;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import java.util.List;
import xyz.vishnum.snoohtmlparser.blocks.CodeBlock;
import xyz.vishnum.snoohtmlparser.blocks.RedditBlock;
import xyz.vishnum.snoohtmlparser.blocks.TableBlock;
import xyz.vishnum.snoohtmlparser.blocks.TextBlock;

/**
 * Author:  vishnu
 * Created: 5/5/17, 11:03 PM
 * Purpose:
 */

public class SnooView extends LinearLayout {
    private static final String TAG = SnooView.class.getSimpleName();
    private static final LayoutParams HR_PARAMS;

    static {
        HR_PARAMS = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 4 /*px*/);
        HR_PARAMS.setMargins(0, 32, 0, 32);
    }

    private OnUrlClickListener urlClickListener = null;

    public SnooView(Context context) {
        super(context);
        init();
    }

    public SnooView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SnooView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setOrientation(VERTICAL);
    }

    /**
     * Adds the correct views to the SnooView
     *
     * @param blocks List of RedditBlock blocks
     */
    public void setBlocks(List<RedditBlock> blocks) {
        removeAllViews();
        Context context = getContext();
        for (int i = 0; i < blocks.size(); i++) {
            RedditBlock block = blocks.get(i);
            switch (block.getBlockType()) {
                case TEXT:
                    Spannable text = ((TextBlock) block).getText();
                    formatUrlSpans(text);
                    TextView textView = new TextView(context);
                    textView.setText(text);
                    textView.setMovementMethod(LinkMovementMethod.getInstance());
                    if (i == 0) {
                        textView.setPadding(0, 0, 0, 32);
                    } else if (i == blocks.size() - 1) {
                        textView.setPadding(0, 32, 0, 0);
                    } else {
                        textView.setPadding(0, 32, 0, 32);
                    }
                    addView(textView);
                    break;
                case HR:
                    View horizontalRule = new View(context);
                    horizontalRule.setLayoutParams(HR_PARAMS);
                    horizontalRule.setBackgroundColor(Color.GRAY);
                    addView(horizontalRule);
                    break;
                case CODE:
                    HorizontalScrollView codeScrollView = new HorizontalScrollView(context);
                    TextView codeView = new TextView(context);
                    codeView.setText(((CodeBlock) block).getText());
                    codeScrollView.addView(codeView);
                    codeScrollView.setScrollbarFadingEnabled(false);
                    addView(codeScrollView);
                    break;
                case TABLE:
                    HorizontalScrollView tableScrollView = new HorizontalScrollView(context);
                    TableBlock tableBlock = (TableBlock) block;
                    TableLayout tableLayout =
                            formatTable(tableBlock.getHeaderRow(), tableBlock.getBodyRows(),
                                    context);
                    tableScrollView.addView(tableLayout);
                    tableScrollView.setScrollbarFadingEnabled(false);
                    addView(tableScrollView);
                    break;
            }
        }
    }

    public void setOnUrlClickListener(OnUrlClickListener urlClickListener) {
        this.urlClickListener = urlClickListener;
    }

    private TableLayout formatTable(String[] headerItems, String[][] bodyRows, Context context) {
        TableLayout table = new TableLayout(context);
        // Add header items
        TableRow headerRow = new TableRow(context);
        for (String headerItem : headerItems) {
            TextView headerItemView = new TextView(context);
            headerItemView.setTypeface(Typeface.DEFAULT_BOLD);
            headerItemView.setPadding(0, 0, 32, 5);
            headerItemView.setText(headerItem);
            headerRow.addView(headerItemView);
        }
        table.addView(headerRow);
        // Add table body items
        for (String[] tableRow : bodyRows) {
            TableRow bodyRow = new TableRow(context);
            for (String tableItem : tableRow) {
                TextView bodyItemView = new TextView(context);
                bodyItemView.setPadding(0, 0, 32, 5);
                bodyItemView.setText(tableItem);
                bodyItemView.setGravity(Gravity.RIGHT);
                bodyRow.addView(bodyItemView);
            }
            table.addView(bodyRow);
        }
        return table;
    }

    private void formatUrlSpans(Spannable spannable) {
        URLSpan[] urlSpans = spannable.getSpans(0, spannable.length(), URLSpan.class);
        for (final URLSpan urlSpan : urlSpans) {
            int start = spannable.getSpanStart(urlSpan);
            int end = spannable.getSpanEnd(urlSpan);
            int flags = spannable.getSpanFlags(urlSpan);
            ClickableSpan clickableSpan = new ClickableSpan() {
                @Override
                public void onClick(View view) {
                    String url = urlSpan.getURL();
                    handleUrlClick(url);
                }
            };
            spannable.removeSpan(urlSpan);
            spannable.setSpan(clickableSpan, start, end, flags);
        }
    }

    private void handleUrlClick(String url) {
        if (urlClickListener != null) {
            urlClickListener.onClick(url);
        } else {
            Uri uri = Uri.parse(url);
            Context context = getContext();
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.putExtra(Browser.EXTRA_APPLICATION_ID, context.getPackageName());
            try {
                context.startActivity(intent);
            } catch (ActivityNotFoundException e) {
                Log.e("ClickableSpan", "Activity was not found for intent, " + intent.toString());
            }
        }
    }

    public interface OnUrlClickListener {
        void onClick(String url);
    }
}
