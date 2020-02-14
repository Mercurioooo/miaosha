package com.pro.miaosha.util;

import org.apache.commons.codec.digest.DigestUtils;

public class MD5Util {
	public static String md5(String src) {
		return DigestUtils.md2Hex(src);
	}
	private static final String salt = "1a2b3c4d";
	
	
	public static String inputPass2FormPass(String inputpass) {
		String str = ""+salt.charAt(0)+salt.charAt(2)+inputpass + salt.charAt(5)+salt.charAt(4);
		return md5(str);
	}
	
	public static String formPass2DBPass(String formpass,String salt) {
		String str = ""+salt.charAt(0)+salt.charAt(2)+formpass + salt.charAt(5)+salt.charAt(4);
		return md5(str);
	}
	
	public static String inputPass2DBPass(String input,String saltDB) {
		//输入密码=> formpass => 数据库密码
		String formPass = inputPass2FormPass(input);
		String dbPass = formPass2DBPass(formPass, saltDB);
		return dbPass;
	}
	//formPass2DBPass(formpass, saltDB)
	public static void main(String[] args) {
		System.out.println(formPass2DBPass("twinkle!", "1a2b3c4d"));
	}
	

}
