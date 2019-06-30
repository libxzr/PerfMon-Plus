package xzr.perfmon;

public class JniTools {
    static {
        System.loadLibrary("tools");
    }
    public static native int getcpufreq(int cpu);
    public static native int getadrenofreq();
    public static native int getadrenoload();
    public static native int getmincpubw();
    public static native int getcpubw();
    public static native int getm4m();
    public static native int getcpuload(int cpu);
    public static native int getcpuonlinestatus(int cpu);
    public static native int getmaxtemp();
}
