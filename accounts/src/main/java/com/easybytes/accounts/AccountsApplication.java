package com.easybytes.accounts;

import com.easybytes.accounts.Config.AccountsConfiguration;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableFeignClients
@EnableConfigurationProperties(value = {AccountsConfiguration.class})
@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@OpenAPIDefinition(
		info = @io.swagger.v3.oas.annotations.info.Info(
				title = "Accounts Microservice API Documentation",
				version = "1.0",
				description = "Documentation Accounts API v1.0",
				contact = @io.swagger.v3.oas.annotations.info.Contact(
						name = "Yous Yoeun, Spring Boot Team",
						email = "oKq8T@example.com",
						url = "https://github.com/camboYY"

				),
				license = @io.swagger.v3.oas.annotations.info.License(
						name = "Apache 2.0",
						url = "https://www.apache.org/licenses/LICENSE-2.0"
				)
		),
		externalDocs = @io.swagger.v3.oas.annotations.ExternalDocumentation(
				description = "EasyBank Account Microservice Documentation",
				url = "https://github.com/camboYY/swagger-ui/index.html"
		)
)
public class AccountsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountsApplication.class, args);
	}

}
