package com.me.code.customize.generator.jdbc;

import cn.hutool.db.ds.simple.SimpleDataSource;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

class DbUtilTest {

    @Test
    void testDb() throws SQLException {
        SimpleDataSource yxt = new SimpleDataSource(
                "jdbc:mysql://10.120.0.2:6033/file?useUnicode=true&characterEncoding=utf-8&allowMultiQueries=TRUE&useSSL=false",
                "yxt", "afg)gppOs22k");
        Connection connection = yxt.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select TABLE_NAME, TABLE_COMMENT from information_schema.`TABLES` t  where  t.TABLE_SCHEMA = 'file'  and t.TABLE_NAME in ('file_localservice_conf')");
        String string = null;
        while (resultSet.next()) {
            string = resultSet.getString(1);
        }

        assertNotNull(string);
    }
}