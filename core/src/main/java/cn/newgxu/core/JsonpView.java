/*
 * The MIT License (MIT)
 * Copyright (c) 2013 newgxu.cn <the original author or authors>.
 * The software shall be used for good, not evil.
 */
package cn.newgxu.core;

import cn.newgxu.lab.util.TextUtils;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 将视图映射成为jsonp的数据类型，继承了MappingJackson2JsonView。
 * <p/>
 * 返回jsonp判断依据
 * <table>
 * <tr>1：请求的后缀是jsonp，或者请求的头部是application/javascript（配置在springbean中，不推荐修改）</tr>
 * <tr>2：必须是GET请求</tr>
 * <tr>3：必须包含callback的查询参数</tr>
 * </table>
 * 否则转换为json的视图
 *
 * @User longkai
 * @Date 13-4-13
 * @Mail im.longkai@gmail.com
 */
public class JsonpView extends MappingJackson2JsonView {

	public static final String DEFAULT_CONTENT_TYPE = "application/javascript";
	public static final String CALLBACK = "callback";

	public JsonpView() {
//		这里，我们只需要在构造函数设置内容类型即可，字符编码已经在spirng的encoding filter中强制转码了
		setContentType(DEFAULT_CONTENT_TYPE);
	}

	@Override
	public String getContentType() {
//		这里，我们必须重载这个方法，否则，spring将不会找到application/javascript的试图解析！
		return DEFAULT_CONTENT_TYPE;
	}

	@Override
	public void render(Map<String, ?> model, HttpServletRequest request,
					   HttpServletResponse response) throws Exception {
		if (request.getMethod().equalsIgnoreCase("GET")) {
			if (request.getParameterMap().containsKey(CALLBACK)) {
				ServletOutputStream ostream = response.getOutputStream();
//				这里，出于安全性考虑，用try包围了异常
				ostream.write(TextUtils.concat("try{", request.getParameter(CALLBACK), "(").getBytes());
				super.render(model, request, response);
				ostream.write(JS_CATCH_BLOCK);
//				这里，其实我也不清楚要不要close和flush，spring会替我们关掉吗？
//				自己打开的资源应该还是自己关闭的好。
				ostream.flush();
				ostream.close();
			} else {
				super.render(model, request, response);
			}
		} else {
			super.render(model, request, response);
		}
	}

	private static byte[] JS_CATCH_BLOCK = ");}catch(e){}".getBytes();
}
