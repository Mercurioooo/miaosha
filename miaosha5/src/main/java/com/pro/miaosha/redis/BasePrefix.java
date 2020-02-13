package com.pro.miaosha.redis;

public abstract class BasePrefix implements KeyPrefix{

	private int expireSeconds;
	private String prefix;
	
	public BasePrefix(String prefix) {
		this(0,prefix);
	}
	
	public BasePrefix(int expireSeconds, String prefix) {
		super();
		this.expireSeconds = expireSeconds;
		this.prefix = prefix;
	}
	public int expireSeconds() {
		return expireSeconds;//默认0代表永不过期
	}

	public String getPrefix() {
		String className = getClass().getSimpleName();
		return className+":"+prefix;
		
	}

}
