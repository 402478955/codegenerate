package com.me.code.customize.generator.template;

import com.me.code.customize.generator.data.ColumnInfo;
import com.me.code.customize.generator.utils.GeneratConfig;
import com.me.code.customize.generator.utils.GeneratUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

public class DaoGenerator extends AbsGenerator {
    private static String classTemplate = "package  &{packagePath};\n" +
            "import &{importClass};\n" +
            "import org.apache.ibatis.annotations.*;\n" +
            "import java.util.List;\n" +
            "import org.springframework.stereotype.Repository;\n" +
            "@Repository\n" +
            "public interface &{className}Dao {\n" +
            "    String  COLUMN=\"&{column}\";\n" +
            "    String  TABLE_NAME=\"`&{tableName}`\";\n" +
            "\n" +
            "    @Update(\"<script>\" +\n" +
            "            \"INSERT INTO \"+TABLE_NAME+\" ( \"+ \n &{insertColoum} \n \") VALUES     ( \" + \n " +
            " &{insertValue}  \" )   ON DUPLICATE KEY UPDATE \" +\n" +
            "            &{updateColumnDb}\n" +
            "            \"  update_time=now()     \"+\n" +
            "            \"</script>\")\n" +
            "    @SelectKey(statement=\" SELECT LAST_INSERT_ID() \", keyProperty=\"id\", before=false, resultType=Long.class)\n" +
            "    Long insertOrUpdate(&{className} &{instance});\n" +
            "\n" +
            "    @Select(\"  select id, \"+COLUMN+\"  from  \"+TABLE_NAME+\"  order by  update_time desc  limit 500 \")\n" +
            "    List<&{className}> selectAll();\n" +
            "\n" +
            "    @Delete(\"  delete  from  \"+TABLE_NAME+\"  where id=#{id} \")\n" +
            "    Integer deleteById(@Param(\"id\") Long id);\n" +
            "\n" +
            "            &{selectMethord}\n" +
            "}\n";
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
    private static String updateColumnDbTemplate = "\"<if test=\\\"&{propertyTuofeng} != null\\\">`&{propertyGang}`=#{&{propertyTuofeng}},</if>\"+\n";

    private static String  insertColumnTemplate= "\"<if  test=\\\"&{propertyTuofeng} != null\\\">`&{propertyGang}`,</if>\"+\n";
    private static String insertValueTemplate = "\"<if  test=\\\"&{propertyTuofeng} != null\\\">#{&{propertyTuofeng}},</if>\"+\n";
    private static String  insertColumnNoDefaultTemplate= "\" `&{propertyGang}`, \"+\n";
    private static String insertValueNoDefaultTemplate = "\" #{&{propertyTuofeng}}, \"+\n";


    private static String selectMethordTemplate = "@Select(\"  select `id`, \"+COLUMN+\"  from  \"+TABLE_NAME+\"   where  `&{propertyGang}`=#{&{propertyTuofeng}} limit 500 \")\n" +
            "        &{className} selectBy&{methoedName}(@Param(\"&{propertyTuofeng}\") String &{propertyTuofeng} );";

    public DaoGenerator(GeneratConfig config) {
        super(config);
    }

    @Override
    public String getClassNameSuffix() throws Exception {
        return "Dao.java";
    }

