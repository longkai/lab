/*
 * The MIT License (MIT)
 * Copyright (c) 2013 newgxu.cn <the original author or authors>.
 * The software shall be used for good, not evil.
 */
package cn.newgxu.core;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.converter.json.Jackson2ObjectMapperFactoryBean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.util.Arrays;

/**
 * spring beans java config, no web beans.
 *
 * @author im.longkai@gmail.com
 * @since 13-7-31
 */
@Configuration
@EnableTransactionManagement
@ComponentScan("cn.newgxu.lab")
@PropertySource("classpath:db.properties")
@EnableCaching
public class Spring {

	public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	static {
		// 允许注释（json是用来传输数据的，不推荐注释，命名尽量简洁明了，但是如有必要请添加注释！）
		OBJECT_MAPPER.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
	}

	@Inject
	private Environment env;

	@Bean
	public CacheManager cacheManager() {
		// configure and return an implementation of Spring's CacheManager SPI
		SimpleCacheManager cacheManager = new SimpleCacheManager();
		cacheManager.setCaches(Arrays.asList(new ConcurrentMapCache("default")));
		return cacheManager;
	}

	@Bean(destroyMethod = "close")
	public DataSource dataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setUrl(env.getRequiredProperty("jdbc.url"));
		dataSource.setDriverClassName(env.getRequiredProperty("jdbc.driver"));
		dataSource.setUsername(env.getRequiredProperty("jdbc.username"));
		dataSource.setPassword(env.getRequiredProperty("jdbc.password"));
		dataSource.setDefaultAutoCommit(env.getProperty("jdbc.auto_commit", Boolean.TYPE, false));
		return dataSource;
	}

//	for some reason, we cannot use java config to config mybatis,
//	please refer to http://stackoverflow.com/questions/8999597
//	@Bean
//	public MapperScannerConfigurer mapperScannerConfigurer() {
//		MapperScannerConfigurer configurer = new MapperScannerConfigurer();
//		configurer.setBasePackage("cn.newgxu.lab");
//		configurer.setAnnotationClass(org.springframework.stereotype.Repository.class);
//		return configurer;
//	}

	@Bean
	public SqlSessionFactory sqlSessionFactory() throws Exception {
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(dataSource());
		bean.setTypeAliasesPackage("cn.newgxu.lab");
		return bean.getObject();
	}

	@Bean
	public PlatformTransactionManager tx() {
		return new DataSourceTransactionManager(dataSource());
	}

	@Bean
	public ObjectMapper jackson2ObjectMapperFactoryBean() {
		Jackson2ObjectMapperFactoryBean bean = new Jackson2ObjectMapperFactoryBean();
		bean.setObjectMapper(OBJECT_MAPPER);
		return bean.getObject();
	}

}
