package xzr.perfmon;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesUtil {
    static SharedPreferences sharedPreferences;

    static final String delay="refreshing_delay";
    static final int default_delay=1000;

    static final String width="window_width";
    static final int default_width=380;

    static final String height="window_height";
    static final int default_height=-1;

    static void init(Context context){
        sharedPreferences=context.getSharedPreferences("main",0);
    }
}
