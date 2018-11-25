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

/**
 *
 * @author shangyidong
 * @version $Id: DmlSqlExecutor.java, v 0.1 2018年11月18日 上午11:41 shangyidong Exp $
 */
@Slf4j
@Component
public class DmlSqlExecutor implements SqlExecutor {

    @Override
    public DataResult execute(PreparedStatement preparedStatement) {
        try {
            int count = preparedStatement.executeUpdate();
            return DataResult.successResult(count);
        } catch (Exception e) {
            String errorMsg = String.format("执行SQL失败！%s", e.getMessage());
            log.error(errorMsg, e);
            return DataResult.failResult(ErrorCode.DML_ERROR.name(), errorMsg);
        }
    }
}