/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.umgsai.utools.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author shangyidong
 * @version $Id: IndexController.java, v 0.1 2018年11月28日 下午11:27 shangyidong Exp $
 */
@Controller
public class IndexController {


    @RequestMapping("/")
    public String index(Model model, HttpServletResponse response) {
        return "redirect:/db/index";
    }
}