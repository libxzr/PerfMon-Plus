//
// Created by xzr on 2019/6/28.
//
#include "perfmon.h"
#include <stdio.h>
#include <string.h>

int readfileint(const char *path,int *result){
    FILE *freqfile=fopen(path,"r");
    int ret=0;
    if (freqfile==NULL)
        return UNSUPPORTED;
    fscanf(freqfile,"%d",result);
    fclose(freqfile);
    return ret;
}

int getCpuTime(int cpu,int *fulltime,int *idletime){
    FILE *file;
    char cpustr[5];
    char cache[10];
    int num=0;

    sprintf(cpustr,"cpu%d",cpu);
    file=fopen("/proc/stat","r");
    if (file==NULL)
        return UNSUPPORTED;

    //Get target point
    while (1){
        if(fscanf(file,"%s",cache)==EOF) {
            return UNSUPPORTED;
        }
        if (!strcmp(cpustr,cache)){
            break;
        }
    }

    //Now the next cache[] should be cputimes
    while (1){
        fscanf(file,"%s",cache);
        int c=atoi(cache);
        if (strcmp(cache,"0") && !c){     //Start of next line
            break;
        }
        //Count cputime
        if(++num == 4){
            *idletime=c;
        }
        *fulltime=*fulltime+c;
    }
    return 0;

}
#define NULLTEMP (-233)
#define TSEN "tsens_tz_sensor"
#define BATT "battery"
#define BMS "bms"

int getmaxtemp(int *temp){
    FILE *process;
    char types[100][20];
    int n=0,cache;
    *temp=NULLTEMP;
    process=popen("cat /sys/class/thermal/thermal_zone*/type","r");
    if (process==NULL)
        return UNSUPPORTED;

    while (fscanf(process,"%s",types[n])!=EOF)
        n++;
    fclose(process);

    process=popen("cat /sys/class/thermal/thermal_zone*/temp","r");
    if (process==NULL)
        return UNSUPPORTED;

    n=0;
    while (fscanf(process,"%d",&cache)!=EOF) {
        if (!strncmp(types[n],TSEN,strlen(TSEN)))
            cache=cache/10;

        if (!strncmp(types[n],BATT,strlen(BATT)))
            cache=cache/1000;

        if (!strncmp(types[n],BMS,strlen(BMS)))
            cache=cache/1000;

        if(cache>*temp)
            *temp=cache;

        n++;
    }
    return 0;
}
