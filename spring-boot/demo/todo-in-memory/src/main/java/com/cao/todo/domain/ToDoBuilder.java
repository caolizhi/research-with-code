package com.cao.todo.domain;

public class ToDoBuilder {

	private static ToDoBuilder instance = new ToDoBuilder();

	private String id = null;
	private String description = "";

	private ToDoBuilder() {}

	public static ToDoBuilder create() {
		return instance;
	}

	public ToDoBuilder withDercription(String dercription) {
		this.description = dercription;
		return instance;
	}

	public ToDoBuilder withId(String id){
		this.id = id;
		return instance;
	}

	public ToDo build() {
		ToDo result = new ToDo(description);
		if (null != id) {
			result.setId(id);
		}
		return result;

	}
}
