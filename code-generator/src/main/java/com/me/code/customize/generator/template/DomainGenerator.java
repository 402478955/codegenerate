package com.me.code.customize.generator.template;

import com.me.code.customize.generator.data.ColumnInfo;
import com.me.code.customize.generator.data.ConfigInfo;
import com.me.code.customize.generator.data.DataTypeTranslator;
import com.me.code.customize.generator.data.TableInfo;
import com.me.code.customize.generator.utils.CommonUtil;
import com.me.code.customize.generator.utils.FileUtil;
import com.me.code.customize.generator.utils.GeneratConfig;
import com.me.code.customize.generator.utils.GeneratUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhull
 * @date 2018/6/13
 * <P>生成Domain的内容</P>
 */
public class DomainGenerator  extends AbsGenerator{

    public DomainGenerator(GeneratConfig config) {
        super(config);
    }

    private static String setGetTemplate = " public void set&{ClassName}(&{type} &{instance}) {\n" +
            "        this.&{instance} = &{instance};\n" +
            "    }\n" +
            " public &{type} get&{ClassName}() {\n" +
            "        return &{instance};\n" +
            "    }\n";

    /**
     * 添加类内容
     *
     * @param tableInfo
     * @param content
     */
    private static void addClass(TableInfo tableInfo, List<String> content) {
        content.add("public class " + tableInfo.getClassName() + " {");
        for (ColumnInfo columnInfo : tableInfo.getColumnInfos()) {
            content.add(CommonUtil.SPACE4 + "private " + columnInfo.getType().java + " " + columnInfo.getProperty() + ";");
            String t1 = setGetTemplate.replace("&{ClassName}", GeneratUtil.firstCharUpperCase(columnInfo.getProperty()));
            String t2 = t1.replace("&{instance}", columnInfo.getProperty());
            String t3 = t2.replace("&{type}", columnInfo.getType().java);
            content.add(CommonUtil.SPACE4 + t3);

        }
        content.add("}");
    }

    /**
     * 添加类注解
     *
     * @param content
     */
    private static void addAnnotation(List<String> content) {

    }

    /**
     * 添加类注释
     *
     * @param tableInfo raw materials
     * @param content   output
     */
    private static void addClassComment(TableInfo tableInfo, List<String> content) {

    }

    /**
     * 添加import语句
     *
     * @param tableInfo raw materials
     * @param content   output
     */
    private static void addImport(TableInfo tableInfo, List<String> content) {
        content.add("");
        //content.add("import lombok.Data;");
        boolean importBlob = false;
        boolean importClob = false;
        boolean importDate = false;
        boolean importLocalDate = false;
        boolean importLocalDateTime = false;
        boolean importBigDecimal = false;
        content.add("import java.util.Date;");
        for (ColumnInfo columnInfo : tableInfo.getColumnInfos()) {
            DataTypeTranslator.Java2Mysql java2Mysql = DataTypeTranslator.Java2Mysql.instanceOf(columnInfo.getType());
            if (java2Mysql != null) {
                switch (java2Mysql) {
                    case BLOB:
                        importBlob = importPackage(content, importBlob, java2Mysql);
                        break;
                    case CLOB:
                        importClob = importPackage(content, importClob, java2Mysql);
                        break;
//                    case DATE:
//                        importDate = importPackage(content, importDate, java2Mysql);
//                        break;
                    case LOCAL_DATE:
                        importLocalDate = importPackage(content, importLocalDate, java2Mysql);
                        break;
                    case LOCAL_DATE_TIME:
                        importLocalDateTime = importPackage(content, importLocalDateTime, java2Mysql);
                        break;
                    case BIG_DECIMAL:
                        importBigDecimal = importPackage(content, importBigDecimal, java2Mysql);
                        break;
                    default:
                        System.err.println("未识别的数据类型: " + java2Mysql);
                }
            }
        }
    }

    private static boolean importPackage(List<String> content, boolean imported, DataTypeTranslator.Java2Mysql typeEnum) {
        if (!imported) {
            content.add(CommonUtil.IMPORT.replace("$modulePackagePath", typeEnum.packageName));
        }
        return true;
    }

    /**
     * 添加引包语句
     *
     * @param packageName 包名
     * @param content     output
     */
    static void addPackage(String packageName, List<String> content) {
        content.add(CommonUtil.PACKAGE.replace("$modulePackagePath", packageName));
    }

    @Override
    public String getClassNameSuffix() throws Exception {
        return ".java";
    }

    @Override
    public String getClassTemplate() throws Exception {
        List<String> content = new ArrayList();
        addPackage(config.getEntityPath(), content);
        addImport(getTableInfo(), content);
        addClassComment(getTableInfo(), content);
        addAnnotation(content);
        addClass(getTableInfo(), content);
        StringBuffer sb=new  StringBuffer();
        for(String contentStr: content){
            sb.append(contentStr+"\n");
        }

        return sb.toString();
    }
}
