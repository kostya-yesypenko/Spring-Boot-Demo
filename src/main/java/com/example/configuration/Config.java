package com.example.configuration;

import javax.sql.DataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
public class Config {

	@Bean
	DataSource dataSource() {
		DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
		dataSourceBuilder.username("root");
		dataSourceBuilder.password("root");
		dataSourceBuilder.url("jdbc:mysql://127.0.0.1:3306/store");
		dataSourceBuilder.driverClassName("com.mysql.cj.jdbc.Driver");
		return dataSourceBuilder.build();
	}
}