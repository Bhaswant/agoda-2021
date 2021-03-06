package com.agoda.interview.analyticsreporter;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Configuration class, that initializes the required beans
 * @author Bhaswant
 *
 */
@Configuration
@EnableSwagger2
@EnableAutoConfiguration
@EnableJpaRepositories("com.agoda.interview.analyticsreporter.repository")
@ComponentScan({ "com.agoda.interview.analyticsreporter.controller", "com.agoda.interview.analyticsreporter.helper",
		"com.agoda.interview.analyticsreporter.service.impl" })
public class AnalyticsReporterConfiguration {
 
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any()).build();
	}
}
