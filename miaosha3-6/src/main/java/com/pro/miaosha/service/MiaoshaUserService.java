package com.pro.miaosha.service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.pro.miaosha.dao.MiaoshaUserDao;
import com.pro.miaosha.domain.MiaoshaUser;
import com.pro.miaosha.exception.GlobalException;
import com.pro.miaosha.redis.MiaoshaUserKey;
import com.pro.miaosha.redis.RedisService;
import com.pro.miaosha.result.CodeMsg;
import com.pro.miaosha.util.MD5Util;
import com.pro.miaosha.util.UUIDUtil;
import com.pro.miaosha.vo.LoginVo;

@Service
public class MiaoshaUserService {
	@Autowired
	RedisService redisService;

	public static final String COOKIE_NAME_TOKEN ="token";
	@Autowired
	MiaoshaUserDao miaoshaUserDao;
	public MiaoshaUser getById(Long id) {
		return miaoshaUserDao.getById(id);
	}
	public MiaoshaUser getByToken(HttpServletResponse response, String token) {
		if(StringUtils.isEmpty(token)) {
			return null;
		}
		MiaoshaUser user = redisService.get(MiaoshaUserKey.token, token, MiaoshaUser.class);
		//延长有效期
		if(user != null) {
			addCookie(response, token, user);
		}
		return user;
	}

	
	public boolean login(HttpServletResponse response,LoginVo loginVo) {
		if(loginVo == null) {
			throw new GlobalException(CodeMsg.SERVER_ERROR); 
		}
		String mobile = loginVo.getMobile();
		String formpass = loginVo.getPassword();
		MiaoshaUser user = getById(Long.parseLong(mobile));//获取数据库中的人
		if(user == null) {
			throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
		}
		//验证密码
		String dbPass = user.getPassword();//获取数据库中人的密码
		System.out.println("db"+dbPass);
		String saltDB = user.getSalt();//获取数据库中人的salt
		System.out.println("salt"+saltDB);
		String calcPass = MD5Util.formPass2DBPass(formpass, saltDB);//通过salt把输入的密码变成calc密码	
		System.out.println("cal"+calcPass);
		if(!calcPass.equals(dbPass)) {//再比较这两个密码
			throw new GlobalException(CodeMsg.PASSWORD_ERROR);
		}

		/* 在登录成功之后,
		 * 要把信息保存起来,如果把用户的所有信息都带上就太麻烦了
		 * 所以就生成一个 tk123456:user
		 * 
		*/
		//生成一个token(随机的immutable universally unique identifier )
		String token = UUIDUtil.uuid();
		/*
		 * 参数:前缀+key+value
		 * 前缀是MiaoshaUserKey.token  
		 * public static MiaoshaUserKey token = new MiaoshaUserKey(TOKEN_EXPIRE,"tk");
		 * 即带有  时间有效期 & “tk”前缀  的一个key,
		 * 
		 * token是不可改变全局唯一变量
		 * 
		 * user就是user
		 * 
		 * 最后是 tk123456:user 有时间有效期
		 */
		redisService.set(MiaoshaUserKey.token,token,user);
		Cookie cookie = new Cookie(COOKIE_NAME_TOKEN,token);//“token”:token(123456)
		cookie.setMaxAge(MiaoshaUserKey.token.expireSeconds());//设置有效期
		cookie.setPath("/");
		response.addCookie(cookie);

		return true;
	}
	
	//添加cookie到response中,redis中存储<token,user>
	private void addCookie(HttpServletResponse response, String token, MiaoshaUser user) {
		//1. redis中存储<token,user>
		redisService.set(MiaoshaUserKey.token, token, user);
		//2.添加cookie到response中
		Cookie cookie = new Cookie(COOKIE_NAME_TOKEN, token);
		cookie.setMaxAge(MiaoshaUserKey.token.expireSeconds());
		cookie.setPath("/");
		response.addCookie(cookie);
	}
}
