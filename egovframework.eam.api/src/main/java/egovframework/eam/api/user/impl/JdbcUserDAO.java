package egovframework.eam.api.user.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import egovframework.eam.api.user.UserDAO;
import egovframework.eam.api.user.UserVO;

public class JdbcUserDAO implements UserDAO {
	static final String SELECT_QUERY = "SELECT USER_ID, PASSWORD, ENABLED, USER_NAME FROM USERS WHERE USER_ID = ?";
	static final String INSERT_QUERY = "INSERT INTO USERS (USER_ID, PASSWORD, ENABLED, USER_NAME) VALUES (?, ?, ?, ?)";
	static final String UPDATE_QUERY = "UPDATE USERS SET PASSWORD = ?, ENABLED = ?, USER_NAME = ? WHERE USER_ID = ?";
	static final String DELETE_QUERY = "DELETE FROM USERS WHERE USER_ID = ?";
	
	private JdbcTemplate jdbcTemplate;

	@Required
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

	public UserVO select(String userId) {
		try {
			return this.jdbcTemplate.queryForObject(
					SELECT_QUERY, 
					new String[] {userId}, 
					new RowMapper<UserVO>() {
			            public UserVO mapRow(ResultSet rs, int rowNum) throws SQLException {
			            	UserVO user = new UserVO();
			            	user.setUserId(rs.getString("USER_ID"));
			            	user.setPassword(rs.getString("PASSWORD"));
			            	user.setEnabled(rs.getBoolean("ENABLED"));
			            	user.setUserName(rs.getString("USER_NAME"));
			                return user;
			            }
					});
		} catch (EmptyResultDataAccessException erdae) {
			return null;
		}
	}

	public void insert(UserVO user) {
		this.jdbcTemplate.update(
		        INSERT_QUERY, 
		        user.getUserId(), user.getEncodedPassword(), user.isEnabled(), user.getUserName());
		
	}

	public void update(UserVO user) {
		this.jdbcTemplate.update(
		        UPDATE_QUERY, 
		        user.getEncodedPassword(), user.isEnabled(), user.getUserName(), user.getUserId());
	}

	public void delete(String userId) {
		this.jdbcTemplate.update(
				DELETE_QUERY, 
				userId); 
	}
}
