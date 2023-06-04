package xzr.perfmon;

public class JniTools {
    static {
        System.loadLibrary("tools");
    }

    public static native int getCpuFreq(int cpu);

    public static native int getAdrenoFreq();

    public static native int getAdrenoLoad();

    public static native int getMinCpuBw();

    public static native int getCpuBw();

    public static native int getLlccBw();

    public static native int getGpuBw();

    public static native int getM4m();

    public static native int getCpuLoad(int cpu);

    public static native boolean checkCpuLoad();

    public static native int getCpuOnlineStatus(int cpu);

    public static native int getMaxTemp();

    public static native int getMemUsage();

    public static native int getCurrent();

    public static native int getCpuNum();

    public static native String getFps();
}
