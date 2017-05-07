# SnooParser - Reddit HTML Parser

## Add to Gradle
Add the following to your app build.gradle file and sync. 

[Jitpack Instructions](https://jitpack.io/#vishnumad/SnooParser/)

```groovy
dependencies {
  ...
  compile 'com.github.vishnumad:SnooParser:LATEST-COMMIT-HASH'
}
```
```groovy
repositories {
  maven { url 'https://jitpack.io' }
}
```
    
## Usage

### XML
```xml
<xyz.vishnum.snoohtmlparser.SnooView
            android:id="@+id/snoo_body_view"
            android:padding="16dp"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            />
```
See [example](app/src/main/res/layout/activity_example.xml)

### Java

#### Basic usage

```java
SnooView snooView = (SnooView) findViewById(R.id.snoo_body_view);
SnooParser parser = new SnooParser();

String exampleHtml = getCommentHtml();
List<RedditBlock> blocks = parser.getBlocks(exampleHtml);

snooView.setBlocks(blocks);
```

**Handling link clicks** — Defaults to open in browser, but you can add your own listener

```java
snooView.setOnUrlClickListener(new SnooView.OnUrlClickListener() {
    @Override
    public void onClick(String url) {
        Log.d(TAG, "Url clicked: " + url);
    }
});
```

**Custom tag handling** — `<pre>`, `<hr/>`, and `<table>` tags will not be affected

```java
SnooParser parser = new SnooParser();

// Override how the <strong> tag is handled
// All bold text with the strong tag will be displayed as blue colored text
parser.replaceHandler("strong", new ExampleStrongHandler());
```

```java
public class ExampleStrongHandler extends TagNodeHandler {
    @Override
    public void handleTagNode(TagNode node, SpannableStringBuilder builder, int start, int end) {
        builder.setSpan(new ForegroundColorSpan(Color.BLUE), start, end,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }
}
```

See full [example](app/src/main/java/xyz/vishnum/snooparser/ExampleActivity.java)

# External dependencies
This project depends on the [JSoup](https://jsoup.org/download) and [HtmlSpanner](https://github.com/NightWhistler/HtmlSpanner) libraries
