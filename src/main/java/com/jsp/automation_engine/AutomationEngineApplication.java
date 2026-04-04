package com.jsp.automation_engine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.jsp.automation_engine",
		excludeName = "org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration"   )
public class AutomationEngineApplication {

	public static void main(String[] args) {
		SpringApplication.run(AutomationEngineApplication.class, args);
	}

}
