package com.huangxin.mapper;

import com.huangxin.entity.User;

import java.util.List;

/**
 * UserMapper
 *
 * @author huangxin
 */
public interface UserMapper {

    List<User> findAll();

    User findByUser(User user);

    int insertUser(User user);

    int updateById(User user);

    int deleteById(Integer id);
}
