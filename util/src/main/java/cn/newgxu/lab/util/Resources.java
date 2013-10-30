/*
 * The MIT License (MIT)
 * Copyright (c) 2013 newgxu.cn <the original author or authors>.
 * The software shall be used for good, not evil.
 */
package cn.newgxu.lab.util;

import java.io.*;
import java.net.URL;
import java.util.Locale;
import java.util.Properties;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * 读写资源，包括本地和基于tcp/ip协议的应用（只限于读数据），采用回调函数实现。
 *
 * @User longkai
 * @Date 13-7-30
 * @Mail im.longkai@gmail.com
 */
public class Resources {

	public static final String DEFAULT_CHARSET = "UTF-8";
	public static final Pattern TCP_IP_PROTOCOL = Pattern.compile("^\\w[\\w|:]*\\w://");

	public static final String getResourceAsString(String uri) {
		InputStream inputStream = null;
		try {
			inputStream = getInputStream(uri);
			Scanner in = new Scanner(inputStream).useDelimiter("\\A");
			return in.hasNext() ? in.next() : null;
		} catch (IOException e) {
			throw new RuntimeException("get resource as string error!", e);
		} finally {
			close(null, inputStream);
		}

	}

	public static final void loadProps(String uri, Locale locale, ResourcesCallback callback) {
		callback.onStart();
		InputStream in = null;
		try {
			in = getInputStream(uri, locale);
			Properties props = new Properties();
			props.load(in);
			callback.onSuccess(props);
		} catch (IOException e) {
			callback.onError(e);
		} finally {
			close(callback, in);
		}
		callback.onFinish();
	}

	public static final void openBufferedReader(String uri, ResourcesCallback callback) {
		openBufferedReader(uri, null, null, callback);
	}

	public static final void openBufferedReader(String uri, Locale locale, String charset, ResourcesCallback callback) {
		callback.onStart();
		charset = TextUtils.isEmpty(charset) ? DEFAULT_CHARSET : charset;
		InputStream inputStream = null;
		InputStreamReader inputStreamReader = null;
		BufferedReader bufferedReader = null;
		try {
			inputStream = getInputStream(uri, locale);
			inputStreamReader = new InputStreamReader(inputStream, charset);
			bufferedReader = new BufferedReader(inputStreamReader);
			callback.onSuccess(bufferedReader);
		} catch (Exception e) {
			callback.onError(e);
		} finally {
			close(callback, bufferedReader, inputStreamReader, inputStream);
		}
		callback.onFinish();
	}

	public static final void openBufferedWriter(String uri, boolean append, ResourcesCallback callback) {
		openBufferedWriter(uri, append, null, callback);
	}

	public static final void openBufferedWriter(String uri, boolean append, String charset, ResourcesCallback callback) {
		callback.onStart();
		charset = TextUtils.isEmpty(charset) ? DEFAULT_CHARSET : charset;
		OutputStream outputStream = null;
		OutputStreamWriter outputStreamWriter = null;
		BufferedWriter br = null;
		try {
			outputStream = new FileOutputStream(uri, append);
			outputStreamWriter = new OutputStreamWriter(outputStream, charset);
			br = new BufferedWriter(outputStreamWriter);
			callback.onSuccess(br);
		} catch (Exception e) {
			callback.onError(e);
		} finally {
			close(callback, br, outputStreamWriter, outputStream);
		}
		callback.onFinish();
	}

	public static final void openInputStream(String uri, ResourcesCallback callback) {
		openInputStream(uri, null, callback);
	}

	public static final void openInputStream(String uri, Locale locale, ResourcesCallback callback) {
		callback.onStart();
		InputStream inputStream = null;
		try {
			inputStream = getInputStream(uri, locale);
			callback.onSuccess(inputStream);
		} catch (Exception e) {
			callback.onError(e);
		} finally {
			close(callback, inputStream);
		}
		callback.onFinish();
	}

	public static final InputStream getInputStream(String uri) throws IOException {
		return getInputStream(uri, null);
	}

