package com.wecash.monitor.service;

import com.wecash.monitor.dao.YtJobDetailDao;
import com.wecash.monitor.entity.YtJobDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class YtJobDetailService {
    @Autowired
    private YtJobDetailDao ytJobDetailDao;

    public Integer insertOrUpdate(YtJobDetail ytJobDetail) {
        return ytJobDetailDao.insertOrUpdate(ytJobDetail);
    }

    public List<YtJobDetail> selectAllNotAlarmed() {
        return ytJobDetailDao.selectAllNotAlarmed();
    }

    public Integer deleteById(Integer id) {
        return ytJobDetailDao.deleteById(id);
    }

    public YtJobDetail selectByJobId(String jobId) {
        return ytJobDetailDao.selectByJobId(jobId);
    }

    public YtJobDetail selectByCron(String cron) {
        return ytJobDetailDao.selectByCron(cron);
    }

    public YtJobDetail selectBySpan(String span) {
        return ytJobDetailDao.selectBySpan(span);
    }

    public YtJobDetail selectByJobClass(String jobClass) {
        return ytJobDetailDao.selectByJobClass(jobClass);
    }

    public YtJobDetail selectByJobName(String jobName) {
        return ytJobDetailDao.selectByJobName(jobName);
    }

    public YtJobDetail selectByDesc(String desc) {
        return ytJobDetailDao.selectByDesc(desc);
    }

    public YtJobDetail selectByNameSpace(String nameSpace) {
        return ytJobDetailDao.selectByNameSpace(nameSpace);
    }

    public YtJobDetail selectByAppId(String appId) {
        return ytJobDetailDao.selectByAppId(appId);
    }
}
