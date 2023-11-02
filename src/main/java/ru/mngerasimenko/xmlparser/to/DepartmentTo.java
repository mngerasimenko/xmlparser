package ru.mngerasimenko.xmlparser.to;

import javax.xml.bind.annotation.XmlAccessType;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import ru.mngerasimenko.xmlparser.entity.Department;

@XmlRootElement(name = "Department")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"depCode", "depJob", "description"})
public class DepartmentTo {
	@XmlElement(name = "DepCode")
	private String depCode;
	@XmlElement(name = "DepJob")
	private String depJob;
	@XmlElement(name = "Description")
	private String description;

	public DepartmentTo() {
	}

	public DepartmentTo(String depCode, String depJob, String description) {
		this.depCode = depCode;
		this.depJob = depJob;
		this.description = description;
	}

	public DepartmentTo(Department department) {
		this.depCode = department.getDepCode();
		this.depJob = department.getDepJob();
		this.description = department.getDescription();
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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		DepartmentTo that = (DepartmentTo) o;

		if (!depCode.equals(that.depCode)) return false;
		return depJob.equals(that.depJob);
	}

	@Override
	public int hashCode() {
		int result = depCode.hashCode();
		result = 31 * result + depJob.hashCode();
		return result;
	}

	@Override
	public String toString() {
		return "DepartmentTo{" +
				"depCode='" + depCode + '\'' +
				", depJob='" + depJob + '\'' +
				", description='" + description + '\'' +
				'}';
	}
}
