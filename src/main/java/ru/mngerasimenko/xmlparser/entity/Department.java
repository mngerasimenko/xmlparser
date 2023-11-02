package ru.mngerasimenko.xmlparser.entity;

import java.util.Objects;
import ru.mngerasimenko.xmlparser.to.DepartmentTo;

public class Department {
	private Integer id;
	private String depCode;
	private String depJob;
	private String description;

	public Department() {
	}

	public Department(String depCode, String depJob, String description) {
		this(null, depCode, depJob, description);
	}

	public Department(Integer id, String depCode, String depJob, String description) {
		this.id = id;
		this.depCode = depCode;
		this.depJob = depJob;
		this.description = description;
	}

	public Department(DepartmentTo dto) {
		this(dto.getDepCode(), dto.getDepJob(), dto.getDescription());
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDepCode() {
		return depCode;
	}

	public void setDepCode(String depCode) {
		this.depCode = depCode;
	}

	public String getDepJob() {
		return depJob;
	}

	public void setDepJob(String depJob) {
		this.depJob = depJob;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isNew() {
		return this.id == null;
	}

	@Override
	public String toString() {
		return "Department{" +
				"id=" + id +
				", depCode='" + depCode + '\'' +
				", depJob='" + depJob + '\'' +
				", description='" + description + '\'' +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Department that = (Department) o;

		if (!Objects.equals(id, that.id)) return false;
		if (!depCode.equals(that.depCode)) return false;
		if (!depJob.equals(that.depJob)) return false;
		return description.equals(that.description);
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + depCode.hashCode();
		result = 31 * result + depJob.hashCode();
		result = 31 * result + description.hashCode();
		return result;
	}
}
