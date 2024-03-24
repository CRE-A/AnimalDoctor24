package kr.itycoon.plutoid;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@MapperScan(basePackageClasses = PlutoidJbackendApplication.class)
@SpringBootApplication
@EnableWebSecurity
@EnableAspectJAutoProxy
public class PlutoidJbackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlutoidJbackendApplication.class, args);
	}

}
