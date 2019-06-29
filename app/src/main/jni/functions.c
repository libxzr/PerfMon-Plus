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
