/*
 * Copyright (C) 2013 Void Main Studio 
 * Project:aTeamarks
 * Author: voidmain
 * Create Date: 2013-2-12下午8:52:08
 */
package com.secreek.ateamarks.adapters;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.secreek.ateamarks.R;
import com.secreek.ateamarks.models.Mark;
import com.secreek.ateamarks.models.User;
import com.secreek.ateamarks.utils.ImageUtils;
import com.secreek.ateamarks.utils.MD5;

/**
 * Adapter that converts data to view
 * 
 * @Project aTeamarks
 * @Package com.secreek.ateamarks.adapters
 * @Class TeamarkAdapter
 * @Date 2013-2-12 下午8:52:08
 * @author voidmain
 * @version
 * @since
 */
public class TeamarkAdapter extends BaseAdapter {

	private Context mContext = null;
	private List<Mark> mMarks = null;

	public TeamarkAdapter(Context context) {
		this(context, new ArrayList<Mark>());
	}

	public TeamarkAdapter(Context context, List<Mark> marks) {
		super();

		mContext = context;
		mMarks = marks;
	}

	public void updateMarks(List<Mark> newMarks) {
		mMarks = newMarks;
		this.notifyDataSetChanged();
	}

	/*
	 * @see android.widget.Adapter#getCount()getCount
	 */
	@Override
	public int getCount() {
		return mMarks.size();
	}

	/*
	 * @see android.widget.Adapter#getItem(int)getItem
	 */
	@Override
	public Object getItem(int position) {
		return mMarks.get(position);
	}

	/*
	 * @see android.widget.Adapter#getItemId(int)getItemId
	 */
	@Override
	public long getItemId(int position) {
		return 0;
	}

	/*
	 * @see android.widget.Adapter#getView(int, android.view.View,
	 * android.view.ViewGroup)getView
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.mark_item, parent, false);
		
		Mark theMark = mMarks.get(position);
		User theUser = theMark.getSharer();
		
		ImageView userAvater = (ImageView)rowView.findViewById(R.id.iv_user_avater);
		String url = String.format("http://www.gravatar.com/avatar/%1$s", MD5.md5(theUser.getEmail()));
		userAvater.setImageBitmap(ImageUtils.getBitmapFromUrl(url));
		
		TextView pageTitle = (TextView)rowView.findViewById(R.id.tv_page_title);
		pageTitle.setText(theMark.getPageTitle());
		
		TextView text = (TextView)rowView.findViewById(R.id.tv_text);
		text.setText(theMark.getText());
		
		rowView.setTag(theMark);
		
		rowView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Mark theMark = (Mark)v.getTag();
				if(theMark != null) {
					String url = theMark.getUrl();
					Intent i = new Intent(Intent.ACTION_VIEW);
					i.setData(Uri.parse(url));
					mContext.startActivity(i);
				}
			}
		});

		return rowView;
	}

}
