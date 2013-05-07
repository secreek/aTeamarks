/*
 * Copyright (C) 2013 Void Main Studio 
 * Project:aTeamarks
 * Author: voidmain
 * Create Date: 2013-2-12下午9:20:24
 */
package com.secreek.ateamarks.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Encodes MD5
 * 
 * @Project aTeamarks
 * @Package com.secreek.ateamarks.utils
 * @Class MD5
 * @Date 2013-2-12 下午9:20:24
 * @author voidmain
 * @version
 * @since
 */
public class MD5 {
	public static String md5(String s) {
		try {
			// Create MD5 Hash
			MessageDigest digest = java.security.MessageDigest
					.getInstance("MD5");
			digest.update(s.getBytes());
			byte messageDigest[] = digest.digest();

			// Create Hex String
			StringBuffer hexString = new StringBuffer();
			for (int i = 0; i < messageDigest.length; i++)
				hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
			return hexString.toString();

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "";
	}
}
