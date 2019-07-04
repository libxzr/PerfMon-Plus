package xzr.perfmon;

class Support {
    static boolean support_cpufreq;
    static boolean support_cpuload;
    static boolean support_adrenofreq;
    static boolean support_mincpubw;
    static boolean support_cpubw;
    static boolean support_m4m;
    static boolean support_temp;
    static boolean support_mem;

    static final int UNSUPPORTED=-1;

    static int CheckSupport(){
        int linen=0;

        if(JniTools.getcpufreq(0)!=UNSUPPORTED) {
            linen = linen + RefreshingDateThread.cpunum;
            support_cpufreq=true;
        }
        else support_cpufreq=false;

        support_cpuload=JniTools.checkcpuload();

        if(JniTools.getadrenofreq()!=UNSUPPORTED) {
            linen++;
            support_adrenofreq=true;
        }
        else support_adrenofreq=false;

        /*
        if(JniTools.getmincpubw()!=UNSUPPORTED) {
            linen++;
            support_mincpubw=true;
        }
        else support_mincpubw=false;
        */

        if(JniTools.getcpubw()!=UNSUPPORTED) {
            linen++;
            support_cpubw=true;
        }
        else support_cpubw=false;

        if(JniTools.getm4m()!=UNSUPPORTED) {
            linen++;
            support_m4m=true;
        }
        else support_m4m=false;

        if(JniTools.getmaxtemp()!=UNSUPPORTED) {
            linen++;
            support_temp=true;
        }
        else support_temp=false;

        if(JniTools.getmemusage()!=UNSUPPORTED) {
            linen++;
            support_mem=true;
        }
        else support_mem=false;

        return linen;
    }

}
