package com.huangxin.executor;

import com.huangxin.mapping.BoundSql;
import com.huangxin.session.Configuration;
import com.huangxin.session.MappedStatement;

import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * SimpleExecutor
 *
 * @author huangxin 
 */
public class SimpleExecutor implements Executor {
    private final Configuration configuration;

    public SimpleExecutor(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public <E> List<E> query(MappedStatement ms, Object parameter) {
        BoundSql boundSql = ms.getBoundSql(parameter);
        try {
            Connection connection = configuration.getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(boundSql.getSql());
            List<Object> paramList = boundSql.getParamList();
            for (int i = 0; i < paramList.size(); i++) {
                preparedStatement.setObject(i + 1, paramList.get(i));
            }
            ResultSet resultSet = preparedStatement.executeQuery();
            Class<?> resultType = ms.getResultType();
            List<E> list = new ArrayList<>();
            while (resultSet.next()) {
                ResultSetMetaData metaData = resultSet.getMetaData();
                Object instance = resultType.newInstance();
                for (int i = 1; i <= metaData.getColumnCount(); i++) {
                    // 字段名
                    String columnName = metaData.getColumnName(i);
                    // 字段值
                    Object value = resultSet.getObject(columnName);
                    Field field = resultType.getDeclaredField(columnName);
                    field.setAccessible(true);
                    field.set(instance, value);
                }
                list.add((E) instance);
            }
            return list;
        } catch (SQLException | InstantiationException | IllegalAccessException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int update(MappedStatement ms, Object parameter) {
        BoundSql boundSql = ms.getBoundSql(parameter);
        try {
            Connection connection = configuration.getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(boundSql.getSql());
            List<Object> paramList = boundSql.getParamList();
            for (int i = 0; i < paramList.size(); i++) {
                preparedStatement.setObject(i + 1, paramList.get(i));
            }
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() throws IOException {

    }
}
