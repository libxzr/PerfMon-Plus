package xzr.perfmon;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;

public class MainActivity extends Activity {
    ScrollView mainView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainView=new ScrollView(this);
        setContentView(mainView);

        if(!FloatingWindow.do_exit){
            Toast.makeText(MainActivity.this,getResources().getString(R.string.please_close_app_first),Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        //Move from service:onCreate in order to show the supporting list
        RefreshingDateThread.cpunum=JniTools.getcpunum();
        FloatingWindow.linen=Support.CheckSupport();
        SharedPreferencesUtil.init(this);

        if(SharedPreferencesUtil.sharedPreferences.getBoolean(SharedPreferencesUtil.skip_first_screen,SharedPreferencesUtil.default_skip_first_screen)){
            permissioncheck();
            finish();
            return;
        }

        addview();

    }

    void addview(){
        LinearLayout main=new LinearLayout(this);
        main.setOrientation(LinearLayout.VERTICAL);
        mainView.addView(main);
        {
            TextView textView=new TextView(this);
            textView.setText(getResources().getString(R.string.support_cpufreq_mo)+Tools.bool2text(Support.support_cpufreq,this));
            main.addView(textView);
        }
        {
            TextView textView=new TextView(this);
            textView.setText(getResources().getString(R.string.support_cpuload_mo)+Tools.bool2text(Support.support_cpuload,this));
            main.addView(textView);
        }
        {
            TextView textView=new TextView(this);
            textView.setText(getResources().getString(R.string.support_gpufreq_mo)+Tools.bool2text(Support.support_adrenofreq,this));
            main.addView(textView);
        }
        {
            TextView textView=new TextView(this);
            textView.setText(getResources().getString(R.string.support_gpuload_mo)+Tools.bool2text(Support.support_adrenofreq,this));
            main.addView(textView);
        }
        {
            TextView textView=new TextView(this);
            textView.setText(getResources().getString(R.string.support_cpubw_mo)+Tools.bool2text(Support.support_cpubw,this));
            main.addView(textView);
        }
        {
            TextView textView=new TextView(this);
            textView.setText(getResources().getString(R.string.support_gpubw_mo)+Tools.bool2text(Support.support_gpubw,this));
            main.addView(textView);
        }
        {
            TextView textView=new TextView(this);
            textView.setText(getResources().getString(R.string.support_llcbw_mo)+Tools.bool2text(Support.support_llcbw,this));
            main.addView(textView);
        }
        {
            TextView textView=new TextView(this);
            textView.setText(getResources().getString(R.string.support_m4mfreq_mo)+Tools.bool2text(Support.support_m4m,this));
            main.addView(textView);
        }
        {
            TextView textView=new TextView(this);
            textView.setText(getResources().getString(R.string.support_thermal_mo)+Tools.bool2text(Support.support_temp,this));
            main.addView(textView);
        }
        {
            TextView textView=new TextView(this);
            textView.setText(getResources().getString(R.string.support_mem_mo)+Tools.bool2text(Support.support_mem,this));
            main.addView(textView);
        }
        {
            TextView textView=new TextView(this);
            textView.setText(getResources().getString(R.string.support_current_mo)+Tools.bool2text(Support.support_current,this));
            main.addView(textView);
        }
        {
            TextView textView=new TextView(this);
            textView.setText(getResources().getString(R.string.support_fps_mo)+Tools.bool2text(Support.support_fps,this));
            main.addView(textView);
        }
        {
            TextView textView=new TextView(this);
            textView.setText(R.string.Unsupport_reason);
            main.addView(textView);
        }
        {
            Button button = new Button(this);
            button.setText(R.string.show_floatingwindow);
            main.addView(button);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    permissioncheck();
                    finish();
                }
            });
        }
        {
            Button button = new Button(this);
            button.setText(R.string.settings);
            main.addView(button);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    xzr.perfmon.Settings.creatDialog(MainActivity.this);
                }
            });
        }
        {
            Button button=new Button(this);
            button.setText(R.string.permissive_selinux);
            main.addView(button);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try{
                        Process process=new ProcessBuilder("su").redirectErrorStream(true).start();
                        BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(process.getInputStream()));
                        OutputStreamWriter outputStreamWriter=new OutputStreamWriter(process.getOutputStream());
                        outputStreamWriter.write("setenforce 0\nexit\n");
                        outputStreamWriter.flush();
                        String log="";
                        String cache;
                        while ((cache=bufferedReader.readLine())!=null){
                            log=log+cache+"\n";
                        }
                        if (log.equals("")){
                            Toast.makeText(MainActivity.this,R.string.permissive_done,Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(MainActivity.this,log,Toast.LENGTH_SHORT).show();
                        }
                        finish();
                    }
                    catch (Exception e){
                        Toast.makeText(MainActivity.this,R.string.permission_denied,Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        {
            TextView textView=new TextView(this);
            textView.setText(R.string.permissive_selinux_description);
            main.addView(textView);
        }
        {
            LinearLayout line=new LinearLayout(MainActivity.this);
            main.addView(line);
            {
                TextView textView = new TextView(this);
                textView.setText(R.string.visit_github);
                line.addView(textView);
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Uri uri = Uri.parse("https://github.com/xzr467706992/PerfMon-Plus");
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                    }
                });
            }

            {
                TextView textView = new TextView(this);
                textView.setText(R.string.visit_coolapk);
                line.addView(textView);
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Uri uri = Uri.parse("https://www.coolapk.com/apk/xzr.perfmon");
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                    }
                });
            }
        }
    }

    void permissioncheck(){

        if (Build.VERSION.SDK_INT >= 23) {
            if (Settings.canDrawOverlays(MainActivity.this)) {
                Intent intent = new Intent(MainActivity.this, FloatingWindow.class);
                startService(intent);
            } else {
                /*Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                Toast.makeText(MainActivity.this,"需要取得权限以使用悬浮窗",Toast.LENGTH_SHORT).show();
                startActivity(intent);
                */
                try {
                    Class clazz = Settings.class;
                    Field field = clazz.getDeclaredField("ACTION_MANAGE_OVERLAY_PERMISSION");
                    Intent intent = new Intent(field.get(null).toString());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setData(Uri.parse("package:" + this.getPackageName()));
                    this.startActivity(intent);
                }
                catch (Exception e){}
            }
        } else {
            //SDK在23以下，不用管.
            Intent intent = new Intent(MainActivity.this, FloatingWindow.class);
            startService(intent);
        }
    }
}
