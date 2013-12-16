/*
 * The MIT License (MIT)
 * Copyright (c) 2013 longkai(龙凯)
 * The software shall be used for good, not evil.
 */
package cn.newgxu.lab.impression.util;

import cn.newgxu.lab.impression.entity.Article;
import cn.newgxu.lab.core.support.UploadPath;
import cn.newgxu.lab.util.FileTransfer;
import cn.newgxu.lab.util.TextUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Calendar;
import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 *
 * @User longkai
 * @Date 13-9-15
 * @Mail im.longkai@gmail.com
 */
public class LayoutProcessor {

	public static final long MAX_IMAGE_SIZE = 2L * 1024 * 1024; // 2M
	public static final String[] IMAGE_TYPES = {"png", "jpg", "jpeg"};

	public static final String TITLE_INDICATOR = "##";
	public static final String PAGER_INDICATOR = ">>";

	private static final int getMonth() {
		Calendar now = Calendar.getInstance();
		return now.get(Calendar.MONTH) + 1;
	}

	public static final void process(Article article, MultipartFile file) throws IOException {
		StringBuilder sb;
		switch (article.getLayout()) {
			case TEXT:
				article.setContent(TextUtils.concat("{\"text\":\"", article.getContent(), "\"}"));
				break;
			case IMAGE_WITH_TEXT:
//				process image (jpg, jpeg, png)
				String imageUri = FileTransfer.fileTransfer(file.getBytes(), UploadPath.FILE_SYSTEM_UPLOAD_PATH, "/articles/" + getMonth() + "/",
						MAX_IMAGE_SIZE, IMAGE_TYPES, file.getOriginalFilename(), false);
				article.setContent(TextUtils.concat("{\"image\":\"", imageUri, "\",\"text\":\"", article.getContent(), "\"}"));
				break;
			case PAGER_TEXT:
				sb = new StringBuilder("[");
				Scanner in = new Scanner(article.getContent()).useDelimiter(PAGER_INDICATOR);
				while (in.hasNext()) {
					String pager = in.next();
					int titleIndex = pager.indexOf(TITLE_INDICATOR);
					if (titleIndex < 0) {
						throw new RuntimeException("your article has no title \"##\"!");
					}
					int contentIndex = pager.indexOf('\n');
					if (contentIndex < 0) {
						throw new RuntimeException("your article has no content!");
					}
					sb.append("{\"title\":\"").append(pager.substring(titleIndex, contentIndex).trim())
							.append("\",\"content\":\"").append(pager.substring(contentIndex).trim())
						.append("\"},"); // append ','
				}
				sb.replace(sb.length() - 1, sb.length(), "]");
				in.close();
				article.setContent(sb.toString());
				break;
//			case LIST_IMAGE_WITH_TEXT:
//				sb = new StringBuilder("[");
//				ZipInputStream zis = new ZipInputStream(file.getInputStream());
//				ZipEntry entry = zis.getNextEntry();
//				while (entry != null) {
//					entry = zis.getNextEntry();
//					if (entry.isDirectory()) {
//						throw new RuntimeException("the zip file cannot have folder -> " + entry.getName());
//					} else {
//						String ext = TextUtils.getFileExt(entry.getName());
//						Assert.notNull("file extension cannot be empty but your file is " + entry.getName(), ext);
//						boolean allow = false;
//						for (int i = 0; i < IMAGE_TYPES.length; i++) {
//							if (IMAGE_TYPES[i].equalsIgnoreCase(ext)) {
//								allow = true;
//								break;
//							}
//						}
//						if (!allow) {
//							throw new RuntimeException("sorry, your file '" + entry.getName()
//									+ "' type is not allowed! the allowed type is " + Arrays.toString(IMAGE_TYPES));
//						} else {
//							sb.append("{\"text\":\"").append()
//						}
//					}
//				}
			default:
				break;
		}
	}
}
