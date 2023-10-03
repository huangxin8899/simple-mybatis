package com.huangxin.test;

import com.huangxin.entity.User;
import com.huangxin.io.Resources;
import com.huangxin.mapper.UserMapper;
import com.huangxin.session.SqlSession;
import com.huangxin.session.SqlSessionFactory;
import com.huangxin.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * MyTest
 *
 * @author huangxin 
 */
public class MyTest {

    private static final UserMapper userMapper;

    static {
        // 读取配置文件
        InputStream inputStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        // 解析配置文件字节流并创建SqlSessionFactory
        SqlSessionFactory sqlSessionFactory = SqlSessionFactoryBuilder.build(inputStream);
        // 用工厂生成SqlSession对象
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            // 动态代理生成mapper接口实现类
            userMapper = sqlSession.getMapper(UserMapper.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testSelectList() {
            List<User> list = userMapper.findAll();
            list.forEach(System.out::println);
    }

    @Test
    public void testSelectOne() {
        User user = userMapper.findByUser(new User(1, "xxx"));
        System.out.println(user);
    }

    @Test
    public void testInsert() {
        int i = userMapper.insertUser(new User(3, "bbb"));
        System.out.println(i);
        testSelectList();
    }

    @Test
    public void testUpdate() {
        int i = userMapper.updateById(new User(3, "ccc"));
        System.out.println(i);
        testSelectList();
    }

    @Test
    public void testDelete() {
        int i = userMapper.deleteById(3);
        System.out.println(i);
        testSelectList();
    }
}
