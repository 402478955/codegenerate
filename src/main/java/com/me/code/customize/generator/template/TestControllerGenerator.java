package com.me.code.customize.generator.template;

import cn.hutool.core.text.CharSequenceUtil;
import com.me.code.customize.generator.data.ColumnInfo;
import com.me.code.customize.generator.utils.GenerateConfig;

public class TestControllerGenerator extends AbsGenerator {
    private static final String CLASS_TEMPLATE =
            "package &{packagePath};\n" + "\n" + "import com.yxt.monitor.alert.util.HttpUtil;\n"
                    + "import com.alibaba.fastjson.JSONObject;\n" + "import &{httpUtilPackagePath} ;\n"
                    + "import &{importClass};\n" + "import &{responsePackagePath};\n" + "import org.junit.After;\n"
                    + "import org.junit.Before;\n" + "import org.junit.Test;\n" + "import org.junit.runner.RunWith;\n"
                    + "import org.springframework.boot.test.context.SpringBootTest;\n"
                    + "import org.springframework.test.context.junit4.SpringRunner;\n" + "import java.util.ArrayList;\n"
                    + "import java.util.List;\n" + "import static org.junit.Assert.assertTrue;\n" + "\n"
                    + "@RunWith(SpringRunner.class)\n" + "public class &{className}ControllerTest {\n"
                    + "    List<Integer>  idList=new ArrayList<>();\n" + "    @Before\n"
                    + "    public void insert() {\n" + "        &{className} &{instance}1=insert(\"1\");\n"
                    + "        idList.add(&{instance}1.getId());\n"
                    + "        &{className} &{instance}2=insert(\"2\");\n"
                    + "        idList.add(&{instance}2.getId());\n" + "   }\n" + "\n" + "    @After\n"
                    + "    public void delete() { \n" + "        for (Integer id : idList) { \n"
                    + "            HttpUtil.getResponseBody(HttpUtil.getHost() + \"&{instance}/deleteById?id=\" + id);\n"
                    + "        } \n" + "    } \n"
                    + "    private void equal&{className}( &{className}  one ,   &{className}  another  ){\n"
                    + "        &{equalField}\n" + "    }\n" + "    private &{className} insert(String  suffix){\n"
                    + "        &{className} &{instance}=new &{className}();\n" + "        &{insertField} \n"
                    + "        String response = HttpUtil.doPostForJson(HttpUtil.getHost() + \"&{instance}/add\", JSONObject.toJSONString(&{instance}));\n"
                    + "        &{responseName} response8 = JSONObject.parseObject(response, &{responseName}.class);\n"
                    + "        JSONObject  json=(JSONObject)response8.getData();\n"
                    + "        return  json.toJavaObject(&{className}.class);\n" + "\n" + "    }\n" + "}\n";

    private static final String ASSERT_EQUAL_TEMPLATE = "assertTrue(one.get&{fieldName}() .equals(another.get&{fieldName}()) );";
    private static final String INSERT_PROPERTY_TEMPLATE = "&{instance}.set&{fieldName}(\"&{value}\"+suffix);";
    public static final String FIELD_NAME = "&{fieldName}";

    public TestControllerGenerator(GenerateConfig config) {
        super(config);
    }

    @Override
    public String getClassNameSuffix() {
        return "ControllerTest.java";
    }

    @Override
    public String getClassTemplate() {
        String simpleName = getClassName();
        StringBuilder sbAssertEqual = new StringBuilder();
        StringBuilder sbInsertProperty = new StringBuilder();
        for (ColumnInfo columnInfo : getTableInfo().getColumnInfos()) {
            String fieldName = columnInfo.getProperty();
            if (!"id".equals(fieldName) && !"updateDate".equals(fieldName)) {
                sbAssertEqual.append(ASSERT_EQUAL_TEMPLATE.replace(FIELD_NAME, CharSequenceUtil.upperFirst(fieldName)));

                if ("String".equals(columnInfo.getType().getJava())) {
                    sbInsertProperty.append(
                            INSERT_PROPERTY_TEMPLATE.replace(FIELD_NAME, CharSequenceUtil.upperFirst(fieldName))
                                    .replace("&{value}", CharSequenceUtil.upperFirst(fieldName)));
                } else {
                    sbInsertProperty.append(
                            INSERT_PROPERTY_TEMPLATE.replace(FIELD_NAME, CharSequenceUtil.upperFirst(fieldName))
                                    .replace("\"&{value}\"+suffix", columnInfo.getType().getDefaultValue()));

                }

            }
        }
        String responsePackagePath = config.getResponsePackagePath();
        return CLASS_TEMPLATE.replace("&{packagePath}", config.getControllerPackagePath())
                .replace("&{httpUtilPackagePath}", config.getHttpUtilPackagePath())
                .replace("&{responsePackagePath}", config.getResponsePackagePath()).replace("&{insertField}",
                        sbInsertProperty.toString().replace("&{instance}", CharSequenceUtil.lowerFirst(simpleName)))
                .replace("&{equalField}", sbAssertEqual.toString())
                .replace("&{responseName}", responsePackagePath.substring(responsePackagePath.lastIndexOf(".") + 1));
    }
}
