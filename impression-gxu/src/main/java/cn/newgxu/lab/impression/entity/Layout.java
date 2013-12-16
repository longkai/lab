/*
 * The MIT License (MIT)
 * Copyright (c) 2013 longkai(龙凯)
 * The software shall be used for good, not evil.
 */
package cn.newgxu.lab.impression.entity;

/**
 * predefined layouts.
 *
 * @User longkai
 * @Date 13-9-14
 * @Mail im.longkai@gmail.com
 */
public enum Layout {

	TEXT("文本"), IMAGE_WITH_TEXT("图文"), LIST_IMAGE_WITH_TEXT("图文列表"), GALLERY("相册"), PAGER_TEXT("分页文字"), VIDEO("视频"), AUDIO("音频");

	private String name;

	Layout(String name) {
		this.name = name;
	}
}
