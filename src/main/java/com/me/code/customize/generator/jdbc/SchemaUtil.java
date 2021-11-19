package com.me.code.customize.generator.jdbc;

import com.me.code.customize.generator.data.ColumnInfo;
import com.me.code.customize.generator.data.TableInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author zhull
 * @author stivenyang
 * <P>Schema操作</P>
 */
@Slf4j
public class SchemaUtil {

    public static final String TABLE_SCHEMA = "$tableSchema";

    private SchemaUtil() {
    }

    /**
     * 获取db的表属性
     */
    private static final String TABLE_TEMPLATE = "select TABLE_NAME, TABLE_COMMENT from information_schema.`TABLES` t where t.TABLE_SCHEMA = '$tableSchema'";
    private static final String SOME_TABLE_TEMPLATE = TABLE_TEMPLATE + " and t.TABLE_NAME in ($tableName)";

    /**
     * 获取表的列属性
     * COLUMN_KEY 列的键属性
     */
    private static final String COLUMN_TEMPLATE = "select t.COLUMN_NAME, upper(t.DATA_TYPE), t.COLUMN_COMMENT, upper(t.COLUMN_KEY),column_default FROM information_schema.COLUMNS t where t.TABLE_SCHEMA = '$tableSchema' and t.TABLE_NAME = '$tableName' ORDER BY t.ORDINAL_POSITION";

    /**
     * 如tableNames为空，则查询tableSchema所有表
     *
     * @param tableSchema 表摘要
     * @param tableNames  表名列表
     * @return 表对象列表
     */
    public static List<TableInfo> getTableInfos(String tableSchema, List<String> tableNames) throws SQLException {
        if (StringUtils.isEmpty(tableSchema)) {
            return Collections.emptyList();
        }
        List<TableInfo> tableInfos = new ArrayList<>();
        if (tableNames == null || tableNames.isEmpty()) {
            getTableInfos(tableInfos, DbUtil.executeQuery(TABLE_TEMPLATE.replace(TABLE_SCHEMA, tableSchema)));
        } else {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < tableNames.size() - 1; ++i) {
                sb.append("'").append(tableNames.get(i)).append("'").append(",");
            }
            sb.append("'").append(tableNames.get(tableNames.size() - 1)).append("'");
            getTableInfos(tableInfos, DbUtil.executeQuery(
                    SOME_TABLE_TEMPLATE.replace(TABLE_SCHEMA, tableSchema).replace("$tableName", sb)));
        }
        log.info("即将处理" + tableInfos.size() + "张表\t");
        for (TableInfo tableInfo : tableInfos) {
            log.info(tableInfo.getTableName() + "; ");
            ResultSet resultSet = DbUtil.executeQuery(COLUMN_TEMPLATE.replace(TABLE_SCHEMA, tableSchema)
                    .replace("$tableName", tableInfo.getTableName().toLowerCase()));
            if (resultSet == null) {
                continue;
            }
            List<ColumnInfo> columnInfos = new ArrayList<>();
            while (resultSet.next()) {
                ColumnInfo columnInfo = new ColumnInfo();
                try {
                    columnInfo.setColumnName(resultSet.getString(1));
                    columnInfo.setDataType(resultSet.getString(2));
                    columnInfo.setComment(resultSet.getString(3));
                    columnInfo.setKey(resultSet.getString(4));
                    columnInfo.setDefaultValue(resultSet.getString(5));
                    columnInfo.setProperty();
                    columnInfo.setType();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                columnInfos.add(columnInfo);
            }
            tableInfo.setColumnInfos(columnInfos);
        }
        log.info("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        return tableInfos;
    }

    /**
     * 从结果集解析表信息
     *
     * @param tableInfos     表信息
     * @param tableResultSet 表的结果集
     * @throws SQLException SQL异常
     */
    private static void getTableInfos(List<TableInfo> tableInfos, ResultSet tableResultSet) throws SQLException {
        if (tableResultSet == null) {
            return;
        }
        while (tableResultSet.next()) {
            TableInfo tableInfo = new TableInfo();
            tableInfo.setTableName(tableResultSet.getString(1).toUpperCase());
            tableInfo.setComment(tableResultSet.getString(2));
            tableInfo.setClassName();
            tableInfo.setObjectName();
            tableInfos.add(tableInfo);
        }
    }

}
