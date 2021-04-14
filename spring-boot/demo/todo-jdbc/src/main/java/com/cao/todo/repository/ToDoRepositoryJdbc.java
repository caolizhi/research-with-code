package com.cao.todo.repository;

import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.validation.constraints.NotNull;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.cao.todo.domain.ToDo;

@Repository
public class ToDoRepositoryJdbc implements CommonRepository<ToDo> {

	private static final String SQL_INSERT = "insert into todo (id, description, created, modified, completed) "
		+ "values (:id,:description,:created,:modified,:completed)";

	private static final String SQL_QUERY_FIND_ALL = "select id, description, created, modified, completed from todo";

	private static final String SQL_QUERY_FIND_BY_ID = SQL_QUERY_FIND_ALL + " where id = :id";

	private static final String SQL_UPDATE = "update todo set description = :description, modified = :modified, completed = :completed where id = :id";

	private static final String SQL_DELETE = "delete from todo where id = :id";

	private final NamedParameterJdbcTemplate jdbcTemplate;

	public ToDoRepositoryJdbc(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	private RowMapper<ToDo> toDoRowMapper = (ResultSet rs, int rowNum) -> {
		ToDo toDo = new ToDo();
		toDo.setId(rs.getString("id"));
		toDo.setDescription(rs.getString("description"));
		toDo.setModified(rs.getTimestamp("modified").toLocalDateTime());
		toDo.setCreated(rs.getTimestamp("created").toLocalDateTime());
		toDo.setCompleted(rs.getBoolean("completed"));
		return toDo;
	};

	@Override
	public ToDo save(ToDo domain) {
		ToDo result = findById(domain.getId());
		if (result != null) {
			result.setDescription(domain.getDescription());
			result.setCompleted(domain.isCompleted());
			result.setModified(LocalDateTime.now());
			return insert(result, SQL_UPDATE);
		}
		return insert(domain, SQL_INSERT);
	}

	private ToDo insert(final ToDo toDo, final String sql) {
		Map<String, Object> namedParameters = new HashMap<>();
		namedParameters.put("id", toDo.getId());
		namedParameters.put("description", toDo.getDescription());
		namedParameters.put("created", java.sql.Timestamp.valueOf(toDo.getCreated()));
		namedParameters.put("modified", java.sql.Timestamp.valueOf(toDo.getModified()));
		namedParameters.put("completed", toDo.isCompleted());
		this.jdbcTemplate.update(sql, namedParameters);
		return findById(toDo.getId());
	}

	@Override
	public Iterable<ToDo> save(Collection<ToDo> domains) {
		domains.forEach(this::save);
		return findAll();
	}

	@Override
	public void delete(ToDo domain) {
		Map<String, @NotNull String> namedParameters = Collections.singletonMap("id", domain.getId());
		this.jdbcTemplate.update(SQL_DELETE, namedParameters);
	}

	@Override
	public ToDo findById(String id) {
		try {
			Map<String, String> namedParameters = Collections.singletonMap("id", id);
			return this.jdbcTemplate.queryForObject(SQL_QUERY_FIND_BY_ID, namedParameters, toDoRowMapper);
		} catch (EmptyResultDataAccessException ex) {
			return null;
		}
	}

	@Override
	public Iterable<ToDo> findAll() {
		return this.jdbcTemplate.query(SQL_QUERY_FIND_ALL, toDoRowMapper);
	}

	/*private Map<String, ToDo> toDos = new HashMap<>();

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

	private Comparator<Map.Entry<String,ToDo>> entryComparator = Comparator.comparing((Map.Entry<String, ToDo> o) -> o.getValue().getCreated());*/
}
