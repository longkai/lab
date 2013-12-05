/*
 * The MIT License (MIT)
 * Copyright (c) 2013 newgxu.cn <the original author or authors>.
 * The software shall be used for good, not evil.
 */
package cn.newgxu.lab.core.config;

import cn.newgxu.lab.core.member.MemberService;
import cn.newgxu.lab.core.member.MemberServiceImpl;
import cn.newgxu.lab.util.Resources;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;

/**
 * spring beans java config, no web beans.
 *
 * @User longkai
 * @Date 13-7-31
 * @Mail im.longkai@gmail.com
 */
@Configuration
@EnableTransactionManagement
@ComponentScan("cn.newgxu.lab.apps")
public class SpringBeans {

    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        OBJECT_MAPPER.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
    }

    @Bean(destroyMethod = "close")
    public DataSource dataSource() {
        final org.apache.tomcat.jdbc.pool.DataSource dataSource
                = new org.apache.tomcat.jdbc.pool.DataSource();
        Resources.openInputStream("classpath:db-config.json", new Resources.ResourcesCallback() {
            @Override
            protected void onSuccess(InputStream inputStream) throws IOException {
                JsonNode jsonNode = OBJECT_MAPPER.readTree(inputStream);
                dataSource.setUrl(jsonNode.get("url").asText());
                dataSource.setDriverClassName(jsonNode.get("driver").asText());
                dataSource.setUsername(jsonNode.get("username").asText());
                dataSource.setPassword(jsonNode.get("password").asText());
                dataSource.setDefaultAutoCommit(jsonNode.get("auto_commit").asBoolean(false));
	            dataSource.setValidationQuery(jsonNode.get("validation_query").asText());
            }

            @Override
            protected void onError(Throwable t) {
                throw new RuntimeException("datasource configuration error!", t);
            }
        });
        return dataSource;
    }

//	for some reason, we cannot use java config to config mybatis,
//	please refer to http://stackoverflow.com/questions/8999597
//	@Bean
//	public MapperScannerConfigurer mapperScannerConfigurer() {
//		MapperScannerConfigurer configurer = new MapperScannerConfigurer();
//		configurer.setBasePackage("cn.newgxu.lab.apps.notty.repository");
//		return configurer;
//	}

    @Bean
    public SqlSessionFactoryBean sqlSessionFactory() {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource());
        bean.setTypeAliases(new Class[]{cn.newgxu.lab.core.member.Member.class});
        bean.setTypeAliasesPackage("cn.newgxu.lab.apps");
        return bean;
    }

    @Bean(name = "transactionManager")
    public PlatformTransactionManager tx() {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean
    public MemberService memberService() {
//        MemberServiceImpl memberService = new MemberServiceImpl();
        return new MemberServiceImpl();
    }

    private static Logger l = LoggerFactory.getLogger(SpringBeans.class);
}
