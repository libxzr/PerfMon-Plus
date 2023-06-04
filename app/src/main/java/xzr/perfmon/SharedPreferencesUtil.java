package xzr.perfmon;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesUtil {
    static SharedPreferences sharedPreferences;

    static final String SKIP_FIRST_SCREEN = "skip_first_screen";
    static final boolean DEFAULT_SKIP_FIRST_SCREEN = false;

    static final String REFRESHING_DELAY = "refreshing_delay";
    static final int DEFAULT_DELAY = 1000;

    static final String WINDOW_WIDTH = "window_width";
    static final int DEFAULT_WIDTH = -1;

    static final String WINDOW_HEIGHT = "window_height";
    static final int DEFAULT_WINDOW_HEIGHT = -1;

    static final String REVERSE_CURRENT = "reverse_current";
    static final boolean REVERSE_CURRENT_DEFAULT = false;

    static final String SIZE_MULTIPLE = "size_multiple";
    static final float SIZE_MULTIPLE_DEFAULT = (float) 0.8;

    static final String SHOW_CPUFREQ = "show_cpufreq";
    static final boolean SHOW_CPUFREQ_DEFAULT = true;

    static final String SHOW_CPULOAD = "show_cpuload";
    static final boolean SHOW_CPULOAD_DEFAULT = true;

    static final String SHOW_GPUFREQ = "show_gpufreq";
    static final boolean SHOW_GPUFREQ_DEFAULT = true;

    static final String SHOW_GPULOAD = "show_gpuload";
    static final boolean SHOW_GPULOAD_DEFAULT = true;

    static final String SHOW_CPUBW = "show_cpubw";
    static final boolean SHOW_CPUBW_DEFAULT = true;

    static final String SHOW_MINCPUBW = "show_mincpubw";
    static final boolean SHOW_MINCPUBW_DEFAULT = false;

    static final String SHOW_M4M = "show_m4m";
    static final boolean SHOW_M4M_DEFAULT = true;

    static final String SHOW_THERMAL = "show_thermal";
    static final boolean SHOW_THERMAL_DEFAULT = true;

    static final String SHOW_MEM = "show_mem";
    static final boolean SHOW_MEM_DEFAULT = true;

    static final String SHOW_CURRENT = "show_current";
    static final boolean SHOW_CURRENT_DEFAULT = true;

    static final String SHOW_GPUBW = "show_gpubw";
    static final boolean SHOW_GPUBW_DEFAULT = true;

    static final String SHOW_LLCBW = "show_llcbw";
    static final boolean SHOW_LLCBW_DEFAULT = true;

    static final String SHOW_FPS = "show_fps";
    static final boolean SHOW_FPS_DEFAULT = true;

    static void init(Context context) {
        sharedPreferences = context.getSharedPreferences("main", 0);
    }
}
