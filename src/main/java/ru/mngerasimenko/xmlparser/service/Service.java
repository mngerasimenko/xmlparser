package ru.mngerasimenko.xmlparser.service;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.mngerasimenko.xmlparser.entity.Department;
import ru.mngerasimenko.xmlparser.repository.Repository;
import ru.mngerasimenko.xmlparser.to.DepartmentTo;
import ru.mngerasimenko.xmlparser.to.ToDataSource;

import static ru.mngerasimenko.xmlparser.utils.ValidationUtils.checkNotFoundWithId;

@org.springframework.stereotype.Service
public class Service {
	public static final Logger logger = LoggerFactory.getLogger(Service.class);

	private final Repository repository;

	@Autowired
	public Service(Repository repository) {
		this.repository = repository;
	}

	public Department create(Department department) {
		logger.debug("Create new department {}", department);
		return repository.save(department);
	}

	public void delete(int id) {
		logger.debug("Delete department with id={}", id);
		checkNotFoundWithId(repository.delete(id), id);
	}

	public Department get(int id) {
		logger.debug("Get department with id={}", id);
		return checkNotFoundWithId(repository.get(id), id);
	}

	public Department getByDepCodeAndDepJob(String depCode, String depJob) {
		logger.debug("Get department with depCode={}, depJop={} ", depCode, depJob);
		return repository.getByDepCodeAndDepJob(depCode, depJob);

	}

	public List<Department> getAll() {
		logger.debug("Get all department");
		return repository.getAll();
	}

	public void update(Department department) {
		logger.debug("Update department {}", department);
		checkNotFoundWithId(repository.save(department), department.getId());
	}

	public void sync(ToDataSource toDataSource) {
		logger.debug("Synchronization...");
		List<Department> departmentsDb = getAll();
		for (Department department : departmentsDb) {
			if (toDataSource.isNotContainsDepartment(department)) {
				delete(department.getId());
			} else {
				DepartmentTo departmentTo = toDataSource.getIfNotEquals(department);
				if (departmentTo != null) {
					department.setDescription(departmentTo.getDescription());
					update(department);
				}
				toDataSource.remove(department);
			}
		}

		if (!toDataSource.getDepartmentTos().isEmpty()) {
			toDataSource.getDepartmentTos().stream()
					.map(Department::new)
					.forEach(this::create);
		}
	}
}
