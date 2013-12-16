/*
 * The MIT License (MIT)
 * Copyright (c) 2013 newgxu.cn <the original author or authors>.
 * The software shall be used for good, not evil.
 */
package cn.newgxu.lab.util;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 *
 * @User longkai
 * @Date 13-8-2
 * @Mail im.longkai@gmail.com
 */
public class ResourcesTest {

	@Test
	public void testOpenBufferedReader() throws Exception {
		Resources.openBufferedReader("http://lab.newgxu.cn", new Resources.ResourcesCallback() {
			@Override
			protected void onSuccess(BufferedReader br) throws IOException {
				String str = null;
				StringBuffer sb = new StringBuffer();
				while ((str = br.readLine()) != null) {
					sb.append(str);
				}
				System.out.println(sb.toString());
			}

			@Override
			protected void onError(Throwable t) {
				l.log(Level.SEVERE, "cannot connect net!", t);
			}
		});
	}

	@Test
	public void testOpenBufferedWriter() throws Exception {
		final String uri = "D:/test.txt";
		Resources.openBufferedWriter(uri, true, new Resources.ResourcesCallback() {
			@Override
			protected void onSuccess(BufferedWriter br) throws IOException {
				br.write(uri);
				br.flush();
				InputStream in = Resources.getInputStream(uri);
				org.junit.Assert.assertNotNull(in);
				in.close();
			}

			@Override
			protected void onError(Throwable t) {
				l.log(Level.SEVERE, "I/O error!", t);
			}
		});
	}

	@Test
	public void testOpenInputStream() throws Exception {
//      注意，在ide中测试和在gradle里测试路径是有区别的，要根据当前的project来区别classpath！
//      String s = Resources.getResourceAsString("settings.gradle");
		String s = null;
		try {
			s = Resources.getResourceAsString("http://www.baidu.com");
		} catch (Exception e) {
			l.log(Level.SEVERE, "I/O error!", e);
			return;
		}
		org.junit.Assert.assertNotNull(s);
		System.out.println(s);
	}

	private static Logger l = Logger.getLogger(ResourcesTest.class.getName());
}
