package xzr.perfmon;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import static xzr.perfmon.RefreshingDateThread.adrenofreq;
import static xzr.perfmon.RefreshingDateThread.adrenoload;
import static xzr.perfmon.RefreshingDateThread.cpubw;
import static xzr.perfmon.RefreshingDateThread.cpufreq;
import static xzr.perfmon.RefreshingDateThread.cpuload;
import static xzr.perfmon.RefreshingDateThread.cpuonline;
import static xzr.perfmon.RefreshingDateThread.current;
import static xzr.perfmon.RefreshingDateThread.fps;
import static xzr.perfmon.RefreshingDateThread.gpubw;
import static xzr.perfmon.RefreshingDateThread.llcbw;
import static xzr.perfmon.RefreshingDateThread.m4m;
import static xzr.perfmon.RefreshingDateThread.maxtemp;
import static xzr.perfmon.RefreshingDateThread.memusage;
import static xzr.perfmon.RefreshingDateThread.mincpubw;

public class FloatingWindow extends Service {
    static String TAG = "FloatingWindow";
    public static boolean doExit = true;
    static WindowManager.LayoutParams params;
    static WindowManager windowManager;
    static int statusBarHeight = -1;
    LinearLayout main;
    static TextView line[];
    static int linen;
    static Handler uiRefresher;
    static float sizeMultipleNow;

    static boolean showCpufreqNow;
    static boolean showCpuloadNow;
    static boolean showGpufreqNow;
    static boolean showGpuloadNow;
    static boolean showCpubwNow;
    static boolean showMincpubwNow;
    static boolean showM4MNow;
    static boolean showThermalNow;
    static boolean showMemNow;
    static boolean showCurrentNow;
    static boolean showGpubwNow;
    static boolean showLlcbwNow;
    static boolean showFpsNow;

