package com.me.code.customize.generator.template;

import cn.hutool.core.text.CharSequenceUtil;
import com.me.code.customize.generator.data.ColumnInfo;
import com.me.code.customize.generator.data.DataTypeTranslator;
import com.me.code.customize.generator.data.TableInfo;
import com.me.code.customize.generator.utils.GenerateConfig;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhull
 * @author stivenyang
 * <P>生成Domain的内容</P>
 */
@Slf4j
public class DomainGenerator extends AbsGenerator {

    public DomainGenerator(GenerateConfig config) {
        super(config);
    }

    public static final String PACKAGE = "package $modulePackagePath;";
    public static final String IMPORT = "import $modulePackagePath;";
    public static final String SPACE4 = "    ";
    private static final String SET_GET_TEMPLATE =
            " public void set&{ClassName}(&{type} &{instance}) {\n" + "        this.&{instance} = &{instance};\n"
                    + "    }\n" + " public &{type} get&{ClassName}() {\n" + "        return &{instance};\n" + "    }\n";

    /**
     * 添加类内容
     *
     * @param tableInfo 表信息
     * @param content   要处理的字符串
     */
    private static void addClass(TableInfo tableInfo, List<String> content) {
        content.add("public class " + tableInfo.getClassName() + " {");
        for (ColumnInfo columnInfo : tableInfo.getColumnInfos()) {
            content.add(SPACE4 + "private " + columnInfo.getType().getJava() + " " + columnInfo.getProperty() + ";");
            String t1 = SET_GET_TEMPLATE.replace("&{ClassName}", CharSequenceUtil.upperFirst(columnInfo.getProperty()));
            String t2 = t1.replace("&{instance}", columnInfo.getProperty());
            String t3 = t2.replace("&{type}", columnInfo.getType().getJava());
            content.add(SPACE4 + t3);

        }
        content.add("}");
    }

    /**
     * 添加类注解
     *
     * @param content 要处理的字符串
     */
    private static void addAnnotation(List<String> content) {
        //do something
        log.info("" + content);
    }

    /**
     * 添加类注释
     *
     * @param tableInfo raw materials 表信息
     * @param content   output 处理的内容
     */
    private static void addClassComment(TableInfo tableInfo, List<String> content) {
        //do something
        log.info("{} {}", tableInfo, content);
    }

    /**
     * 添加import语句
     *
     * @param tableInfo raw materials
     * @param content   output
     */
    private static void addImport(TableInfo tableInfo, List<String> content) {
        content.add("");
        boolean importBlob = false;
        boolean importClob = false;
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
                        log.error("未识别的数据类型: " + java2Mysql);
                }
            }
        }
    }

    private static boolean importPackage(List<String> content, boolean imported,
            DataTypeTranslator.Java2Mysql typeEnum) {
        if (!imported) {
            content.add(IMPORT.replace("$modulePackagePath", typeEnum.getPackageName()));
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
        content.add(PACKAGE.replace("$modulePackagePath", packageName));
    }

    @Override
    public String getClassNameSuffix() {
        return ".java";
    }

    @Override
    public String getClassTemplate() {
        List<String> content = new ArrayList<>();
        addPackage(config.getEntityPath(), content);
        addImport(getTableInfo(), content);
        addClassComment(getTableInfo(), content);
        addAnnotation(content);
        addClass(getTableInfo(), content);
        StringBuilder sb = new StringBuilder();
        for (String contentStr : content) {
            sb.append(contentStr).append("\n");
        }

        return sb.toString();
    }
}
