package com.kubrick.sbt.admin.web;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @author k
 * @version 1.0.0
 * @ClassName AdminMonitorApplication
 * @description: TODO
 * @date 2020/12/24 下午8:42
 */
@SpringBootApplication
@EnableAdminServer
public class AdminMonitorApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(AdminMonitorApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(AdminMonitorApplication.class, args);
	}

}
