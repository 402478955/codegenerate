package com.me.code.customize.generator;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import cn.hutool.db.dialect.DriverNamePool;
import cn.hutool.db.dialect.DriverUtil;
import cn.hutool.db.ds.DataSourceWrapper;
import cn.hutool.db.ds.simple.SimpleDataSource;
import com.me.code.customize.generator.data.TableInfo;
import com.me.code.customize.generator.jdbc.DbUtil;
import com.me.code.customize.generator.jdbc.SchemaUtil;
import com.me.code.customize.generator.template.AbsGenerator;
import com.me.code.customize.generator.template.ControllerGenerator;
import com.me.code.customize.generator.template.DaoGenerator;
import com.me.code.customize.generator.template.DomainGenerator;
import com.me.code.customize.generator.template.ServiceGenerator;
import com.me.code.customize.generator.template.TestControllerGenerator;
import com.me.code.customize.generator.template.TestDaoGenerator;
import com.me.code.customize.generator.utils.GenerateConfig;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author zhull
 * <P>Mybatis生成器</P>
 */
@Slf4j
public class MybatisGenerator {
    private static String baseDirectory = ClassLoader.getSystemResource("").getPath()
            .replace("target/classes/", "src/main/");

    public static void main(String[] args) throws SQLException {

        GenerateConfig generateConfig = getConfig();
        DbUtil.initConnection(generateConfig.getDbUrl(), generateConfig.getUserName(), generateConfig.getPassWord());
        // 生成数据库中所有的表对应的dao文件
        //        List<TableInfo> tableInfos = SchemaUtil.getTableInfos("test_db", null);
        // 生成数据库中指定表对应的dao文件

        //        ResultSet rs= DbUtil.executeQuery("SELECT *  from job_now_status  where  job_name='generatePrizeRecords' limit 10");
        //        while(rs.next()){
        //            Timestamp timestamp= rs.getTimestamp("start_time");
        //            Date date=new Date(timestamp.getTime());
        //
        //            CronSequenceGenerator cronSequenceGenerator1 = new CronSequenceGenerator("0 40 23 ? * SUN,MON,TUE,WED,THU");
        //            Date nextDate=  cronSequenceGenerator1.next(date);
        //            log.info("sadfasdf");
        //
        //        }

        List<TableInfo> tableInfos = SchemaUtil.getTableInfos(generateConfig.getDbName(),
                generateConfig.getTableList());

        List<AbsGenerator> generatorList = initList(generateConfig);
        for (TableInfo tableInfo : tableInfos) {
            for (AbsGenerator generator : generatorList) {
                generator.setTableInfo(tableInfo);
                try {
                    generator.generateClass();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            log.info("正在处理\t\t" + tableInfo.getTableName());
        }

        log.info("~~~~~~~~~~~~全部完成~~~~~~~~~~~~");
    }

    private static GenerateConfig getConfig() {
        // return getSmsConfig();
        return getFileConfig();
    }

    private static GenerateConfig getFileConfig() {
        GenerateConfig generateConfig = new GenerateConfig();
        generateConfig.setControllerPackagePath("com.yxt.file.web.controller");
        generateConfig.setServicePackagePath("com.yxt.file.service");
        generateConfig.setEntityPath("com.yxt.file.entity");
        generateConfig.setGeneratePath("C:\\Users\\86379\\Desktop\\generate\\");
        generateConfig.setDaoPackagePath("com.yxt.file.mapper");
        generateConfig.setStartUpPath("com.yxt");
        generateConfig.setHttpUtilPackagePath("com.yxt.monitor.alert.util.HttpUtil");
        generateConfig.setResponsePackagePath("com.yxt.monitor.web.vo.BaseResponse");
        generateConfig.setDbName("file");
        generateConfig.setDbUrl("jdbc:mysql://10.120.0.2:6033/" + generateConfig.getDbName()
                + "?useUnicode=true&characterEncoding=utf-8&allowMultiQueries=TRUE&useSSL=false");
        generateConfig.setUserName("yxt");
        generateConfig.setPassWord("afg)gppOs22k");
        //!!!!检查和数据库关键字冲突字段
        //删除insertOrUpdate的末尾逗号
        generateConfig.setTableList(ListUtil.toList("file_localservice_conf"));
        return generateConfig;
    }

    /**
     * 获取监控配置
     *
     * @return 生成配置
     */
    private static GenerateConfig getMonitorConfig() {
        GenerateConfig generateConfig = new GenerateConfig();
        generateConfig.setControllerPackagePath("com.yxt.monitor.web.controller");
        generateConfig.setServicePackagePath("com.yxt.monitor.service");
        generateConfig.setEntityPath("com.yxt.monitor.entity");
        generateConfig.setGeneratePath("C:\\Users\\40247\\Desktop\\");
        generateConfig.setDaoPackagePath("com.yxt.monitor.dao");
        generateConfig.setStartUpPath("com.yxt");
        generateConfig.setHttpUtilPackagePath("com.yxt.monitor.alert.util.HttpUtil");
        generateConfig.setResponsePackagePath("com.yxt.monitor.web.vo.BaseResponse");
        generateConfig.setDbName("monitor");
        generateConfig.setDbUrl("jdbc:mysql://172.17.126.158:3306/" + generateConfig.getDbName()
                + "?useUnicode=true&characterEncoding=utf-8&allowMultiQueries=TRUE&useSSL=false");
        generateConfig.setUserName("yxt");
        generateConfig.setPassWord("afg)gppOs22k");
        //!!!!检查和数据库关键字冲突字段
        //删除insertOrUpdate的末尾逗号
        ArrayList<String> tables = new ArrayList<>();
        tables.add("file_cdn");
        generateConfig.setTableList(tables);
        return generateConfig;
    }

    private static List<AbsGenerator> initList(GenerateConfig generateConfig) {
        TestDaoGenerator testDaoGenerator = new TestDaoGenerator(generateConfig);
        DaoGenerator daoGenerator = new DaoGenerator(generateConfig);
        ServiceGenerator serviceGenerator = new ServiceGenerator(generateConfig);
        ControllerGenerator controllerGenerator = new ControllerGenerator(generateConfig);
        TestControllerGenerator testControllerGenerator = new TestControllerGenerator(generateConfig);
        DomainGenerator domainGenerator = new DomainGenerator(generateConfig);
        List<AbsGenerator> generatorList = new LinkedList<>();
        generatorList.add(testDaoGenerator);
        generatorList.add(daoGenerator);
        generatorList.add(serviceGenerator);
        generatorList.add(controllerGenerator);
        generatorList.add(testControllerGenerator);
        generatorList.add(domainGenerator);
        return generatorList;
    }
}
