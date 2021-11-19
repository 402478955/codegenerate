package com.me.code.customize.generator.template;

import cn.hutool.core.text.CharSequenceUtil;
import com.me.code.customize.generator.data.ColumnInfo;
import com.me.code.customize.generator.utils.GenerateConfig;

public class ServiceGenerator extends AbsGenerator {
    private static final String CLASS_TEMPLATE =
            "package &{packagePath};\n" + "\n" + "import &{importDaoClass}.&{className}Dao;\n"
                    + "import &{importClass};\n" + "import org.springframework.beans.factory.annotation.Autowired;\n"
                    + "import org.springframework.stereotype.Component; \n" + "import java.util.List;\n" + "\n"
                    + "@Component\n" + "public class &{className}Service {\n" + "    @Autowired\n"
                    + "    private &{className}Dao &{instance}Dao;\n" + "\n"
                    + "    public Long insertOrUpdate(&{className} &{instance}) {\n"
                    + "        return  &{instance}Dao.insertOrUpdate(&{instance});\n" + "   }\n" + "\n" +

                    "    public List<&{className}> selectAll() {\n" + "        return &{instance}Dao.selectAll();\n"
                    + "    }\n" + "\n" + "    public Integer deleteById(Integer id) {\n"
                    + "        return &{instance}Dao.deleteById(id);\n" + "    }\n" + "\n" + "&{selectMethod} \n" + "}";

    private static final String SELECT_METHOD_TEMPLATE =
            "public &{className} selectBy&{PropertyName}(String &{LowCasePropertyName}) {\n"
                    + "        return &{instance}Dao.selectBy&{PropertyName}(&{LowCasePropertyName});\n" + "    }";

    public ServiceGenerator(GenerateConfig config) {
        super(config);
    }

    @Override
    public String getClassNameSuffix() {
        return "Service.java";
    }

    @Override
    public String getClassTemplate() {
        String simpleName = getClassName();
        StringBuilder sbSelectMethodTemplate = new StringBuilder();
        for (ColumnInfo field : getTableInfo().getColumnInfos()) {
            String fieldName = field.getProperty();
            if (!"id".equals(fieldName) && !"updateDate".equals(fieldName)) {
                sbSelectMethodTemplate.append(
                        SELECT_METHOD_TEMPLATE.replace("&{PropertyName}", CharSequenceUtil.upperFirst(fieldName))
                                .replace("&{LowCasePropertyName}", fieldName).replace("&{className}", simpleName)
                                .replace("&{instance}", CharSequenceUtil.lowerFirst(simpleName)));
            }

        }
        return CLASS_TEMPLATE.replace("&{packagePath}", config.getServicePackagePath())
                .replace("&{selectMethod}", sbSelectMethodTemplate.toString());
    }
}
