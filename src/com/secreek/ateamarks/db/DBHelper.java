/*
 * Copyright (C) 2013 Void Main Studio 
 * Project:aTeamarks
 * Author: voidmain
 * Create Date: 2013-2-12下午6:33:21
 */
package com.secreek.ateamarks.db;

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.secreek.ateamarks.R;
import com.secreek.ateamarks.models.Mark;
import com.secreek.ateamarks.models.User;

/**
 * The helper class for OrmLite
 * 
 * @Project aTeamarks
 * @Package com.secreek.ateamarks.db
 * @Class DBHelper
 * @Date 2013-2-12 下午6:33:21
 * @author voidmain
 * @version
 * @since
 */
public class DBHelper extends OrmLiteSqliteOpenHelper {
	
	private static final String TAG = DBHelper.class.getCanonicalName();

	private static final String DATABASE_NAME = "aTeamarks.db";
	private static final int DATABASE_VERSION = 1;
	
	private Dao<User, String> userDao = null;
	private RuntimeExceptionDao<User, String> userRuntimeDao = null;
	
	private Dao<Mark, String> markDao = null;
	private RuntimeExceptionDao<Mark, String> markRuntimeDao = null;
	
	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION,
				R.raw.ormlite_config);
	}

	/*
	 * @see
	 * com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper#onCreate(android
	 * .database.sqlite.SQLiteDatabase,
	 * com.j256.ormlite.support.ConnectionSource)onCreate
	 */
	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
		try {
			Log.i(TAG, "onCreate");
			TableUtils.createTable(connectionSource, User.class);
			TableUtils.createTable(connectionSource, Mark.class);
		} catch (SQLException e) {
			Log.e(TAG, "Can't create database", e);
			throw new RuntimeException(e);
		}
	}

	/*
	 * @see
	 * com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper#onUpgrade(android
	 * .database.sqlite.SQLiteDatabase,
	 * com.j256.ormlite.support.ConnectionSource, int, int)onUpgrade
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource,
			int oldVersion, int newVersion) {
		try {
			Log.i(TAG, "onUpgrade");
			TableUtils.dropTable(connectionSource, User.class, true);
			TableUtils.dropTable(connectionSource, Mark.class, true);
			// after we drop the old databases, we create the new ones
			onCreate(db, connectionSource);
		} catch (SQLException e) {
			Log.e(TAG, "Can't drop databases", e);
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Returns the Database Access Object (DAO) for our User class. It will create it or just give the cached
	 * value.
	 */
	public Dao<User, String> getUserDao() throws SQLException {
		if (userDao == null) {
			userDao = getDao(Mark.class);
		}
		return userDao;
	}
	
	/**
	 * Returns the Database Access Object (DAO) for our Mark class. It will create it or just give the cached
	 * value.
	 */
	public Dao<Mark, String> getMarkDao() throws SQLException {
		if (markDao == null) {
			markDao = getDao(Mark.class);
		}
		return markDao;
	}
	
	/**
	 * Returns the RuntimeExceptionDao (Database Access Object) version of a Dao for our User class. It will
	 * create it or just give the cached value. RuntimeExceptionDao only through RuntimeExceptions.
	 */
	public RuntimeExceptionDao<User, String> getUserDataDao() {
		if (userRuntimeDao == null) {
			userRuntimeDao = getRuntimeExceptionDao(User.class);
		}
		return userRuntimeDao;
	}

	/**
	 * Returns the RuntimeExceptionDao (Database Access Object) version of a Dao for our Mark class. It will
	 * create it or just give the cached value. RuntimeExceptionDao only through RuntimeExceptions.
	 */
	public RuntimeExceptionDao<Mark, String> getMarkDataDao() {
		if (markRuntimeDao == null) {
			markRuntimeDao = getRuntimeExceptionDao(Mark.class);
		}
		return markRuntimeDao;
	}

	/**
	 * Close the database connections and clear any cached DAOs.
	 */
	@Override
	public void close() {
		super.close();
		userRuntimeDao = null;
		markRuntimeDao = null;
	}

}
