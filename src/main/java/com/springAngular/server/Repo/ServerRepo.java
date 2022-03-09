package com.springAngular.server.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.springAngular.server.model.Server;

public interface ServerRepo extends JpaRepository<Server, Long> {
	Server findByIpAddress (String ipAddress);//
	//... there are many functions... but it has no d/ce even if we dont write them including this one
}
