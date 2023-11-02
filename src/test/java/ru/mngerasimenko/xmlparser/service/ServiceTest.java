package ru.mngerasimenko.xmlparser.service;

import java.util.Arrays;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.mngerasimenko.xmlparser.exceptions.NotFoundException;
import ru.mngerasimenko.xmlparser.entity.Department;

import static org.junit.Assert.*;
import static ru.mngerasimenko.xmlparser.DepartmentTestData.*;

@ContextConfiguration("classpath:testSpring.xml")
@PropertySource("classpath:app.properties")
@RunWith(SpringRunner.class)
@Sql(scripts = {"classpath:testInitDB.sql", "classpath:testPopulateDB.sql"}, config = @SqlConfig(encoding = "UTF-8"))
public class ServiceTest {

	@Autowired
	private Service service;

	@Test
	public void create() {
		Department created = service.create(NEW_DEPARTMENT);
		Integer newId = created.getId();
		Department newDepartment = NEW_DEPARTMENT;
		newDepartment.setId(newId);
		Assertions.assertThat(created).isEqualTo(newDepartment);
		Assertions.assertThat(service.get(newId)).isEqualTo(newDepartment);
	}

	@Test
	public void duplicateCreate() {
		assertThrows(DataAccessException.class, () -> service.create(DUBLICATE_DEPARTMENT));
	}

	@Test
	public void delete() {
		service.delete(ID_0);
		assertThrows(NotFoundException.class, () -> service.get(ID_0));
	}

	@Test
	public void deletedNotFound() {
		assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND));
	}

	@Test
	public void get() {
		Department department = service.get(ID_0);
		Assertions.assertThat(department).isEqualTo(DEPARTMENT_0);
	}

	@Test
	public void getNotFound() {
		assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND));
	}

	@Test
	public void getAll() {
		List<Department> departments = service.getAll();
		Assertions.assertThat(departments)
				.usingRecursiveComparison()
				.isEqualTo(Arrays.asList(DEPARTMENT_0, DEPARTMENT_1, DEPARTMENT_2));
	}

	@Test
	public void update() {
		Department updated = UPDATED_DEPARTMENT;
		service.update(updated);
		Assertions.assertThat(updated).isEqualTo(UPDATED_DEPARTMENT);
	}
}