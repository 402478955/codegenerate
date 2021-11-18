package com.me.code.customize.generator;

import com.me.code.customize.generator.data.TableInfo;
import com.me.code.customize.generator.jdbc.DbUtil;
import com.me.code.customize.generator.jdbc.SchemaUtil;
import com.me.code.customize.generator.template.*;
import com.me.code.customize.generator.utils.GeneratConfig;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author zhull
 * @date 2018/6/13
 * <P>Mybatis生成器</P>
 */
public class MybatisGenerator {
    private static String baseDirectory = ClassLoader.getSystemResource("").getPath().replace("target/classes/", "src/main/");
    private static GeneratConfig getConfig(){
		// return getSmsConfig();
       return getFileConfig();

    }
    private static GeneratConfig  getFileConfig(){
        GeneratConfig  generatConfig=new GeneratConfig();
        generatConfig.setControllerPackagePath("com.yxt.file.web.controller");
        generatConfig.setServicePackagePath("com.yxt.file.service");
        generatConfig.setEntityPath("com.yxt.file.entity");
        generatConfig.setGeneratePath("C:\\Users\\40247\\Desktop\\generate\\");
        generatConfig.setDaoPackagePath("com.yxt.file.mapper");
        generatConfig.setStartUpPath("com.yxt");
        generatConfig.setHttpUtilPackagePath("com.yxt.monitor.alert.util.HttpUtil");
        generatConfig.setResponsePackagePath("com.yxt.monitor.web.vo.BaseResponse");
        generatConfig.setDbName("file");
        generatConfig.setDbUrl("jdbc:mysql://10.120.0.2:6033/" + generatConfig.getDbName() + "?useUnicode=true&characterEncoding=utf-8&allowMultiQueries=TRUE&useSSL=false");
        generatConfig.setUserName("yxt");
        generatConfig.setPassWord( "afg)gppOs22k");
        //!!!!检查和数据库关键字冲突字段
        //删除insertOrUpdate的末尾逗号
        generatConfig.setTableList(new ArrayList<String>() {{
            add("file_localservice_conf");
        }});
        return generatConfig;
    }
    private static GeneratConfig  getMonitorConfig(){
        GeneratConfig  generatConfig=new GeneratConfig();
        generatConfig.setControllerPackagePath("com.yxt.monitor.web.controller");
        generatConfig.setServicePackagePath("com.yxt.monitor.service");
        generatConfig.setEntityPath("com.yxt.monitor.entity");
        generatConfig.setGeneratePath("C:\\Users\\40247\\Desktop\\");
        generatConfig.setDaoPackagePath("com.yxt.monitor.dao");
        generatConfig.setStartUpPath("com.yxt");
        generatConfig.setHttpUtilPackagePath("com.yxt.monitor.alert.util.HttpUtil");
        generatConfig.setResponsePackagePath("com.yxt.monitor.web.vo.BaseResponse");
        generatConfig.setDbName("monitor");
        generatConfig.setDbUrl("jdbc:mysql://172.17.126.158:3306/" + generatConfig.getDbName() + "?useUnicode=true&characterEncoding=utf-8&allowMultiQueries=TRUE&useSSL=false");
        generatConfig.setUserName("yxt");
        generatConfig.setPassWord( "afg)gppOs22k");
        //!!!!检查和数据库关键字冲突字段
		//删除insertOrUpdate的末尾逗号
		generatConfig.setTableList(new ArrayList<String>() {{
			add("file_cdn");
		}});
        return generatConfig;
    }
    public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException {

        GeneratConfig generatConfig=getConfig();
        DbUtil.initConnection(generatConfig.getDbUrl(), generatConfig.getUserName(), generatConfig.getPassWord());
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
//            System.out.println("sadfasdf");
//
//        }

        List<TableInfo> tableInfos = SchemaUtil.getTableInfos(generatConfig.getDbName(), generatConfig.getTableList());

        List<AbsGenerator> generatorList=initList( generatConfig);
        for (TableInfo tableInfo : tableInfos) {
            for(AbsGenerator  generator:generatorList){
                generator.setTableInfo(tableInfo);
                try {
                    generator.generateClass();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            System.out.println("正在处理\t\t" + tableInfo.getTableName());

        }

        System.out.println("~~~~~~~~~~~~全部完成~~~~~~~~~~~~");


    }
    private static List<AbsGenerator> initList(GeneratConfig  generatConfig){
        TestDaoGenerator  testDaoGenerator=new TestDaoGenerator(generatConfig );
        DaoGenerator  daoGenerator=new DaoGenerator(generatConfig );
        ServiceGenerator serviceGenerator=new ServiceGenerator(generatConfig );
        ControllerGenerator controllerGenerator=new ControllerGenerator(generatConfig );
        TestControllerGenerator testControllerGenerator=new TestControllerGenerator(generatConfig );
        DomainGenerator domainGenerator=new DomainGenerator(generatConfig);
        List<AbsGenerator>  generatorList=new LinkedList<AbsGenerator>();
        generatorList.add(testDaoGenerator);
        generatorList.add(daoGenerator);
        generatorList.add(serviceGenerator);
        generatorList.add(controllerGenerator);
        generatorList.add(testControllerGenerator);
        generatorList.add(domainGenerator);
        return generatorList;
    }
}
