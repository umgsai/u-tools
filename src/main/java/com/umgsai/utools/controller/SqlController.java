/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.umgsai.utools.controller;

import com.umgsai.utools.data.DbForm;
import com.umgsai.utools.data.ServerType;
import com.umgsai.utools.dbTools.AbstractDbManager;
import com.umgsai.utools.dbTools.MySqlManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author shangyidong
 * @version $Id: SqlController.java, v 0.1 2018年11月24日 下午7:18 shangyidong Exp $
 */
@Slf4j
@Controller
@RequestMapping("/db")
public class SqlController {

    @Autowired
    private MySqlManager mySqlManager;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "db";
    }

    @ResponseBody
    @RequestMapping("/getTableColumnList")
    public Object getTableColumnList(@RequestBody DbForm dbForm) {
        AbstractDbManager dbManager = getDbManager(ServerType.valueOf(dbForm.getServerType()));

        return dbManager.getTableColumnList(dbForm.getHost(), dbForm.getPort(), dbForm.getUsername(), dbForm.getPassword(),
                dbForm.getDbName(), dbForm.getTableName());
    }

    @ResponseBody
    @RequestMapping("/getTableNameList")
    public Object getTableNameList(@RequestBody DbForm dbForm) {
        AbstractDbManager dbManager = getDbManager(ServerType.valueOf(dbForm.getServerType()));
        return dbManager.getTableNameList(dbForm.getHost(), dbForm.getPort(), dbForm.getUsername(), dbForm.getPassword(),
                dbForm.getDbName());
    }

    @ResponseBody
    @RequestMapping("/getDbNameList")
    public Object getDbNameList(@RequestBody DbForm dbForm) {
        AbstractDbManager dbManager = getDbManager(ServerType.valueOf(dbForm.getServerType()));
        return dbManager.getDbNameList(dbForm.getHost(), dbForm.getPort(), dbForm.getUsername(), dbForm.getPassword());
    }

    @ResponseBody
    @RequestMapping("/exeSql")
    public Object exeSql(@RequestBody DbForm dbForm) {
        AbstractDbManager dbManager = getDbManager(ServerType.valueOf(dbForm.getServerType()));

        return dbManager.executeSql(dbForm.getHost(), dbForm.getPort(), dbForm.getUsername(), dbForm.getPassword(), dbForm.getDbName(),
                dbForm.getSql());
    }

    private AbstractDbManager getDbManager(ServerType serverType) {
        switch (serverType) {
            case MySQL:
                return mySqlManager;
            default:
                return null;
        }
    }
}