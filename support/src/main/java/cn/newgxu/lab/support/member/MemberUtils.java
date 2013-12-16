/*
 * The MIT License (MIT)
 * Copyright (c) 2013 longkai(龙凯)
 * The software shall be used for good, not evil.
 */
package cn.newgxu.lab.support.member;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * Created with IntelliJ IDEA.
 *
 * @User longkai
 * @Date 13-8-19
 * @Mail im.longkai@gmail.com
 */
public class MemberUtils {

	public static final String MEMEBER = "member";
	public static final String MEMEBERS = "members";
	public static final String SESSEION_MEMEBER = "session_member";


	public static JsonNode lookat(int from, String account, String password) {
		switch (from) {
			case 0:
//				String uri = "http://210.36.16.166/api/users/login?username=" + account + "&password=" + password;
//				try {
//					InputStream inputStream = Resources.getInputStream(uri);
//					JsonNode node = SpringBeans.OBJECT_MAPPER.readTree(inputStream);
//					inputStream.close();
//					return node;
//				} catch (IOException e) {
//					throw new RuntimeException("an ERROR occur when connecting to the bbs server! please contact yws!", e);
//				}
			default:
				throw new IllegalArgumentException("not exist option[from = " + from + " ]");
		}
	}
}
