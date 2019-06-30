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
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;

public class MainActivity extends Activity {
    LinearLayout main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(!FloatingWindow.do_exit){
            Toast.makeText(MainActivity.this,"请先关闭悬浮窗 再打开软件",Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        main=findViewById(R.id.mainlayout);

        //Move from service:onCreate in order to show the supporting list
        RefreshingDateThread.cpunum=Tools.getCpuNum();
        FloatingWindow.linen=Support.CheckSupport();

        addview();

    }

    void addview(){
        {
            TextView textView=new TextView(this);
            textView.setText("支持cpu频率监控："+Tools.bool2text(Support.support_cpufreq));
            main.addView(textView);
        }
        {
            TextView textView=new TextView(this);
            textView.setText("支持cpu负载监控："+Tools.bool2text(Support.support_cpuload));
            main.addView(textView);
        }
        {
            TextView textView=new TextView(this);
            textView.setText("支持gpu频率监控："+Tools.bool2text(Support.support_adrenofreq));
            main.addView(textView);
        }
        {
            TextView textView=new TextView(this);
            textView.setText("支持gpu负载监控："+Tools.bool2text(Support.support_adrenofreq));
            main.addView(textView);
        }
        {
            TextView textView=new TextView(this);
            textView.setText("支持cpubw频率监控："+Tools.bool2text(Support.support_cpubw));
            main.addView(textView);
        }
        {
            TextView textView=new TextView(this);
            textView.setText("支持m4m频率监控："+Tools.bool2text(Support.support_m4m));
            main.addView(textView);
        }
        {
            TextView textView=new TextView(this);
            textView.setText("不支持的项目可能是你的设备真的不支持，也有可能是因为SELinux的阻挠。");
            main.addView(textView);
        }
        {
            Button button = new Button(this);
            button.setText("打开悬浮窗");
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
            Button button=new Button(this);
            button.setText("关闭SELinux(ROOT)");
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
                            Toast.makeText(MainActivity.this,"执行完毕 请重新打开软件",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(MainActivity.this,log,Toast.LENGTH_SHORT).show();
                        }
                        finish();
                    }
                    catch (Exception e){
                        Toast.makeText(MainActivity.this,"没有ROOT权限",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        {
            TextView textView=new TextView(this);
            textView.setText("* 并不是真正的关闭SELinux 仅仅只是make it permissive\n* 此操作可能在一些设备上诱发崩溃");
            main.addView(textView);
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
