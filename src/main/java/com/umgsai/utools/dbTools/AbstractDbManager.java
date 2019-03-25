/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.umgsai.utools.dbTools;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.umgsai.utools.data.DataResult;
import com.umgsai.utools.data.ErrorCode;
import com.umgsai.utools.data.SqlType;
import com.umgsai.utools.data.TableColumn;
import com.umgsai.utools.util.SqlUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author shangyidong
 * @version $Id: AbstractDbManager.java, v 0.1 2018年11月25日 上午11:06 shangyidong Exp $
 */
@Slf4j
public abstract class AbstractDbManager {

    @Autowired
    private SqlExecutor dqlSqlExecutor;
    @Autowired
    private SqlExecutor dmlSqlExecutor;

    public DataResult<List<TableColumn>> getTableColumnList(String host, int port, String username, String password,
                                                            String dbName, String tableName) {
        Map<String, Object> map = Maps.newHashMap();

        try {
            String sql = String.format("select * from information_schema.columns where table_schema = '%s' and table_name = '%s';", dbName,
                    tableName);
            DataResult dataResult = execute(host, port, username, password, dbName, sql);
            if (!dataResult.isSuccess()) {
                return dataResult;
            }
            map.putAll((Map<String, Object>) dataResult.getData());
            ResultSet resultSet = (ResultSet) map.get("resultSet");
            List<TableColumn> tableColumnList = new ArrayList<>();
            while (resultSet.next()) {
                String columnName = resultSet.getString("COLUMN_NAME");
                String columnDataType = resultSet.getString("DATA_TYPE");
                String columnComment = resultSet.getString("COLUMN_COMMENT");
                String columnType = resultSet.getString("COLUMN_TYPE");
                TableColumn tableColumn = new TableColumn(columnName, columnType, columnDataType, columnComment);
                tableColumnList.add(tableColumn);
                //                ConvertUtil.convertToJavaClass(tableColumn);
            }
            return DataResult.successResult(tableColumnList, dataResult.getMessage());
        } catch (SQLException e) {
            String errorMsg = String.format("查询表结构异常！dbName=%s，tableName=%s，%s", dbName, tableName, e.getMessage());
            log.error(errorMsg, e);
            return DataResult.failResult("", errorMsg);
        } finally {
            close((ResultSet) map.get("resultSet"), (PreparedStatement) map.get("preparedStatement"), (Connection) map.get("connection"));
        }
    }

