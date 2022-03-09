package com.springAngular.server.Service.implementation;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Collection;
import java.util.Random;

import javax.transaction.Transactional;

import org.springframework.data.domain.PageRequest;//what is the d/ce this with the 
//import static org.springframework.data.domain.PageRequest.of;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.springAngular.server.Repo.ServerRepo;
import com.springAngular.server.Service.ServerService;
//import com.springAngular.server.enumeration.Status;
import com.springAngular.server.model.Server;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static com.springAngular.server.enumeration.Status.SERVER_UP;
import static com.springAngular.server.enumeration.Status.SERVER_DOWN;
//import com.springAngular.server.model.Server;


@Transactional
@Service
@RequiredArgsConstructor//this makes invisible initialize(dependency inject) ServerRepo || or any variable that MUST BE initialized.
@Slf4j
public class ServerServiceImpl implements ServerService {
	private  final ServerRepo serverRepo;//this one is injected by @RequiredArgsConstructor(b/c it's "final")

//if i make this method @Transactional there is no need to save() when updating (not adding)
	//When we use findById() to retrieve an entity within a transactional method, the returned entity
	//is managed by the persistence provider. So, any change to that entity will be automatically persisted in the database,
	//regardless of whether we are invoking the save() method.	
	@Override
	public Server create(Server server) {
		log.info("Saving new server:{}", server.getName());
		server.setImageUrl(setServerImageUrl());
		return serverRepo.save(server);//save() used to add/update db entry.
	}

	@Override
	public Server ping(String ipAddress) throws IOException {
		log.info("Pinging server: {}", ipAddress);
		Server server = serverRepo.findByIpAddress(ipAddress);
		InetAddress address = InetAddress.getByName(ipAddress);//changing ipAddress (from string to InetAddress) type
		server.setStatus(address.isReachable(6000) ? SERVER_UP : SERVER_DOWN);//trying to ping
		serverRepo.save(server);//b/c we edited status of the server
		return server;
	}
	//we will create another method to reach(connect/ping) with a remote server java.net package
	

	@Override
	public Collection<Server> list(int limit) { //we can make this return Page(to avoid converting the returned page to list)
		log.info("Fetching all servers");
		return serverRepo.findAll(PageRequest.of(0, limit)).toList();//returns Page but convert it to list.//we can say 0f(x,y) if we import 
																	 // if we didn't pass limit it will the findAll will return all the servers
	}

	@Override
	public Server get(Long id) {
		log.info("Fetching by Id: {}", id);
		
		return serverRepo.findById(id).get();// if you use getById it wont return Server it returns reference of Server
	}

	@Override
	public Server update(Server server) {
		log.info("Updating servr: {}", server.getName());
		return serverRepo.save(server);//same as save() //d/c is when it sees the ID JPA is smart
		//enough to know that :- if the id exist it will do an update but if the id doesn't exist it
		//will do create new server (in the database).
	}

	@Override
	public Boolean delete(Long id) {
		log.info("Deleting server by Id: {}", id);
		serverRepo.deleteById(id);
		return Boolean.TRUE;//we can just say true
	}
	//this fN return string uri(folder-path) of random images
	private String setServerImageUrl() {
		String imageNames[] = {"server1.png", "server2.png", "server3.jfif","server4.png", "server5.png" };
		return ServletUriComponentsBuilder.fromCurrentContextPath().path("/server/image/" + imageNames[new Random().nextInt(5)]).toUriString();
	}
	
}










/*package com.springAngular.server.Service.implementation;

import com.springAngular.server.model.Server;

import ch.qos.logback.classic.Logger;

import com.springAngular.server.Repo.ServerRepo;
import com.springAngular.server.Service.ServerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Collection;
import java.util.Random;

import static com.springAngular.server.enumeration.Status.SERVER_DOWN;
import static com.springAngular.server.enumeration.Status.SERVER_UP;
import static java.lang.Boolean.TRUE;
import static org.springframework.data.domain.PageRequest.of;

/**
 * @author Get Arrays (https://www.getarrays.io/)
 * @version 1.0
 * @since 9/4/2021
 

@Service
@Transactional
@Slf4j
//@RequiredArgsConstructor//made for constructor injection => working

//@Log4j
public class ServerServiceImpl implements ServerService {
	private final ServerRepo serverRepo;

	public ServerServiceImpl(ServerRepo serverRepo) {
		this.serverRepo = serverRepo;
	}

	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ServerServiceImpl.class);

	@Override
	public Server create(Server server) {
		log.info("Saving new server: {}", server.getName());
		server.setImageUrl(setServerImageUrl());
//        server.setImageUrl(setServerImageUrl());
//        return serverRepo.save(server);
		return null;
	}

	@Override
	public Server ping(String ipAddress)  throws IOException  {
//        log.info("Pinging server IP: {}", ipAddress);
//        Server server = serverRepo.findByIpAddress(ipAddress);
//        InetAddress address = InetAddress.getByName(ipAddress);
//        server.setStatus(address.isReachable(10000) ? SERVER_UP : SERVER_DOWN);
//        serverRepo.save(server);
//        return server;
		return null;
	}

	@Override
	public Collection<Server> list(int limit) {
//        log.info("Fetching all servers");
//        return serverRepo.findAll(of(0, limit)).toList();
		return null;
	}

	@Override
	public Server get(Long id) {
//        log.info("Fetching server by id: {}", id);
//        return serverRepo.findById(id).get();
		return null;
	}

	@Override
	public Server update(Server server) {
//        log.info("Updating server: {}", server.getName());
//        return serverRepo.save(server);
		return null;
	}

	@Override
	public Boolean delete(Long id) {
//        log.info("Deleting server by ID: {}", id);
//        serverRepo.deleteById(id);
		return TRUE;
	}

	
	 * private String setServerImageUrl() {
	 * 
	 * String[] imageNames = { "server1.png", "server2.png", "server3.png",
	 * "server4.png" }; return
	 * ServletUriComponentsBuilder.fromCurrentContextPath().path("/server/image/" +
	 * imageNames[new Random().nextInt(4)]).toUriString();
	 * 
	 * return null; }
	 
	private boolean isReachable(String ipAddress, int port, int timeOut) {
        try {
            try(Socket socket = new Socket()) {
                socket.connect(new InetSocketAddress(ipAddress, port), timeOut);
            }
            return true;
        }catch (IOException exception){
            return false;
		}
    }return true;
}*/
	