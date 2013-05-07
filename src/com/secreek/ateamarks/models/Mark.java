/*
 * Copyright (C) 2013 Void Main Studio 
 * Project:aTeamarks
 * Author: voidmain
 * Create Date: 2013-2-12下午6:22:37
 */
package com.secreek.ateamarks.models;

import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Saves the mark content for Teamarks
 * 
 * @Project aTeamarks
 * @Package com.secreek.ateamarks.models
 * @Class Mark
 * @Date 2013-2-12 下午6:22:37
 * @author voidmain
 * @version
 * @since
 */
@DatabaseTable
public class Mark {

	@DatabaseField(id = true)
	private int markId;

	@DatabaseField(canBeNull = false)
	private String pageTitle;

	@DatabaseField(canBeNull = false)
	private String text;

	@DatabaseField(canBeNull = false)
	private String url;

	@DatabaseField(canBeNull = false)
	private int userId;

	public Mark() {
		
	}
	
	public Mark(JSONObject object) {
		try {
			this.markId = object.getInt("id");
			this.pageTitle = object.getString("page_title");
			this.text = object.getString("text");
			this.url = object.getString("url");
			this.userId = object.getInt("user_id");
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public Mark(int markId, String pageTitle, String text, String url,
			User sharer) {
		super();
		this.markId = markId;
		this.pageTitle = pageTitle;
		this.text = text;
		this.url = url;
		this.userId = sharer.getUserId();
	}

	public int getMarkId() {
		return markId;
	}

	public void setMarkId(int markId) {
		this.markId = markId;
	}

	public String getPageTitle() {
		return pageTitle;
	}

	public void setPageTitle(String pageTitle) {
		this.pageTitle = pageTitle;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public User getSharer() {
		return User.getUserById(userId);
	}

	public void setSharer(User sharer) {
		this.userId = sharer.getUserId();
	}
	
	public void saveToDatabase(Dao<Mark, String> resultDao) {
		 try {
			resultDao.create(this);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
