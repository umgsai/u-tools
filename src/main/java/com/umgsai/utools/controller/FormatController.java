/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.umgsai.utools.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author shangyidong
 * @version $Id: FormatController.java, v 0.1 2018年12月01日 上午11:59 shangyidong Exp $
 */
@Slf4j
@Controller
@RequestMapping("/format")
public class FormatController {

    @RequestMapping("/json.html")
    public String index(Model model, String jsonContent) {
        if (log.isInfoEnabled()) {
            log.info(jsonContent);
        }
        return "jsonFormat";
    }
}