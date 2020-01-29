package xzr.perfmon;

import android.content.Context;

import java.io.BufferedReader;
import java.io.InputStreamReader;

class Tools {
    static int getCpuNum(){
        int count=0;
        try {
            Process p = new ProcessBuilder("ls","/sys/devices/system/cpu").start();
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(p.getInputStream()));
            while (true){
                String c=bufferedReader.readLine();
                if(c==null){
                    bufferedReader.close();
                    p.destroy();
                    return count-2;
                }
                if(c.startsWith("cpu"))
                    count++;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return -1;
    }
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
