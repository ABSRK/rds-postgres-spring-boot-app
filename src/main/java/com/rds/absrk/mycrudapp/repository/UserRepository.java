package com.rds.absrk.mycrudapp.repository;
/**
 * @author ABSRK Manikanta
 */
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.rds.absrk.mycrudapp.model.User;

@Repository
public class UserRepository {

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<User> getUsers() {
		List<User> users = new ArrayList<>();

		try {

			String sql = "SELECT * FROM user_data";

			SqlParameterSource param = new MapSqlParameterSource();

			users = namedParameterJdbcTemplate.query(sql, param, new RowMapper() {

				@Override
				public Object mapRow(ResultSet rs, int rowNum) throws SQLException {

					User user = new User(rs.getLong("id"), rs.getString("firstname"), rs.getString("lastname"));

					return user;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return users;
	}

	public List<User> createUser(User user) {

		try {

			String sql = "INSERT INTO user_data (id, firstname, lastname) VALUES (:id, :firstname, :lastname)";

			MapSqlParameterSource params = new MapSqlParameterSource();

			params.addValue("id", user.getId()).addValue("firstname", user.getFirstName()).addValue("lastname",
					user.getLastName());

			namedParameterJdbcTemplate.update(sql, params);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return getUsers();
	}

	public int countUsers() {
		int count = 0;
		try {

			String sql = "SELECT COUNT(*) FROM user_data";

			MapSqlParameterSource params = new MapSqlParameterSource();

			count = namedParameterJdbcTemplate.queryForObject(sql, params, Integer.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return count;

	}

	public User findById(Long id) {

		User user = new User();
		try {
			String sql = "SELECT * FROM user_data WHERE id=:id";

			SqlParameterSource param = new MapSqlParameterSource("id", id);

			user = namedParameterJdbcTemplate.query(sql, param, new ResultSetExtractor<User>() {

				@Override
				public User extractData(ResultSet rs) throws SQLException, DataAccessException {
					if (rs.next()) {

						return new User(rs.getLong("id"), rs.getString("firstname"), rs.getString("lastname"));
					}
					return new User();
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}


}