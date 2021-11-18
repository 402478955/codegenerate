package com.me.code.customize.generator.template;

import com.me.code.customize.generator.data.ColumnInfo;
import com.me.code.customize.generator.utils.GeneratConfig;
import com.me.code.customize.generator.utils.GeneratUtil;

public class TestControllerGenerator  extends AbsGenerator{
    private static String classTemplate = "package &{packagePath};\n" +
            "\n" +
            "import com.yxt.monitor.alert.util.HttpUtil;\n" +
            "import com.alibaba.fastjson.JSONObject;\n" +
            "import &{httpUtilPackagePath} ;\n" +
            "import &{importClass};\n" +
            "import &{responsePackagePath};\n" +
            "import org.junit.After;\n" +
            "import org.junit.Before;\n" +
            "import org.junit.Test;\n" +
            "import org.junit.runner.RunWith;\n" +
            "import org.springframework.boot.test.context.SpringBootTest;\n" +
            "import org.springframework.test.context.junit4.SpringRunner;\n" +
            "import java.util.ArrayList;\n" +
            "import java.util.List;\n" +
            "import static org.junit.Assert.assertTrue;\n" +
            "\n" +
            "@RunWith(SpringRunner.class)\n" +
            "public class &{className}ControllerTest {\n" +
            "    List<Integer>  idList=new ArrayList<>();\n" +
            "    @Before\n" +
            "    public void insert() {\n" +
            "        &{className} &{instance}1=insert(\"1\");\n" +
            "        idList.add(&{instance}1.getId());\n" +
            "        &{className} &{instance}2=insert(\"2\");\n" +
            "        idList.add(&{instance}2.getId());\n" +
            "    }\n" +
            "\n" +
            "    @After\n" +
            "    public void delete() { \n" +
            "        for (Integer id : idList) { \n" +
            "            HttpUtil.getResponseBody(HttpUtil.getHost() + \"&{instance}/deleteById?id=\" + id);\n" +
            "        } \n" +
            "    } \n" +
            "    private void equal&{className}( &{className}  one ,   &{className}  another  ){\n" +
            "        &{equalField}\n" +
            "    }\n" +
            "    private &{className} insert(String  suffix){\n" +
            "        &{className} &{instance}=new &{className}();\n" +
            "        &{insertField} \n" +
            "        String response = HttpUtil.doPostForJson(HttpUtil.getHost() + \"&{instance}/add\", JSONObject.toJSONString(&{instance}));\n" +
            "        &{responseName} response8 = JSONObject.parseObject(response, &{responseName}.class);\n" +
            "        JSONObject  json=(JSONObject)response8.getData();\n" +
            "        return  json.toJavaObject(&{className}.class);\n" +
            "\n" +
            "    }\n" +
            "}\n";

    private static String  assertEqualTemplate = "assertTrue(one.get&{fieldName}() .equals(another.get&{fieldName}()) );";
    private static String  insertPropertyTemplate = "&{instance}.set&{fieldName}(\"&{value}\"+suffix);";

    public TestControllerGenerator(GeneratConfig config) {
        super(config);
    }

    @Override
    public String getClassNameSuffix() throws Exception {
        return "ControllerTest.java";
    }

    @Override
    public String getClassTemplate() throws Exception {
        String simpleName = getClassName();
        StringBuffer sbColumn = new StringBuffer();
        StringBuffer sbAssertEqual = new StringBuffer();
        StringBuffer sbInsertProperty = new StringBuffer();
        for (ColumnInfo columnInfo:  getTableInfo().getColumnInfos()) {
            String fieldName = columnInfo.getProperty();
            sbColumn.append( fieldName+ ",");
            if(!"id".equals(fieldName)&&!"updateDate".equals(fieldName)){
                sbAssertEqual.append(assertEqualTemplate.replace("&{fieldName}", GeneratUtil.firstCharUpperCase(fieldName))  );

                if("String".equals(columnInfo.getType().java)){
                    sbInsertProperty.append(insertPropertyTemplate
                            .replace("&{fieldName}", GeneratUtil.firstCharUpperCase(fieldName))
                            .replace("&{value}", GeneratUtil.firstCharUpperCase(fieldName)));
                }else {
                    sbInsertProperty.append(insertPropertyTemplate
                            .replace("&{fieldName}", GeneratUtil.firstCharUpperCase(fieldName))
                            .replace("\"&{value}\"+suffix", columnInfo.getType().defaultValue))
                    ;

                }

            }
        }
        String responsePackagePath=config.getResponsePackagePath();
        String generateClass=classTemplate.replace("&{packagePath}", config.getControllerPackagePath())
                .replace("&{httpUtilPackagePath}", config.getHttpUtilPackagePath())
                .replace("&{responsePackagePath}", config.getResponsePackagePath())
                .replace("&{insertField}",
                        sbInsertProperty.toString().replace("&{instance}", GeneratUtil.firstCharLowCase(simpleName)))
                .replace("&{equalField}",sbAssertEqual.toString())
                .replace("&{responseName}",responsePackagePath.substring(responsePackagePath.lastIndexOf(".")+1,
                        responsePackagePath.length()));
        return generateClass;
    }
}
