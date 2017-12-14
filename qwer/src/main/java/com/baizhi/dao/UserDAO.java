package com.baizhi.dao;

import com.baizhi.entity.User;

import java.util.List;

public interface UserDAO {
    List<User> selectAll();

    User selectOne(String id);
}
