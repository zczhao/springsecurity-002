package zzc.security.springboot.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import zzc.security.springboot.model.Permission;
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
	
	/**
	 * 根据用户id查询用户权限
	 * @param userId
	 * @return
	 */
	public List<String> findPermissionsByUserId(String userId) {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT * FROM t_permission WHERE id IN (");
			sb.append("SELECT permission_id FROM t_role_permission WHERE role_id IN (");
				sb.append("SELECT role_id FROM t_user_role WHERE user_id=?");
			sb.append(")");
		sb.append(")");
		List<Permission> list = jdbcTemplate.query(sb.toString(), new Object[] { userId }, new BeanPropertyRowMapper<>(Permission.class));
		List<String> permissions = new ArrayList<String>();
		list.forEach(item -> permissions.add(item.getCode()));
		return permissions;
	}
}
