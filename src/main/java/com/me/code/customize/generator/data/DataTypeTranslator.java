package com.me.code.customize.generator.data;

import cn.hutool.core.collection.ListUtil;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhull
 * @author stivenyang
 * <P>MySQL -> Java, Mybatis 类型转换</P>
 */
@Getter
@ToString
public class DataTypeTranslator {

    private static final String DATE_PACKAGE = "java.util.Date";
    private static final String LOCAL_DATE_PACKAGE = "java.time.LocalDate";
    private static final String LOCAL_DATE_TIME_PACKAGE = "java.time.LocalDateTime";
    private static final String BIG_DECIMAL_PACKAGE = "java.math.BigDecimal";
    private static final String BLOB_PACKAGE = "java.sql.Blob";
    private static final String CLOB_PACKAGE = "java.sql.Clob";

    /**
     * Java转MySQL对应数据类型
     */
    @Getter
    public enum Java2Mysql {
        LOCAL_DATE(ListUtil.toList(DataType.DATE_4LD), LOCAL_DATE_PACKAGE),
        BIG_DECIMAL(ListUtil.toList(DataType.NUMERIC, DataType.DECIMAL), BIG_DECIMAL_PACKAGE),
        LOCAL_DATE_TIME(ListUtil.toList(DataType.DATETIME_4LD), LOCAL_DATE_TIME_PACKAGE),
        BLOB(ListUtil.toList(DataType.BLOB), BLOB_PACKAGE),
        CLOB(ListUtil.toList(DataType.CLOB), CLOB_PACKAGE),
        ;

        private final List<DataType> dataTypes;
        private final String packageName;

        Java2Mysql(ArrayList<DataType> dataTypes, String packageName) {
            this.dataTypes = dataTypes;
            this.packageName = packageName;
        }

        public static Java2Mysql instanceOf(DataType dataType) {
            if (dataType == null) {
                return null;
            }
            for (Java2Mysql e : Java2Mysql.values()) {
                if (e.dataTypes.contains(dataType)) {
                    return e;
                }
            }
            return null;
        }
    }

    /**
     * MySql数据类型
     */
    @Getter
    public enum DataType {
        BIGINT("BIGINT", "BIGINT", "Long", "java.lang.Long", false, "1l"),
        CHAR("CHAR", "CHAR", Constants.STRING, Constants.JAVA_LANG_STRING, false, "1"),
        TEXT("TEXT", "CLOB", Constants.STRING, Constants.JAVA_LANG_STRING, false, "1"),
        JSON("JSON", Constants.STRING, Constants.STRING, Constants.JAVA_LANG_STRING, false, "1"),
        DOUBLE("DOUBLE", "DOUBLE", "Double", "java.lang.Double", false, "1d"),
        FLOAT(Constants.FLOAT1, Constants.FLOAT1, "Double", "java.lang.Double", false, "1d"),
        INTEGER(Constants.INTEGER1, Constants.INTEGER1, "Integer", "java.lang.Integer", false, "1"),
        INT("INT", Constants.INTEGER1, "Integer", "java.lang.Integer", false, "1"),
        TINYINT("TINYINT", "TINYINT", "Byte", "java.lang.Byte", false, "1"),
        VARCHAR("VARCHAR", "VARCHAR", Constants.STRING, Constants.JAVA_LANG_STRING, false, "1"),
        REAL("REAL", "REAL", Constants.FLOAT1, "java.lang.Float", false, "1f"),
        SMALLINT("SMALLINT", "SMALLINT", "Short", "java.lang.Short", false, "1"),
        LONGBLOB("LONGBLOB", "BLOB", "Byte[]", "java.lang.Byte", false, "1"),
        BLOB("BLOB", "BLOB", "Blob", BLOB_PACKAGE, true, "1"),
        CLOB("CLOB", "CLOB", "Clob", CLOB_PACKAGE, true, "1"),
        NUMERIC("NUMERIC", "NUMERIC", "BigDecimal", BIG_DECIMAL_PACKAGE, true, "1"),
        DECIMAL("DECIMAL", "DECIMAL", "BigDecimal", BIG_DECIMAL_PACKAGE, true, "1"),

        DATE_4LD("DATE", "DATE", "LocalDate", LOCAL_DATE_PACKAGE, true, Constants.NEW_DATE),
        DATETIME_4LD("DATETIME", Constants.TIMESTAMP, "Date", LOCAL_DATE_TIME_PACKAGE, true, Constants.NEW_DATE),
        TIMESTAMP_4LD(Constants.TIMESTAMP, Constants.TIMESTAMP, "Date", LOCAL_DATE_TIME_PACKAGE, true,
                Constants.NEW_DATE),
        ;

        private static class Constants {
            public static final String JAVA_LANG_STRING = "java.lang.String";
            public static final String STRING = "String";
            public static final String FLOAT1 = "FLOAT";
            public static final String INTEGER1 = "INTEGER";
            public static final String TIMESTAMP = "TIMESTAMP";
            public static final String NEW_DATE = "new Date()";
        }

        private final String mysql;
        private final String mybatis;
        private final String java;
        private final String packageNeed;
        private final Boolean needImport;
        private final String defaultValue;

        DataType(String mysql, String mybatis, String java, String packageNeed, Boolean needImport,
                String defaultValue) {
            this.mysql = mysql;
            this.mybatis = mybatis;
            this.java = java;
            this.packageNeed = packageNeed;
            this.needImport = needImport;
            this.defaultValue = defaultValue;
        }

        public static DataType instanceOf(String mysql) {
            if (mysql == null || "".equals(mysql)) {
                return null;
            }
            for (DataType e : DataType.values()) {
                if (e.getMysql().equals(mysql)) {
                    return e;
                }
            }
            return null;
        }
    }
}
