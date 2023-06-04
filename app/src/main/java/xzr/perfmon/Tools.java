package xzr.perfmon;

import android.content.Context;

class Tools {
    static String formatIfyAddBlank(String in) {
        String s = "";
        for (int i = 0; i < 5 - in.length(); i++) {
            s = s + "  ";
        }
        return s;
    }

    static String bool2Text(boolean bool, Context context) {
        if (bool) {
            return context.getResources().getString(R.string.yes);
        }
        return context.getResources().getString(R.string.no);
    }
}
