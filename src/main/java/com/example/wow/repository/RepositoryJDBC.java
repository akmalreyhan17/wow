package com.example.wow.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.wow.model.UserJDBC;

@Repository
public class RepositoryJDBC {
    @Autowired
    JdbcTemplate jdbcTemplate;

    // Using 'query' to fetch all users with BeanPropertyRowMapper
    public List<UserJDBC> findAll() {
        String sql = "SELECT * FROM user_jdbc";
        return jdbcTemplate.query(sql, new JDBCMapper());
    }

    // Using 'query' to fetch a single user by ID with BeanPropertyRowMapper
    public UserJDBC findById(Long id) {
        String sql = "SELECT * FROM user_jdbc WHERE id = ?";
        return jdbcTemplate.queryForObject(
                sql,
                new JDBCMapper(),
                id);
    }

    public UserJDBC findByNameAndEmail(String name, String email) {
        String sql = "SELECT * FROM user_jdbc WHERE name = ? AND email = ?";
        return jdbcTemplate.queryForObject(
                sql,
                new JDBCMapper(), // RowMapper to map the result set);
                name, email // Parameters to bind to the SQL query
        );
    }

    @SuppressWarnings("deprecation")
    public UserJDBC findByIdDeprecated(String name, String email) {
        String sql = "SELECT * FROM user_jdbc WHERE name = ? AND email = ?";
        return jdbcTemplate.queryForObject(
                sql,
                new Object[] { name, email }, // Parameters to bind to the SQL query
                new BeanPropertyRowMapper<>(UserJDBC.class) // RowMapper to map the result set);
        );
    }

    // Using 'update' to insert a new user
    public int save(UserJDBC user) {
        String sql = "INSERT INTO user_jdbc (name, email) VALUES (?, ?)";
        return jdbcTemplate.update(sql, user.getName(), user.getEmail());
    }

    // Using 'update' to update an existing user's email
    public UserJDBC updateEmail(Long id, String newEmail) {
        try {
            String sql = "UPDATE user_jdbc SET email = ? WHERE id = ?";
            int rowAffected = jdbcTemplate.update(sql, newEmail, id);

            if(rowAffected==0) {
                throw new RuntimeException("No user found with id: " + id);
            }

            return findById(id);
        } catch (EmptyResultDataAccessException e) {
            // Handle cases where no rows were returned when expected
            System.err.println("No user found with id: " + id);
            e.printStackTrace();
            return null; // or handle as needed
            
        } catch (DataAccessException e) {
            // Handle data access exceptions (e.g., SQL errors, connection issues)
            System.err.println("Database access error occurred: " + e.getMessage());
            e.printStackTrace();
            return null; // or you can throw a custom exception
            
        } catch (Exception e) {
            // Handle any other unexpected exceptions
            System.err.println("An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
            return null; // or handle as needed
        }
    }

    public UserJDBC updateEmailReturning(Long id, String newEmail) {
        String sql = "UPDATE user_jdbc SET email = ? WHERE id = ? RETURNING *";
        return jdbcTemplate.queryForObject(
                sql,
                new JDBCMapper(),
                newEmail, id);
    }

    // Using 'update' to delete a user by ID
    public int deleteById(Long id) {
        String sql = "DELETE FROM user_jdbc WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    public void batchInsertUsers(List<UserJDBC> users) {
        String sql = "INSERT INTO users (id, name, email) VALUES (?, ?, ?)";

        // Convert the list of users into a 2D array of objects for batch processing
        List<Object[]> batchArgs = new ArrayList<>();
        for (UserJDBC user : users) {
            batchArgs.add(new Object[]{user.getId(), user.getName(), user.getEmail()});
        }

        // Perform the batch update
        jdbcTemplate.batchUpdate(sql, batchArgs);
    }
}
