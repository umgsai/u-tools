/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.umgsai.utools.config;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

/**
 *
 * @author shangyidong
 * @version $Id: ConfigData.java, v 0.1 2018年11月16日 下午10:18 shangyidong Exp $
 */
@Slf4j
public class ConfigData {

    public static String dbName = "";

    public static final String tablePreFix = "";

    public static Map<String, String> dataTypeMap = Maps.newHashMap();

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

        System.out.println(dataTypeMap);

        Properties properties = new Properties();
        // 使用ClassLoader加载properties配置文件生成对应的输入流
        InputStream in = ConfigData.class.getClassLoader().getResourceAsStream("application.properties");
        // 使用properties对象加载输入流
        try {
            properties.load(in);
            String port = properties.getProperty("server.port");
            log.info("port=" + port);
        } catch (Exception e) {
            log.error("Exception", e);
        }
        //获取key对应的value值
    }
}