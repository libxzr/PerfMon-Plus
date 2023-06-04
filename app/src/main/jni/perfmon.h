//
// Created by xzr on 2019/6/28.
//

#ifndef PERFMON_PERFMON_H
#define PERFMON_PERFMON_H

#include <jni.h>

#define DEFAULT_PATH_SIZE 60
#define UNSUPPORTED (-1)
#define NULLTEMP (-233)

int read_file_int(const char *from, int *to);

int read_file_str(const char *from, char *to);

int get_cpu_time(int cpu, int *full_time, int *idle_time);

int get_max_temp(int *temp);

int get_mem_info(char name[], int *data);

int read_process_int(const char *cmd, int *result);

int read_process_str(const char *cmd, char *result);

#endif //PERFMON_PERFMON_H