    @SuppressLint("ClickableViewAccessibility")
    void init() {
        sizeMultipleNow = SharedPreferencesUtil.sharedPreferences.getFloat(SharedPreferencesUtil.SIZE_MULTIPLE, SharedPreferencesUtil.SIZE_MULTIPLE_DEFAULT);
        {
            showCpufreqNow = SharedPreferencesUtil.sharedPreferences.getBoolean(SharedPreferencesUtil.SHOW_CPUFREQ, SharedPreferencesUtil.SHOW_CPUFREQ_DEFAULT);
            if (!showCpufreqNow && Support.support_cpufreq)
                linen = linen - JniTools.getCpuNum();
            showCpuloadNow = SharedPreferencesUtil.sharedPreferences.getBoolean(SharedPreferencesUtil.SHOW_CPULOAD, SharedPreferencesUtil.SHOW_CPULOAD_DEFAULT);

            showGpufreqNow = SharedPreferencesUtil.sharedPreferences.getBoolean(SharedPreferencesUtil.SHOW_GPUFREQ, SharedPreferencesUtil.SHOW_GPUFREQ_DEFAULT);
            if (!showGpufreqNow && Support.support_adrenofreq)
                linen--;
            showGpuloadNow = SharedPreferencesUtil.sharedPreferences.getBoolean(SharedPreferencesUtil.SHOW_GPULOAD, SharedPreferencesUtil.SHOW_GPULOAD_DEFAULT);

            showCpubwNow = SharedPreferencesUtil.sharedPreferences.getBoolean(SharedPreferencesUtil.SHOW_CPUBW, SharedPreferencesUtil.SHOW_CPUBW_DEFAULT);
            if (!showCpubwNow && Support.support_cpubw)
                linen--;

            showMincpubwNow = SharedPreferencesUtil.sharedPreferences.getBoolean(SharedPreferencesUtil.SHOW_MINCPUBW, SharedPreferencesUtil.SHOW_MINCPUBW_DEFAULT);
            if (!showMincpubwNow && Support.support_mincpubw)
                linen--;

            showM4MNow = SharedPreferencesUtil.sharedPreferences.getBoolean(SharedPreferencesUtil.SHOW_M4M, SharedPreferencesUtil.SHOW_M4M_DEFAULT);
            if (!showM4MNow && Support.support_m4m)
                linen--;

            showThermalNow = SharedPreferencesUtil.sharedPreferences.getBoolean(SharedPreferencesUtil.SHOW_THERMAL, SharedPreferencesUtil.SHOW_THERMAL_DEFAULT);
            if (!showThermalNow && Support.support_temp)
                linen--;

            showMemNow = SharedPreferencesUtil.sharedPreferences.getBoolean(SharedPreferencesUtil.SHOW_MEM, SharedPreferencesUtil.SHOW_MEM_DEFAULT);
            if (!showMemNow && Support.support_mem)
                linen--;

            showCurrentNow = SharedPreferencesUtil.sharedPreferences.getBoolean(SharedPreferencesUtil.SHOW_CURRENT, SharedPreferencesUtil.SHOW_CURRENT_DEFAULT);
            if (!showCurrentNow && Support.support_current)
                linen--;

            showGpubwNow = SharedPreferencesUtil.sharedPreferences.getBoolean(SharedPreferencesUtil.SHOW_GPUBW, SharedPreferencesUtil.SHOW_GPUBW_DEFAULT);
            if (!showGpubwNow && Support.support_gpubw)
                linen--;

            showLlcbwNow = SharedPreferencesUtil.sharedPreferences.getBoolean(SharedPreferencesUtil.SHOW_LLCBW, SharedPreferencesUtil.SHOW_LLCBW_DEFAULT);
            if (!showLlcbwNow && Support.support_llcbw)
                linen--;

            showFpsNow = SharedPreferencesUtil.sharedPreferences.getBoolean(SharedPreferencesUtil.SHOW_FPS, SharedPreferencesUtil.SHOW_FPS_DEFAULT);
            if (!showFpsNow && Support.support_fps)
                linen--;
        }
        params = new WindowManager.LayoutParams();
        windowManager = (WindowManager) getApplication().getSystemService(Context.WINDOW_SERVICE);
        if (Build.VERSION.SDK_INT >= 26) {
            params.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        }

        params.format = PixelFormat.RGBA_8888;
        params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        params.gravity = Gravity.LEFT | Gravity.TOP;
        params.x = 0;
        params.y = 0;
        if (SharedPreferencesUtil.sharedPreferences.getInt(SharedPreferencesUtil.WINDOW_WIDTH, SharedPreferencesUtil.DEFAULT_WIDTH) != SharedPreferencesUtil.DEFAULT_WIDTH)
            params.width = SharedPreferencesUtil.sharedPreferences.getInt(SharedPreferencesUtil.WINDOW_WIDTH, SharedPreferencesUtil.DEFAULT_WIDTH);
        else if ((Support.support_cpuload && showCpuloadNow) || (Support.support_adrenofreq && showGpuloadNow))
            params.width = (int) ((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 145, getResources().getDisplayMetrics()) * sizeMultipleNow);
        else
            params.width = (int) ((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 120, getResources().getDisplayMetrics()) * sizeMultipleNow);
        params.height = 300;
        main = new LinearLayout(this);
        main.setOrientation(LinearLayout.VERTICAL);
        main.setBackgroundColor(getResources().getColor(R.color.floating_window_backgrouns));
        main.setPadding((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 5, getResources().getDisplayMetrics()), 0, 0, 0);
        TextView close = new TextView(this);
        close.setText(R.string.close);
        close.setTextSize(TypedValue.COMPLEX_UNIT_PX, close.getTextSize() * sizeMultipleNow);
        close.setTextColor(getResources().getColor(R.color.white));
        main.addView(close);
        close.setOnClickListener(view -> stopSelf());
        close.setOnLongClickListener(view -> {
            SharedPreferencesUtil.sharedPreferences.edit().putBoolean(SharedPreferencesUtil.SKIP_FIRST_SCREEN, false).commit();
            Toast.makeText(FloatingWindow.this, R.string.skip_first_screen_str_disabled, Toast.LENGTH_LONG).show();
            return false;
        });
        main.setOnTouchListener(new View.OnTouchListener() {
            private int x, y;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        x = (int) event.getRawX();
                        y = (int) event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        int nowX = (int) event.getRawX();
                        int nowY = (int) event.getRawY();
                        int movedX = nowX - x;
                        int movedY = nowY - y;
                        x = nowX;
                        y = nowY;
                        params.x = params.x + movedX;
                        params.y = params.y + movedY;
                        windowManager.updateViewLayout(main, params);
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
        windowManager.addView(main, params);

        main.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);

        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = getResources().getDimensionPixelSize(resourceId);
        }
    }

