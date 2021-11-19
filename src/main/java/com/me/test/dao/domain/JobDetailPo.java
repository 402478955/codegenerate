package com.me.test.dao.domain;


import java.time.LocalDateTime;

/**
 * 任务详情
 *
 * @author ali
 * @date 2019/02/25
 */
public class JobDetailPo {

    /**
     * id
     */
    private Integer id;

    /**
     * job id
     */
    private String jobId;

    /**
     * 定时表达式
     */
    private String cron;

    /**
     * 多长时间执行一次
     */
    private Integer span;

    /**
     * 作业执行类
     */
    private String jobClass;

    /**
     * 作业名
     */
    private String jobName;

    /**
     * 作业描述
     */
    private String desc;

    /**
     * 定时任务的namespace
     */
    private String nameSpace;

    /**
     * 应用标识
     */
    private String appId;

    /**
     * 更新时间
     */
    private LocalDateTime updateDate;
}
