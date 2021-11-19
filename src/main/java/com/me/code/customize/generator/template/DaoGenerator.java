package com.me.code.customize.generator.template;

import cn.hutool.core.text.CharSequenceUtil;
import com.me.code.customize.generator.data.ColumnInfo;
import com.me.code.customize.generator.utils.GenerateConfig;
import org.apache.commons.lang3.StringUtils;

public class DaoGenerator extends AbsGenerator {
    private static final String CLASS_TEMPLATE =
            "package  &{packagePath};\n" + "import &{importClass};\n" + "import org.apache.ibatis.annotations.*;\n"
                    + "import java.util.List;\n" + "import org.springframework.stereotype.Repository;\n"
                    + "@Repository\n" + "public interface &{className}Dao {\n" + "    String  COLUMN=\"&{column}\";\n"
                    + "    String  TABLE_NAME=\"`&{tableName}`\";\n" + "\n" + "    @Update(\"<script>\" +\n"
                    + "            \"INSERT INTO \"+TABLE_NAME+\" ( \"+ \n &{insertColoum} \n \") VALUES     ( \" + \n "
                    + " &{insertValue}  \" )   ON DUPLICATE KEY UPDATE \" +\n" + "            &{updateColumnDb}\n"
                    + "            \"  update_time=now()     \"+\n" + "            \"</script>\")\n"
                    + "    @SelectKey(statement=\" SELECT LAST_INSERT_ID() \", keyProperty=\"id\", before=false, resultType=Long.class)\n"
                    + "    Long insertOrUpdate(&{className} &{instance});\n" + "\n"
                    + "    @Select(\"  select id, \"+COLUMN+\"  from  \"+TABLE_NAME+\"  order by  update_time desc  limit 500 \")\n"
                    + "    List<&{className}> selectAll();\n" + "\n"
                    + "    @Delete(\"  delete  from  \"+TABLE_NAME+\"  where id=#{id} \")\n"
                    + "    Integer deleteById(@Param(\"id\") Long id);\n" + "\n" + "            &{selectMethord}\n"
                    + "}\n";
    //    @Insert(" insert into user ("+COLUMN+ ") " +
    //            "    values (#{ldapId},#{name},#{email},#{departmentBizId},#{phone},#{role}   )")
    //    //@SelectKey(statement="call identity()", keyProperty="id", before=false, resultType=int.class)
    //    @SelectKey(statement=" SELECT LAST_INSERT_ID() ", keyProperty="id", before=false, resultType=int.class)
    //    int insert(User user);
    //
    //    @Update(" <script> \n" +
    //            "update  user "+
    //            "<trim prefix=\"SET\" suffixOverrides=\",\">\n" +
    //            "<if test=\"ldapId != null\">ldap_id=#{ldapId},</if>\n" +
    //            "<if test=\"name != null\">name=#{name},</if>\n" +
    //            "<if test=\"email != null\">email=#{email},</if>\n" +
    //            "<if test=\"departmentBizId != null\">department_biz_id=#{departmentBizId},</if>\n" +
    //            "<if test=\"phone != null\">phone=#{phone},</if>\n" +
    //            "<if test=\"role != null\">role=#{role},</if>\n" +
    //            "<if test=\"status != null\">status=#{status},</if>\n" +
    //            "</trim>    " +
    //            "            where id=#{id }  \n" +
    //
    //            " </script>")
    //    Integer update( User user);
    private static final String UPDATE_COLUMN_DB_TEMPLATE = "\"<if test=\\\"&{propertyTuofeng} != null\\\">`&{propertyGang}`=#{&{propertyTuofeng}},</if>\"+\n";
    private static final String INSERT_COLUMN_TEMPLATE = "\"<if  test=\\\"&{propertyTuofeng} != null\\\">`&{propertyGang}`,</if>\"+\n";
    private static final String INSERT_VALUE_TEMPLATE = "\"<if  test=\\\"&{propertyTuofeng} != null\\\">#{&{propertyTuofeng}},</if>\"+\n";
    private static final String INSERT_COLUMN_NO_DEFAULT_TEMPLATE = "\" `&{propertyGang}`, \"+\n";
    private static final String INSERT_VALUE_NO_DEFAULT_TEMPLATE = "\" #{&{propertyTuofeng}}, \"+\n";
    private static final String SELECT_METHOD_TEMPLATE =
            "@Select(\"  select `id`, \"+COLUMN+\"  from  \"+TABLE_NAME+\"   where  `&{propertyGang}`=#{&{propertyTuofeng}} limit 500 \")\n"
                    + "        &{className} selectBy&{methoedName}(@Param(\"&{propertyTuofeng}\") String &{propertyTuofeng} );";
    public static final String PROPERTY_GANG = "&{propertyGang}";
    public static final String PROPERTY_TUOFENG = "&{propertyTuofeng}";

    public DaoGenerator(GenerateConfig config) {
        super(config);
    }

    @Override
    public String getClassNameSuffix() {
        return "Dao.java";
    }

