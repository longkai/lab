/*
 * The MIT License (MIT)
 * Copyright (c) 2013 newgxu.cn <the original author or authors>.
 * The software shall be used for good, not evil.
 */
package cn.newgxu.lab.core.config;

import cn.newgxu.lab.util.Resources;
import cn.newgxu.lab.util.TextUtils;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.accept.HeaderContentNegotiationStrategy;
import org.springframework.web.accept.PathExtensionContentNegotiationStrategy;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import static cn.newgxu.lab.util.Resources.ResourcesCallback;
import static cn.newgxu.lab.util.Resources.openInputStream;

/**
 * web相关的配置，这里导入了spring原有的基础配置（非web容器必须的bean）
 *
 * @User longkai
 * @Date 13-8-1
 * @Mail im.longkai@gmail.com
 */
@Configuration
@EnableWebMvc
@Import(SpringBeans.class)
public class WebMvcConfig extends WebMvcConfigurerAdapter {

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
                JsonNode node = SpringBeans.OBJECT_MAPPER.readTree(inputStream);
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
        viewResolver.setContentType("text/html;charset=utf-8");
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
        openInputStream("classpath:freemarker.json", new ResourcesCallback() {
            @Override
            protected void onSuccess(InputStream inputStream) throws IOException {
                JsonNode node = SpringBeans.OBJECT_MAPPER.readTree(inputStream);
                Iterator<String> it = node.fieldNames();
                Properties props = new Properties();
                String key;
                while (it.hasNext()) {
                    key = it.next();
                    props.put(key, node.get(key).asText());
                }
                configurer.setFreemarkerSettings(props);
            }

            @Override
            protected void onError(Throwable t) {
                throw new RuntimeException("Freemarker configuration error!", t);
            }
        });
        return configurer;
    }

    //    文件上传 commons-fileupload
    @Bean
    public MultipartResolver multipartResolver() {
        return new CommonsMultipartResolver();
    }
//
//    // 文件上传 servlet 3.0+
//    @Bean
//    public StandardServletMultipartResolver servletMultipartResolver() {
//        return new StandardServletMultipartResolver();
//    }

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
        jsonView.setObjectMapper(SpringBeans.OBJECT_MAPPER);
        JsonpView jsonpView = new JsonpView();
        jsonpView.setObjectMapper(SpringBeans.OBJECT_MAPPER);

        defaultViews.add(jsonView);
        defaultViews.add(jsonpView);
        viewResolver.setDefaultViews(defaultViews);
        return viewResolver;
    }

}