    void monitorInit() {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        line = new TextView[linen];

        if (SharedPreferencesUtil.sharedPreferences.getInt(SharedPreferencesUtil.WINDOW_HEIGHT, SharedPreferencesUtil.DEFAULT_WINDOW_HEIGHT) != SharedPreferencesUtil.DEFAULT_WINDOW_HEIGHT)
            params.height = SharedPreferencesUtil.sharedPreferences.getInt(SharedPreferencesUtil.WINDOW_HEIGHT, SharedPreferencesUtil.DEFAULT_WINDOW_HEIGHT);
        else
            params.height = (linen + 1) * (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 20, getResources().getDisplayMetrics()) * sizeMultipleNow);

        windowManager.updateViewLayout(main, params);
        uiRefresher = new Handler(message -> {
            int i = 0;
            if (Support.support_cpufreq && showCpufreqNow) {
                for (i = 0; i < RefreshingDateThread.cpunum; i++) {
                    String text = "cpu" + i + " ";
                    if (cpuonline[i] == 1) {
                        text = text + cpufreq[i] + " Mhz";
                        if (Support.support_cpuload && showCpuloadNow)
                            text = text + Tools.formatIfyAddBlank(cpufreq[i] + "") + cpuload[i] + "%";
                    } else {
                        text = text + getResources().getString(R.string.offline);
                    }
                    line[i].setText(text);
                }
            }
            if (Support.support_adrenofreq && showGpufreqNow) {
                if (showGpuloadNow)
                    line[i].setText("gpu0 " + adrenofreq + " Mhz" + Tools.formatIfyAddBlank(adrenofreq + "") + adrenoload + "%");
                else
                    line[i].setText("gpu0 " + adrenofreq + " Mhz" + Tools.formatIfyAddBlank(adrenofreq + ""));
                i++;
            }
            if (Support.support_mincpubw && showMincpubwNow) {
                line[i].setText("mincpubw " + mincpubw);
                i++;
            }
            if (Support.support_cpubw && showCpubwNow) {
                line[i].setText("cpubw " + cpubw);
                i++;
            }
            if (Support.support_gpubw && showGpubwNow) {
                line[i].setText("gpubw " + gpubw);
                i++;
            }
            if (Support.support_llcbw && showLlcbwNow) {
                line[i].setText("llccbw " + llcbw);
                i++;
            }
            if (Support.support_m4m & showM4MNow) {
                line[i].setText("m4m " + m4m + " Mhz");
                i++;
            }
            if (Support.support_temp && showThermalNow) {
                line[i].setText(getResources().getString(R.string.temp) + maxtemp + " ℃");
                i++;
            }
            if (Support.support_mem && showMemNow) {
                line[i].setText(getResources().getString(R.string.mem) + memusage + "%");
                i++;
            }
            if (Support.support_current && showCurrentNow) {
                line[i].setText(getResources().getString(R.string.current) + current + " mA");
                i++;
            }
            if (Support.support_fps && showFpsNow) {
                line[i].setText("fps " + fps);
                i++;
            }
            return false;
        });

        for (int i = 0; i < linen; i++) {
            line[i] = new TextView(this);
            line[i].setTextColor(getResources().getColor(R.color.white));
            line[i].setLayoutParams(layoutParams);
            line[i].setTextSize(TypedValue.COMPLEX_UNIT_PX, line[i].getTextSize() * sizeMultipleNow);
            main.addView(line[i]);
        }
        windowManager.updateViewLayout(main, params);
        new RefreshingDateThread().start();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        doExit = false;
        init();
        monitorInit();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "Calling destory service");
        doExit = true;
        try {
            windowManager.removeView(main);
        } catch (Exception e) {
        }
        super.onDestroy();
    }
}
