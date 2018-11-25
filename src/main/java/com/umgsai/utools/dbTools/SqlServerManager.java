/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.umgsai.utools.dbTools;

import com.mysql.jdbc.Connection;
import com.umgsai.utools.data.DataResult;

/**
 *
 * @author shangyidong
 * @version $Id: SqlServerManager.java, v 0.1 2018年11月25日 上午11:11 shangyidong Exp $
 */
public class SqlServerManager extends AbstractDbManager {

    @Override
    public DataResult<Connection> getConnection(String host, int port, String username, String password, String dbName) {
        return null;
    }
}