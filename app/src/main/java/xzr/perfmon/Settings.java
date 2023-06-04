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

class Settings {
    private static AlertDialog dialog;

    static void createDialog(Context context) {
        dialog = new AlertDialog.Builder(context)
                .setView(settingsView(context))
                .setTitle(R.string.settings)
                .create();
        dialog.show();
    }

    private static View settingsView(final Context context) {
        ScrollView scrollView = new ScrollView(context);


        LinearLayout linearLayout = new LinearLayout(context);
        scrollView.addView(linearLayout);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        {
            final Switch sw = new Switch(context);
            linearLayout.addView(sw);
            sw.setText(R.string.skip_first_screen_str);
            if (SharedPreferencesUtil.sharedPreferences.getBoolean(SharedPreferencesUtil.SKIP_FIRST_SCREEN, SharedPreferencesUtil.DEFAULT_SKIP_FIRST_SCREEN))
                sw.setChecked(true);
            sw.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (sw.isChecked()) {
                        new AlertDialog.Builder(context)
                                .setTitle(R.string.notice)
                                .setMessage(R.string.skip_first_screen_str2)
                                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        SharedPreferencesUtil.sharedPreferences.edit().putBoolean(SharedPreferencesUtil.SKIP_FIRST_SCREEN, true).commit();
                                    }
                                })
                                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        sw.setChecked(false);
                                    }
                                })
                                .create().show();
                    } else {
                        SharedPreferencesUtil.sharedPreferences.edit().putBoolean(SharedPreferencesUtil.SKIP_FIRST_SCREEN, false).commit();
                    }
                }
            });
        }

        {
            LinearLayout line = new LinearLayout(context);
            linearLayout.addView(line);

            TextView textView = new TextView(context);
            line.addView(textView);
            textView.setText(R.string.refresh_interval);

            EditText editText = new EditText(context);
            line.addView(editText);
            editText.setHint(R.string.default_value);
            editText.setText(SharedPreferencesUtil.sharedPreferences.getInt(SharedPreferencesUtil.REFRESHING_DELAY, SharedPreferencesUtil.DEFAULT_DELAY) + "");
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
                    try {
                        SharedPreferencesUtil.sharedPreferences.edit().putInt(SharedPreferencesUtil.REFRESHING_DELAY, Integer.parseInt(editable.toString())).commit();
                    } catch (Exception e) {
                        SharedPreferencesUtil.sharedPreferences.edit().remove(SharedPreferencesUtil.REFRESHING_DELAY).commit();
                    }
                }
            });


        }
        {
            TextView textView = new TextView(context);
            linearLayout.addView(textView);
            textView.setText(R.string.interval_notice);
        }
        {
            final Switch sw = new Switch(context);
            linearLayout.addView(sw);
            sw.setText(R.string.reverse_current);
            if (SharedPreferencesUtil.sharedPreferences.getBoolean(SharedPreferencesUtil.REVERSE_CURRENT, SharedPreferencesUtil.REVERSE_CURRENT_DEFAULT))
                sw.setChecked(true);
            sw.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (sw.isChecked()) {
                        SharedPreferencesUtil.sharedPreferences.edit().putBoolean(SharedPreferencesUtil.REVERSE_CURRENT, true).commit();
                    } else {
                        SharedPreferencesUtil.sharedPreferences.edit().putBoolean(SharedPreferencesUtil.REVERSE_CURRENT, false).commit();
                    }
                }
            });
        }
        {
            LinearLayout line = new LinearLayout(context);
            linearLayout.addView(line);

            TextView textView = new TextView(context);
            line.addView(textView);
            textView.setText(R.string.size_multiple);

            EditText editText = new EditText(context);
            line.addView(editText);
            editText.setHint(R.string.default_value);
            editText.setText(SharedPreferencesUtil.sharedPreferences.getFloat(SharedPreferencesUtil.SIZE_MULTIPLE, SharedPreferencesUtil.SIZE_MULTIPLE_DEFAULT) + "");
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
                    try {
                        SharedPreferencesUtil.sharedPreferences.edit().putFloat(SharedPreferencesUtil.SIZE_MULTIPLE, Float.parseFloat(editable.toString())).commit();
                    } catch (Exception e) {
                        SharedPreferencesUtil.sharedPreferences.edit().remove(SharedPreferencesUtil.SIZE_MULTIPLE).commit();
                    }
                }
            });


        }
        {
            TextView textView = new TextView(context);
            textView.setText(R.string.moni_ctl);
            linearLayout.addView(textView);
            {
                final Switch sw = new Switch(context);
                linearLayout.addView(sw);
                sw.setText(R.string.show_cpufreq);
                if (SharedPreferencesUtil.sharedPreferences.getBoolean(SharedPreferencesUtil.SHOW_CPUFREQ, SharedPreferencesUtil.SHOW_CPUFREQ_DEFAULT))
                    sw.setChecked(true);
                sw.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (sw.isChecked()) {
                            SharedPreferencesUtil.sharedPreferences.edit().putBoolean(SharedPreferencesUtil.SHOW_CPUFREQ, true).commit();
                        } else {
                            SharedPreferencesUtil.sharedPreferences.edit().putBoolean(SharedPreferencesUtil.SHOW_CPUFREQ, false).commit();
                        }
                    }
                });
            }
            {
                final Switch sw = new Switch(context);
                linearLayout.addView(sw);
                sw.setText(R.string.show_cpuload);
                if (SharedPreferencesUtil.sharedPreferences.getBoolean(SharedPreferencesUtil.SHOW_CPULOAD, SharedPreferencesUtil.SHOW_CPULOAD_DEFAULT))
                    sw.setChecked(true);
                sw.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (sw.isChecked()) {
                            SharedPreferencesUtil.sharedPreferences.edit().putBoolean(SharedPreferencesUtil.SHOW_CPULOAD, true).commit();
                        } else {
                            SharedPreferencesUtil.sharedPreferences.edit().putBoolean(SharedPreferencesUtil.SHOW_CPULOAD, false).commit();
                        }
                    }
                });
            }
            {
                final Switch sw = new Switch(context);
                linearLayout.addView(sw);
                sw.setText(R.string.show_gpufreq);
                if (SharedPreferencesUtil.sharedPreferences.getBoolean(SharedPreferencesUtil.SHOW_GPUFREQ, SharedPreferencesUtil.SHOW_GPUFREQ_DEFAULT))
                    sw.setChecked(true);
                sw.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (sw.isChecked()) {
                            SharedPreferencesUtil.sharedPreferences.edit().putBoolean(SharedPreferencesUtil.SHOW_GPUFREQ, true).commit();
                        } else {
                            SharedPreferencesUtil.sharedPreferences.edit().putBoolean(SharedPreferencesUtil.SHOW_GPUFREQ, false).commit();
                        }
                    }
                });
            }
            {
                final Switch sw = new Switch(context);
                linearLayout.addView(sw);
                sw.setText(R.string.show_gpuload);
                if (SharedPreferencesUtil.sharedPreferences.getBoolean(SharedPreferencesUtil.SHOW_GPULOAD, SharedPreferencesUtil.SHOW_GPULOAD_DEFAULT))
                    sw.setChecked(true);
                sw.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (sw.isChecked()) {
                            SharedPreferencesUtil.sharedPreferences.edit().putBoolean(SharedPreferencesUtil.SHOW_GPULOAD, true).commit();
                        } else {
                            SharedPreferencesUtil.sharedPreferences.edit().putBoolean(SharedPreferencesUtil.SHOW_GPULOAD, false).commit();
                        }
                    }
                });
            }
            {
                final Switch sw = new Switch(context);
                linearLayout.addView(sw);
                sw.setText(R.string.show_cpubw);
                if (SharedPreferencesUtil.sharedPreferences.getBoolean(SharedPreferencesUtil.SHOW_CPUBW, SharedPreferencesUtil.SHOW_CPUBW_DEFAULT))
                    sw.setChecked(true);
                sw.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (sw.isChecked()) {
                            SharedPreferencesUtil.sharedPreferences.edit().putBoolean(SharedPreferencesUtil.SHOW_CPUBW, true).commit();
                        } else {
                            SharedPreferencesUtil.sharedPreferences.edit().putBoolean(SharedPreferencesUtil.SHOW_CPUBW, false).commit();
                        }
                    }
                });
            }
            {
                final Switch sw = new Switch(context);
                linearLayout.addView(sw);
                sw.setText(R.string.show_gpubw);
                if (SharedPreferencesUtil.sharedPreferences.getBoolean(SharedPreferencesUtil.SHOW_GPUBW, SharedPreferencesUtil.SHOW_GPUBW_DEFAULT))
                    sw.setChecked(true);
                sw.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (sw.isChecked()) {
                            SharedPreferencesUtil.sharedPreferences.edit().putBoolean(SharedPreferencesUtil.SHOW_GPUBW, true).commit();
                        } else {
                            SharedPreferencesUtil.sharedPreferences.edit().putBoolean(SharedPreferencesUtil.SHOW_GPUBW, false).commit();
                        }
                    }
                });
            }
            {
                final Switch sw = new Switch(context);
                linearLayout.addView(sw);
                sw.setText(R.string.show_mincpubw);
                if (SharedPreferencesUtil.sharedPreferences.getBoolean(SharedPreferencesUtil.SHOW_MINCPUBW, SharedPreferencesUtil.SHOW_MINCPUBW_DEFAULT))
                    sw.setChecked(true);
                sw.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (sw.isChecked()) {
                            SharedPreferencesUtil.sharedPreferences.edit().putBoolean(SharedPreferencesUtil.SHOW_MINCPUBW, true).commit();
                        } else {
                            SharedPreferencesUtil.sharedPreferences.edit().putBoolean(SharedPreferencesUtil.SHOW_MINCPUBW, false).commit();
                        }
                    }
                });
            }
            {
                final Switch sw = new Switch(context);
                linearLayout.addView(sw);
                sw.setText(R.string.show_llcbw);
                if (SharedPreferencesUtil.sharedPreferences.getBoolean(SharedPreferencesUtil.SHOW_LLCBW, SharedPreferencesUtil.SHOW_LLCBW_DEFAULT))
                    sw.setChecked(true);
                sw.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (sw.isChecked()) {
                            SharedPreferencesUtil.sharedPreferences.edit().putBoolean(SharedPreferencesUtil.SHOW_LLCBW, true).commit();
                        } else {
                            SharedPreferencesUtil.sharedPreferences.edit().putBoolean(SharedPreferencesUtil.SHOW_LLCBW, false).commit();
                        }
                    }
                });
            }
            {
                final Switch sw = new Switch(context);
                linearLayout.addView(sw);
                sw.setText(R.string.show_m4m);
                if (SharedPreferencesUtil.sharedPreferences.getBoolean(SharedPreferencesUtil.SHOW_M4M, SharedPreferencesUtil.SHOW_M4M_DEFAULT))
                    sw.setChecked(true);
                sw.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (sw.isChecked()) {
                            SharedPreferencesUtil.sharedPreferences.edit().putBoolean(SharedPreferencesUtil.SHOW_M4M, true).commit();
                        } else {
                            SharedPreferencesUtil.sharedPreferences.edit().putBoolean(SharedPreferencesUtil.SHOW_M4M, false).commit();
                        }
                    }
                });
            }
            {
                final Switch sw = new Switch(context);
                linearLayout.addView(sw);
                sw.setText(R.string.show_thermal);
                if (SharedPreferencesUtil.sharedPreferences.getBoolean(SharedPreferencesUtil.SHOW_THERMAL, SharedPreferencesUtil.SHOW_THERMAL_DEFAULT))
                    sw.setChecked(true);
                sw.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (sw.isChecked()) {
                            SharedPreferencesUtil.sharedPreferences.edit().putBoolean(SharedPreferencesUtil.SHOW_THERMAL, true).commit();
                        } else {
                            SharedPreferencesUtil.sharedPreferences.edit().putBoolean(SharedPreferencesUtil.SHOW_THERMAL, false).commit();
                        }
                    }
                });
            }
            {
                final Switch sw = new Switch(context);
                linearLayout.addView(sw);
                sw.setText(R.string.show_mem);
                if (SharedPreferencesUtil.sharedPreferences.getBoolean(SharedPreferencesUtil.SHOW_MEM, SharedPreferencesUtil.SHOW_MEM_DEFAULT))
                    sw.setChecked(true);
                sw.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (sw.isChecked()) {
                            SharedPreferencesUtil.sharedPreferences.edit().putBoolean(SharedPreferencesUtil.SHOW_MEM, true).commit();
                        } else {
                            SharedPreferencesUtil.sharedPreferences.edit().putBoolean(SharedPreferencesUtil.SHOW_MEM, false).commit();
                        }
                    }
                });
            }
            {
                final Switch sw = new Switch(context);
                linearLayout.addView(sw);
                sw.setText(R.string.show_current);
                if (SharedPreferencesUtil.sharedPreferences.getBoolean(SharedPreferencesUtil.SHOW_CURRENT, SharedPreferencesUtil.SHOW_CURRENT_DEFAULT))
                    sw.setChecked(true);
                sw.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (sw.isChecked()) {
                            SharedPreferencesUtil.sharedPreferences.edit().putBoolean(SharedPreferencesUtil.SHOW_CURRENT, true).commit();
                        } else {
                            SharedPreferencesUtil.sharedPreferences.edit().putBoolean(SharedPreferencesUtil.SHOW_CURRENT, false).commit();
                        }
                    }
                });
            }
            {
                final Switch sw = new Switch(context);
                linearLayout.addView(sw);
                sw.setText(R.string.show_fps);
                if (SharedPreferencesUtil.sharedPreferences.getBoolean(SharedPreferencesUtil.SHOW_FPS, SharedPreferencesUtil.SHOW_FPS_DEFAULT))
                    sw.setChecked(true);
                sw.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (sw.isChecked()) {
                            SharedPreferencesUtil.sharedPreferences.edit().putBoolean(SharedPreferencesUtil.SHOW_FPS, true).commit();
                        } else {
                            SharedPreferencesUtil.sharedPreferences.edit().putBoolean(SharedPreferencesUtil.SHOW_FPS, false).commit();
                        }
                    }
                });
            }
        }
        {
            LinearLayout line = new LinearLayout(context);
            linearLayout.addView(line);

            TextView textView = new TextView(context);
            line.addView(textView);
            textView.setText(R.string.window_width);
            EditText editText = new EditText(context);
            line.addView(editText);
            editText.setHint(R.string.default_value);
            int width = SharedPreferencesUtil.sharedPreferences.getInt(SharedPreferencesUtil.WINDOW_WIDTH, SharedPreferencesUtil.DEFAULT_WIDTH);
            if (width != -1)
                editText.setText(width + "");
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
                    try {
                        SharedPreferencesUtil.sharedPreferences.edit().putInt(SharedPreferencesUtil.WINDOW_WIDTH, Integer.parseInt(editable.toString())).commit();
                    } catch (Exception e) {
                        SharedPreferencesUtil.sharedPreferences.edit().remove(SharedPreferencesUtil.WINDOW_WIDTH).commit();
                    }

                }
            });


        }

        {
            LinearLayout line = new LinearLayout(context);
            linearLayout.addView(line);

            TextView textView = new TextView(context);
            line.addView(textView);
            textView.setText(R.string.window_height);

            EditText editText = new EditText(context);
            line.addView(editText);
            editText.setHint(R.string.default_value);
            int height = SharedPreferencesUtil.sharedPreferences.getInt(SharedPreferencesUtil.WINDOW_HEIGHT, SharedPreferencesUtil.DEFAULT_WINDOW_HEIGHT);
            if (height != -1)
                editText.setText(height + "");
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
                    try {
                        SharedPreferencesUtil.sharedPreferences.edit().putInt(SharedPreferencesUtil.WINDOW_HEIGHT, Integer.parseInt(editable.toString())).commit();
                    } catch (Exception e) {
                        SharedPreferencesUtil.sharedPreferences.edit().remove(SharedPreferencesUtil.WINDOW_HEIGHT).commit();
                    }

                }
            });


        }
        return scrollView;
    }
}
