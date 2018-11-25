/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.umgsai.utools.data;

/**
 *
 * @author shangyidong
 * @version $Id: JavaClass.java, v 0.1 2018年11月16日 下午10:12 shangyidong Exp $
 */
public class JavaClass {

    private String javaClassName = "";

    private String javaClassNameWithFirstLetterLowerCase = "";

    private String javaDataType = "";

    private String comment = "";

    public JavaClass(String javaClassName, String javaDataType, String comment) {
        this.javaClassName = javaClassName;
        this.javaDataType = javaDataType;
        this.comment = comment;
    }

    /**
     * Getter method for property <tt>javaClassName</tt>.
     *
     * @return property value of javaClassName
     */
    public String getJavaClassName() {
        return javaClassName;
    }

    /**
     * Setter method for property <tt>javaClassName</tt>.
     *
     * @param javaClassName  value to be assigned to property javaClassName
     */
    public void setJavaClassName(String javaClassName) {
        this.javaClassName = javaClassName;
    }

    /**
     * Getter method for property <tt>javaClassNameWithFirstLetterLowerCase</tt>.
     *
     * @return property value of javaClassNameWithFirstLetterLowerCase
     */
    public String getJavaClassNameWithFirstLetterLowerCase() {
        return javaClassName.substring(0, 1).toLowerCase() + javaClassName.substring(1);
    }

    /**
     * Setter method for property <tt>javaClassNameWithFirstLetterLowerCase</tt>.
     *
     * @param javaClassNameWithFirstLetterLowerCase  value to be assigned to property javaClassNameWithFirstLetterLowerCase
     */
    private void setJavaClassNameWithFirstLetterLowerCase(String javaClassNameWithFirstLetterLowerCase) {
        this.javaClassNameWithFirstLetterLowerCase = javaClassNameWithFirstLetterLowerCase;
    }

    /**
     * Getter method for property <tt>javaDataType</tt>.
     *
     * @return property value of javaDataType
     */
    public String getJavaDataType() {
        return javaDataType;
    }

    /**
     * Setter method for property <tt>javaDataType</tt>.
     *
     * @param javaDataType  value to be assigned to property javaDataType
     */
    public void setJavaDataType(String javaDataType) {
        this.javaDataType = javaDataType;
    }

    /**
     * Getter method for property <tt>comment</tt>.
     *
     * @return property value of comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * Setter method for property <tt>comment</tt>.
     *
     * @param comment  value to be assigned to property comment
     */
    public void setComment(String comment) {
        this.comment = comment;
    }
}