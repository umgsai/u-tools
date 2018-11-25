/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.umgsai.utools.dbTools;

import com.mysql.jdbc.PreparedStatement;
import com.umgsai.utools.data.DataResult;
import com.umgsai.utools.data.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;

/**
 *
 * @author shangyidong
 * @version $Id: DqlSqlExecutor.java, v 0.1 2018年11月25日 上午11:19 shangyidong Exp $
 */
@Slf4j
@Component
public class DqlSqlExecutor implements SqlExecutor {

    @Override
    public DataResult execute(PreparedStatement preparedStatement) {
        try {
            ResultSet resultSet = preparedStatement.executeQuery();
            return DataResult.successResult(resultSet);
        } catch (Exception e) {
            String errorMsg = String.format("执行SQL失败！%s", e.getMessage());
            log.error(errorMsg, e);
            return DataResult.failResult(ErrorCode.DQL_ERROR.name(), errorMsg);
        }
    }
}