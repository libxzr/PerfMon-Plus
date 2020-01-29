package xzr.perfmon;

import android.content.Context;

import java.io.BufferedReader;
import java.io.InputStreamReader;

class Tools {
    static String format_ify_add_blank(String in){
        String s="";
        for (int i=0;i<5-in.length();i++){
            s=s+"  ";
        }
        return s;
    }
    static String bool2text(boolean bool, Context context){
        if(bool){
            return context.getResources().getString(R.string.yes);
        }
        return  context.getResources().getString(R.string.no);
    }
}
