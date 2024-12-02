package com.example.wow.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.example.wow.model.UserJDBC;

@Component
public class JDBCMapper implements RowMapper<UserJDBC> {

    @Override
    public UserJDBC mapRow(ResultSet rs, int rowNum) throws SQLException {
        UserJDBC user = new UserJDBC();
        user.setId(rs.getInt("id"));
        user.setName(rs.getString("name"));
        user.setEmail(rs.getString("email"));
        return user;
    }
    
}
