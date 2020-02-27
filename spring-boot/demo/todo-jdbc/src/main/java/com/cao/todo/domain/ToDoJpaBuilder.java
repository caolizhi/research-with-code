package com.cao.todo.domain;

public class ToDoJpaBuilder {

	private static ToDoJpaBuilder instance = new ToDoJpaBuilder();

	private String id = null;
	private String description = "";

	private ToDoJpaBuilder() {}

	public static ToDoJpaBuilder create() {
		return instance;
	}

	public ToDoJpaBuilder withDercription(String dercription) {
		this.description = dercription;
		return instance;
	}

	public ToDoJpaBuilder withId(String id){
		this.id = id;
		return instance;
	}

	public ToDoJpa build() {
		ToDoJpa result = new ToDoJpa(description);
		if (null != id) {
			result.setId(id);
		}
		return result;

	}
}
