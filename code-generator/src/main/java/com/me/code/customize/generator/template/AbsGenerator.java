package com.me.code.customize.generator.template;

import com.me.code.customize.generator.data.TableInfo;
import com.me.code.customize.generator.utils.FileUtil;
import com.me.code.customize.generator.utils.GeneratConfig;
import com.me.code.customize.generator.utils.GeneratUtil;

import java.io.IOException;
import java.util.Arrays;

public abstract class AbsGenerator {
    protected GeneratConfig config;
    private TableInfo tableInfo;

    public AbsGenerator(GeneratConfig config ) {
        this.config = config;
    }
    abstract public String getClassNameSuffix() throws Exception;

    abstract public String getClassTemplate() throws Exception;



    public void generateClass() throws Exception {
        String className = getClassName();
        String classStr =null;
        try {
             classStr = getClassTemplate()
                    .replace("&{importClass}", config.getEntityPath() + "." + className)
                    .replace("&{servicePath}", config.getServicePackagePath()+"."+className+"Service")
                    .replace("&{importDaoClass}", config.getDaoPackagePath())
                    .replace("&{startUpPath}", config.getStartUpPath())
                    .replace("&{className}", className)
                    .replace("&{instance}", GeneratUtil.firstCharLowCase(className));
        }catch (Exception e){
            System.out.println("asdffds");
        }

        try {
            FileUtil.writeToFile(config.getGeneratePath() + className + getClassNameSuffix(),  classStr );
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("asdfasdfdfs");
    }
    protected String getClassName() {
        return GeneratUtil.firstCharUpperCase(GeneratUtil.fromGang2Tuofeng(tableInfo.getTableName().toLowerCase()));
    }

    protected TableInfo getTableInfo() {
        return tableInfo;
    }

    public void setTableInfo(TableInfo tableInfo) {
        this.tableInfo = tableInfo;
    }


}
