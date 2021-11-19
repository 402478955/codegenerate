package com.me.code.customize.generator.data;

import cn.hutool.core.text.CharSequenceUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * @author zhull
 * @author stivenyang
 * <P>表信息</P>
 */
@Getter
@Setter
@ToString
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
        this.className = CharSequenceUtil.toCamelCase(this.tableName);
    }

    public void setObjectName() {
        if (StringUtils.isEmpty(this.className)) {
            this.setClassName();
        }
        this.objectName = CharSequenceUtil.lowerFirst(this.className);
    }
}
