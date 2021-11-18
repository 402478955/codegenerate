package com.wecash.monitor.entity;

import java.util.Date;
public class YtJobDetail {
    private Integer id;
     public void setId(Integer id) {
        this.id = id;
    }
 public Integer getId() {
        return id;
    }

    private String jobId;
     public void setJobId(String jobId) {
        this.jobId = jobId;
    }
 public String getJobId() {
        return jobId;
    }

    private String cron;
     public void setCron(String cron) {
        this.cron = cron;
    }
 public String getCron() {
        return cron;
    }

    private Integer span;
     public void setSpan(Integer span) {
        this.span = span;
    }
 public Integer getSpan() {
        return span;
    }

    private String jobClass;
     public void setJobClass(String jobClass) {
        this.jobClass = jobClass;
    }
 public String getJobClass() {
        return jobClass;
    }

    private String jobName;
     public void setJobName(String jobName) {
        this.jobName = jobName;
    }
 public String getJobName() {
        return jobName;
    }

    private String desc;
     public void setDesc(String desc) {
        this.desc = desc;
    }
 public String getDesc() {
        return desc;
    }

    private String nameSpace;
     public void setNameSpace(String nameSpace) {
        this.nameSpace = nameSpace;
    }
 public String getNameSpace() {
        return nameSpace;
    }

    private String appId;
     public void setAppId(String appId) {
        this.appId = appId;
    }
 public String getAppId() {
        return appId;
    }

    private Date updateDate;
     public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
 public Date getUpdateDate() {
        return updateDate;
    }

}
