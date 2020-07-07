package xzr.perfmon;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;

class Settings{
    private static AlertDialog dialog;
    static void creatDialog(Context context){
        dialog=new AlertDialog.Builder(context)
                .setView(settingsView(context))
                .setTitle(R.string.settings)
                .create();
        dialog.show();
    }

    private static View settingsView(final Context context){
        ScrollView scrollView=new ScrollView(context);


        LinearLayout linearLayout=new LinearLayout(context);
        scrollView.addView(linearLayout);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        {
            final Switch sw=new Switch(context);
            linearLayout.addView(sw);
            sw.setText(R.string.skip_first_screen_str);
            if(SharedPreferencesUtil.sharedPreferences.getBoolean(SharedPreferencesUtil.skip_first_screen,SharedPreferencesUtil.default_skip_first_screen))
                sw.setChecked(true);
            sw.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(sw.isChecked()) {
                        new AlertDialog.Builder(context)
                                .setTitle(R.string.notice)
                                .setMessage(R.string.skip_first_screen_str2)
                                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        SharedPreferencesUtil.sharedPreferences.edit().putBoolean(SharedPreferencesUtil.skip_first_screen,true).commit();
                                    }
                                })
                                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        sw.setChecked(false);
                                    }
                                })
                                .create().show();
                    }
                    else{
                        SharedPreferencesUtil.sharedPreferences.edit().putBoolean(SharedPreferencesUtil.skip_first_screen,false).commit();
                    }
                }
            });
        }

        {
            LinearLayout line=new LinearLayout(context);
            linearLayout.addView(line);

            TextView textView=new TextView(context);
            line.addView(textView);
            textView.setText(R.string.refresh_interval);

            EditText editText=new EditText(context);
            line.addView(editText);
            editText.setHint(R.string.default_value);
            editText.setText(SharedPreferencesUtil.sharedPreferences.getInt(SharedPreferencesUtil.delay, SharedPreferencesUtil.default_delay)+"");
            editText.setWidth(500);
            editText.setInputType(InputType.TYPE_CLASS_NUMBER);
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    try{
                        SharedPreferencesUtil.sharedPreferences.edit().putInt(SharedPreferencesUtil.delay,Integer.parseInt(editable.toString())).commit();
                    }
                    catch (Exception e){
                        SharedPreferencesUtil.sharedPreferences.edit().remove(SharedPreferencesUtil.delay).commit();
                    }
                }
            });


        }
        {
            TextView textView=new TextView(context);
            linearLayout.addView(textView);
            textView.setText(R.string.interval_notice);
        }
        {
            final Switch sw=new Switch(context);
            linearLayout.addView(sw);
            sw.setText(R.string.reverse_current);
            if(SharedPreferencesUtil.sharedPreferences.getBoolean(SharedPreferencesUtil.reverse_current,SharedPreferencesUtil.reverse_current_default))
                sw.setChecked(true);
            sw.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(sw.isChecked()) {
                        SharedPreferencesUtil.sharedPreferences.edit().putBoolean(SharedPreferencesUtil.reverse_current,true).commit();
                    }
                    else{
                        SharedPreferencesUtil.sharedPreferences.edit().putBoolean(SharedPreferencesUtil.reverse_current,false).commit();
                    }
                }
            });
        }
        {
            LinearLayout line=new LinearLayout(context);
            linearLayout.addView(line);

            TextView textView=new TextView(context);
            line.addView(textView);
            textView.setText(R.string.size_multiple);

            EditText editText=new EditText(context);
            line.addView(editText);
            editText.setHint(R.string.default_value);
            editText.setText(SharedPreferencesUtil.sharedPreferences.getFloat(SharedPreferencesUtil.size_multiple, SharedPreferencesUtil.size_multiple_default)+"");
            editText.setWidth(500);
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    try{
                        SharedPreferencesUtil.sharedPreferences.edit().putFloat(SharedPreferencesUtil.size_multiple,Float.parseFloat(editable.toString())).commit();
                    }
                    catch (Exception e){
                        SharedPreferencesUtil.sharedPreferences.edit().remove(SharedPreferencesUtil.size_multiple).commit();
                    }
                }
            });


        }
        {
            TextView textView=new TextView(context);
            textView.setText(R.string.moni_ctl);
            linearLayout.addView(textView);
            {
                final Switch sw=new Switch(context);
                linearLayout.addView(sw);
                sw.setText(R.string.show_cpufreq);
                if(SharedPreferencesUtil.sharedPreferences.getBoolean(SharedPreferencesUtil.show_cpufreq,SharedPreferencesUtil.show_cpufreq_default))
                    sw.setChecked(true);
                        sw.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(sw.isChecked()) {
                                    SharedPreferencesUtil.sharedPreferences.edit().putBoolean(SharedPreferencesUtil.show_cpufreq,true).commit();
                                }
                                else{
                                    SharedPreferencesUtil.sharedPreferences.edit().putBoolean(SharedPreferencesUtil.show_cpufreq,false).commit();
                                }
                    }
                });
            }
            {
                final Switch sw=new Switch(context);
                linearLayout.addView(sw);
                sw.setText(R.string.show_cpuload);
                if(SharedPreferencesUtil.sharedPreferences.getBoolean(SharedPreferencesUtil.show_cpuload,SharedPreferencesUtil.show_cpuload_default))
                    sw.setChecked(true);
                sw.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(sw.isChecked()) {
                            SharedPreferencesUtil.sharedPreferences.edit().putBoolean(SharedPreferencesUtil.show_cpuload,true).commit();
                        }
                        else{
                            SharedPreferencesUtil.sharedPreferences.edit().putBoolean(SharedPreferencesUtil.show_cpuload,false).commit();
                        }
                    }
                });
            }
            {
                final Switch sw=new Switch(context);
                linearLayout.addView(sw);
                sw.setText(R.string.show_gpufreq);
                if(SharedPreferencesUtil.sharedPreferences.getBoolean(SharedPreferencesUtil.show_gpufreq,SharedPreferencesUtil.show_gpufreq_default))
                    sw.setChecked(true);
                sw.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(sw.isChecked()) {
                            SharedPreferencesUtil.sharedPreferences.edit().putBoolean(SharedPreferencesUtil.show_gpufreq,true).commit();
                        }
                        else{
                            SharedPreferencesUtil.sharedPreferences.edit().putBoolean(SharedPreferencesUtil.show_gpufreq,false).commit();
                        }
                    }
                });
            }
            {
                final Switch sw=new Switch(context);
                linearLayout.addView(sw);
                sw.setText(R.string.show_gpuload);
                if(SharedPreferencesUtil.sharedPreferences.getBoolean(SharedPreferencesUtil.show_gpuload,SharedPreferencesUtil.show_gpuload_default))
                    sw.setChecked(true);
                sw.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(sw.isChecked()) {
                            SharedPreferencesUtil.sharedPreferences.edit().putBoolean(SharedPreferencesUtil.show_gpuload,true).commit();
                        }
                        else{
                            SharedPreferencesUtil.sharedPreferences.edit().putBoolean(SharedPreferencesUtil.show_gpuload,false).commit();
                        }
                    }
                });
            }
            {
                final Switch sw=new Switch(context);
                linearLayout.addView(sw);
                sw.setText(R.string.show_cpubw);
                if(SharedPreferencesUtil.sharedPreferences.getBoolean(SharedPreferencesUtil.show_cpubw,SharedPreferencesUtil.show_cpubw_default))
                    sw.setChecked(true);
                sw.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(sw.isChecked()) {
                            SharedPreferencesUtil.sharedPreferences.edit().putBoolean(SharedPreferencesUtil.show_cpubw,true).commit();
                        }
                        else{
                            SharedPreferencesUtil.sharedPreferences.edit().putBoolean(SharedPreferencesUtil.show_cpubw,false).commit();
                        }
                    }
                });
            }
            {
                final Switch sw=new Switch(context);
                linearLayout.addView(sw);
                sw.setText(R.string.show_gpubw);
                if(SharedPreferencesUtil.sharedPreferences.getBoolean(SharedPreferencesUtil.show_gpubw,SharedPreferencesUtil.show_gpubw_default))
                    sw.setChecked(true);
                sw.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(sw.isChecked()) {
                            SharedPreferencesUtil.sharedPreferences.edit().putBoolean(SharedPreferencesUtil.show_gpubw,true).commit();
                        }
                        else{
                            SharedPreferencesUtil.sharedPreferences.edit().putBoolean(SharedPreferencesUtil.show_gpubw,false).commit();
                        }
                    }
                });
            }
            {
                final Switch sw=new Switch(context);
                linearLayout.addView(sw);
                sw.setText(R.string.show_mincpubw);
                if(SharedPreferencesUtil.sharedPreferences.getBoolean(SharedPreferencesUtil.show_mincpubw,SharedPreferencesUtil.show_mincpubw_default))
                    sw.setChecked(true);
                sw.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(sw.isChecked()) {
                            SharedPreferencesUtil.sharedPreferences.edit().putBoolean(SharedPreferencesUtil.show_mincpubw,true).commit();
                        }
                        else{
                            SharedPreferencesUtil.sharedPreferences.edit().putBoolean(SharedPreferencesUtil.show_mincpubw,false).commit();
                        }
                    }
                });
            }
            {
                final Switch sw=new Switch(context);
                linearLayout.addView(sw);
                sw.setText(R.string.show_llcbw);
                if(SharedPreferencesUtil.sharedPreferences.getBoolean(SharedPreferencesUtil.show_llcbw,SharedPreferencesUtil.show_llcbw_default))
                    sw.setChecked(true);
                sw.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(sw.isChecked()) {
                            SharedPreferencesUtil.sharedPreferences.edit().putBoolean(SharedPreferencesUtil.show_llcbw,true).commit();
                        }
                        else{
                            SharedPreferencesUtil.sharedPreferences.edit().putBoolean(SharedPreferencesUtil.show_llcbw,false).commit();
                        }
                    }
                });
            }
            {
                final Switch sw=new Switch(context);
                linearLayout.addView(sw);
                sw.setText(R.string.show_m4m);
                if(SharedPreferencesUtil.sharedPreferences.getBoolean(SharedPreferencesUtil.show_m4m,SharedPreferencesUtil.show_m4m_default))
                    sw.setChecked(true);
                sw.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(sw.isChecked()) {
                            SharedPreferencesUtil.sharedPreferences.edit().putBoolean(SharedPreferencesUtil.show_m4m,true).commit();
                        }
                        else{
                            SharedPreferencesUtil.sharedPreferences.edit().putBoolean(SharedPreferencesUtil.show_m4m,false).commit();
                        }
                    }
                });
            }
            {
                final Switch sw=new Switch(context);
                linearLayout.addView(sw);
                sw.setText(R.string.show_thermal);
                if(SharedPreferencesUtil.sharedPreferences.getBoolean(SharedPreferencesUtil.show_thermal,SharedPreferencesUtil.show_thermal_default))
                    sw.setChecked(true);
                sw.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(sw.isChecked()) {
                            SharedPreferencesUtil.sharedPreferences.edit().putBoolean(SharedPreferencesUtil.show_thermal,true).commit();
                        }
                        else{
                            SharedPreferencesUtil.sharedPreferences.edit().putBoolean(SharedPreferencesUtil.show_thermal,false).commit();
                        }
                    }
                });
            }
            {
                final Switch sw=new Switch(context);
                linearLayout.addView(sw);
                sw.setText(R.string.show_mem);
                if(SharedPreferencesUtil.sharedPreferences.getBoolean(SharedPreferencesUtil.show_mem,SharedPreferencesUtil.show_mem_default))
                    sw.setChecked(true);
                sw.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(sw.isChecked()) {
                            SharedPreferencesUtil.sharedPreferences.edit().putBoolean(SharedPreferencesUtil.show_mem,true).commit();
                        }
                        else{
                            SharedPreferencesUtil.sharedPreferences.edit().putBoolean(SharedPreferencesUtil.show_mem,false).commit();
                        }
                    }
                });
            }
            {
                final Switch sw=new Switch(context);
                linearLayout.addView(sw);
                sw.setText(R.string.show_current);
                if(SharedPreferencesUtil.sharedPreferences.getBoolean(SharedPreferencesUtil.show_current,SharedPreferencesUtil.show_current_default))
                    sw.setChecked(true);
                sw.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(sw.isChecked()) {
                            SharedPreferencesUtil.sharedPreferences.edit().putBoolean(SharedPreferencesUtil.show_current,true).commit();
                        }
                        else{
                            SharedPreferencesUtil.sharedPreferences.edit().putBoolean(SharedPreferencesUtil.show_current,false).commit();
                        }
                    }
                });
            }
            {
                final Switch sw=new Switch(context);
                linearLayout.addView(sw);
                sw.setText(R.string.show_fps);
                if(SharedPreferencesUtil.sharedPreferences.getBoolean(SharedPreferencesUtil.show_fps,SharedPreferencesUtil.show_fps_default))
                    sw.setChecked(true);
                sw.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(sw.isChecked()) {
                            SharedPreferencesUtil.sharedPreferences.edit().putBoolean(SharedPreferencesUtil.show_fps,true).commit();
                        }
                        else{
                            SharedPreferencesUtil.sharedPreferences.edit().putBoolean(SharedPreferencesUtil.show_fps,false).commit();
                        }
                    }
                });
            }
        }
        {
            LinearLayout line=new LinearLayout(context);
            linearLayout.addView(line);

            TextView textView=new TextView(context);
            line.addView(textView);
            textView.setText(R.string.window_width);
            EditText editText=new EditText(context);
            line.addView(editText);
            editText.setHint(R.string.default_value);
            int width=SharedPreferencesUtil.sharedPreferences.getInt(SharedPreferencesUtil.width, SharedPreferencesUtil.default_width);
            if (width!=-1)
                editText.setText(width+"");
            editText.setWidth(500);
            editText.setInputType(InputType.TYPE_CLASS_NUMBER);
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    try{
                        SharedPreferencesUtil.sharedPreferences.edit().putInt(SharedPreferencesUtil.width,Integer.parseInt(editable.toString())).commit();
                    }
                    catch (Exception e){
                        SharedPreferencesUtil.sharedPreferences.edit().remove(SharedPreferencesUtil.width).commit();
                    }

                }
            });


        }

        {
            LinearLayout line=new LinearLayout(context);
            linearLayout.addView(line);

            TextView textView=new TextView(context);
            line.addView(textView);
            textView.setText(R.string.window_height);

            EditText editText=new EditText(context);
            line.addView(editText);
            editText.setHint(R.string.default_value);
            int height=SharedPreferencesUtil.sharedPreferences.getInt(SharedPreferencesUtil.height, SharedPreferencesUtil.default_height);
            if (height!=-1)
                editText.setText(height+"");
            editText.setWidth(500);
            editText.setInputType(InputType.TYPE_CLASS_NUMBER);
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    try{
                        SharedPreferencesUtil.sharedPreferences.edit().putInt(SharedPreferencesUtil.height,Integer.parseInt(editable.toString())).commit();
                    }
                    catch (Exception e){
                        SharedPreferencesUtil.sharedPreferences.edit().remove(SharedPreferencesUtil.height).commit();
                    }

                }
            });


        }
        return scrollView;
    }
}
