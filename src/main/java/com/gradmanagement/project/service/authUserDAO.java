package com.gradmanagement.project.service;

import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import com.gradmanagement.project.model.AuthUser;

import ch.qos.logback.classic.Logger;

@Repository
public class authUserDAO {
	private JdbcTemplate jdbcobj;

	Logger logger = (Logger) LoggerFactory.getLogger(authUserDAO.class);
	
	public authUserDAO(JdbcTemplate jdbcobj) {
		super();
		this.jdbcobj = jdbcobj;
	}
	
	public void saveAuthUser(AuthUser authUser)
	{
		String sql = "SELECT * FROM authuser WHERE id=?";
		@SuppressWarnings("deprecation")
		Iterable<AuthUser> list = jdbcobj.query(sql, new Object[]{authUser.getId()}, BeanPropertyRowMapper.newInstance(AuthUser.class));
		if(!list.iterator().hasNext())
		{
			SimpleJdbcInsert insertActor = new SimpleJdbcInsert(jdbcobj);
			insertActor.withTableName("authuser").usingColumns("id", "email", "name", "authToken");
			BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(authUser);
			insertActor.execute(param);
		}
		else
		{
			removeResponse(authUser.getId());
			SimpleJdbcInsert insertActor = new SimpleJdbcInsert(jdbcobj);
			insertActor.withTableName("authuser").usingColumns("id", "email", "name", "authToken");
			BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(authUser);
			insertActor.execute(param);
		}
	}
	
	public void removeResponse(String id)
	{
		String sql = "DELETE FROM authuser WHERE id=?";
		jdbcobj.update(sql, id);
	}
	
}
