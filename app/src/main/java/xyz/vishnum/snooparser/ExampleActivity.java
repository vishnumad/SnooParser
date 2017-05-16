package xyz.vishnum.snooparser;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TimingLogger;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import xyz.vishnum.snoohtmlparser.SnooParser;
import xyz.vishnum.snoohtmlparser.SnooView;
import xyz.vishnum.snoohtmlparser.blocks.RedditBlock;

public class ExampleActivity extends AppCompatActivity {
    private static final String TAG = ExampleActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example);

        SnooView snooView = (SnooView) findViewById(R.id.snooView);
        SnooParser parser = new SnooParser();

        // Parse and display the comment/self-text
        String exampleHtml = getCommentHtml();

        // adb shell setprop log.tag.YOUR_TAG VERBOSE
        TimingLogger logger = new TimingLogger(TAG, "SnooParser Timing");
        List<RedditBlock> blocks = parser.getBlocks(exampleHtml);
        logger.addSplit("PARSED COMMENT BLOCKS");
        snooView.setBlocks(blocks);
        logger.addSplit("RENDERED COMMENT");
        logger.dumpToLog();

        // Handle link clicks
        snooView.setOnUrlClickListener(new SnooView.OnUrlClickListener() {
            @Override
            public void onClick(String url) {
                Log.d(TAG, "Url clicked: " + url);
            }
        });
    }

    private String getCommentHtml() {
        try {
            //InputStream inputStream = getAssets().open("example-comment-2.html");
            InputStream inputStream = getAssets().open("example-comment-2.html");
            //InputStream inputStream = getAssets().open("table-formatting-example-1.html");
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            inputStream.close();
            return new String(buffer);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return "<div class=\"md\"><p>Something went wrong getting comment</p></div>";
    }
}
