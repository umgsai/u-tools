/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.umgsai.utools.data;


/**
 *
 * @author shangyidong
 * @version $Id: ErrorCode.java, v 0.1 2018年11月18日 上午11:32 shangyidong Exp $
 */
public enum ErrorCode {

    /**
     * 非法参数
     */
    ILLEGAL_PARAMETER,

    /**
     * 未查询到结果
     */
    NOT_FOUND,

    /**
     * 未知的SQL类型
     */
    UNKNOWN_SQL_TYPE,

    /**
     * 执行DQL失败
     */
    DQL_ERROR,

    /**
     * 执行DML失败
     */
    DML_ERROR;
}