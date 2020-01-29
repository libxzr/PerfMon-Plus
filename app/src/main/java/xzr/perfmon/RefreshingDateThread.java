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
    static int gpubw;
    static int llcbw;

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
                    if(cpuonline[i]==1&&FloatingWindow.show_cpufreq_now)
                    cpufreq[i]=JniTools.getcpufreq(i);
                }
                if(FloatingWindow.show_gpufreq_now)
                    adrenofreq=JniTools.getadrenofreq();
                if(FloatingWindow.show_gpuload_now)
                    adrenoload=JniTools.getadrenoload();
                if(FloatingWindow.show_mincpubw_now)
                    mincpubw=JniTools.getmincpubw();
                if(FloatingWindow.show_cpubw_now)
                    cpubw=JniTools.getcpubw();
                if(FloatingWindow.show_m4m_now)
                    m4m=JniTools.getm4m();
                if(FloatingWindow.show_thermal_now)
                    maxtemp=JniTools.getmaxtemp();
                if(FloatingWindow.show_mem_now)
                    memusage=JniTools.getmemusage();
                if(FloatingWindow.show_current_now)
                    current=JniTools.getcurrent();
                if(FloatingWindow.show_gpubw_now)
                    gpubw=JniTools.getgpubw();
                if(FloatingWindow.show_llcbw_now)
                    llcbw=JniTools.getllcbw();
                if(reverse_current_now)
                    current=-current;
                FloatingWindow.ui_refresher.sendEmptyMessage(0);
                for (int i=0;i<cpunum;i++){
                    if(cpuonline[i]==1&&FloatingWindow.show_cpuload_now) {
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
