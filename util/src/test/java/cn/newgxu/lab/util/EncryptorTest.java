/*
 * The MIT License (MIT)
 * Copyright (c) 2013 newgxu.cn <the original author or authors>.
 * The software shall be used for good, not evil.
 */
package cn.newgxu.lab.util;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created with IntelliJ IDEA.
 *
 * @User longkai
 * @Date 13-8-10
 * @Mail im.longkai@gmail.com
 */
public class EncryptorTest {

	@Test
	public void testMD5() throws Exception {
		String str1 = "123456";
		String str2 = "654321";

		assertThat(Encryptor.MD5(str1), is("e10adc3949ba59abbe56e057f20f883e"));
		assertThat(Encryptor.MD5(str2), is("c33367701511b4f6020ec61ded352059"));
	}
}
