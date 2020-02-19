package com.cao.todo.client;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.cao.todo.client.domain.ToDo;
import com.cao.todo.client.error.ToDoErrorHandler;

@Component
public class ToDoRestClient {

	private RestTemplate restTemplate;

	private ToDoRestClientProperties properties;

	public ToDoRestClient(ToDoRestClientProperties properties) {
		this.restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
		this.restTemplate.setErrorHandler(new ToDoErrorHandler());
		this.properties = properties;
	}

	public Iterable<ToDo> findAll() throws URISyntaxException {
		RequestEntity<Iterable<ToDo>> requestEntity = new RequestEntity<Iterable<ToDo>>(HttpMethod.GET, new URI(properties.getUrl() + properties.getBasePath()));
		ResponseEntity<Iterable<ToDo>> response = restTemplate.exchange(requestEntity, new ParameterizedTypeReference<Iterable<ToDo>>(){});
		if(response.getStatusCode() == HttpStatus.OK){
			return response.getBody();
		}
		return null;
	}

	public ToDo findById(String id){
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", id);
		return restTemplate.getForObject(properties.getUrl() + properties.getBasePath() + "/{id}", ToDo.class, params);
	}

	public ToDo upsert(ToDo toDo) throws URISyntaxException {
		RequestEntity<?> requestEntity = new RequestEntity<>(toDo,HttpMethod.POST, new URI(properties.getUrl() + properties.getBasePath()));
		ResponseEntity<?> response = restTemplate.exchange(requestEntity, new ParameterizedTypeReference<ToDo>() {});
		if(response.getStatusCode() == HttpStatus.CREATED){
			return restTemplate.getForObject(response.getHeaders().getLocation(),ToDo.class);
		}
		return null;
	}

	public ToDo setCompleted(String id) throws URISyntaxException{
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", id);
		restTemplate.patchForObject(properties.getUrl() + properties.getBasePath() + "/{id}",null, ResponseEntity.class, params);
		return findById(id);
	}

	public void delete(String id){
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", id);
		restTemplate.delete(properties.getUrl() + properties.getBasePath() + "/{id}", params);
	}
}
