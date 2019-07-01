//
// Created by xzr on 2019/6/28.
//

#ifndef PERFMON_PERFMON_H
#define PERFMON_PERFMON_H

#include <jni.h>
#define DEFAULT_PATH_SIZE 60
#define UNSUPPORTED (-1)
#define NULLTEMP (-233)

int readfileint(const char *from,int *to);
int getCpuTime(int cpu,int *fulltime,int *idletime);
int getmaxtemp(int *temp);

#endif //PERFMON_PERFMON_H
