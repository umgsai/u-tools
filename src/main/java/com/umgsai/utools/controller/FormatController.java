/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.umgsai.utools.controller;

import com.google.common.base.CaseFormat;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @ResponseBody
    @RequestMapping(value = "/toCamelFormat", method = RequestMethod.POST)
    public Object toCamelFormat(@RequestBody List<String> keyList) {
        Map<String, String> keyMap = Maps.newHashMap();
        if (keyList != null) {
            for(String key : keyList){
               String formatKey = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, key);
                keyMap.put(key, formatKey);
            }
        }
        return keyMap;
    }
}