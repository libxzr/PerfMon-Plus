package xzr.perfmon;

public class RefreshingDateThread extends Thread {
    static int cpunum;
    int[] cpufreq;
    int[] cpuload;
    int[] cpuonline;
    public void run(){
        cpufreq=new int[cpunum];
        cpuload=new int[cpunum];
        cpuonline=new int[cpunum];
        while (!FloatingWindow.do_exit){
                for (int i=0;i<cpunum;i++){
                    cpufreq[i]=JniTools.getcpufreq(i);
                    cpuonline[i]=JniTools.getcpuonlinestatus(i);
                }
                    FloatingWindow.refresh_ui(cpufreq,cpuload,cpuonline, JniTools.getadrenofreq(), JniTools.getadrenoload(), JniTools.getmincpubw(), JniTools.getcpubw(), JniTools.getm4m());
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
