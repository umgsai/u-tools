/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.umgsai.utools.dbTools;

import com.umgsai.utools.data.DataResult;

import java.sql.PreparedStatement;

/**
 *
 * @author shangyidong
 * @version $Id: SqlExecutor.java, v 0.1 2018年11月25日 上午11:16 shangyidong Exp $
 */
public interface SqlExecutor {

    DataResult execute(PreparedStatement preparedStatement);

}