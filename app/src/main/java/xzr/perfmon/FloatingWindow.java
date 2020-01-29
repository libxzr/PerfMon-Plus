package xzr.perfmon;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import static xzr.perfmon.RefreshingDateThread.adrenofreq;
import static xzr.perfmon.RefreshingDateThread.adrenoload;
import static xzr.perfmon.RefreshingDateThread.cpubw;
import static xzr.perfmon.RefreshingDateThread.cpufreq;
import static xzr.perfmon.RefreshingDateThread.cpuload;
import static xzr.perfmon.RefreshingDateThread.cpuonline;
import static xzr.perfmon.RefreshingDateThread.current;
import static xzr.perfmon.RefreshingDateThread.gpubw;
import static xzr.perfmon.RefreshingDateThread.m4m;
import static xzr.perfmon.RefreshingDateThread.maxtemp;
import static xzr.perfmon.RefreshingDateThread.memusage;
import static xzr.perfmon.RefreshingDateThread.mincpubw;

public class FloatingWindow extends Service {
    static String TAG="FloatingWindow";
    public static boolean do_exit=true;
    static WindowManager.LayoutParams params;
    static WindowManager windowManager;
    static int statusBarHeight = -1;
    LinearLayout main;
    static TextView line[];
    static int linen;
    static Handler ui_refresher;
    static float size_multiple_now;

