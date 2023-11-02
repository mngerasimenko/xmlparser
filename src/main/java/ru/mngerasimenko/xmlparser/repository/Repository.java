package ru.mngerasimenko.xmlparser.repository;

import java.util.List;
import ru.mngerasimenko.xmlparser.entity.Department;

public interface Repository {

	Department save(Department department);

	// false if entity does not belong to id
	boolean delete(int id);

	// null if entity does not belong to id
	Department get(int id);

	// null if entity does not
	Department getByDepCodeAndDepJob(String depCode, String depJob);

	List<Department> getAll();
}
