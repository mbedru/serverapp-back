package com.springAngular.server.model;

import java.time.LocalDateTime;

import java.util.Map;

import org.springframework.boot.autoconfigure.data.redis.RedisProperties.Sentinel;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import lombok.Data;
import lombok.experimental.SuperBuilder;
//a way to send response to the user.//in every response(situation) we get the same response(but it contains d/t content depending on the response)
//we created this model b/c we want to send the same response in every situation(if we get error, success ...)
//but this is not a must, but good for API consistency ... we could do it the traditional way
@JsonInclude(NON_NULL)//e.g:- in success=> reason & developerMessage will be empty so they will not be included in our Json 
@Data
@SuperBuilder //(same as @Builder) Response.builder().name("dfd).age()...   .build(); malet endenchel
public class Response { //we will use this class as a Response body -- in every request
	protected LocalDateTime timeStamp; //
	protected int statusCode;//
	protected HttpStatus status; //
	protected String reason;
	protected String message;//notification for success//
	protected String developerMessage;
	protected Map<?, ?> data;//
}
