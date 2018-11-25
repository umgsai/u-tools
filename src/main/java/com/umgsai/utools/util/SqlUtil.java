/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.umgsai.utools.util;

import com.umgsai.utools.data.SqlType;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author shangyidong
 * @version $Id: SqlUtil.java, v 0.1 2018年11月18日 上午11:13 shangyidong Exp $
 */
public class SqlUtil {

    public static SqlType getSqlType(String sql) {
        String sqlWithLowerCase = StringUtils.trim(StringUtils.lowerCase(sql));

        if (StringUtils.startsWith(sqlWithLowerCase, "select ")) {
            return SqlType.DQL;
        }
        if (StringUtils.startsWith(sqlWithLowerCase, "show ")) {
            return SqlType.DQL;
        }
        if (StringUtils.startsWith(sqlWithLowerCase, "explain ")) {
            return SqlType.DQL;
        }
        if (StringUtils.startsWith(sqlWithLowerCase, "update ")) {
            return SqlType.DML;
        }
        if (StringUtils.startsWith(sqlWithLowerCase, "insert ")) {
            return SqlType.DML;
        }
        if (StringUtils.startsWith(sqlWithLowerCase, "delete ")) {
            return SqlType.DML;
        }
        if (StringUtils.startsWith(sqlWithLowerCase, "set ")) {
            return SqlType.DDL;
        }
        if (StringUtils.startsWith(sqlWithLowerCase, "desc ")) {
            return SqlType.DQL;
        }
        return SqlType.UNKNOMN;
    }
}