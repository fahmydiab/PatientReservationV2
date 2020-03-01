package com.doctor.reservation.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EntityNotFoundException extends RuntimeException {

	public EntityNotFoundException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}
	
}
