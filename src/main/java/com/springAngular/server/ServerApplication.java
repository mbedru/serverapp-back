package com.springAngular.server;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.springAngular.server.Repo.ServerRepo;
import com.springAngular.server.enumeration.Status;
import com.springAngular.server.model.Server;

@SpringBootApplication
public class ServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
	
	}
	// will run exactly after the app starts(initialized)
	@Bean
	CommandLineRunner run (ServerRepo serverRepo) {
		return Args -> {
			serverRepo.save(new Server (null, "8.8.4.4", "Ubunt linux", "64 GB", "Personal PC","http://localhost:8096/server/image/server1.png", Status.SERVER_UP));
			serverRepo.save(new Server (null, "8.8.8.8", "Fedora Linux", "32 GB", "Dell Tower","http://localhost:8096/server/image/server2.png", Status.SERVER_DOWN));
			serverRepo.save(new Server (null, "127.0.0.1", "ms 2008", "16 GB", "Web Server","http://localhost:8096/server/image/server3.jfif", Status.SERVER_UP));
			serverRepo.save(new Server (null, "127.0.0.2", "red hat enterprise linux", "128 GB", "Mail server","http://localhost:8096/server/image/server4.png", Status.SERVER_DOWN));
//			serverRepo.save(new Server (null, "::1 ", "local host", "128 GB", "win server","http://localhost:8096/server/image/server4.png", Status.SERVER_DOWN));
			
		};
	}
//without this bean if we try to access this backend with angular/react/otherFrontend application we will have "Cors Error" b/c we are not in the same domain
	//corsErr happens when we have 2 apps(but with d/t domain) running, & we want them to communicate through the browser
	// so there is sth that's setup in the browser for security purposes that's gonna stop this (communication of 2 apps with d/t domains ) from happening 
	/*yihe err endayfeter the backend specifically specify mareg alebet this front-end app is can access it blo...then erroru yitefal*/
	//this bean is for react and angular
	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.setAllowCredentials(true);
		corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:3000", "http://localhost:4200")); // TELLING IT TO... ALLOW THESE DOMAINS react-3000 and angular-4200
		corsConfiguration.setAllowedHeaders(Arrays.asList("Origin", "Access-Control-Allow-Origin", "Content-Type",// ALLOW THESE HEADERS
				
				"Accept", "Jwt-Token", "Authorization", "Origin, Accept", "X-Requested-With",
				"Access-Control-Request-Method", "Access-Control-Request-Headers"));
		corsConfiguration.setExposedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "Jwt-Token", "Authorization",
				"Access-Control-Allow-Origin", "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials", "Filename"));
		corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
		urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
		return new CorsFilter(urlBasedCorsConfigurationSource);
	}
}



//any address starting from 
//127.0.0.X is ping able

//we can also ping our router
//192.168.8.1
