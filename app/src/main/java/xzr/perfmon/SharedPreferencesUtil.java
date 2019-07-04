package xzr.perfmon;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.TypedValue;

public class SharedPreferencesUtil {
    static SharedPreferences sharedPreferences;

    static final String delay="refreshing_delay";
    static final int default_delay=1000;

    static final String width="window_width";
    static int default_width=-1;

    static final String height="window_height";
    static final int default_height=-1;

    static void init(Context context){
        sharedPreferences=context.getSharedPreferences("main",0);
        default_width=(int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 145,context.getResources().getDisplayMetrics());
    }
}
