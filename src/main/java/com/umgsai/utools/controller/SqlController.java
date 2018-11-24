/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.umgsai.utools.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author shangyidong
 * @version $Id: SqlController.java, v 0.1 2018年11月24日 下午7:18 shangyidong Exp $
 */
//@RequestMapping("/sql")
@Controller
public class SqlController {

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    //@RequestMapping("/index")
    public String index() {

        return "sql";

    }
}