    @Override
    public String getClassTemplate() {
        String generateClass = null;
        try {
            String simpleName = getClassName();
            StringBuilder sbColumn = new StringBuilder();
            StringBuilder sbColumnDb = new StringBuilder();
            StringBuilder sbUpdateColumnDb = new StringBuilder();
            StringBuilder sbSelectMethod = new StringBuilder();

            //为了让default值生效，需要insertSelective
            StringBuilder sbInsertColumn = new StringBuilder();
            StringBuilder sbInsertValue = new StringBuilder();
            StringBuilder sbInsertColumnNoDefault = new StringBuilder();
            StringBuilder sbInsertValueNoDefault = new StringBuilder();
            int i = 0;

            for (ColumnInfo columnInfo : getTableInfo().getColumnInfos()) {
                String fieldName = columnInfo.getProperty();

                if (!"id".equals(fieldName) && !"updateDate".equals(fieldName) && !"updateTime".equals(fieldName)) {
                    sbColumnDb.append("#{").append(fieldName).append("},");
                    sbColumn.append("`").append(CharSequenceUtil.toUnderlineCase(fieldName)).append("`,");
                    sbUpdateColumnDb.append(UPDATE_COLUMN_DB_TEMPLATE.replace(PROPERTY_TUOFENG, fieldName)
                            .replace(PROPERTY_GANG, CharSequenceUtil.toUnderlineCase(fieldName)));
                    sbSelectMethod.append(SELECT_METHOD_TEMPLATE.replace(PROPERTY_TUOFENG, fieldName)
                            .replace(PROPERTY_GANG, CharSequenceUtil.toUnderlineCase(fieldName))
                            .replace("&{methoedName}", CharSequenceUtil.toUnderlineCase(fieldName))
                            .replace("&{className}", simpleName));

                    String defaultValue = columnInfo.getDefaultValue();
                    if (StringUtils.isNotBlank(defaultValue)) {
                        sbInsertColumn.append(INSERT_COLUMN_TEMPLATE.replace(PROPERTY_TUOFENG, fieldName)
                                .replace(PROPERTY_GANG, CharSequenceUtil.toUnderlineCase(fieldName)));
                        sbInsertValue.append(INSERT_VALUE_TEMPLATE.replace(PROPERTY_TUOFENG, fieldName)
                                .replace(PROPERTY_GANG, CharSequenceUtil.toUnderlineCase(fieldName)));
                    } else {

                        String insertColumnNoDefaultElement = INSERT_COLUMN_NO_DEFAULT_TEMPLATE.replace(
                                        PROPERTY_TUOFENG, fieldName)
                                .replace(PROPERTY_GANG, CharSequenceUtil.toUnderlineCase(fieldName));
                        String insertValueNoDefaultElement = INSERT_VALUE_NO_DEFAULT_TEMPLATE.replace(PROPERTY_TUOFENG,
                                fieldName).replace(PROPERTY_GANG, CharSequenceUtil.toUnderlineCase(fieldName));
                        //去掉最后的逗号
                        if (i == getTableInfo().getColumnInfos().size() - 2) {
                            insertColumnNoDefaultElement = insertColumnNoDefaultElement.replace(",", "");
                            insertValueNoDefaultElement = insertValueNoDefaultElement.replace(",", "");
                        }
                        sbInsertColumnNoDefault.append(insertColumnNoDefaultElement);
                        sbInsertValueNoDefault.append(insertValueNoDefaultElement);
                    }
                    i++;
                }
            }


            String sbInsertColoumStr = sbInsertColumn.toString();
            String sbInsertValueStr = sbInsertValue.toString();
            String insertColoumFinnal = sbInsertColoumStr + sbInsertColumnNoDefault;
            String insertValueFinnal = sbInsertValueStr + sbInsertValueNoDefault;
            int a = insertColoumFinnal.lastIndexOf(",");//去掉最后一个逗号
            insertColoumFinnal = insertColoumFinnal.substring(0, a) + insertColoumFinnal.substring(a + 1);
            int b = insertValueFinnal.lastIndexOf(",");//去掉最后一个逗号
            insertValueFinnal = insertValueFinnal.substring(0, b) + insertValueFinnal.substring(b + 1);

            generateClass = CLASS_TEMPLATE.replace("&{packagePath}", config.getDaoPackagePath())
                    .replace("&{column}", sbColumn.substring(0, sbColumn.length() - 1))
                    .replace("&{tableName}", getTableInfo().getTableName().toLowerCase())
                    .replace("&{updateColumnDb}", sbUpdateColumnDb.toString())
                    .replace("&{selectMethord}", sbSelectMethod.toString())
                    .replace("&{columnDb}", sbColumnDb.substring(0, sbColumnDb.length() - 1))
                    //没有默认值的放后面，这样就是必须会出现，有返回值的不一定会出现，做insertselective，防止默认值被设置的空值覆盖
                    .replace("&{insertColoum}", insertColoumFinnal).replace("&{insertValue}", insertValueFinnal);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return generateClass;
    }
}