    private static void close(ResultSet resultSet, PreparedStatement preparedStatement, Connection connection) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (Exception e) {
                log.error("关闭resultSet异常", e);
            }
        }
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (Exception e) {
                log.error("关闭preparedStatement异常", e);
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (Exception e) {
                log.error("关闭connection异常", e);
            }
        }
    }

    public DataResult<List<String>> getTableNameList(String host, int port, String username, String password, String dbName) {
        Map<String, Object> map = Maps.newHashMap();

        try {
            String sql = "show tables ; ";
            DataResult dataResult = execute(host, port, username, password, dbName, sql);
            if (!dataResult.isSuccess()) {
                return dataResult;
            }
            map.putAll((Map<String, Object>) dataResult.getData());
            List<String> tableNameList = new ArrayList<>();
            String columnName = String.format("Tables_in_%s", dbName);
            ResultSet resultSet = (ResultSet) map.get("resultSet");
            while (resultSet.next()) {
                String tableName = resultSet.getString(columnName);
                tableNameList.add(tableName);
            }
            return DataResult.successResult(tableNameList, dataResult.getMessage());
        } catch (SQLException e) {
            String errorMsg = String.format("查询表名异常！%s", e.getMessage());
            log.error(errorMsg, e);
            return DataResult.failResult("", errorMsg);
        } finally {
            close((ResultSet) map.get("resultSet"), (PreparedStatement) map.get("preparedStatement"), (Connection) map.get("connection"));
        }
    }

    public DataResult<List<String>> getDbNameList(String host, int port, String username, String password) {
        Map<String, Object> map = Maps.newHashMap();
        try {
            String sql = "show databases ; ";
            DataResult dataResult = execute(host, port, username, password, "", sql);
            if (!dataResult.isSuccess()) {
                return dataResult;
            }
            map.putAll((Map<String, Object>) dataResult.getData());
            List<String> dbNameList = new ArrayList<>();
            ResultSet resultSet = (ResultSet) map.get("resultSet");
            while (resultSet.next()) {
                String tableName = resultSet.getString("Database");
                dbNameList.add(tableName);
            }
            return DataResult.successResult(dbNameList, dataResult.getMessage());
        } catch (SQLException e) {
            String errorMsg = String.format("查询数据库列表异常！%s", e.getMessage());
            log.error(errorMsg, e);
            return DataResult.failResult("", errorMsg);
        } finally {
            close((ResultSet) map.get("resultSet"), (PreparedStatement) map.get("preparedStatement"), (Connection) map.get("connection"));
        }
    }

    public DataResult executeSql(String host, int port, String username, String password, String dbName,
                                 String sql) {
        Map<String, Object> map = Maps.newHashMap();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            SqlType sqlType = SqlUtil.getSqlType(sql);
            DataResult dataResult = execute(host, port, username, password, dbName, sql);
            if (!dataResult.isSuccess()) {
                return dataResult;
            }
            map = (Map<String, Object>) dataResult.getData();
            Map<String, Object> resultMap = Maps.newHashMap();
            resultMap.put("sqlType", sqlType);
            switch (sqlType) {
                case DQL:
                    ResultSet resultSet = (ResultSet) map.get("resultSet");
                    //获取列名
                    ResultSetMetaData metaData = resultSet.getMetaData();
                    int columnCount = metaData.getColumnCount();

                    List<String> columnNameList = Lists.newArrayList();
                    //int columnCount = 0;
                    for (int i = 0; i < columnCount; i++) {
                        columnNameList.add(metaData.getColumnName(i + 1));
                        //columnCount++;
                    }
                    resultSet.last();
                    int rowCount = resultSet.getRow();
                    Object data[][] = new Object[rowCount][columnCount];
                    resultMap.put("columnNameList", columnNameList);
                    resultMap.put("data", data);
                    if (rowCount == 0) {
                        return DataResult.successResult(resultMap);
                    }
                    int row = 0;
                    int column = 0;
                    resultSet.first();
                    String tableName = "";
                    do {
                        for (int i = 0; i < columnCount; i++) {
                            Object o = resultSet.getObject(i + 1);
                            if (o instanceof Date) {
                                data[row][i] = simpleDateFormat.format(o);
                            } else {
                                data[row][i] = o;
                            }
                            tableName = metaData.getTableName(i + 1);
                            if (log.isInfoEnabled()) {
                                log.info(tableName);
                            }
                        }
                        row++;
                    } while (resultSet.next());
                    resultMap.put("tableName", tableName);
                    return DataResult.successResult(resultMap, dataResult.getMessage());
                case DML:
                    int count = (int) map.get("count");
                    //resultMap.put("sqlType", sqlType);
                    resultMap.put("count", count);
                    resultMap.put("message", String.format("执行成功，影响行数：%d", count));
                    return DataResult.successResult(resultMap, dataResult.getMessage());
                case DCL:

                    //break;

                case DDL:
                    //break;
                case UNKNOMN:
                default:
                    return DataResult.failResult(ErrorCode.UNKNOWN_SQL_TYPE.name(), "不支持的SQL类型");
            }
        } catch (SQLException e) {
            String errorMsg = String.format("查询数据库列表异常！%s", e.getMessage());
            log.error(errorMsg, e);
            return DataResult.failResult("", errorMsg);
        } finally {
            close((ResultSet) map.get("resultSet"), (PreparedStatement) map.get("preparedStatement"), (Connection) map.get("connection"));
        }
    }

    private DataResult<Map<String, Object>> execute(String host, int port, String username, String password, String dbName,
                                                    String sql) {
        Map<String, Object> map = Maps.newHashMap();
        DataResult dataResult = getConnection(host, port, username, password, dbName);
        if (!dataResult.isSuccess()) {
            return dataResult;
        }
        Connection connection = (Connection) dataResult.getData();
        map.put("connection", connection);
        PreparedStatement preparedStatement;
        //ResultSet resultSet;

        try {
            preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
            String sqlWithLowerCase = StringUtils.lowerCase(sql);
            SqlType sqlType = SqlUtil.getSqlType(sqlWithLowerCase);
            map.put("preparedStatement", preparedStatement);
            SqlExecutor sqlExecutor = null;
            long start = System.currentTimeMillis();
            String message = "";
            switch (sqlType) {
                case DQL:
                    DataResult dqlResult = dqlSqlExecutor.execute(preparedStatement);
                    if (!dqlResult.isSuccess()) {
                        return dqlResult;
                    }
                    map.put("resultSet", dqlResult.getData());
                    break;
                case DML:
                    DataResult dmlResult = dmlSqlExecutor.execute(preparedStatement);
                    if (!dmlResult.isSuccess()) {
                        return dmlResult;
                    }
                    map.put("count", dmlResult.getData());
                    break;
                case DCL:

                    break;

                case DDL:
                    break;
                case UNKNOMN:
                default:
                    return DataResult.failResult(ErrorCode.UNKNOWN_SQL_TYPE.name(), "不支持的SQL类型");
            }
            message = String.format("执行成功，耗时：%sms", System.currentTimeMillis() - start);
            if (log.isInfoEnabled()) {
                log.info(String.format("执行SQL:%s  %s", sql, message));
            }
            return DataResult.successResult(map, message);
        } catch (SQLException e) {
            String errorMsg = String.format("执行SQL异常：%s。%s", sql, e.getMessage());
            log.error(errorMsg, e);
            return DataResult.failResult("", errorMsg);
        }

    }

    public abstract DataResult<Connection> getConnection(String host, int port, String username, String password, String dbName);

}