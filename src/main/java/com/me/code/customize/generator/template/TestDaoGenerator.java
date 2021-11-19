package com.me.code.customize.generator.template;

import cn.hutool.core.text.CharSequenceUtil;
import com.me.code.customize.generator.data.ColumnInfo;
import com.me.code.customize.generator.utils.GenerateConfig;

public class TestDaoGenerator extends AbsGenerator {
    private static final String CLASS_TEMPLATE = "package &{packagePath};\n" + "\n" + "import &{importClass};\n"
            + "import &{importDaoClass}.&{className}Dao;\n" + "import &{startUpPath}.ApiStartApplication;\n"
            + "import org.junit.After;\n" + "import org.junit.Before;\n" + "import org.junit.Test;\n"
            + "import org.junit.runner.RunWith;\n" + "import org.springframework.beans.factory.annotation.Autowired;\n"
            + "import org.springframework.boot.test.context.SpringBootTest;\n"
            + "import org.springframework.test.context.junit4.SpringRunner;\n"
            + "import org.springframework.test.context.ActiveProfiles;\n" + "import java.util.ArrayList;\n"
            + "import java.util.List;\n" + "import java.util.Date;\n" + "import static org.junit.Assert.assertTrue;\n"
            + "\n" + "@RunWith(SpringRunner.class)\n" + "@ActiveProfiles(\"native\")\n"
            + "@SpringBootTest(classes = ApiStartApplication.class )\n" + "public class &{className}DaoTest {\n"
            + "    @Autowired\n" + "    private &{className}Dao &{instance}Dao;\n"
            + "    List<Long>  idList=new ArrayList<>();\n" + "    @Before\n" + "    public void insert() {\n"
            + "        &{className} &{instance}1=insert(\"1\");\n" + "        idList.add(&{instance}1.getId());\n"
            + "        &{className} &{instance}2=insert(\"2\");\n" + "        idList.add(&{instance}2.getId());\n"
            + "   }\n" + "    private &{className} insert(String  suffix){\n"
            + "        &{className} &{instance}=new &{className}();\n" + "        &{insertField} \n"
            + "        &{instance}Dao.insertOrUpdate(&{instance});\n" + "        return &{instance};\n" + "\n" + "  }\n"
            + "    @After\n" + "    public void delete() {\n" + "        for (Long id : idList) {\n"
            + "            &{instance}Dao.deleteById(id);\n" + "        }\n" + "\n" + "    }\n"
            + "    private void equal&{className}( &{className}  one ,   &{className}  another  ){\n"
            + "        &{equalField}\n" + "    }\n" + "\n" + "}\n";

    private static final String ASSERT_EQUAL_TEMPLATE = "assertTrue(one.get&{fieldName}() .equals(another.get&{fieldName}()) );";
    private static final String INSERT_PROPERTY_TEMPLATE = "&{instance}.set&{fieldName}(\"&{value}\"+suffix);";
    public static final String FIELD_NAME = "&{fieldName}";

    public TestDaoGenerator(GenerateConfig config) {
        super(config);
    }

    @Override
    public String getClassNameSuffix() {
        return "DaoTest.java";
    }

    @Override
    public String getClassTemplate() {
        StringBuilder sbColumn = new StringBuilder();
        StringBuilder sbAssertEqual = new StringBuilder();
        StringBuilder sbInsertProperty = new StringBuilder();
        for (ColumnInfo columnInfo : super.getTableInfo().getColumnInfos()) {
            String fieldName = columnInfo.getProperty();
            sbColumn.append(fieldName).append(",");
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
        return CLASS_TEMPLATE.replace("&{packagePath}", config.getDaoPackagePath())
                .replace("&{insertField}", sbInsertProperty.toString())
                .replace("&{equalField}", sbAssertEqual.toString());
    }
}
