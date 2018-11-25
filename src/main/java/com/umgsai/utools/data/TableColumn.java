/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.umgsai.utools.data;

import lombok.Data;

/**
 *
 * @author shangyidong
 * @version $Id: TableColumn.java, v 0.1 2018年11月25日 上午11:12 shangyidong Exp $
 */
@Data
public class TableColumn {

    private String columnName;
    private String columnType;
    private String columnDataType;
    private String columnComment;

    public TableColumn() {
    }

    public TableColumn(String columnName, String columnType, String columnDataType, String columnComment) {
        this.columnName = columnName;
        this.columnType = columnType;
        this.columnDataType = columnDataType;
        this.columnComment = columnComment;
    }

}