package com.gradmanagement.project.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.gradmanagement.project.model.ObjectString;
import com.gradmanagement.project.model.User;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;


@Repository
public class UserDAO {
private JdbcTemplate jdbcobj;

	public UserDAO(JdbcTemplate jdbcobj) {
		super();
		this.jdbcobj = jdbcobj;
	}
	
	public Iterable<User> listGrad(){   
		String sql = "SELECT * FROM grads";
		return jdbcobj.query(sql, BeanPropertyRowMapper.newInstance(User.class));
	}
	
	public void deleteGrad(int id)   //
	{
		String sql = "DELETE FROM grads WHERE id=?";
		jdbcobj.update(sql, id);
		String sql2 = "DELETE FROM object_string WHERE id=?";
		jdbcobj.update(sql2,id);
	}
	
	public static String toObjectString(User user)
	{
		return user.getFname() + " " + user.getLname() + " " + user.getAddress() + " " + user.getAge() + " " + user.getContact() + " " + user.getEmail() + " " + user.getGender() + " " + user.getInstitution() + " " + user.getLocation() + " " + user.getSkillset() + " " + user.getDate() + " " + user.getRole() + " " + user.getFeedback();
	}
	
	
	public void registerUser(User user)  //
	{
		SimpleJdbcInsert insertActor = new SimpleJdbcInsert(jdbcobj);
		insertActor.withTableName("grads").usingColumns("id","fname", "lname", "gender", "age", "email", "contact", "address", "role", "date", "feedback", "institution", "skillset", "location");
		BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(user);
		insertActor.execute(param);
		
		SimpleJdbcInsert insertActor2 = new SimpleJdbcInsert(jdbcobj);
		insertActor2.withTableName("object_string").usingColumns("id","string");
		BeanPropertySqlParameterSource param2 = new BeanPropertySqlParameterSource(new ObjectString(0, toObjectString(user)));
		insertActor2.execute(param2);
		
	}
	
	public void editUser(User user)   //
	{
		String sql = "UPDATE grads SET fname=:fname, lname=:lname, gender=:gender, age=:age, email=:email, contact=:contact, address=:address, role=:role, date=:date, feedback=:feedback, institution=:institution, skillset=:skillset, location=:location WHERE id=:id";
		BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(user);
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(jdbcobj);
		template.update(sql, param);
		
		String sql2 = "UPDATE object_string SET string=:string WHERE id=:id";
		BeanPropertySqlParameterSource param2 = new BeanPropertySqlParameterSource(new ObjectString(user.getId(),toObjectString(user)));
		NamedParameterJdbcTemplate template2 = new NamedParameterJdbcTemplate(jdbcobj);
		template2.update(sql2, param2);
	}
	
	@SuppressWarnings("deprecation")
	public List<User> findByFirstname(String searchVar) {
	    String sql = "SELECT id FROM object_string WHERE string LIKE ?";
	    String sql2 = "SELECT * FROM grads WHERE id=?";
	    String new_searchVar = "%"+searchVar+"%";
	    
	    List<ObjectString> list_id = new ArrayList<>();
	    list_id = jdbcobj.query(sql, new Object[]{new_searchVar} ,BeanPropertyRowMapper.newInstance(ObjectString.class));
	    List<User> ret_list = new ArrayList<>();
	    for(int i=0;i<list_id.size();i++)
	    {
	    	ret_list.add(jdbcobj.queryForObject(sql2, new Object[]{list_id.get(i).getId()} ,BeanPropertyRowMapper.newInstance(User.class)));
	    }
		return ret_list;
	  }
	
	@SuppressWarnings("deprecation")
	public User findById(int id)
	{
		String sql = "SELECT * FROM grads WHERE id=?";
		return jdbcobj.queryForObject(sql, new Object[]{id} ,BeanPropertyRowMapper.newInstance(User.class));
	}
	
	public HashMap<String,Integer> skillMap()
	{
		String sql = "SELECT skillset FROM grads";
		Iterable<User> list = jdbcobj.query(sql, BeanPropertyRowMapper.newInstance(User.class));
		HashMap<String,Integer> skills = new HashMap<>();
		Iterator<User> iter = list.iterator();  
		while(iter.hasNext())   
		{  
			String str = iter.next().getSkillset();
			String[] rts = str.split(",");
			for(int i=0;i<rts.length;i++)
			{
				String temp = rts[i].trim().toUpperCase();
				if(temp!="")
				{
					skills.put(temp, skills.getOrDefault(temp, 0)+1);
				}
			}
 		}
		skills.remove("");
		return skills;
	}
	
	public HashMap<String,Integer> gradyearMap()
	{
		String sql = "SELECT date FROM grads";
		Iterable<User> list = jdbcobj.query(sql, BeanPropertyRowMapper.newInstance(User.class));
		HashMap<String,Integer> years = new HashMap<>();
		Iterator<User> iter = list.iterator();  
		while(iter.hasNext())   
		{  
			@SuppressWarnings("deprecation")
			int yr = iter.next().getDate().getYear()+1900;
			String str = Integer.toString(yr);
			String temp = str.trim();
			years.put(temp, years.getOrDefault(temp, 0)+1);
 		}  
		years.remove("");
		return years;
	}
	
	public HashMap<String,Integer> graddiversityMap()
	{
		String sql = "SELECT location FROM grads";
		Iterable<User> list = jdbcobj.query(sql, BeanPropertyRowMapper.newInstance(User.class));
		HashMap<String,Integer> location = new HashMap<>();
		Iterator<User> iter = list.iterator();  
		while(iter.hasNext())   
		{  
			String str = iter.next().getLocation();
			String temp = str.trim().toUpperCase();
			location.put(temp, location.getOrDefault(temp, 0)+1);
 		}  
		location.remove("");
		return location;
	}
	
	public HashMap<String,Integer> gradFeedbackMap()
	{
		String sql = "SELECT feedback FROM grads";
		Iterable<User> list = jdbcobj.query(sql, BeanPropertyRowMapper.newInstance(User.class));
		HashMap<String,Integer> feedback = new HashMap<>();
		Iterator<User> iter = list.iterator();  
		while(iter.hasNext())   
		{  
			String str = iter.next().getFeedback();
			String temp = str.trim().toUpperCase();
			feedback.put(temp, feedback.getOrDefault(temp, 0)+1);
 		}  
		feedback.remove("");
		return feedback;
	}
	
	public HashMap<String,Integer> gradRolesMap()
	{
		String sql = "SELECT role FROM grads";
		Iterable<User> list = jdbcobj.query(sql, BeanPropertyRowMapper.newInstance(User.class));
		HashMap<String,Integer> role = new HashMap<>();
		Iterator<User> iter = list.iterator();  
		while(iter.hasNext())   
		{  
			String str = iter.next().getRole();
			String temp = str.trim().toUpperCase();
			role.put(temp, role.getOrDefault(temp, 0)+1);
 		}  
		role.remove("");
		return role;
	}
}
