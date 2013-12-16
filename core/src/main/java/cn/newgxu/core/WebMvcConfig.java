/*
 * The MIT License (MIT)
 * Copyright (c) 2013 newgxu.cn <the original author or authors>.
 * The software shall be used for good, not evil.
 */
package cn.newgxu.core;

import cn.newgxu.lab.util.Resources;
import cn.newgxu.lab.util.TextUtils;
import cn.newgxu.lab.util.ViewConstants;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.accept.HeaderContentNegotiationStrategy;
import org.springframework.web.accept.PathExtensionContentNegotiationStrategy;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.inject.Inject;
import javax.servlet.MultipartConfigElement;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import static cn.newgxu.lab.util.Resources.ResourcesCallback;

/**
 * web相关的配置，这里导入了spring原有的基础配置（非web容器必须的bean）
 *
 * @author im.longkai@gmail.com
 * @since 13-8-1
 */
@Configuration
@EnableWebMvc
@Import(Spring.class)
@PropertySource("classpath:multipart.properties")
public class WebMvcConfig extends WebMvcConfigurerAdapter {

	@Inject
	private Environment env;

	@Inject
	private ObjectMapper mapper;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		int cachePeriod = 3600 * 24 * 15;
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/").setCachePeriod(cachePeriod);
		registry.addResourceHandler("/pages/**").addResourceLocations("/resources/").setCachePeriod(cachePeriod);
		registry.addResourceHandler("/r/**").addResourceLocations("/resources/").setCachePeriod(cachePeriod);
		registry.addResourceHandler("/favicon.ico").addResourceLocations("/").setCachePeriod(cachePeriod);
		registry.addResourceHandler("/robots.txt").addResourceLocations("/").setCachePeriod(cachePeriod);
	}

	@Override
	public void addViewControllers(final ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("index");
		registry.addViewController("/index").setViewName("index");
		registry.addViewController("/home").setViewName("index");

		Resources.openInputStream("classpath:uri.json", new ResourcesCallback() {
			@Override
			protected void onSuccess(InputStream inputStream) throws IOException {
				JsonNode node = mapper.readTree(inputStream);
				Iterator<String> it = node.fieldNames();
				String key;
				while (it.hasNext()) {
					key = it.next();
					String name = node.get(key).asText();
					if (!TextUtils.isEmpty(name, true)) {
						registry.addViewController(key).setViewName(name);
					}
				}
			}

			@Override
			protected void onError(Throwable t) {
				// 由于我们可能是单独测试某个项目的web层，而不是部署到整个项目中去
				l.error("打开自定义uri失败！请检查是文件你的项目（子项目）classpath下包含uri.json文件");
			}
		});
	}

	@Override
	public void configureContentNegotiation(
			ContentNegotiationConfigurer configurer) {
		configurer.favorPathExtension(true).favorParameter(false).ignoreAcceptHeader(false);
	}

	@Bean
	public FreeMarkerViewResolver freeMarkerViewResolver() {
		FreeMarkerViewResolver viewResolver = new FreeMarkerViewResolver();
		viewResolver.setCache(true);
		viewResolver.setPrefix("");
		viewResolver.setSuffix(".ftl");
		viewResolver.setContentType(ViewConstants.MEDIA_TYPE_HTML);
		viewResolver.setRequestContextAttribute("request");
		viewResolver.setExposeSpringMacroHelpers(true);
		viewResolver.setExposeRequestAttributes(true);
		viewResolver.setExposeSessionAttributes(true);
		viewResolver.setOrder(2);
		return viewResolver;
	}

	@Bean
	public InternalResourceViewResolver jspviViewResolver() {
		InternalResourceViewResolver jsp = new InternalResourceViewResolver();
		jsp.setOrder(4);
		jsp.setCache(true);
		jsp.setViewClass(org.springframework.web.servlet.view.JstlView.class);
		jsp.setPrefix("/WEB-INF/jsp/");
		jsp.setSuffix(".jsp");
		return jsp;
	}

	@Bean
	public FreeMarkerConfig freeMarkerConfig() {
		final FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
		configurer.setTemplateLoaderPath("/WEB-INF/freemarker/");
		Resources.openInputStream("classpath:freemarker.properties", new ResourcesCallback() {
			@Override
			protected void onSuccess(InputStream inputStream) throws IOException {
				Properties props = new Properties();
				props.load(inputStream);
				configurer.setFreemarkerSettings(props);
			}
		});
		return configurer;
	}

	/**
	 * servlet-api:3.0+ 文件上传spring @RequestPart helper，实际上直接使用HttpServletRequest很直接
	 */
	@Bean
	public StandardServletMultipartResolver servletMultipartResolver() {
		return new StandardServletMultipartResolver();
	}

	/**
	 * servlet-api:3.0+ 文件上传参数配置
	 */
	@Bean
	public MultipartConfigElement multipartConfigElement() {
		return new MultipartConfigElement(
				env.getRequiredProperty("file_upload.location"),
				env.getRequiredProperty("file_upload.max_file_size", Long.class),
				env.getProperty("file_upload.max_request_size", Long.class),
				env.getProperty("file_upload.file_size_threshold", Integer.class));
	}

	@Bean
	public ContentNegotiationManager contentNegotiationManager() {
		Map<String, MediaType> mediaTypes = new HashMap<String, MediaType>(2);
		mediaTypes.put("json", MediaType.APPLICATION_JSON);
		mediaTypes.put("jsonp", MediaType.parseMediaType("application/javascript"));
		PathExtensionContentNegotiationStrategy extension = new PathExtensionContentNegotiationStrategy(mediaTypes);
		HeaderContentNegotiationStrategy header = new HeaderContentNegotiationStrategy();
		ContentNegotiationManager contentNegotiationManager;
		contentNegotiationManager = new ContentNegotiationManager(extension, header);
		return contentNegotiationManager;
	}

	@Bean
	public ContentNegotiatingViewResolver contentNegotiatingViewResolver() {
		ContentNegotiatingViewResolver viewResolver = new ContentNegotiatingViewResolver();
		viewResolver.setOrder(1);
		viewResolver.setContentNegotiationManager(contentNegotiationManager());

		List<View> defaultViews = new ArrayList<View>(2);
		MappingJackson2JsonView jsonView = new MappingJackson2JsonView();
		jsonView.setObjectMapper(mapper);
		JsonpView jsonpView = new JsonpView();
		jsonpView.setObjectMapper(mapper);

		defaultViews.add(jsonView);
		defaultViews.add(jsonpView);
		viewResolver.setDefaultViews(defaultViews);
		return viewResolver;
	}

	@Bean
	public GlobalExceptionHandler ex() {
		return new GlobalExceptionHandler();
	}

	private static Logger l = LoggerFactory.getLogger(WebMvcConfig.class);

}
