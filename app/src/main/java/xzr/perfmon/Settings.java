package xzr.perfmon;

import android.app.AlertDialog;
import android.content.Context;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
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

    private static View settingsView(Context context){
        LinearLayout linearLayout=new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

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
        }

        return linearLayout;
    }
}
