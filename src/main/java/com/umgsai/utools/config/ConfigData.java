/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.umgsai.utools.config;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 *
 * @author shangyidong
 * @version $Id: ConfigData.java, v 0.1 2018年11月16日 下午10:18 shangyidong Exp $
 */
@Slf4j
public class ConfigData {

    public static String dbName = "";

    public static final String tablePreFix = "";

    public static final Map<String, String> dataTypeMap = Maps.newHashMap();
    public static final Map<String, Object> configMap = Maps.newHashMap();

    static {
        dataTypeMap.put("bigint", "long");
        dataTypeMap.put("int", "int");
        dataTypeMap.put("smallint", "int");
        dataTypeMap.put("varchar", "String");
        dataTypeMap.put("text", "String");
        dataTypeMap.put("mediumtext", "String");
        dataTypeMap.put("longtext", "String");
        dataTypeMap.put("char", "String");
        dataTypeMap.put("longblob", "String");
        dataTypeMap.put("datetime", "Date");
        dataTypeMap.put("date", "Date");
        dataTypeMap.put("timestamp", "Date");
        dataTypeMap.put("tinyint", "boolean");
        dataTypeMap.put("double", "double");
        dataTypeMap.put("decimal", "double");
        dataTypeMap.put("float", "float");

        Properties properties = new Properties();
        // 使用ClassLoader加载properties配置文件生成对应的输入流
        InputStream in = ConfigData.class.getClassLoader().getResourceAsStream("application.properties");
        // 使用properties对象加载输入流
        try {
            properties.load(in);
            Set<Object> keys = properties.keySet();
            for (Object key : keys) {
                configMap.put(key.toString(), properties.get(key));
            }
            if (log.isInfoEnabled()) {
                log.info(String.format("configMap=%s", JSON.toJSONString(configMap)));
            }
        } catch (Exception e) {
            log.error("Exception", e);
        }
    }
}