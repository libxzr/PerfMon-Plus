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
    static String fps;

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
                if(FloatingWindow.show_gpufreq_now&&Support.support_adrenofreq)
                    adrenofreq=JniTools.getadrenofreq();
                if(FloatingWindow.show_gpuload_now&&Support.support_adrenofreq)
                    adrenoload=JniTools.getadrenoload();
                if(FloatingWindow.show_mincpubw_now&&Support.support_mincpubw)
                    mincpubw=JniTools.getmincpubw();
                if(FloatingWindow.show_cpubw_now&&Support.support_cpubw)
                    cpubw=JniTools.getcpubw();
                if(FloatingWindow.show_m4m_now&&Support.support_m4m)
                    m4m=JniTools.getm4m();
                if(FloatingWindow.show_thermal_now&&Support.support_temp)
                    maxtemp=JniTools.getmaxtemp();
                if(FloatingWindow.show_mem_now&&Support.support_mem)
                    memusage=JniTools.getmemusage();
                if(FloatingWindow.show_current_now&&Support.support_current)
                    current=JniTools.getcurrent();
                if(FloatingWindow.show_gpubw_now&&Support.support_gpubw)
                    gpubw=JniTools.getgpubw();
                if(FloatingWindow.show_llcbw_now&&Support.support_llcbw)
                    llcbw=JniTools.getllcbw();
            if(FloatingWindow.show_fps_now&&Support.support_fps)
                fps=JniTools.getfps();
                if(reverse_current_now)
                    current=-current;
                FloatingWindow.ui_refresher.sendEmptyMessage(0);
                for (int i=0;i<cpunum;i++){
                    if(cpuonline[i]==1&&FloatingWindow.show_cpuload_now&&Support.support_cpuload) {
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
