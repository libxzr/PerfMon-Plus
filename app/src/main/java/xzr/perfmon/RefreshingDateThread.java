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
    public void run(){
        cpufreq=new int[cpunum];
        cpuload=new int[cpunum];
        cpuonline=new int[cpunum];
        while (!FloatingWindow.do_exit){
                for (int i=0;i<cpunum;i++){
                    cpufreq[i]=JniTools.getcpufreq(i);
                    cpuonline[i]=JniTools.getcpuonlinestatus(i);
                }
                adrenofreq=JniTools.getadrenofreq();
                adrenoload=JniTools.getadrenoload();
                mincpubw=JniTools.getmincpubw();
                cpubw=JniTools.getcpubw();
                m4m=JniTools.getm4m();
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
                    Thread.sleep(1000);
                }
                catch (Exception e){

                }
        }
    }
}
