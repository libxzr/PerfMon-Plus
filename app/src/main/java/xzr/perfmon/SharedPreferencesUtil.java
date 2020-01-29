package xzr.perfmon;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.TypedValue;

public class SharedPreferencesUtil {
    static SharedPreferences sharedPreferences;

    static final String delay="refreshing_delay";
    static final int default_delay=1000;

    static void init(Context context){
        sharedPreferences=context.getSharedPreferences("main",0);
    }
}
