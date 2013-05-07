/*
 * Copyright (C) 2013 Void Main Studio 
 * Project:aTeamarks
 * Author: voidmain
 * Create Date: 2013-2-12下午6:14:44
 */
package com.secreek.ateamarks.models;

import java.sql.SQLException;

import org.json.JSONObject;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**   
 * User model for Teamarks
 * 
 * @Project aTeamarks
 * @Package com.secreek.ateamarks.models
 * @Class User
 * @Date 2013-2-12 下午6:14:44
 * @author voidmain
 * @version 
 * @since 
 */

@DatabaseTable
public class User {
	
	@DatabaseField(id = true)
    private int userId;
	
	@DatabaseField(unique = true, canBeNull = false)
	private String username;
    
    @DatabaseField(canBeNull = true)
    private String password;
    
    @DatabaseField(unique = true, canBeNull = false)
    private String email;
    
    @DatabaseField(unique = true, canBeNull = false)
    private String apikey;
    
    public User() {
    }
    
    public User(JSONObject userObj) {
    	
    }

	public User(int userId, String username, String password, String email,
			String apikey) {
		super();
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.email = email;
		this.apikey = apikey;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getApikey() {
		return apikey;
	}

	public void setApikey(String apikey) {
		this.apikey = apikey;
	}
	
	public static User getUserById(int userId) {
		return null;
	}
	
	public void saveToDatabase(Dao<User, String> resultDao) {
		 try {
			resultDao.create(this);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
