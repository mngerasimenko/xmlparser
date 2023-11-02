package ru.mngerasimenko.xmlparser.repository;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import ru.mngerasimenko.xmlparser.entity.Department;

@org.springframework.stereotype.Repository
public class DepartmentRepository implements Repository {

	private static final BeanPropertyRowMapper<Department> ROW_MAPPER = BeanPropertyRowMapper.newInstance(Department.class);
	private final JdbcTemplate jdbcTemplate;
	private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private final SimpleJdbcInsert insert;

	@Autowired
	public DepartmentRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
		this.insert = new SimpleJdbcInsert(jdbcTemplate)
				.withTableName("Department")
				.usingGeneratedKeyColumns("ID");
	}

	@Override
	public Department save(Department department) {
		MapSqlParameterSource map = new MapSqlParameterSource()
				.addValue("id", department.getId())
				.addValue("depCode", department.getDepCode())
				.addValue("depJob", department.getDepJob())
				.addValue("description", department.getDescription());

		if (department.isNew()) {
			Number newKey = insert.executeAndReturnKey(map);
			department.setId(newKey.intValue());
		} else if (namedParameterJdbcTemplate.update(
				"UPDATE DEPARTMENT SET DEP_CODE=:depCode, DEP_JOB=:depJob," +
						" DESCRIPTION=:description WHERE ID=:id", map) == 0) {
			return null;
		}
		return department;
	}

	@Override
	public boolean delete(int id) {
		return jdbcTemplate.update("DELETE FROM DEPARTMENT WHERE ID=?", id) != 0;
	}

	@Override
	public Department get(int id) {
		List<Department> department = jdbcTemplate.query("SELECT * FROM DEPARTMENT WHERE ID=?", ROW_MAPPER, id);
		return DataAccessUtils.singleResult(department);
	}

	@Override
	public Department getByDepCodeAndDepJob(String depCode, String depJob) {
		List<Department> department = jdbcTemplate.query(
				"SELECT * FROM DEPARTMENT WHERE DEP_CODE=? AND DEP_JOB=? ", ROW_MAPPER, depCode, depJob);
		return DataAccessUtils.singleResult(department);
	}

	@Override
	public List<Department> getAll() {
		return jdbcTemplate.query("SELECT * FROM DEPARTMENT", ROW_MAPPER);
	}
}
