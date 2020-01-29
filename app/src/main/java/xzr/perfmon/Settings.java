package xzr.perfmon;

import android.app.AlertDialog;
import android.content.Context;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
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

        return linearLayout;
    }
}
