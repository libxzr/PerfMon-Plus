package xzr.perfmon;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.TypedValue;

public class SharedPreferencesUtil {
    static SharedPreferences sharedPreferences;

    static final String delay="refreshing_delay";
    static final int default_delay=1000;

    static final String reverse_current="reverse_current";
    static final boolean reverse_current_default=false;

    static void init(Context context){
        sharedPreferences=context.getSharedPreferences("main",0);
    }
}
