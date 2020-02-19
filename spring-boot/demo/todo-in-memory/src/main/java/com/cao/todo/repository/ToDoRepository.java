package com.cao.todo.repository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.cao.todo.domain.ToDo;

@Repository
public class ToDoRepository implements CommonRepository<ToDo> {

	private Map<String, ToDo> toDos = new HashMap<>();

	@Override
	public ToDo save(ToDo domain) {
		ToDo result = toDos.get(domain.getId());
		if (Objects.nonNull(result)) {
			result.setModified(LocalDateTime.now());
			result.setCreated(LocalDateTime.now());
			result.setCompleted(domain.isCompleted());
			domain = result;
		}
		toDos.put(domain.getId(), domain);
		return toDos.get(domain.getId());
	}

	@Override
	public Iterable<ToDo> save(Collection<ToDo> domains) {
		domains.forEach(this::save);
		return findAll();
	}

	@Override
	public void delete(ToDo domain) {
		toDos.remove(domain.getId());
	}

	@Override
	public ToDo findById(String id) {
		return toDos.get(id);
	}

	@Override
	public Iterable<ToDo> findAll() {
		return toDos.entrySet().stream().sorted(entryComparator).map(Map.Entry::getValue).collect(Collectors.toList());
	}

	private Comparator<Map.Entry<String,ToDo>> entryComparator = Comparator.comparing((Map.Entry<String, ToDo> o) -> o.getValue().getCreated());
}
