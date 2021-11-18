package com.me.code.customize.generator.data;

import com.me.code.customize.generator.utils.CommonUtil;

/**
 * @author zhull
 * @date 2018/6/12
 * <P>列信息</P>
 */
public class ColumnInfo {
    private String defaultValue;
    /**
     * 列名
     */
    private String columnName;

    /**
     * 数据类型
     */
    private String dataType;

    /**
     * 注释
     */
    private String comment;

    /**
     * 键
     */
    private String key;

    /**
     * 属性名
     */
    private String property;

    /**
     * 数据类型枚举
     */
    private DataTypeTranslator.DataType type;

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public void setProperty() {
        this.property = CommonUtil.toLowerHead(CommonUtil.toHump(this.columnName));
    }

    public void setType() {
        this.type = DataTypeTranslator.DataType.instanceOf(this.dataType);
        if (this.type == null) {
            throw new RuntimeException("未识别的数据类型: " + this);
        }
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public DataTypeTranslator.DataType getType() {
        return type;
    }

    public void setType(DataTypeTranslator.DataType type) {
        this.type = type;
    }
}
