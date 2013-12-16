/*
 * The MIT License (MIT)
 * Copyright (c) 2013 longkai
 * The software shall be used for good, not evil.
 */
package cn.newgxu.extras;

import org.springframework.http.MediaType;

import javax.servlet.http.Part;

/**
 * Created by longkai on 13-12-10.
 */
public class FileUpload {

	private static final String CONTENT_DISPOSITION = "content-disposition";

	private static final String FILENAME_KEY = "filename=";

	public static void check(long maxSize, String[] types, Part part) {
		if (part.getSize() > maxSize) {
			throw new IllegalArgumentException("fise");
		}
	}

	/**
	 * 获取part文件真实的名字，servlet3.1已经有现成的接口了，但是，servlet3.0现在是我们用的。
	 * <p/>
	 * 此方法copy自spring
	 *
	 * @param part part 文件
	 * @return 文件原来的名字
	 * @see org.springframework.web.multipart.support.StandardMultipartHttpServletRequest#extractFilename(String)
	 */
	public static String resolveFileName(Part part) {
		String contentDisposition = part.getHeader(CONTENT_DISPOSITION);
		if (contentDisposition == null) {
			return null;
		}
		int startIndex = contentDisposition.indexOf(FILENAME_KEY);
		if (startIndex == -1) {
			return null;
		}
		String filename = contentDisposition.substring(startIndex + FILENAME_KEY.length());
		if (filename.startsWith("\"")) {
			int endIndex = filename.indexOf("\"", 1);
			if (endIndex != -1) {
				return filename.substring(1, endIndex);
			}
		} else {
			int endIndex = filename.indexOf(";");
			if (endIndex != -1) {
				return filename.substring(0, endIndex);
			}
		}
		return filename;
	}

}
