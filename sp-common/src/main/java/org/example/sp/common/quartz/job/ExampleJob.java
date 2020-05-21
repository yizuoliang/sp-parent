package org.example.sp.common.quartz.job;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: yizl
 * @Date: 2020/5/21
 * @Description: 案例任务
 */
@Slf4j
public class ExampleJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        JobDataMap dataMap =context.getJobDetail().getJobDataMap();
        String jobParam = dataMap.getString("jobParam");
        log.info(String.format("welcome %s! 定时任务 exampleJob !  时间:" + simpleDateFormat.format(new Date()) , jobParam));
    }
}
