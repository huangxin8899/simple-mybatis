package com.huangxin.util;

import com.huangxin.mapping.BoundSql;

import java.lang.reflect.Field;
import java.util.List;


/**
 * parseSql
 *
 * @author huangxin 
 */
public class SqlParser {

    public static void parseSql(BoundSql boundSql) {
        String sql = boundSql.getSql();
        Class<?> paramType = boundSql.getParamType();
        List<Object> paramList  = boundSql.getParamList();
        StringBuilder targetSql = new StringBuilder();
        int startIndex = 0;
        int endIndex;
        try {
            while ((endIndex = sql.indexOf("#{", startIndex)) != -1) {
                targetSql.append(sql, startIndex, endIndex);
                targetSql.append("?");
                int closingIndex = sql.indexOf("}", endIndex);
                if (closingIndex != -1) {
                    String paramName = sql.substring(endIndex + 2, closingIndex);
                    if (paramType!=null) {
                        Field field = paramType.getDeclaredField(paramName);
                        field.setAccessible(true);
                        Object param = field.get(boundSql.getParameter());
                        paramList.add(param);
                    } else {
                        paramList.add(boundSql.getParameter());
                    }
                    startIndex = closingIndex + 1;
                } else {
                    // 没有找到匹配的"}"，抛出异常或进行错误处理
                    throw new IllegalArgumentException("Invalid sql: missing '}'");
                }
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        targetSql.append(sql.substring(startIndex));
        boundSql.setSql(targetSql.toString());
    }
}
