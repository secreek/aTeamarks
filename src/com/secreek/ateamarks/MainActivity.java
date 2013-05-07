package com.secreek.ateamarks;

import java.sql.SQLException;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.secreek.ateamarks.adapters.TeamarkAdapter;
import com.secreek.ateamarks.api.ApiFetcher;
import com.secreek.ateamarks.db.DBHelper;
import com.secreek.ateamarks.models.Mark;

public class MainActivity extends Activity {

	private DBHelper mDbHelper = null;

	private ListView mMarkListView = null;
	private TeamarkAdapter mMarkAdapter = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mMarkListView = (ListView) findViewById(R.id.mark_list);

		DBHelper helper = getHelper();
		try {
			mMarkAdapter = new TeamarkAdapter(this, helper.getMarkDao()
					.queryForAll());
		} catch (SQLException e) {
			e.printStackTrace();
			mMarkAdapter = new TeamarkAdapter(this);
		}

		mMarkListView.setAdapter(mMarkAdapter);

		Dao<Mark, String> markDao;
		int after = 0;
		try {
			markDao = getHelper().getMarkDao();
			List<Mark> currentMarks = markDao.query(markDao.queryBuilder()
					.orderBy("markId", false).prepare());
			if (currentMarks.size() > 0) {
				Mark latestMark = currentMarks.get(0);
				after = Integer.valueOf(latestMark.getMarkId());
			}

			new FetchNewsTask().execute(after);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (mDbHelper != null) {
			OpenHelperManager.releaseHelper();
			mDbHelper = null;
		}
	}

	private DBHelper getHelper() {
		if (mDbHelper == null) {
			mDbHelper = OpenHelperManager.getHelper(this, DBHelper.class);
		}
		return mDbHelper;
	}

	private class FetchNewsTask extends
			AsyncTask<Integer, Void, List<JSONObject>> {

		@Override
		protected List<JSONObject> doInBackground(Integer... params) {
			if (params.length > 0) {
				return ApiFetcher.grabNewMarks(params[0]);
			}

			return null;
		}

		@Override
		protected void onPostExecute(List<JSONObject> result) {
			if (result == null) {
				return;
			}

			Dao<Mark, String> markDao;
			try {
				markDao = getHelper().getMarkDao();
				for (JSONObject collection : result) {
					JSONArray links;
					try {
						links = collection.getJSONArray("links");
						for (int idx = 0; idx < links.length(); idx++) {
							Mark newMark = new Mark(links.getJSONObject(idx));
							newMark.saveToDatabase(markDao);
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}

				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

}
