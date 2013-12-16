/*
 * The MIT License (MIT)
 * Copyright (c) 2013 longkai(龙凯)
 * The software shall be used for good, not evil.
 */
package cn.newgxu.extras;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * 应用的文件路径的配置参数。
 *
 * @User longkai
 * @Date 13-8-16
 * @Mail im.longkai@gmail.com
 */
public class UploadPath {

	private static JsonNode params;

	static {
//		InputStream in;
//		try {
//			in = Resources.getInputStream("classpath:multipart.properties");
//			params = SpringBeans.OBJECT_MAPPER.readTree(in);
//		} catch (IOException e) {
//			throw new RuntimeException("cannot initialize file upload params.", e);
//		}
	}

	/** 应用在部署到文件系统上的绝对路径 */
	public static final String FILE_SYSTEM_ROOT_PATH = params.get("file_system_root_path").asText();

	/** 上传文件相对于 file_system_root_path 的存放路径，也是下载路径的根路径 */
	public static final String UPLOAD_BASE_PATH = params.get("upload_base_path").asText();

	/** 文件上传的绝对路径 */
	public static final String FILE_SYSTEM_UPLOAD_PATH = FILE_SYSTEM_ROOT_PATH + UPLOAD_BASE_PATH;

	/** fine-uploader的用户指定文件名的查询参数名 */
	public static final String FINE_UPLOADER_FILE_NAME = params.get("fine_uploader_file_name").asText();
}
