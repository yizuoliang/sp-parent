package org.example.sp.common.quartz.service;

import org.example.sp.common.quartz.entity.JobInfo;
import org.quartz.SchedulerException;

import java.text.ParseException;
import java.util.List;

/**
 * @Author: yizl
 * @Date: 2020/5/20
 * @Description: quartz调度服务接口
 */
public interface IQuartzSchedulerService {

    boolean saveJob(JobInfo jobInfo) ;

    boolean updateJobTime(JobInfo jobInfo) ;

    boolean resumeJob(JobInfo jobInfo) ;

    boolean pauseJob(JobInfo info) ;

    boolean deleteJob(JobInfo jobInfo) ;

    List<JobInfo> getAllJob();

    void runJobNow(JobInfo info);
}
