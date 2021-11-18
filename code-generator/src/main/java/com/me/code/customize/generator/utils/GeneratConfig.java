package com.me.code.customize.generator.utils;

import java.util.ArrayList;

public class GeneratConfig {
    String entityPath = "com.yxt.monitor.entity";
    String generatePath = "/Users/yangtiantian/Desktop/generate/";
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
 ArrayList<String>  tableList;

    public ArrayList<String> getTableList() {
        return tableList;
    }

    public void setTableList(ArrayList<String> tableList) {
        this.tableList = tableList;
    }

    public String getEntityPath() {
        return entityPath;
    }

    public void setEntityPath(String entityPath) {
        this.entityPath = entityPath;
    }

    public String getGeneratePath() {
        return generatePath;
    }

    public void setGeneratePath(String generatePath) {
        this.generatePath = generatePath;
    }

    public String getDaoPackagePath() {
        return daoPackagePath;
    }

    public void setDaoPackagePath(String daoPackagePath) {
        this.daoPackagePath = daoPackagePath;
    }

    public String getServicePackagePath() {
        return servicePackagePath;
    }

    public void setServicePackagePath(String servicePackagePath) {
        this.servicePackagePath = servicePackagePath;
    }

    public String getControllerPackagePath() {
        return controllerPackagePath;
    }

    public void setControllerPackagePath(String controllerPackagePath) {
        this.controllerPackagePath = controllerPackagePath;
    }

    public String getStartUpPath() {
        return startUpPath;
    }

    public void setStartUpPath(String startUpPath) {
        this.startUpPath = startUpPath;
    }

    public String getHttpUtilPackagePath() {
        return httpUtilPackagePath;
    }

    public void setHttpUtilPackagePath(String httpUtilPackagePath) {
        this.httpUtilPackagePath = httpUtilPackagePath;
    }

    public String getResponsePackagePath() {
        return responsePackagePath;
    }

    public void setResponsePackagePath(String responsePackagePath) {
        this.responsePackagePath = responsePackagePath;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getDbUrl() {
        return dbUrl;
    }

    public void setDbUrl(String dbUrl) {
        this.dbUrl = dbUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
}
