package org.example.sp.common.quartz.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.quartz.Job;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: yizl
 * @Date: 2020/5/20
 * @Description: quartz相关job信息
 */
@Data
@ApiModel(value="调度任务对象", description="调度任务对象")
public class JobInfo {

    @ApiModelProperty(value = "任务名称")
    private String jobName;

    @ApiModelProperty(value = "任务分组")
    private String groupName;

    @ApiModelProperty(value = "cron表达式")
    private String cronExpression;

    @ApiModelProperty(value = "任务状态")
    private String state;

    @ApiModelProperty(value = "任务的类名")
    private Class<? extends Job> jobClass;

    @ApiModelProperty(value = "调度传递参数")
    private Map<String, Object> data = new HashMap<>();

    @ApiModelProperty(value = "是否简单调度")
    private boolean simpleScheduler;

    @ApiModelProperty(value = "调度间隔时长,秒")
    private Integer second;

}
