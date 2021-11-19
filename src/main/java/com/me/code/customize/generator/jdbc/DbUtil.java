package com.me.code.customize.generator.jdbc;

import cn.hutool.db.DbRuntimeException;
import cn.hutool.db.ds.simple.SimpleDataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
public class DbUtil {

    private DbUtil() {
        throw new IllegalStateException("Utility class");
    }

    private static Connection connection;

    public static void initConnection(String url, String user, String password) throws SQLException {
        try (SimpleDataSource db = new SimpleDataSource(url, user, password)){
            connection = db.getConnection();
        }
    }

    public static ResultSet executeQuery(String sql) throws SQLException {
        if (StringUtils.isEmpty(sql)) {
            return null;
        }
        if (connection == null || connection.isClosed()) {
            log.error("请先初始化数据库连接");
            throw new DbRuntimeException("未建立连接");
        }
        return connection.createStatement().executeQuery(sql);
    }
}