	public static final InputStream getInputStream(String uri, Locale locale) throws IOException {
		InputStream inputStream;
		if (tcpIp(uri)) {
			URL url = new URL(uri);
			inputStream = url.openStream();
		} else {
			uri = i18nUriName(uri, locale);
			if (fromClassPath(uri)) {
				uri = classpathUri(uri);
				inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(uri);
			} else {
				inputStream = new FileInputStream(uri);
			}
		}
		return inputStream;
	}

	public static final void openOutputStream(String uri, boolean append, ResourcesCallback callback) {
		callback.onStart();
		OutputStream os = null;
		try {
			os = new FileOutputStream(uri, append);
			callback.onSuccess(os);
		} catch (Exception e) {
			callback.onError(e);
		} finally {
			close(callback, os);
		}
		callback.onFinish();
	}

	public static final void openInputStreamReader(String uri, ResourcesCallback callback) {
		openInputStreamReader(uri, null, null, callback);
	}

	public static final void openInputStreamReader(String uri, Locale locale, String charset, ResourcesCallback callback) {
		callback.onStart();
		charset = TextUtils.isEmpty(charset) ? DEFAULT_CHARSET : charset;
		InputStream inputStream = null;
		InputStreamReader reader = null;
		try {
			inputStream = getInputStream(uri, locale);
			reader = new InputStreamReader(inputStream, charset);
			callback.onSuccess(reader);
		} catch (Exception e) {
			callback.onError(e);
		} finally {
			close(callback, reader, inputStream);
		}
		callback.onFinish();
	}

	public static final boolean tcpIp(String uri) {
		return TCP_IP_PROTOCOL.matcher(uri).find();
	}

	public static final boolean fromClassPath(String uri) {
		return uri.startsWith("classpath:");
	}

	public static final String classpathUri(String uri) {
//      classpath:/path/to/somewhere -> path/to/somewhere
		return uri.charAt(10) == '/' ? uri.substring(11) : uri.substring(10);
	}

	/**
	 * 将uri转换为国际化的uri。比如 hello.txt 的加拿大版uri就是 hello_en_CA.txt，中文版就是 hello_zh_CN.txt
	 *
	 * @param uri    原始uri
	 * @param locale 地方，区域，国家等
	 * @return 国际化后的uri，若locale为空，返回原始uri
	 */
	public static final String i18nUriName(String uri, Locale locale) {
		if (locale != null) {
			int indexOfDot = uri.lastIndexOf(".");
			String local = locale.getLanguage() + "_" + locale.getCountry();
			if (indexOfDot != -1) {
				uri = uri.substring(0, indexOfDot) + "_" + local + uri.substring(indexOfDot);
			} else {
				uri = uri + "_" + local;
			}
		}
		return uri;
	}

	public static final void close(ResourcesCallback callback, Closeable... ios) {
		for (Closeable io : ios) {
			if (io != null) {
				try {
					io.close();
				} catch (IOException e) {
					if (callback != null) {
						callback.onCloseFail(e);
					} else {
						throw new RuntimeException("fail to close I/O", e);
					}
				}
			}
		}
	}

	public static void main(String[] args) {
		System.out.println(i18nUriName("hello", Locale.getDefault()));
	}

	/**
	 * 取资源的工具类，采用回调函数的方式，使用者只需要重载自己想要的回调函数，系统会自动释放打开的资源。
	 * 当然，自己在关掉也可以。异常处理也不需要自己手动处理，想要处理的话可以重载当出现异常时的方法。
	 */
	public static abstract class ResourcesCallback {

		protected void onStart() {
		}

		protected void onSuccess(Properties props) {
		}

		protected void onSuccess(InputStream inputStream) throws IOException {
		}

		protected void onSuccess(OutputStream os) throws IOException {
		}

		protected void onSuccess(BufferedReader br) throws IOException {
		}

		protected void onSuccess(BufferedWriter br) throws IOException {
		}

		protected void onSuccess(InputStreamReader reader) throws IOException {
		}

		protected void onError(Throwable t) {
			throw new RuntimeException("ERROR when open I/O", t);
		}

		protected void onCloseFail(Throwable t) {
			throw new RuntimeException("ERROR when close I/O", t);
		}

		protected void onFinish() {
		}
	}
}
