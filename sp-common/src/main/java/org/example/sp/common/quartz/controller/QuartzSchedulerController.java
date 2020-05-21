package org.example.sp.common.quartz.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.example.sp.common.quartz.entity.JobInfo;
import org.example.sp.common.quartz.service.IQuartzSchedulerService;
import org.example.sp.common.result.Result;
import org.example.sp.common.result.ResultEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: yizl
 * @Date: 2020/5/21
 * @Description: 定时调度接口
 */
@RestController
@RequestMapping("/quartz")
@Api(tags = "定时调度接口")
public class QuartzSchedulerController {
    @Autowired
    private IQuartzSchedulerService service;

    @PostMapping("/saveJob")
    @ApiOperation("添加定时任务")
    public Result<?> saveJob(@RequestBody JobInfo jobInfo) {
        service.saveJob(jobInfo);
        return new Result<>(ResultEnum.SUCCESS);
    }

    @PostMapping("/deleteJob")
    @ApiOperation("删除定时任务")
    public Result<?> deleteJob(@RequestBody JobInfo jobInfo) {
        service.deleteJob(jobInfo);
        return new Result<>(ResultEnum.SUCCESS);
    }

    @PostMapping("/resumeJob")
    @ApiOperation("恢复定时任务")
    public Result<?> resumeJob(@RequestBody JobInfo jobInfo) {
        service.resumeJob(jobInfo);
        return new Result<>(ResultEnum.SUCCESS);
    }

    @PostMapping("/pauseJob")
    @ApiOperation("暂停定时任务")
    public Result<?> pauseJob(@RequestBody JobInfo jobInfo) {
        service.pauseJob(jobInfo);
        return new Result<>(ResultEnum.SUCCESS);
    }

    @PostMapping("/updateJobTime")
    @ApiOperation("更新定时调度时间")
    public Result<?> updateJobTime(@RequestBody JobInfo jobInfo) {
        service.updateJobTime(jobInfo);
        return new Result<>(ResultEnum.SUCCESS);
    }

    @PostMapping("/runJobNow")
    @ApiOperation("立即执行调度")
    public Result<?> runJobNow(@RequestBody JobInfo jobInfo) {
        service.runJobNow(jobInfo);
        return new Result<>(ResultEnum.SUCCESS);
    }

    @GetMapping("/getAllJob")
    @ApiOperation("获取所有的调度列表")
    public Result<List<JobInfo>> getAllJob() {
        List<JobInfo> allJob = service.getAllJob();
        return new Result<>(ResultEnum.SUCCESS,allJob);
    }

}
