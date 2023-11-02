package ru.mngerasimenko.xmlparser.to;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import org.springframework.stereotype.Component;
import ru.mngerasimenko.xmlparser.entity.Department;


@Component
@XmlRootElement(name = "Departments")
public class ToDataSource {
	private Set<DepartmentTo> departmentTos;

	public ToDataSource() {
	}

	public ToDataSource(Set<DepartmentTo> departments) {
		this.departmentTos = departments;
	}

	public boolean isNotContainsDepartment(Department department) {
		DepartmentTo departmentTo =	new DepartmentTo(department);
		return !departmentTos.contains(departmentTo);
	}

	public DepartmentTo getIfNotEquals(Department department) {
		DepartmentTo departmentTo = getByDepCodeAndDepJob(department);
		return !departmentTo.getDescription().equals(department.getDescription())
				? departmentTo
				: null;
	}

	private DepartmentTo getByDepCodeAndDepJob(Department department) {
		return departmentTos.stream()
				.filter(d -> d.getDepCode().equals(department.getDepCode())
						&& d.getDepJob().equals(department.getDepJob()))
				.findFirst().orElse(null);
	}

	@XmlElement(name = "Department")
	public Set<DepartmentTo> getDepartmentTos() {
		return departmentTos;
	}

	public void setDepartmentTos(List<Department> departments) {
		this.departmentTos = departments.stream()
				.map(DepartmentTo::new)
				.collect(Collectors.toSet());
	}

	public void remove(Department department) {
		departmentTos.remove(new DepartmentTo(department));
	}
}