    static boolean show_cpufreq_now;
    static boolean show_cpuload_now;
    static boolean show_gpufreq_now;
    static boolean show_gpuload_now;
    static boolean show_cpubw_now;
    static boolean show_mincpubw_now;
    static boolean show_m4m_now;
    static boolean show_thermal_now;
    static boolean show_mem_now;
    static boolean show_current_now;
    static boolean show_gpubw_now;
    @SuppressLint("ClickableViewAccessibility")
    void init(){
        size_multiple_now=SharedPreferencesUtil.sharedPreferences.getFloat(SharedPreferencesUtil.size_multiple,SharedPreferencesUtil.size_multiple_default);
        {
            show_cpufreq_now = SharedPreferencesUtil.sharedPreferences.getBoolean(SharedPreferencesUtil.show_cpufreq, SharedPreferencesUtil.show_cpufreq_default);
            if (!show_cpufreq_now)
                linen=linen-JniTools.getcpunum();
            show_cpuload_now = SharedPreferencesUtil.sharedPreferences.getBoolean(SharedPreferencesUtil.show_cpuload, SharedPreferencesUtil.show_cpuload_default);

            show_gpufreq_now = SharedPreferencesUtil.sharedPreferences.getBoolean(SharedPreferencesUtil.show_gpufreq, SharedPreferencesUtil.show_gpufreq_default);
            if (!show_gpufreq_now)
                linen--;
            show_gpuload_now = SharedPreferencesUtil.sharedPreferences.getBoolean(SharedPreferencesUtil.show_gpuload, SharedPreferencesUtil.show_gpuload_default);

            show_cpubw_now = SharedPreferencesUtil.sharedPreferences.getBoolean(SharedPreferencesUtil.show_cpubw, SharedPreferencesUtil.show_cpubw_default);
            if (!show_cpubw_now)
                linen--;
            
            show_mincpubw_now = SharedPreferencesUtil.sharedPreferences.getBoolean(SharedPreferencesUtil.show_mincpubw, SharedPreferencesUtil.show_mincpubw_default);
            if (!show_mincpubw_now)
                linen--;

            show_m4m_now = SharedPreferencesUtil.sharedPreferences.getBoolean(SharedPreferencesUtil.show_m4m, SharedPreferencesUtil.show_m4m_default);
            if (!show_m4m_now)
                linen--;

            show_thermal_now = SharedPreferencesUtil.sharedPreferences.getBoolean(SharedPreferencesUtil.show_thermal, SharedPreferencesUtil.show_thermal_default);
            if (!show_thermal_now)
                linen--;

            show_mem_now = SharedPreferencesUtil.sharedPreferences.getBoolean(SharedPreferencesUtil.show_mem, SharedPreferencesUtil.show_mem_default);
            if (!show_mem_now)
                linen--;

            show_current_now = SharedPreferencesUtil.sharedPreferences.getBoolean(SharedPreferencesUtil.show_current, SharedPreferencesUtil.show_current_default);
            if (!show_current_now)
                linen--;

            show_gpubw_now = SharedPreferencesUtil.sharedPreferences.getBoolean(SharedPreferencesUtil.show_gpubw, SharedPreferencesUtil.show_gpubw_default);
            if (!show_gpubw_now)
                linen--;
        }
        params=new WindowManager.LayoutParams();
        windowManager=(WindowManager)getApplication().getSystemService(Context.WINDOW_SERVICE);
        if(Build.VERSION.SDK_INT>=26){
            params.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        }
        else{
            params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        }

        params.format = PixelFormat.RGBA_8888;
        params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        params.gravity = Gravity.LEFT | Gravity.TOP;
        params.x = 0;
        params.y = 0;
        if((Support.support_cpuload&&show_cpuload_now)||(Support.support_adrenofreq&&show_gpuload_now))
            params.width=(int)((int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 145,getResources().getDisplayMetrics())*size_multiple_now);
        else
            params.width=(int)((int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 120,getResources().getDisplayMetrics())*size_multiple_now);
        params.height = 300;
        main= new LinearLayout(this);
        main.setOrientation(LinearLayout.VERTICAL);
        main.setBackgroundColor(getResources().getColor(R.color.floating_window_backgrouns));
        main.setPadding((int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 5,getResources().getDisplayMetrics()),0,0,0);
        TextView close=new TextView(this);
        close.setText(R.string.close);
        close.setTextSize(TypedValue.COMPLEX_UNIT_PX,close.getTextSize()*size_multiple_now);
        close.setTextColor(getResources().getColor(R.color.white));
        main.addView(close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopSelf();
            }
        });
        main.setOnTouchListener(new View.OnTouchListener() {
            private int x,y;
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
        windowManager.addView(main,params);

        main.measure(View.MeasureSpec.UNSPECIFIED,View.MeasureSpec.UNSPECIFIED);

        int resourceId = getResources().getIdentifier("status_bar_height","dimen","android");
        if (resourceId > 0) {
            statusBarHeight = getResources().getDimensionPixelSize(resourceId);
        }
    }

    void monitor_init(){
        LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        line=new TextView[linen];
        params.height=(linen+1)*(int)(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 20,getResources().getDisplayMetrics())*size_multiple_now);
        windowManager.updateViewLayout(main,params);
        ui_refresher=new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message message) {
                int i=0;
                if(Support.support_cpufreq&&show_cpufreq_now) {
                    for (i = 0; i < RefreshingDateThread.cpunum; i++) {
                        String text = "cpu" + i + " ";
                        if (cpuonline[i] == 1) {
                            text = text + cpufreq[i] + " Mhz";
                            if (Support.support_cpuload&&show_cpuload_now)
                                text = text + Tools.format_ify_add_blank(cpufreq[i] + "") + cpuload[i] + "%";
                        } else {
                            text = text +getResources().getString(R.string.offline);
                        }
                        line[i].setText(text);
                    }
                }
                if(Support.support_adrenofreq&&show_gpufreq_now) {
                    if(show_gpuload_now)
                        line[i].setText("gpu0 " + adrenofreq + " Mhz"+Tools.format_ify_add_blank(adrenofreq+"") + adrenoload + "%");
                    else
                        line[i].setText("gpu0 " + adrenofreq + " Mhz"+Tools.format_ify_add_blank(adrenofreq+""));
                    i++;
                }
                if (Support.support_mincpubw&&show_mincpubw_now) {
                    line[i].setText("mincpubw " + mincpubw);
                    i++;
                }
                if (Support.support_cpubw&&show_cpubw_now) {
                    line[i].setText("cpubw " + cpubw);
                    i++;
                }
                if (Support.support_gpubw&&show_gpubw_now) {
                    line[i].setText("gpubw " + gpubw);
                    i++;
                }
                if (Support.support_m4m&show_m4m_now) {
                    line[i].setText("m4m " + m4m+" Mhz");
                    i++;
                }
                if (Support.support_temp&&show_thermal_now) {
                    line[i].setText(getResources().getString(R.string.temp) + maxtemp+" ℃");
                    i++;
                }
                if (Support.support_mem&&show_mem_now) {
                    line[i].setText(getResources().getString(R.string.mem) + memusage+"%");
                    i++;
                }
                if (Support.support_current&&show_current_now) {
                    line[i].setText(getResources().getString(R.string.current)+ current+" mA");
                    i++;
                }
                return false;
            }
        });

        for (int i=0;i<linen;i++){
            line[i]=new TextView(this);
            line[i].setTextColor(getResources().getColor(R.color.white));
            line[i].setLayoutParams(layoutParams);
            line[i].setTextSize(TypedValue.COMPLEX_UNIT_PX,line[i].getTextSize()*size_multiple_now);
            main.addView(line[i]);
        }
        windowManager.updateViewLayout(main,params);
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
        do_exit=false;
        init();
        monitor_init();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG,"Calling destory service");
        do_exit=true;
        try{
        windowManager.removeView(main);}
        catch (Exception e){}
        super.onDestroy();
    }
}
