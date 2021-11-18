package com.me.code.customize.generator.data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhull
 * @date 2018/6/13
 * <P>MySQL -> Java, Mybatis 类型转换</P>
 */
public class DataTypeTranslator {

    private static String DATE_PACKAGE = "java.util.Date";
    private static String LOCAL_DATE_PACKAGE = "java.time.LocalDate";
    private static String LOCAL_DATE_TIME_PACKAGE = "java.time.LocalDateTime";
    private static String BIG_DECIMAL_PACKAGE = "java.math.BigDecimal";
    private static String BLOB_PACKAGE = "java.sql.Blob";
    private static String CLOB_PACKAGE = "java.sql.Clob";

    public enum Java2Mysql {
        //        DATE(new ArrayList<DataType>() {{
//                add(DataType.DATE);
//                add(DataType.DATETIME);
//                add(DataType.TIMESTAMP);
//            }}, DATE_PACKAGE),
        LOCAL_DATE(new ArrayList<DataType>() {{
            add(DataType.DATE_4LD);
        }}, LOCAL_DATE_PACKAGE),
        BIG_DECIMAL(new ArrayList<DataType>() {{
            add(DataType.NUMERIC);
            add(DataType.DECIMAL);
        }}, BIG_DECIMAL_PACKAGE),
        LOCAL_DATE_TIME(new ArrayList<DataType>() {{
            add(DataType.DATETIME_4LD);
        }}, LOCAL_DATE_TIME_PACKAGE),
        BLOB(new ArrayList<DataType>() {{
            add(DataType.BLOB);
        }}, BLOB_PACKAGE),
        CLOB(new ArrayList<DataType>() {{
            add(DataType.CLOB);
        }}, CLOB_PACKAGE),
        ;

        public List<DataType> dataTypes;
        public String packageName;

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
    public enum DataType {
        BIGINT("BIGINT", "BIGINT", "Long", "java.lang.Long", false,"1l"),
        CHAR("CHAR", "CHAR", "String", "java.lang.String", false,"1"),
        TEXT("TEXT", "CLOB", "String", "java.lang.String", false,"1"),
        JSON("JSON", "String", "String", "java.lang.String", false,"1"),
        DOUBLE("DOUBLE", "DOUBLE", "Double", "java.lang.Double", false,"1d"),
        FLOAT("FLOAT", "FLOAT", "Double", "java.lang.Double", false,"1d"),
        INTEGER("INTEGER", "INTEGER", "Integer", "java.lang.Integer", false,"1"),
        INT("INT", "INTEGER", "Integer", "java.lang.Integer", false,"1"),
        TINYINT("TINYINT", "TINYINT", "Byte", "java.lang.Byte", false,"1"),
        VARCHAR("VARCHAR", "VARCHAR", "String", "java.lang.String", false,"1"),
        REAL("REAL", "REAL", "FLOAT", "java.lang.Float", false,"1f"),
        SMALLINT("SMALLINT", "SMALLINT", "Short", "java.lang.Short", false,"1"),
        LONGBLOB("LONGBLOB", "BLOB", "Byte[]", "java.lang.Byte", false,"1"),
        BLOB("BLOB", "BLOB", "Blob", BLOB_PACKAGE, true,"1"),
        CLOB("CLOB", "CLOB", "Clob", CLOB_PACKAGE, true,"1"),
        NUMERIC("NUMERIC", "NUMERIC", "BigDecimal", BIG_DECIMAL_PACKAGE, true,"1"),
        DECIMAL("DECIMAL", "DECIMAL", "BigDecimal", BIG_DECIMAL_PACKAGE, true,"1"),

//        DATE("DATE", "DATE", "Date", DATE_PACKAGE, true),
//        DATETIME("DATETIME", "TIMESTAMP", "Date", DATE_PACKAGE, true),
//        TIMESTAMP("TIMESTAMP", "TIMESTAMP", "Date", DATE_PACKAGE, true),

        DATE_4LD("DATE", "DATE", "LocalDate", LOCAL_DATE_PACKAGE, true,"new Date()"),
        DATETIME_4LD("DATETIME", "TIMESTAMP", "Date", LOCAL_DATE_TIME_PACKAGE, true,"new Date()"),
        TIMESTAMP_4LD("TIMESTAMP", "TIMESTAMP", "Date", LOCAL_DATE_TIME_PACKAGE, true,"new Date()"),
        ;

        public String mysql;
        public String mybatis;
        public String java;
        public String packageNeed;
        public Boolean needImport;
        public String defaultValue;

        DataType(String mysql, String mybatis, String java, String packageNeed, Boolean needImport,String defaultValue) {
            this.mysql = mysql;
            this.mybatis = mybatis;
            this.java = java;
            this.packageNeed = packageNeed;
            this.needImport = needImport;
            this.defaultValue=defaultValue;
        }

        public static DataType instanceOf(String mysql) {
            if (mysql == null || mysql == "") {
                return null;
            }
            for (DataType e : DataType.values()) {
                if (e.mysql.equals(mysql)) {
                    return e;
                }
            }
            return null;
        }
    }
}
