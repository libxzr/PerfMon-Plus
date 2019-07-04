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

    @SuppressLint("ClickableViewAccessibility")
    void init(){
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
        params.width = SharedPreferencesUtil.sharedPreferences.getInt(SharedPreferencesUtil.width,SharedPreferencesUtil.default_width);
        params.height = 300;
        main= new LinearLayout(this);
        main.setOrientation(LinearLayout.VERTICAL);
        main.setBackgroundColor(getResources().getColor(R.color.floating_window_backgrouns));
        TextView close=new TextView(this);
        close.setText("关闭");
        close.setTextColor(getResources().getColor(R.color.white));
        main.addView(close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopSelf();
            }
        });
        main.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                params.x = (int) event.getRawX() - 250;
                params.y = (int) event.getRawY() - 150 - statusBarHeight;
                windowManager.updateViewLayout(main,params);
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
        if (SharedPreferencesUtil.sharedPreferences.getInt(SharedPreferencesUtil.height,SharedPreferencesUtil.default_height)!=SharedPreferencesUtil.default_height)
            params.height=SharedPreferencesUtil.sharedPreferences.getInt(SharedPreferencesUtil.height,SharedPreferencesUtil.default_height);
        else
            params.height=(linen+1)*(int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 20,getResources().getDisplayMetrics());;
        windowManager.updateViewLayout(main,params);
        ui_refresher=new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message message) {
                int i=0;
                if(Support.support_cpufreq) {
                    for (i = 0; i < RefreshingDateThread.cpunum; i++) {
                        String text = "cpu" + i + " ";
                        if (cpuonline[i] == 1) {
                            text = text + cpufreq[i] + "Mhz";
                            if (Support.support_cpuload)
                                text = text + Tools.format_ify_add_blank(cpufreq[i] + "") + cpuload[i] + "%";
                        } else {
                            text = text + "离线";
                        }
                        line[i].setText(text);
                    }
                }
                if(Support.support_adrenofreq) {
                    line[i].setText("gpu0 " + adrenofreq + "Mhz"+Tools.format_ify_add_blank(adrenofreq+"") + adrenoload + "%");
                    i++;
                }
                if (Support.support_mincpubw) {
                    line[i].setText("mincpubw " + mincpubw);
                    i++;
                }
                if (Support.support_cpubw) {
                    line[i].setText("cpubw " + cpubw);
                    i++;
                }
                if (Support.support_m4m) {
                    line[i].setText("m4m " + m4m+" Mhz");
                    i++;
                }
                if (Support.support_temp) {
                    line[i].setText("温度 " + maxtemp+" ℃");
                    i++;
                }
                if (Support.support_mem) {
                    line[i].setText("内存 " + memusage+"%");
                    i++;
                }
                return false;
            }
        });

        for (int i=0;i<linen;i++){
            line[i]=new TextView(this);
            line[i].setTextColor(getResources().getColor(R.color.white));
            line[i].setLayoutParams(layoutParams);
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
