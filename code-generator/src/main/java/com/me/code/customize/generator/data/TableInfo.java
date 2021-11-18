package com.me.code.customize.generator.data;

import com.me.code.customize.generator.utils.CommonUtil;
import org.apache.commons.lang.StringUtils;

import java.util.List;

/**
 * @author zhull
 * @date 2018/6/12
 * <P>表信息</P>
 */
public class TableInfo {

    /**
     * 表名
     */
    private String tableName;

    /**
     * 表注释
     */
    private String comment;

    /**
     * 类名(首字母大写)
     */
    private String className;

    /**
     * 对象名(首字母小写)
     */
    private String objectName;

    /**
     * 列属性
     */
    private List<ColumnInfo> columnInfos;

    public void setClassName() {
        this.className = CommonUtil.toHump(this.tableName);
    }

    public void setObjectName() {
        if (StringUtils.isEmpty(this.className)) {
            this.setClassName();
        }
        this.objectName = CommonUtil.toLowerHead(this.className);
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public List<ColumnInfo> getColumnInfos() {
        return columnInfos;
    }

    public void setColumnInfos(List<ColumnInfo> columnInfos) {
        this.columnInfos = columnInfos;
    }
}
