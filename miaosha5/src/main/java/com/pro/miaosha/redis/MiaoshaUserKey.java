package com.pro.miaosha.redis;

public class MiaoshaUserKey extends BasePrefix{
	
	public static final int TOKEN_EXPIRE = 3200*24*2;
	private MiaoshaUserKey(int expreSeconds,String prefix){
		super(expreSeconds,prefix);
	}
	public static MiaoshaUserKey token = new MiaoshaUserKey(TOKEN_EXPIRE,"tk");
	public static MiaoshaUserKey getById = new MiaoshaUserKey(0,"id");
 
	//public static MiaoshaUserKey getByName = new MiaoshaUserKey("name");

}
