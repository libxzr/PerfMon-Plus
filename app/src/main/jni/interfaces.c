//
// Created by xzr on 2019/6/23.
//

#include "xzr_perfmon_JniTools.h"
#include "perfmon.h"
#include <stdio.h>
#include <unistd.h>
#include <math.h>

JNIEXPORT jint JNICALL Java_xzr_perfmon_JniTools_getcpufreq
        (JNIEnv *env, jclass jclass1, jint cpu){
    char path[DEFAULT_PATH_SIZE];
    int freq;
    sprintf(path,"/sys/devices/system/cpu/cpu%d/cpufreq/scaling_cur_freq",cpu);
    if(readfileint(path,&freq))
        return UNSUPPORTED;

    return freq/1000;
}

JNIEXPORT jint JNICALL Java_xzr_perfmon_JniTools_getadrenofreq
        (JNIEnv *env, jclass jclass1){
    int freq;
    if(readfileint("/sys/kernel/gpu/gpu_clock",&freq))
        return UNSUPPORTED;

    return freq;
}

JNIEXPORT jint JNICALL Java_xzr_perfmon_JniTools_getadrenoload
        (JNIEnv *env, jclass jclass1){
    int freq;
    if(readfileint("/sys/kernel/gpu/gpu_busy",&freq))
        return UNSUPPORTED;

    return freq;
}

JNIEXPORT jint JNICALL Java_xzr_perfmon_JniTools_getmincpubw
        (JNIEnv *env, jclass jclass1){
    int freq;

    if(readfileint("/sys/class/devfreq/soc:qcom,mincpubw/cur_freq",&freq))
        return UNSUPPORTED;

    return freq;
}

JNIEXPORT jint JNICALL Java_xzr_perfmon_JniTools_getcpubw
        (JNIEnv *env, jclass jclass1){
    int freq;

    if(!readfileint("/sys/class/devfreq/soc:qcom,cpubw/cur_freq",&freq))
        return freq;

    if(!readfileint("/sys/class/devfreq/soc:qcom,cpu-cpu-llcc-bw/cur_freq",&freq))
        return freq;

    return UNSUPPORTED;
}

JNIEXPORT jint JNICALL Java_xzr_perfmon_JniTools_getm4m
        (JNIEnv *env, jclass jclass1){
    int freq;

    if(readfileint("/sys/class/devfreq/soc:qcom,m4m/cur_freq",&freq))
        return UNSUPPORTED;

    return freq/1000;
}

JNIEXPORT jint JNICALL Java_xzr_perfmon_JniTools_getcpuload
        (JNIEnv *env, jclass jclass1, jint cpu) {
    int time1=0,time2=0,idle1=0,idle2=0;
    if(getCpuTime(cpu,&time1,&idle1))
        return UNSUPPORTED;
    usleep(pow(1*10,6));
    if(getCpuTime(cpu,&time2,&idle2))
        return UNSUPPORTED;
    return (int)((1-(((float)idle2-idle1)/(time2-time1)))*100);
}

JNIEXPORT jint JNICALL Java_xzr_perfmon_JniTools_getcpuonlinestatus
        (JNIEnv *env, jclass jclass1, jint cpu){
    int status;
    char path[DEFAULT_PATH_SIZE];

    sprintf(path,"/sys/devices/system/cpu/cpu%d/online",cpu);
    if(readfileint(path,&status))
        return UNSUPPORTED;

    return status;
}

JNIEXPORT jint JNICALL Java_xzr_perfmon_JniTools_getmaxtemp
        (JNIEnv *env, jclass jclass1){
    int temp;
    if(getmaxtemp(&temp)||temp==NULLTEMP)
        return UNSUPPORTED;

    return temp;
}

JNIEXPORT jboolean JNICALL Java_xzr_perfmon_JniTools_checkcpuload
        (JNIEnv *env, jclass jclass1){
    FILE *file=fopen("/proc/stat","r");
    if (file==NULL)
        return 0;
    return 1;
}

JNIEXPORT jint JNICALL Java_xzr_perfmon_JniTools_getmemusage
        (JNIEnv *env, jclass jclass1){
    int total=-1;
    int available=-1;

    if(getmeminfo("MemTotal",&total))
        return UNSUPPORTED;

    if(getmeminfo("MemAvailable",&available))
        return UNSUPPORTED;

    return (int)((1-(float)available/total)*100);
}

JNIEXPORT jint JNICALL Java_xzr_perfmon_JniTools_getcurrent
        (JNIEnv *env, jclass jclass1){
    int current;

    if(readfileint("/sys/class/power_supply/battery/current_now",&current))
        return UNSUPPORTED;

    return -current/1000;
}

JNIEXPORT jint JNICALL Java_xzr_perfmon_JniTools_getcpunum
        (JNIEnv *env, jclass jclass1){
    int cpunum;
    if(readprocessint("ls /sys/devices/system/cpu | grep -o \"cpu[0-9]*$\" | wc -l",&cpunum))
        return 0;
    return cpunum;
}

JNIEXPORT jint JNICALL Java_xzr_perfmon_JniTools_getgpubw
        (JNIEnv *env, jclass jclass1){
    int freq;

    if(readfileint("/sys/class/devfreq/soc:qcom,gpubw/cur_freq",&freq))
        return UNSUPPORTED;

    return freq;
}

JNIEXPORT jint JNICALL Java_xzr_perfmon_JniTools_getllcbw
        (JNIEnv *env, jclass jclass1){
    int freq;

    if(!readfileint("/sys/class/devfreq/soc:qcom,cpu-llcc-ddr-bw/cur_freq",&freq))
        return freq;

    if(!readfileint("/sys/class/devfreq/soc:qcom,llccbw/cur_freq",&freq))
        return freq;

    return UNSUPPORTED;
}

JNIEXPORT jstring JNICALL Java_xzr_perfmon_JniTools_getfps
        (JNIEnv *env, jclass jclass1){
    char fps[5]="";

    if(!readfilestr("/sys/devices/virtual/graphics/fb0/measured_fps",&fps))
        return (*env)->NewStringUTF(env,&fps);

    if(!readprocessstr("cat /sys/class/drm/sde-crtc-0/measured_fps | awk '{print $2}'",&fps))
        return (*env)->NewStringUTF(env,&fps);

    return (*env)->NewStringUTF(env,"");
}
