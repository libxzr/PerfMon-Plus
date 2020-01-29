package xzr.perfmon;

public class RefreshingDateThread extends Thread {
    static int cpunum;
    static int[] cpufreq;
    static int[] cpuload;
    static int[] cpuonline;
    static int adrenoload;
    static int adrenofreq;
    static int mincpubw;
    static int cpubw;
    static int m4m;
    static int maxtemp;
    static int memusage;
    static int current;

    static int delay;
    static boolean reverse_current_now;
    public void run(){
        delay= SharedPreferencesUtil.sharedPreferences.getInt(SharedPreferencesUtil.delay, SharedPreferencesUtil.default_delay);
        reverse_current_now=SharedPreferencesUtil.sharedPreferences.getBoolean(SharedPreferencesUtil.reverse_current,SharedPreferencesUtil.reverse_current_default);
        cpufreq=new int[cpunum];
        cpuload=new int[cpunum];
        cpuonline=new int[cpunum];
        while (!FloatingWindow.do_exit){
                for (int i=0;i<cpunum;i++){
                    cpuonline[i]=JniTools.getcpuonlinestatus(i);
                    if(cpuonline[i]==1)
                    cpufreq[i]=JniTools.getcpufreq(i);
                }
                adrenofreq=JniTools.getadrenofreq();
                adrenoload=JniTools.getadrenoload();
                mincpubw=JniTools.getmincpubw();
                cpubw=JniTools.getcpubw();
                m4m=JniTools.getm4m();
                maxtemp=JniTools.getmaxtemp();
                memusage=JniTools.getmemusage();
                current=JniTools.getcurrent();
                if(reverse_current_now)
                    current=-current;
                FloatingWindow.ui_refresher.sendEmptyMessage(0);
                for (int i=0;i<cpunum;i++){
                    if(cpuonline[i]==1) {
                        final int ii=i;
                        new Thread() {
                            public void run() {
                                cpuload[ii] = JniTools.getcpuload(ii);
                            }
                        }.start();
                    }
                }
                try {
                    Thread.sleep(delay);
                }
                catch (Exception e){

                }
        }
    }
}
