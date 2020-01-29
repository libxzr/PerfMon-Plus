//
// Created by xzr on 2019/6/28.
//
#include "perfmon.h"
#include <stdio.h>
#include <string.h>

int readfileint(const char *path,int *result){
    FILE *freqfile=fopen(path,"r");
    if (freqfile==NULL)
        return UNSUPPORTED;
    fscanf(freqfile,"%d",result);
    fclose(freqfile);
    return 0;
}

int readprocessint(const char *cmd,int *result){
    FILE *process=popen(cmd,"r");
    if (process==NULL)
        return UNSUPPORTED;
    fscanf(process,"%d",result);
    pclose(process);
    return 0;
}

int getCpuTime(int cpu,int *fulltime,int *idletime){
    FILE *file;
    char cpustr[5];
    char cache[50];
    int num=0,c;

    sprintf(cpustr,"cpu%d",cpu);
    file=fopen("/proc/stat","r");
    if (file==NULL)
        return UNSUPPORTED;

    //Get to the target point
    while (1){
        if(fscanf(file,"%s",cache)==EOF) {
            fclose(file);
            return UNSUPPORTED;
        }
        if (!strcmp(cpustr,cache)){
            break;
        }
    }

    //Now the next cache[] should be cputimes
    while (1){
        fscanf(file,"%s",cache);
        c=atoi(cache);
        if (strcmp(cache,"0") && !c){     //Start of next line
            break;
        }
        //Count cputime
        if(++num == 4){
            *idletime=c;
        }
        *fulltime=*fulltime+c;
    }
    fclose(file);
    return 0;

}

#define MAX_SENSOR_NUM 100
#define DEFAULT_SENSOR_NAME_SIZE 30
#define TSENS_TZ "tsens_tz_sensor"
#define CPU_SENSOR "cpu"
int getmaxtemp(int *temp){
    char folders[MAX_SENSOR_NUM][DEFAULT_SENSOR_NAME_SIZE];
    char types[MAX_SENSOR_NUM][DEFAULT_SENSOR_NAME_SIZE];
    int num=0;
    int ctemp=NULLTEMP;
    char cpath[DEFAULT_PATH_SIZE]="";
    FILE *process;

    *temp=NULLTEMP;
    for(int i=0;i<MAX_SENSOR_NUM;i++){
        strcpy(folders[i],"");
        strcpy(types[i],"");
    }

    process=popen("ls /sys/class/thermal | grep thermal_zone","r");
    if(process==NULL)
        return UNSUPPORTED;
    //Read sensor folders
    while(fscanf(process,"%s",folders[num])!=EOF)
        num++;
    pclose(process);

    num=0;
    process=popen("cat /sys/class/thermal/thermal_zone*/type","r");
    if(process==NULL)
        return UNSUPPORTED;
    //Read sensor type
    while(fscanf(process,"%s",types[num])!=EOF)
        num++;
    pclose(process);

    //Looking for the target sensor
    for(int i=0;i<MAX_SENSOR_NUM;i++){
        if(!strncmp(TSENS_TZ,types[i],strlen(TSENS_TZ))){
            //Locked the target
            sprintf(cpath,"/sys/class/thermal/%s/temp",folders[i]);
            process=fopen(cpath,"r");
            if(process==NULL)
                continue;
            if(fscanf(process,"%d",&ctemp)==EOF) {
                fclose(process);
                continue;
            }
            ctemp=ctemp/10;
            fclose(process);
        }
        else if(!strncmp(CPU_SENSOR,types[i],strlen(CPU_SENSOR))){
            //Locked the target
            sprintf(cpath,"/sys/class/thermal/%s/temp",folders[i]);
            process=fopen(cpath,"r");
            if(process==NULL)
                continue;
            if(fscanf(process,"%d",&ctemp)==EOF) {
                fclose(process);
                continue;
            }
            ctemp=ctemp/1000;
            fclose(process);
        }
        if(*temp<ctemp)
            *temp=ctemp;
    }
    return 0;
}

int getmeminfo(char name[],int *data){
    FILE *meminfo;
    char cache[20]="";

    meminfo=fopen("/proc/meminfo","r");
    if(meminfo==NULL)
        return UNSUPPORTED;

    //Get target line
    while (fscanf(meminfo,"%s",cache)!=EOF){
        if(!strncmp(cache,name,strlen(name))){
            //Locked target line
            //Read target data
            if(fscanf(meminfo,"%s",cache)==EOF)
                return UNSUPPORTED;
            *data=atoi(cache);
        }
    }
    fclose(meminfo);

    return 0;


}
