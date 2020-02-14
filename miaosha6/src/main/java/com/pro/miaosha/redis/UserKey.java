package com.pro.miaosha.redis;

public class UserKey extends BasePrefix{
	
	private UserKey(String prefix) {
		super(prefix);
	}
	
	private UserKey(int expireSeconds,String prefix) {
		super(expireSeconds,prefix);
	}
	
	public static UserKey getById = new UserKey("id");
	public static UserKey getByName = new UserKey("name");
	
}
