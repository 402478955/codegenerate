package com.me.code.customize.generator.utils;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class GenerateConfig {
    String entityPath = "com.yxt.monitor.entity";
    String generatePath = "c:\\86379\\Desktop\\generate\\";
    String daoPackagePath = "com.yxt.monitor.dao";
    String servicePackagePath = "com.yxt.monitor.service";
    String controllerPackagePath = "com.yxt.monitor.controller";
    String startUpPath = "com.yxt.monitor.service.prometheus";
    String httpUtilPackagePath = "com.yxt.monitor.alert.util.HttpUtil";
    String responsePackagePath = "com.yxt.monitor.web.vo.BaseResponse";
    String dbName = "test_db";
    String dbUrl = "jdbc:mysql://localhost/" + dbName + "?useSSL=false";
    String userName = "root";
    String passWord = "admin123";
    List<String> tableList;
}
