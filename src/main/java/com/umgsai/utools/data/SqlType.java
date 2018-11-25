/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.umgsai.utools.data;

/**
 *
 * @author shangyidong
 * @version $Id: SqlType.java, v 0.1 2018年11月18日 上午11:11 shangyidong Exp $
 */
public enum SqlType {

    /**
     * 数据定义语言
     * 用于创建、修改、和删除数据库内的数据结构，如：1：创建和删除数据库(CREATE DATABASE || DROP  DATABASE)；
     * 2：创建、修改、重命名、删除表(CREATE  TABLE || ALTER TABLE|| RENAME
     * TABLE||DROP  TABLE)；3：创建和删除索引(CREATEINDEX  || DROP INDEX)
     */
    DDL,

    /**
     * 从数据库中的一个或多个表中查询数据(SELECT)
     */
    DQL,

    /**
     * 修改数据库中的数据，包括插入(INSERT)、更新(UPDATE)和删除(DELETE)
     */
    DML,

    /**
     * 用于对数据库的访问，如：1：给用户授予访问权限（GRANT）;2：取消用户访问权限（REMOKE）
     */
    DCL,

    /**
     * 未知类型
     */
    UNKNOMN
}