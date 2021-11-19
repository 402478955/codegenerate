package com.me.test.dao;

import com.me.test.dao.domain.JobDetailPo;

import java.util.List;

/**
 * 任务详情数据访问
 *
 * @author ali
 * @date 2019/02/25
 */
public interface JobDetailDao {

    /**
     * 插入一条数据
     *
     * @param jobDetailPo 待插入对象
     */
    void insert(JobDetailPo jobDetailPo);

    /**
     * 批量插入多条数据
     *
     * @param list 待插入对象列表
     */
    void batchInsert(List<JobDetailPo> list);

    /**
     * 根据主键删除
     *
     * @param id 主键值
     * @return 影响条数
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 根据主键更新
     *
     * @param condition 要更新的对象
     * @return 影响条数
     */
    int updateByPrimaryKey(JobDetailPo condition);

    /**
     * 根据主键查询
     *
     * @param id 主键值
     * @return 根据主键查询到的对象
     */
    JobDetailPo queryByPrimaryKey(Integer id);

    /**
     * 根据条件查询一条记录
     *
     * @param condition 查询条件
     * @return 查询出来的对象
     */
    JobDetailPo queryOne(JobDetailPo condition);

    /**
     * 根据条件查询列表
     *
     * @param condition 查询条件
     * @return 查询出来的对象列表
     */
    List<JobDetailPo> queryList(JobDetailPo condition);

    /**
     * 根据条件统计数量
     *
     * @param condition 统计条件
     * @return 记录总数
     */
    int count(JobDetailPo condition);
}
