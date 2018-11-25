/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.umgsai.utools.data;

import lombok.Data;

import java.util.List;

/**
 *
 * @author shangyidong
 * @version $Id: DbForm.java, v 0.1 2018年11月25日 上午11:28 shangyidong Exp $
 */
@Data
public class DbForm {

    private String       serverType;
    private String       host;
    private int          port;
    private String       username;
    private String       password;
    private String       dbName;
    private String       tableName;
    private List<String> dbNameList;
    private String       sql;

}