package com.agoda.interview.analyticsreporter;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({ "com.agoda.interview.analyticsreporter.controller", "com.agoda.interview.analyticsreporter.helper",
		"com.agoda.interview.analyticsreporter.service.impl", "com.agoda.interview.analyticsreporter.repository" })
public class AnalyticsReporterConfiguration {

}
