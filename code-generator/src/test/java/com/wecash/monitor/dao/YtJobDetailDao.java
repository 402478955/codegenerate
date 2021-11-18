package com.wecash.monitor.dao;

import com.wecash.monitor.entity.YtJobDetail;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface YtJobDetailDao {
    String COLUMN = "id,jobId,cron,span,jobClass,jobName,desc,nameSpace,appId,updateDate,";
    String TABLE_NAME = "YT_JOB_DETAIL";

    @Update(" <script>" +
            "INSERT INTO " + TABLE_NAME + " (" + COLUMN + ") VALUES  " +
            "(&{columnDb}   )\n" +
            "    ON DUPLICATE KEY UPDATE " +
            "<if test=\"jobId != null\">job_id=jobId,</if>" +
            "<if test=\"cron != null\">cron=cron,</if>" +
            "<if test=\"span != null\">span=span,</if>" +
            "<if test=\"jobClass != null\">job_class=jobClass,</if>" +
            "<if test=\"jobName != null\">job_name=jobName,</if>" +
            "<if test=\"desc != null\">desc=desc,</if>" +
            "<if test=\"nameSpace != null\">name_space=nameSpace,</if>" +
            "<if test=\"appId != null\">app_id=appId,</if>" +

            "  update_date=now()     " +
            " </script>")
    @SelectKey(statement = " SELECT LAST_INSERT_ID() ", keyProperty = "id", before = false, resultType = int.class)
    int insertOrUpdate(YtJobDetail ytJobDetail);

    @Select("  select id, " + COLUMN + "  from  " + TABLE_NAME + "  order by  update_date desc  limit 500 ")
    List<YtJobDetail> selectAllNotAlarmed();

    @Delete("  delete  from  " + TABLE_NAME + "  where id=#{id} ")
    Integer deleteById(@Param("id") Integer id);

    @Select("  select id, " + COLUMN + "  from  " + TABLE_NAME + "   where  job_id=#{jobId} limit 500 ")
    YtJobDetail selectByJobId(@Param("jobId") String jobId);

    @Select("  select id, " + COLUMN + "  from  " + TABLE_NAME + "   where  cron=#{cron} limit 500 ")
    YtJobDetail selectByCron(@Param("cron") String cron);

    @Select("  select id, " + COLUMN + "  from  " + TABLE_NAME + "   where  span=#{span} limit 500 ")
    YtJobDetail selectBySpan(@Param("span") String span);

    @Select("  select id, " + COLUMN + "  from  " + TABLE_NAME + "   where  job_class=#{jobClass} limit 500 ")
    YtJobDetail selectByJobClass(@Param("jobClass") String jobClass);

    @Select("  select id, " + COLUMN + "  from  " + TABLE_NAME + "   where  job_name=#{jobName} limit 500 ")
    YtJobDetail selectByJobName(@Param("jobName") String jobName);

    @Select("  select id, " + COLUMN + "  from  " + TABLE_NAME + "   where  desc=#{desc} limit 500 ")
    YtJobDetail selectByDesc(@Param("desc") String desc);

    @Select("  select id, " + COLUMN + "  from  " + TABLE_NAME + "   where  name_space=#{nameSpace} limit 500 ")
    YtJobDetail selectByNameSpace(@Param("nameSpace") String nameSpace);

    @Select("  select id, " + COLUMN + "  from  " + TABLE_NAME + "   where  app_id=#{appId} limit 500 ")
    YtJobDetail selectByAppId(@Param("appId") String appId);
}

