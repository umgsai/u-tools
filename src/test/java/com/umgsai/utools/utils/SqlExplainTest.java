package com.umgsai.utools.utils;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.expr.SQLIntegerExpr;
import com.alibaba.druid.sql.ast.statement.SQLSelect;
import com.alibaba.druid.sql.ast.statement.SQLSelectQuery;
import com.alibaba.druid.sql.ast.statement.SQLSelectStatement;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlSelectQueryBlock;
import com.alibaba.druid.sql.dialect.mysql.parser.MySqlStatementParser;
import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlOutputVisitor;

import java.util.List;

public class SqlExplainTest {

    public static void main(String[] args){
        String sqls = "select ID from BCP_Prize; select name from BCP_Prize limit 10";
        MySqlStatementParser parser = new MySqlStatementParser(sqls);
        List<SQLStatement> stmtList = parser.parseStatementList();

        // 将AST通过visitor输出
        StringBuilder out = new StringBuilder();
        MySqlOutputVisitor visitor = new MySqlOutputVisitor(out);

        for (SQLStatement stmt : stmtList) {
            stmt.accept(visitor);
            System.out.println(out + ";");
            out.setLength(0);

            SQLSelect select = ((SQLSelectStatement) stmt).getSelect();
            SQLSelectQuery query = select.getQuery();
            MySqlSelectQueryBlock mySqlSelectQueryBlock = (MySqlSelectQueryBlock)query;
            if (mySqlSelectQueryBlock.getLimit() == null){
                MySqlSelectQueryBlock.Limit limit = new MySqlSelectQueryBlock.Limit();
                SQLIntegerExpr sqlIntegerExpr = new SQLIntegerExpr();
                sqlIntegerExpr.setNumber(100);

                limit.setRowCount(sqlIntegerExpr);
                mySqlSelectQueryBlock.setLimit(limit);
            }

        }


    }

}
