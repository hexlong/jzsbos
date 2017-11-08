package cn.itcast.md5;

import org.apache.shiro.crypto.hash.Md5Hash;

public class PasswordMD5 {

	public static String getMd5Password(String password){
//		int hashIterations; 加密的次数（加料）
//		Object salt;         料
//		String source;       要加密的字符串
		Md5Hash md =new Md5Hash(password, "lalalademaxiya", 10);
		
		return md.toString();
	}
}
