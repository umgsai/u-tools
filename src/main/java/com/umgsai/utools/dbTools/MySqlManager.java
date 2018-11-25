/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.umgsai.utools.dbTools;

import com.mysql.jdbc.Connection;
import com.umgsai.utools.data.DataResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.sql.DriverManager;

/**
 *
 * @author shangyidong
 * @version $Id: MySqlManager.java, v 0.1 2018年11月25日 上午11:07 shangyidong Exp $
 */
@Slf4j
@Component
public class MySqlManager extends AbstractDbManager {

    private static final String driverClass = "com.mysql.jdbc.Driver";

    @Override
    public DataResult<Connection> getConnection(String host, int port, String username, String password, String dbName) {
        String driver = driverClass;
        String url = String.format("jdbc:mysql://%s:%d/%s?useUnicode=true&characterEncoding=UTF-8", host, port, dbName);
        Connection connection = null;
        try {
            Class.forName(driver);
            connection = (Connection) DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            String errorMsg = String.format("建立数据库连接失败！%s", e.getMessage());
            log.error(errorMsg, e);
            return DataResult.failResult("", errorMsg);
        }
        return DataResult.successResult(connection);
    }
}