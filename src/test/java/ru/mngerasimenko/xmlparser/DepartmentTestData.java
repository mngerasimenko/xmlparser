package ru.mngerasimenko.xmlparser;

import ru.mngerasimenko.xmlparser.entity.Department;

public class DepartmentTestData {

	public static final int ID_0 = 0;
	public static final int NOT_FOUND = 10;


	public static final Department NEW_DEPARTMENT = new Department(null, "new code", "new job", "new description");
	public static final Department DUBLICATE_DEPARTMENT = new Department(null, "1", "IT", "something else");
	public static final Department DEPARTMENT_0 = new Department(0, "1", "IT", "it department");
	public static final Department DEPARTMENT_1 = new Department(1, "2", "Sale", "sale department");
	public static final Department DEPARTMENT_2 = new Department(2, "3", "Customer service", "сustomer service department");
	public static final Department UPDATED_DEPARTMENT = new Department(0, "1", "update", "update");




}
