package com.cao.todo.validation;

import org.springframework.validation.Errors;

public class ToDoValidationErrorBuilder {

	public static ToDoValidationError fromBindingErrors(Errors errors) {
		ToDoValidationError toDoValidationError = new ToDoValidationError(
			"Validation failed. " + errors.getErrorCount() + " error(s)");
		errors.getAllErrors()
			.forEach(objectError -> toDoValidationError.addValidationError(objectError.getDefaultMessage()));
		return toDoValidationError;
	}

}