    @Override
    public String getClassTemplate() throws Exception {
        String generateClass = null;
        try {
            String simpleName = getClassName();
            StringBuffer sbColumn = new StringBuffer();
            StringBuffer sbColumnDb = new StringBuffer();
            StringBuffer sbUpdateColumnDb = new StringBuffer();
            StringBuffer sbSelectMethord = new StringBuffer();

//为了让default值生效，需要insertSelective
            StringBuffer sbInsertColoum = new StringBuffer();
            StringBuffer sbInsertValue = new StringBuffer();
            StringBuffer sbInsertColoumNoDefault = new StringBuffer();
            StringBuffer sbInsertValueNoDefault = new StringBuffer();
            int i = 0;

            for (ColumnInfo columnInfo : getTableInfo().getColumnInfos()) {
                String fieldName = columnInfo.getProperty();

                if (!"id".equals(fieldName) && !"updateDate".equals(fieldName) && !"updateTime".equals(fieldName)) {
                    sbColumnDb.append("#{" + fieldName + "},");
                    sbColumn.append("`" + GeneratUtil.fromTuofengToGang(fieldName) + "`,");
                    sbUpdateColumnDb.append(updateColumnDbTemplate.replace("&{propertyTuofeng}", fieldName)
                            .replace("&{propertyGang}", GeneratUtil.fromTuofengToGang(fieldName)));
                    sbSelectMethord.append(selectMethordTemplate.replace("&{propertyTuofeng}", fieldName)
                            .replace("&{propertyGang}", GeneratUtil.fromTuofengToGang(fieldName))
                            .replace("&{methoedName}", GeneratUtil.firstCharUpperCase(fieldName))
                            .replace("&{className}", simpleName));

                    String defaultValue = columnInfo.getDefaultValue();
                    if (StringUtils.isNotBlank(defaultValue)) {
                        sbInsertColoum.append(insertColumnTemplate.replace("&{propertyTuofeng}", fieldName)
                                .replace("&{propertyGang}", GeneratUtil.fromTuofengToGang(fieldName)));
                        sbInsertValue.append(insertValueTemplate.replace("&{propertyTuofeng}", fieldName)
                                .replace("&{propertyGang}", GeneratUtil.fromTuofengToGang(fieldName)));
                    } else {

                        String insertColumnNoDefaultElement = insertColumnNoDefaultTemplate.replace("&{propertyTuofeng}", fieldName)
                                .replace("&{propertyGang}", GeneratUtil.fromTuofengToGang(fieldName));
                        String insertValueNoDefaultElement = insertValueNoDefaultTemplate.replace("&{propertyTuofeng}", fieldName)
                                .replace("&{propertyGang}", GeneratUtil.fromTuofengToGang(fieldName));
                        //去掉最后的逗号
                        if (i == getTableInfo().getColumnInfos().size() - 2) {
                            insertColumnNoDefaultElement = insertColumnNoDefaultElement.replace(",", "");
                            insertValueNoDefaultElement = insertValueNoDefaultElement.replace(",", "");
                        }
                        sbInsertColoumNoDefault.append(insertColumnNoDefaultElement);
                        sbInsertValueNoDefault.append(insertValueNoDefaultElement);
                    }
                    i++;
                }
            }


            String sbInsertColoumStr = sbInsertColoum.toString();
            String sbInsertValueStr = sbInsertValue.toString();
            String insertColoumFinnal = sbInsertColoumStr + sbInsertColoumNoDefault;
            String insertValueFinnal = sbInsertValueStr + sbInsertValueNoDefault;
            Integer  a=   insertColoumFinnal.lastIndexOf(",");//去掉最后一个逗号
            insertColoumFinnal= insertColoumFinnal.substring(0,a)+insertColoumFinnal.substring(a+1,insertColoumFinnal.length());
            Integer  b=   insertValueFinnal.lastIndexOf(",");//去掉最后一个逗号
            insertValueFinnal= insertValueFinnal.substring(0,b)+insertValueFinnal.substring(b+1,insertValueFinnal.length());

            generateClass = classTemplate.replace("&{packagePath}", config.getDaoPackagePath())
                    .replace("&{column}", sbColumn.toString().substring(0, sbColumn.length() - 1))
                    .replace("&{tableName}", getTableInfo().getTableName().toLowerCase())
                    .replace("&{updateColumnDb}", sbUpdateColumnDb.toString())
                    .replace("&{selectMethord}", sbSelectMethord.toString())
                    .replace("&{columnDb}", sbColumnDb.toString().substring(0, sbColumnDb.length() - 1))
                    //没有默认值的放后面，这样就是必须会出现，有返回值的不一定会出现，做insertselective，防止默认值被设置的空值覆盖
                    .replace("&{insertColoum}", insertColoumFinnal)
                    .replace("&{insertValue}", insertValueFinnal);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return generateClass;
    }
}
