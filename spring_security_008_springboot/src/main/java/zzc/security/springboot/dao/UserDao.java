package zzc.security.springboot.dao;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import zzc.security.springboot.model.User;

@Repository
public class UserDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public User getUserByUsername(String username) {
		String sql = "SELECT id,username,password,fullname,mobile FROM t_user WHERE username= ?";
		List<User> list = jdbcTemplate.query(sql, new Object[] { username }, new BeanPropertyRowMapper<>(User.class));
		if (Objects.nonNull(list) && list.size() == 1) {
			return list.get(0);
		}
		return null;
	}
	
	
}
