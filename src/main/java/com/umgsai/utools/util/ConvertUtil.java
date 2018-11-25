/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.umgsai.utools.util;

import com.google.common.base.CaseFormat;
import com.umgsai.utools.config.ConfigData;
import com.umgsai.utools.data.JavaClass;
import com.umgsai.utools.data.TableColumn;
import org.springframework.util.StringUtils;

/**
 *
 * @author shangyidong
 * @version $Id: Converter.java, v 0.1 2018年11月16日 下午10:16 shangyidong Exp $
 */
public class ConvertUtil {

    public static JavaClass convertToJavaClass(TableColumn tableColumn) {
        String javaClassName = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, tableColumn.getColumnName());
        if (!StringUtils.isEmpty(ConfigData.tablePreFix)) {
             javaClassName = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, tableColumn.getColumnName().replace(
                    ConfigData.tablePreFix + "_", ""));
        }
         //String javaClassNameWithFirstLetterLowerCase = "";
         String javaDataType = ConfigData.dataTypeMap.get(tableColumn.getColumnDataType());
         String comment = tableColumn.getColumnComment();
         return new JavaClass(javaClassName, javaDataType, comment);
    }
}