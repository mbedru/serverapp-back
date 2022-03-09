package com.springAngular.server.resource;

import java.util.Map;
import static java.time.LocalDateTime.now;

import org.springframework.http.ResponseEntity;
import static org.springframework.http.HttpStatus.*;//ok,created,...

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Timestamp;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.apache.catalina.User;
import org.springframework.http.MediaType;

import com.springAngular.server.Service.ServerService;
import com.springAngular.server.Service.implementation.ServerServiceImpl;
import com.springAngular.server.enumeration.Status;
import com.springAngular.server.model.Response;
import com.springAngular.server.model.Server;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/server")
@RequiredArgsConstructor // to inject dependency => ServerServiceImpl
public class ServerResource { // we can call this Controller but in rest controller we can call it RESOURCE
	private final ServerServiceImpl serverService;

	@GetMapping("/list")
	public ResponseEntity<Response> getServers() throws InterruptedException {
		Thread.sleep(1000);
		// throw new InterruptedException("Something wrong!!");

		return ResponseEntity.ok(// we aint using the developerMessage or reason b/c we dealing with .ok()=>>>no
									// error/ok,
				Response.builder().timeStamp(now()).data(Map.of("servers", serverService.list(20)))
						.message("Servers retrieved").status(OK).statusCode(OK.value()).build());

	}

	@GetMapping("/ping/{ipAddress}")
	public ResponseEntity<Response> pingServer(@PathVariable("ipAddress") String ipAddress) throws IOException {
		Server server = serverService.ping(ipAddress);
		return ResponseEntity.ok(Response.builder().timeStamp(now()).data(Map.of("server", server))
				.message(server.getStatus() == Status.SERVER_UP ? "Ping success" : "Ping failed").status(OK)
				.statusCode(OK.value()).build());

	}

	@PostMapping("/save")
	public ResponseEntity<Response> saveServer(@RequestBody @Valid Server server) {// @Valid >>> yemnlekew Server
																					// validate yareglnal against Server
																					// milew class lay yalut variables
																					// lay balut constraints antsar
																					// validate yareglnal... some say
																					// ("@Valid baynorem validate
																					// yaregal")
		return ResponseEntity.ok( // .create also yichalal but response melak anchelem in that way
				Response.builder().timeStamp(now()).data(Map.of("server", serverService.create(server)))
						.message("Server created").status(CREATED).statusCode(CREATED.value()).build());
	}

	// we can use this to update BUT saveServer can do it as long as we pass the id
	// with it
	/*
	 * @PutMapping("/update") public ResponseEntity<Response> updateServer
	 * (@RequestBody @Valid Server server) { return ResponseEntity.ok(
	 * Response.builder() .timeStamp(now()) .data(Map.of("server",
	 * serverService.update(server))) .message("Server created") .status(OK)
	 * .statusCode(OK.value()) .build() );
	 * 
	 * }
	 */

	@GetMapping("/get/{id}")
	public ResponseEntity<Response> getServer(@PathVariable("id") Long id) {
		return ResponseEntity.ok(Response.builder().timeStamp(now()).data(Map.of("server", serverService.get(id)))
				.message("Server retrieved").status(OK)// FOUND malet yichalal
				.statusCode(OK.value())// FOUND.value
				.build());
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Response> deleteServer(@PathVariable("id") Long id) {
		return ResponseEntity.ok(Response.builder().timeStamp(now()).data(Map.of("deleted", serverService.delete(id)))
				.message("Server deleted").status(OK).statusCode(OK.value()).build());
	}

	@GetMapping(path = "/image/{filename}", produces = MediaType.IMAGE_PNG_VALUE) // by default others produce = json;
	public byte[] getServerImage(@PathVariable("filename") String filename) throws IOException {
		return Files.readAllBytes(Paths.get(System.getProperty("user.home") + "/Desktop/images/" + filename));
	}
}
