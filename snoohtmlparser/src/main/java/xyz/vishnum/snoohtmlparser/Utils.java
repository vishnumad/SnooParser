package xyz.vishnum.snoohtmlparser;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;

/**
 * Author:  vishnu
 * Created: 5/16/17, 12:02 PM
 * Purpose: Utils class
 */

class Utils {
    static int dipToPixels(Context context, float dipValue) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, metrics);
    }
}
