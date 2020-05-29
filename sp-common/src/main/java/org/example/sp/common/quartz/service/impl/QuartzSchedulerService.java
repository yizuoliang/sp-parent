package org.example.sp.common.quartz.service.impl;

import org.example.sp.common.exception.BusinessException;
import org.example.sp.common.quartz.entity.JobInfo;
import org.example.sp.common.quartz.service.IQuartzSchedulerService;
import org.example.sp.common.result.ResultEnum;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/**
 * @Author: yizl
 * @Date: 2020/5/20
 * @Description: quartz调度服务类
 */
@Service
public class QuartzSchedulerService implements IQuartzSchedulerService {

    @Autowired
    private Scheduler scheduler;
    /**
     * 功能描述: <br> 添加调度和启动调度
     * @Param: [jobInfo]
     * @Return: boolean
     * @Author: yizl
     * @Date: 2020/5/20 17:18
     */
    @Override
    public boolean saveJob(JobInfo jobInfo){
        String jobName = jobInfo.getJobName();
        String groupName = jobInfo.getGroupName();
        TriggerKey triggerKey = TriggerKey.triggerKey(jobName, groupName);
        JobKey jobKey = JobKey.jobKey(jobName, groupName);
        try {
            //判断此调度是否存在,存在,就不能添加
            if (scheduler.checkExists(triggerKey))
            {
                throw new BusinessException(ResultEnum.PROVIDER_QUARTZ_SAVE_FAIL);
            }
            if (jobInfo.isSimpleScheduler())
            {

                SimpleTrigger trigger = TriggerBuilder.newTrigger().withIdentity(triggerKey)
                        .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(
                                jobInfo.getSecond()).repeatForever()).build();

                // storeDurably(true)就算没有绑定Trigger,该job依旧在
                //Class<? extends Job>clazz = (Class<? extends Job>) Class.forName("org.example.sp.common.quartz.job.ExampleJob");
                JobDetail jobDetail = JobBuilder.newJob(jobInfo.getJobClass())
                        .requestRecovery().storeDurably().withIdentity(jobKey).build();
                //传参
                jobDetail.getJobDataMap().putAll(jobInfo.getData());
                scheduler.scheduleJob(jobDetail,trigger);
            }
            else
            {
                CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(triggerKey)
                        .withSchedule(CronScheduleBuilder.cronSchedule(jobInfo.getCronExpression())).build();

                JobDetail jobDetail = JobBuilder.newJob(jobInfo.getJobClass())
                        .requestRecovery().storeDurably().withIdentity(jobKey).build();
                //传参
                jobDetail.getJobDataMap().putAll(jobInfo.getData());
                scheduler.scheduleJob(jobDetail,trigger);
            }
        } catch (SchedulerException  e) {
            throw new BusinessException(ResultEnum.PROVIDER_QUARTZ_SAVE_FAIL);
        }
        return true;
    }
    /**
     * 功能描述: <br> 更新调度时间
     * @Param: [jobInfo]
     * @Return: boolean
     * @Author: yizl
     * @Date: 2020/5/21 9:35
     */
    @Override
    public boolean updateJobTime(JobInfo jobInfo)  {
        TriggerKey triggerKey = TriggerKey.triggerKey(jobInfo.getJobName(), jobInfo.getGroupName());
        try {
            if (jobInfo.isSimpleScheduler())
            {
                SimpleTrigger trigger = (SimpleTrigger) scheduler.getTrigger(triggerKey);
                //创建新的触发器
                SimpleTrigger newTrigger = trigger.getTriggerBuilder().withIdentity(triggerKey)
                        .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(
                                jobInfo.getSecond()).repeatForever()).build();
                // 重新执行这个调度,更改触发器
                scheduler.rescheduleJob(triggerKey, newTrigger);
            }
            else
            {
                //创建新的触发器
                CronTrigger trigger =(CronTrigger)scheduler.getTrigger(triggerKey);
                //Cron 表达式
                CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(jobInfo.getCronExpression());
                //创建新的触发器
                CronTrigger newTrigger = trigger.getTriggerBuilder()
                        .withIdentity(triggerKey).withSchedule(cronScheduleBuilder).build();
                scheduler.rescheduleJob(triggerKey, newTrigger);

            }
        } catch (SchedulerException e) {
            throw new BusinessException(ResultEnum.PROVIDER_QUARTZ_UPDATE_FAIL);
        }
        return true;
    }


    /**
     * 功能描述: <br> 恢复一个任务
     * @Param: [jobInfo]
     * @Return: boolean
     * @Author: yizl
     * @Date: 2020/5/21 9:53
     */
    @Override
    public boolean resumeJob(JobInfo jobInfo) {
        JobKey jobKey = JobKey.jobKey(jobInfo.getJobName(), jobInfo.getGroupName());
        try {
            scheduler.resumeJob(jobKey);
        } catch (SchedulerException e) {
            throw new BusinessException(ResultEnum.PROVIDER_QUARTZ_RESUME_FAIL);
        }
        return true;
    }

    /**
     * 功能描述: <br> 删除一个定时任务
     * @Param: [jobInfo]
     * @Return: boolean
     * @Author: yizl
     * @Date: 2020/5/21 10:12
     */
    @Override
    public boolean deleteJob(JobInfo jobInfo) {
        JobKey jobKey = JobKey.jobKey(jobInfo.getJobName(), jobInfo.getGroupName());
        try {
            scheduler.deleteJob(jobKey);
        } catch (SchedulerException e) {
            throw new BusinessException(ResultEnum.PROVIDER_QUARTZ_DELETE_FAIL);
        }
        return true;
    }

    /**
     * 功能描述: <br> 暂停一个任务
     * @Param: [info]
     * @Return: void
     * @Author: yizl
     * @Date: 2020/5/21 10:17
     */
    @Override
    public boolean pauseJob(JobInfo info)
    {
        JobKey jobKey = JobKey.jobKey(info.getJobName(), info.getGroupName());
        try {
            scheduler.pauseJob(jobKey);
        } catch (SchedulerException e) {
            throw new BusinessException(ResultEnum.PROVIDER_QUARTZ_PAUSE_FAIL);
        }
        return true;
    }
    /**
     * 功能描述: <br> 获取所有的任务列表
     * @Param: []
     * @Return: java.util.List<org.example.sp.common.quartz.entity.JobInfo>
     * @Author: yizl
     * @Date: 2020/5/21 13:48
     */
    @Override
    public List<JobInfo> getAllJob() {
        GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
        List<JobInfo>  jobList = new ArrayList<JobInfo>();
        try {
            Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);
            for (JobKey jobKey : jobKeys)
            {
                List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
                for (Trigger trigger : triggers)
                {
                    JobInfo job = new JobInfo();
                    job.setJobName(jobKey.getName());
                    job.setGroupName(jobKey.getGroup());
                    Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                    job.setState(triggerState.name());
                    if (trigger instanceof CronTrigger)
                    {
                        CronTrigger cronTrigger = (CronTrigger)trigger;
                        String cronExpression = cronTrigger.getCronExpression();
                        job.setCronExpression(cronExpression);
                    }
                    else if (trigger instanceof SimpleTrigger)
                    {
                        SimpleTrigger simpleTrigger = (SimpleTrigger)trigger;
                        long repeatInterval = simpleTrigger.getRepeatInterval();
                        job.setSecond((int)(repeatInterval / 1000));
                    }
                    jobList.add(job);
                }
            }
        } catch (SchedulerException e) {
            throw new BusinessException(ResultEnum.PROVIDER_QUARTZ_ERROR);
        }
        return jobList;
    }
    /**
     * 功能描述: <br> 立即执行某个调度,只会执行一次
     * @Param: [info]
     * @Return: void
     * @Author: yizl
     * @Date: 2020/5/21 13:53
     */
    @Override
    public void runJobNow(JobInfo info)
    {
        JobKey jobKey = JobKey.jobKey(info.getJobName(), info.getGroupName());
        try {
            scheduler.triggerJob(jobKey);
        } catch (SchedulerException e) {
            throw new BusinessException(ResultEnum.PROVIDER_QUARTZ_ERROR);
        }
    }

}
