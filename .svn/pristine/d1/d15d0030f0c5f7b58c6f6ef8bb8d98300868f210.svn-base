package com.dayside.dbrs.config;

import java.sql.SQLException;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Database, Mybatis config
 */
@Configuration
@MapperScan(basePackages="com.dayside.*.**.*.mapper", sqlSessionFactoryRef="sqlSessionFactory")
@EnableTransactionManagement(proxyTargetClass = true)
public class ContextSqlMapper {
	
	private final static Logger logger = LoggerFactory.getLogger(ContextSqlMapper.class);
	
	
	@Bean(name="dataSource")
	@Primary
	public DataSource dataSource() {
		JndiDataSourceLookup jndiDataSourceLookup = new JndiDataSourceLookup();
		jndiDataSourceLookup.setResourceRef(true);
		return jndiDataSourceLookup.getDataSource("jdbc/smcpDev");
	}
	

	@Resource(name="dataSource")
	private DataSource dataSource;
		

	@Bean(name="transactionManager")
	@Primary
	public DataSourceTransactionManager transactionManager() throws SQLException {
		return new DataSourceTransactionManager(dataSource);
	}
	
	@Bean(name="sqlSessionFactory")
	@Primary
    public SqlSessionFactory sqlSessionFactory() throws Exception {
		
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:/com/dayside/*/**/mapper/*Mapper.xml"));
        
        String springProfilesActive = System.getProperty("spring.profiles.active", "local");  
        sessionFactory.setConfigLocation(new PathMatchingResourcePatternResolver().getResource("classpath:/config/mybatis/mybatis-" + springProfilesActive + "-config.xml"));
        
        sessionFactory.getObject()
        			  .getConfiguration()
        			  .setMapUnderscoreToCamelCase(true);
        
        return sessionFactory.getObject();
    }
	
	
	
	@Bean(destroyMethod="clearCache",name="sqlSessionTemplate")
	@Primary
	public SqlSessionTemplate sqlSessionTemplate() throws Exception {
		return new SqlSessionTemplate(sqlSessionFactory(), ExecutorType.REUSE);
	}	
	
}
