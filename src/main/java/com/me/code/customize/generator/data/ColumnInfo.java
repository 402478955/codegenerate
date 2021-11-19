package com.me.code.customize.generator.data;

import cn.hutool.core.text.CharSequenceUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author zhull
 * @author stivenyang
 * <P>列信息</P>
 */
@Getter
@Setter
@ToString
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

    public void setProperty() {
        this.property = CharSequenceUtil.lowerFirst(CharSequenceUtil.toCamelCase(this.columnName));
    }

    public void setType() {
        this.type = DataTypeTranslator.DataType.instanceOf(this.dataType);
        if (this.type == null) {
            throw new RuntimeException("未识别的数据类型: " + this);
        }
    }
}
