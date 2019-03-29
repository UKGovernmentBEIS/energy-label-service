package uk.gov.beis.els;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class EnergyLabelServiceApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(EnergyLabelServiceApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(EnergyLabelServiceApplication.class, args);
	}
}
