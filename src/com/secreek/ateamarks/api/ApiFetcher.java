/*
 * Copyright (C) 2013 Void Main Studio 
 * Project:aTeamarks
 * Author: voidmain
 * Create Date: 2013-2-12下午7:09:02
 */
package com.secreek.ateamarks.api;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

/**
 * Fetches content from api.teamarks.com/
 * 
 * @Project aTeamarks
 * @Package com.secreek.ateamarks.api
 * @Class ApiFetcher
 * @Date 2013-2-12 下午7:09:02
 * @author voidmain
 * @version
 * @since
 */
public class ApiFetcher {
	private static final String TAG = ApiFetcher.class.getCanonicalName();
	
	private static final String BASE_MARK_URL = "http://api.teamarks.com/v1/list?after=%1$s";
	
	public static List<JSONObject> grabNewMarks(int after) {
		List<JSONObject> resultMarks = new ArrayList<JSONObject>();
		System.out.println(String.format(BASE_MARK_URL, after));
		JSONObject requestResult = getJSONfromURL(String.format(BASE_MARK_URL, after));
		try {
			JSONArray markArray = requestResult.getJSONArray("result");
			for(int index = 0;index < markArray.length();index ++) {
				resultMarks.add(markArray.getJSONObject(index));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return resultMarks;
	}
	
	private static JSONObject getJSONfromURL(String url) {
		InputStream is = null;
		String result = "";
		JSONObject jObject = null;

		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(url);
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();

		} catch (Exception e) {
			Log.e(TAG, "Error in http connection " + e.toString());
		}

		// convert response to string
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "utf-8"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			result = sb.toString();
		} catch (Exception e) {
			Log.e(TAG, "Error converting result " + e.toString());
		}

		// try parse the string to a JSON object
		try {
			jObject = new JSONObject(result);
		} catch (JSONException e) {
			Log.e(TAG, "Error parsing data " + e.toString());
		}

		return jObject;
	}
}
