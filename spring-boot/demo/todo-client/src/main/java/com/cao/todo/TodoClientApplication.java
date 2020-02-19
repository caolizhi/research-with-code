package com.cao.todo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.cao.todo.client.ToDoRestClient;
import com.cao.todo.client.domain.ToDo;

@SpringBootApplication
public class TodoClientApplication {

	private Logger log = LoggerFactory.getLogger(TodoClientApplication.class);

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(TodoClientApplication.class);
		app.setWebApplicationType(WebApplicationType.NONE);
		app.run(args);
	}

	@Bean
	public CommandLineRunner process(ToDoRestClient client) {

		return args -> {
			log.info("=================== findAll:=======================");
			Iterable<ToDo> toDos = client.findAll();
			assert toDos != null;
			toDos.forEach( toDo -> log.info(toDo.toString()));

			log.info("=================== createToDo:=======================");
			ToDo newToDo = client.upsert(new ToDo("Drink plenty of Water daily!"));
			assert newToDo != null;
			log.info(newToDo.toString());

			log.info("=================== findById:=======================");
			ToDo toDo = client.findById(newToDo.getId());
			assert toDo != null;
			log.info(toDo.toString());

			log.info("=================== setCompleted:=======================");
			ToDo completed = client.setCompleted(newToDo.getId());
			assert completed.isCompleted();
			log.info(completed.toString());

			log.info("=================== delete:=======================");
			client.delete(newToDo.getId());
			assert client.findById(newToDo.getId()) == null;
		};
	}


}
