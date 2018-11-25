/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.umgsai.utools.data;

import java.io.Serializable;

/**
 *
 * @author shangyidong
 * @version $Id: DataResult.java, v 0.1 2018年11月13日 下午4:38 shangyidong Exp $
 */
public class DataResult<T> implements Serializable {

    private boolean success = false;

    private String code;

    private String message;

    private T data;

    public static <T> DataResult successResult(T data) {
        DataResult dataResult = new DataResult();
        dataResult.setSuccess(true);
        dataResult.setData(data);
        return dataResult;
    }

    public static <T> DataResult successResult(T data, String message) {
        DataResult dataResult = new DataResult();
        dataResult.setSuccess(true);
        dataResult.setData(data);
        dataResult.setMessage(message);
        return dataResult;
    }

    public static DataResult successResult() {
        DataResult dataResult = new DataResult();
        dataResult.setSuccess(true);
        return dataResult;
    }

    public static DataResult failResult(String code, String message) {
        DataResult dataResult = new DataResult();
        dataResult.setCode(code);
        dataResult.setMessage(message);
        return dataResult;
    }

    /**
     * Getter method for property <tt>success</tt>.
     *
     * @return property value of success
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Setter method for property <tt>success</tt>.
     *
     * @param success  value to be assigned to property success
     */
    private void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * Getter method for property <tt>code</tt>.
     *
     * @return property value of code
     */
    public String getCode() {
        return code;
    }

    /**
     * Setter method for property <tt>code</tt>.
     *
     * @param code  value to be assigned to property code
     */
    private void setCode(String code) {
        this.code = code;
    }

    /**
     * Getter method for property <tt>message</tt>.
     *
     * @return property value of message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Setter method for property <tt>message</tt>.
     *
     * @param message  value to be assigned to property message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Getter method for property <tt>data</tt>.
     *
     * @return property value of data
     */
    public T getData() {
        return data;
    }

    /**
     * Setter method for property <tt>data</tt>.
     *
     * @param data  value to be assigned to property data
     */
    private void setData(T data) {
        this.data = data;
    }
}