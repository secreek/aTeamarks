/*
 * Copyright (C) 2013 Void Main Studio 
 * Project:aTeamarks
 * Author: voidmain
 * Create Date: 2013-2-12下午9:14:43
 */
package com.secreek.ateamarks.utils;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Load image from url on the fly
 * 
 * @Project aTeamarks
 * @Package com.secreek.ateamarks.utils
 * @Class ImageUtils
 * @Date 2013-2-12 下午9:14:43
 * @author voidmain
 * @version
 * @since
 */
public class ImageUtils {
	public static Bitmap getBitmapFromUrl(String url) {
		InputStream ins = null;
		try {
			ins = new java.net.URL(url).openStream();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return BitmapFactory.decodeStream(new FlushedInputStream(ins));
	}

	static class FlushedInputStream extends FilterInputStream {
		public FlushedInputStream(InputStream inputStream) {
			super(inputStream);
		}

		@Override
		public long skip(long n) throws IOException {
			long totalBytesSkipped = 0L;
			while (totalBytesSkipped < n) {
				long bytesSkipped = in.skip(n - totalBytesSkipped);
				if (bytesSkipped == 0L) {
					int b = read();
					if (b < 0) {
						break; // we reached EOF
					} else {
						bytesSkipped = 1; // we read one byte
					}
				}
				totalBytesSkipped += bytesSkipped;
			}
			return totalBytesSkipped;
		}
	}
}
