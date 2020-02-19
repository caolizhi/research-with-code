package com.cao.todo.client.domain;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Data;

@Data
public class ToDo {

	private String id;
	private String description;
	private LocalDateTime created;
	private LocalDateTime modified;
	private boolean completed;

	public ToDo(){
		LocalDateTime date = LocalDateTime.now();
		this.id = UUID.randomUUID().toString();
		this.created = date;
		this.modified = date;
	}

	public ToDo(String description){
		this();
		this.description = description;
	}

}
