package com.me.code.customize.generator.template;

import com.me.code.customize.generator.data.ColumnInfo;
import com.me.code.customize.generator.data.TableInfo;
import com.me.code.customize.generator.utils.GeneratConfig;
import com.me.code.customize.generator.utils.GeneratUtil;

public class ControllerGenerator  extends AbsGenerator{
    private static String classTemplate = "package &{packagePath};\n" +
            "\n" +
            "import &{importClass};\n" +
            "import &{servicePath};\n" +
            "import com.yxt.monitor.error.MonitorException;\n" +
            "import com.yxt.monitor.error.ResultEnum; \n" +
            "import com.yxt.monitor.web.aop.Web;\n" +
            "import org.slf4j.LoggerFactory;\n" +
            "import org.springframework.beans.factory.annotation.Autowired;\n" +
            "import org.springframework.web.bind.annotation.RequestBody;\n" +
            "import org.springframework.web.bind.annotation.RequestMapping;\n" +
            "import org.springframework.web.bind.annotation.RequestParam;\n" +
            "import org.springframework.web.bind.annotation.RestController;\n" +
            " \n" +
            "@RestController\n" +
            "@RequestMapping(\"/&{instance}\")\n" +
            "public class &{className}Controller {\n" +
            "    private final static org.slf4j.Logger logger = LoggerFactory.getLogger(&{className}Controller.class);\n" +
            "\n" +
            "    @Autowired\n" +
            "    private &{className}Service &{instance}Service;  \n" +
            "\n" +
            "    @RequestMapping(\"/add\")\n" +
            "    public Object insertOrUpdate(@RequestBody  &{className} &{instance}) {\n" +
            "        \n" +
            "        &{instance}Service.insertOrUpdate(&{instance});\n" +
            "        return  &{instance};\n" +
            "    } \n" +
            "\n" +

            "    @RequestMapping(\"/getAll\")\n" +
            "    public Object getAll&{className}() {\n" +
            "        return  &{instance}Service.selectAll();\n" +
            "    }\n" +
            "    \n" +
            "    @RequestMapping(\"/deleteById\")\n" +
            "    public Object deleteById(@RequestParam(\"id\") Integer id ) {\n" +
            "\n" +
            "        return  &{instance}Service.deleteById(id);\n" +
            "    }\n" +
            "\n" +
            "        &{selectMethod}\n" +
            "}";

    private static String selectMethodTemplate = "    @RequestMapping(\"/getBy&{UpperfieldName}\")\n" +
            "    public Object selectBy&{UpperfieldName}(@RequestParam(\"&{fieldName}\") String  &{fieldName} ) {\n" +
            "            return &{instance}Service.selectBy&{UpperfieldName}(&{fieldName});\n" +
            "    }\n"  ;

    public ControllerGenerator(GeneratConfig config) {
        super(config);
    }

    public  void doGenerat(GeneratConfig config, TableInfo tableInfo ) throws  Exception {


    }

    @Override
    public String getClassNameSuffix() throws Exception {
        return "Controller.java";
    }

    @Override
    public String getClassTemplate() throws Exception {
        String simpleName = getClassName();

        StringBuffer sbSelectMethodTemplate = new StringBuffer();
        for (ColumnInfo  columnInfo:  getTableInfo().getColumnInfos()) {
            String fieldName = columnInfo.getProperty();
            if (!"id".equals(fieldName) && !"updateDate".equals(fieldName)) {
                sbSelectMethodTemplate.append(selectMethodTemplate.replace("&{UpperfieldName}", GeneratUtil.firstCharUpperCase(fieldName))
                        .replace("&{fieldName}",  fieldName )
                        .replace("&{className}",simpleName)
                        .replace("&{instance}",GeneratUtil.firstCharLowCase(simpleName)));
            }
        }
        String generateClass = classTemplate.replace("&{packagePath}", config.getControllerPackagePath())
                .replace("&{selectMethod}", sbSelectMethodTemplate.toString());
        return generateClass;
    }
}
