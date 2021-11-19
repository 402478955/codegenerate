package com.me.code.customize.generator.template;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.CharsetUtil;
import com.me.code.customize.generator.data.TableInfo;
import com.me.code.customize.generator.utils.GenerateConfig;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbsGenerator {
    protected GenerateConfig config;
    private TableInfo tableInfo;

    protected AbsGenerator(GenerateConfig config) {
        this.config = config;
    }

    public abstract String getClassNameSuffix();

    public abstract String getClassTemplate();

    public void generateClass() {
        String className = getClassName();
        String classStr = getClassTemplate().replace("&{importClass}", config.getEntityPath() + "." + className)
                .replace("&{servicePath}", config.getServicePackagePath() + "." + className + "Service")
                .replace("&{importDaoClass}", config.getDaoPackagePath())
                .replace("&{startUpPath}", config.getStartUpPath()).replace("&{className}", className)
                .replace("&{instance}", CharSequenceUtil.lowerFirst(className));
        FileUtil.writeString(classStr, config.getGeneratePath() + className + getClassNameSuffix(),
                CharsetUtil.CHARSET_UTF_8);
    }

    protected String getClassName() {
        return CharSequenceUtil.toCamelCase(tableInfo.getTableName());
    }

    protected TableInfo getTableInfo() {
        return tableInfo;
    }

    public void setTableInfo(TableInfo tableInfo) {
        this.tableInfo = tableInfo;
    }
}
