/*
 * The MIT License (MIT)
 * Copyright (c) 2013 longkai(龙凯)
 * The software shall be used for good, not evil.
 */
package cn.newgxu.lab.core.support;

import cn.newgxu.lab.core.config.SpringBeans;
import cn.newgxu.lab.util.Resources;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.io.InputStream;

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
		InputStream in;
		try {
			in = Resources.getInputStream("classpath:file-upload.json");
			params = SpringBeans.OBJECT_MAPPER.readTree(in);
		} catch (IOException e) {
			throw new RuntimeException("cannot initialize file upload params.", e);
		}
	}

	/** 上传文件的存放地点（文件系统的绝对路径） */
	public static final String FILE_SYSTEM_ROOT_PATH = params.get("file_system_root_path").asText();

	/** web url中上传文件资源的根路径 */
	public static final String WEB_ROOT_PATH = params.get("web_root_path").asText();

	/** fine-uploader的用户指定文件名的查询参数名 */
	public static final String FINE_UPLOADER_FILE_NAME = params.get("fine_uploader_file_name").asText();
}
