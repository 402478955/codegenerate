package com.me.code.customize.generator.template;

import com.me.code.customize.generator.data.ColumnInfo;
import com.me.code.customize.generator.utils.GeneratConfig;
import com.me.code.customize.generator.utils.GeneratUtil;

public class ServiceGenerator extends AbsGenerator {
    private static String classTemplate = "package &{packagePath};\n" +
            "\n" +
            "import &{importDaoClass}.&{className}Dao;\n" +
            "import &{importClass};\n" +
            "import org.springframework.beans.factory.annotation.Autowired;\n" +
            "import org.springframework.stereotype.Component; \n" +
            "import java.util.List;\n" +
            "\n" +
            "@Component\n" +
            "public class &{className}Service {\n" +
            "    @Autowired\n" +
            "    private &{className}Dao &{instance}Dao;\n" +
            "\n" +
            "    public Long insertOrUpdate(&{className} &{instance}) {\n" +
            "        return  &{instance}Dao.insertOrUpdate(&{instance});\n" +
            "    }\n" +
            "\n" +

            "    public List<&{className}> selectAll() {\n" +
            "        return &{instance}Dao.selectAll();\n" +
            "    }\n" +
            "\n" +
            "    public Integer deleteById(Integer id) {\n" +
            "        return &{instance}Dao.deleteById(id);\n" +
            "    }\n" +
            "\n" +
            "&{selectMethod} \n" +
            "}";

    private static String selectMethodTemplate = "public &{className} selectBy&{PropertyName}(String &{LowCasePropertyName}) {\n" +
            "        return &{instance}Dao.selectBy&{PropertyName}(&{LowCasePropertyName});\n" +
            "    }";

    public ServiceGenerator(GeneratConfig config) {
        super(config);
    }

    @Override
    public String getClassNameSuffix() throws Exception {
        return "Service.java";
    }

    @Override
    public String getClassTemplate() throws Exception {
        String simpleName = getClassName();
        StringBuffer sbSelectMethodTemplate = new StringBuffer();
        for (ColumnInfo field : getTableInfo().getColumnInfos()) {
            String fieldName = field.getProperty();
            if (!"id".equals(fieldName) && !"updateDate".equals(fieldName)) {
                sbSelectMethodTemplate.append(selectMethodTemplate.replace("&{PropertyName}", GeneratUtil.firstCharUpperCase(fieldName))
                        .replace("&{LowCasePropertyName}", fieldName)
                        .replace("&{className}", simpleName)
                        .replace("&{instance}", GeneratUtil.firstCharLowCase(simpleName)));
            }

        }
        String generateClass = classTemplate.replace("&{packagePath}", config.getServicePackagePath())
                .replace("&{selectMethod}", sbSelectMethodTemplate.toString());
        return generateClass;
    }
}
