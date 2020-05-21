package org.example.sp.common.quartz.constant;

/**
 * @Author: yizl
 * @Date: 2020/5/21
 * @Description: Quartz模块的常量
 */
public interface QuartzConstant {
    /*
     * 任务不存在
     */
    public static final Integer QUARTZ_STATUS_NON = 0;

    /*
     * 任务正常
     */
    public static final Integer QUARTZ_STATUS_NORMAL = 1;

    /*
     * 任务暂停
     */
    public static final Integer QUARTZ_STATUS_PAUSE = 2;

    /*
     * 任务不存在
     */
    public static final Integer QUARTZ_STATUS_COMPLETE = 3;

    /*
     * 任务错误
     */
    public static final Integer QUARTZ_STATUS_ERROR = 4;

    /*
     * 任务阻塞
     */
    public static final Integer QUARTZ_STATUS_BLOCK = 5;

}
