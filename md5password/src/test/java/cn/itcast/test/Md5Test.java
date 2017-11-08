package cn.itcast.test;

import cn.itcast.md5.PasswordMD5;

public class Md5Test {

	public static void main(String[] args) {
		String password = PasswordMD5.getMd5Password("123456");
		System.out.println(password);
	}
}
