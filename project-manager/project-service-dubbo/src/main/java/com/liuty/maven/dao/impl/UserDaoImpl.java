package com.liuty.maven.dao.impl;

import com.liuty.maven.dao.UserDao;
import com.liuty.maven.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository("userDao")
public class UserDaoImpl implements UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public UserEntity findUserById(String id) {
        return this.jdbcTemplate.queryForObject("select t.* from fasp_t_causer t where t.guid = ?"
                , new Object[]{id}, new UserRowMapper());
    }
}

class UserRowMapper implements RowMapper<UserEntity> {

    @Override
    public UserEntity mapRow(ResultSet resultSet, int i) throws SQLException {
        UserEntity user = new UserEntity();
        user.setGuid(resultSet.getString("guid"));
        user.setCode(resultSet.getString("code"));
        user.setPassword(resultSet.getString("password"));
        user.setName(resultSet.getString("name"));
        user.setEmail(resultSet.getString("email"));
        user.setStatus(resultSet.getInt("status"));
        user.setRemark(resultSet.getString("remark"));
        return user;
    }
}
