package com.spring.jdbctype2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.spring.jdbctype2.Dao.StudentDao;
import com.spring.jdbctype2.Dao.StudentImpel;

@Configuration
@ComponentScan(basePackages = {"com.spring.jdbctype2.Dao"})
public class JdbcConfig {
	@Bean("ds")
	public DriverManagerDataSource getDataSource() {
		DriverManagerDataSource dataSource=new DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/springjdbc");
		dataSource.setUsername("root");
		dataSource.setPassword("root");
		return dataSource;
	}
	@Bean("jdbcTemplate")
	public JdbcTemplate getTemplate() {
		JdbcTemplate template=new JdbcTemplate();
		template.setDataSource(getDataSource());
		return template;
	}
	
	//below is the configuration type to use it remove @component from StudentImpel.class and also the @Component scan from JDBCCongig.class
	
//	@Bean("studdao")
//	public StudentDao getStudentDao() {
//		StudentImpel si=new StudentImpel();
//		si.setJdbctemplate(getTemplate());
//		return si;
// 	}
	
	
}
