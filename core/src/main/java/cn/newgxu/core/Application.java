/*
 * The MIT License (MIT)
 * Copyright (c) 2013 longkai
 * The software shall be used for good, not evil.
 */
package cn.newgxu.core;

import cn.newgxu.lab.util.Resources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.EnumSet;
import java.util.Properties;

/**
 * web应用启动器，no web.xml，但是由于mybatis的原因，也不是完全的java-config，被注释的地方是使用100%的java-config。
 * <b>but, very very slow, and, some errors are swallowed.</b>
 *
 * @author longkai
 * @date 2013-12-10
 */
public class Application implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		XmlWebApplicationContext appContext = new XmlWebApplicationContext();
		appContext.setConfigLocation("classpath:spring.xml");

		// html 转义
		servletContext.addListener(new ContextLoaderListener(appContext));
		servletContext.setInitParameter("defaultHtmlEscape", "true");

		// utf8强制转码
		FilterRegistration.Dynamic addFilter = servletContext
				.addFilter("encodingFilter", new CharacterEncodingFilter());
		addFilter.setInitParameter("encoding", "utf-8");
		addFilter.setInitParameter("forceEncoding", "true");
		addFilter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");

		// springmvc servlet
		ServletRegistration.Dynamic dispatcher =
				servletContext.addServlet("springmvc", new DispatcherServlet(appContext));
		dispatcher.setLoadOnStartup(1);
		dispatcher.addMapping("/");

		// servlet file upload
//		things below went wrong, so use the method
//		dispatcher.setMultipartConfig(appContext.getBean(MultipartConfigElement.class));
		dispatcher.setMultipartConfig(configElement());
	}

	/**
	 * 临时凑的方法，上面的方法跪了。
	 */
	public static MultipartConfigElement configElement() {
		Properties properties = new Properties();
		InputStream inputStream = null;
		try {
			inputStream = Resources.getInputStream("classpath:multipart.properties");
			properties.load(inputStream);
		} catch (IOException e) {
			l.error("init multipart config error!", e);
		} finally {
			Resources.close(null, inputStream);
		}
		return new MultipartConfigElement(
				properties.getProperty("file_upload.location"),
				Long.parseLong(properties.getProperty("file_upload.max_file_size")),
				Long.parseLong(properties.getProperty("file_upload.max_request_size")),
				Integer.parseInt(properties.getProperty("file_upload.file_size_threshold"))
		);
	}

	private static Logger l = LoggerFactory.getLogger(Application.class);

//	@Override
//	public void onStartup(ServletContext servletContext) throws ServletException {
//		// Create the root appcontext
//		AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
//		rootContext.register(SpringBeans.class);
//
//		// if you're not passing in the config classes to the constructor,
//		// refresh
//		rootContext.refresh();
//
//		// Manage the lifecycle of the root appcontext
//		servletContext.addListener(new ContextLoaderListener(rootContext));
//		servletContext.setInitParameter("defaultHtmlEscape", "true");
//
//		// now the config for the Dispatcher servlet
//		AnnotationConfigWebApplicationContext mvcContext = new AnnotationConfigWebApplicationContext();
//		mvcContext.register(WebMvcConfig.class);
//
//		FilterRegistration.Dynamic addFilter = servletContext
//			.addFilter("encodingFilter", new CharacterEncodingFilter());
//		addFilter.setInitParameter("encoding", "utf-8");
//		addFilter.setInitParameter("forceEncoding", "true");
//		addFilter.addMappingForUrlPatterns(null, true, "/*");
//
//		// The main Spring MVC servlet.
//		ServletRegistration.Dynamic appServlet = servletContext.addServlet(
//			"springmvc", new DispatcherServlet(mvcContext));
//		appServlet.setLoadOnStartup(1);
//		appServlet.addMapping("/");
//	}
}
