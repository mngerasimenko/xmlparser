package ru.mngerasimenko.xmlparser.service;

import java.io.File;
import java.util.List;
import java.util.Set;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.mngerasimenko.xmlparser.entity.Department;
import ru.mngerasimenko.xmlparser.to.DepartmentTo;
import ru.mngerasimenko.xmlparser.to.ToDataSource;
import ru.mngerasimenko.xmlparser.xml.XmlDepartmentParser;


@org.springframework.stereotype.Service
public class DepartmentSynchronizer {
	public static final Logger logger = LoggerFactory.getLogger(DepartmentSynchronizer.class);

	private final XmlDepartmentParser parser;
	private final Service service;
	private final ToDataSource toDataSource;

	@Autowired
	public DepartmentSynchronizer(XmlDepartmentParser parser, Service service, ToDataSource toDataSource) {
		this.parser = parser;
		this.service = service;
		this.toDataSource = toDataSource;
	}

	public boolean syncFromXmlFile(File file) {
		logger.debug("Synchronize with file {}", file.getName());
		Set<DepartmentTo> departments = parser.parseFile(file);
		if (departments.isEmpty()) {
			return false;
		}
		service.sync(new ToDataSource(departments));
		return true;
	}

	public boolean exportToFile(File file) {
		try {
			List<Department> departments = service.getAll();
			if (departments.isEmpty()) {
				logger.error("Unable to export DB is empty");
				return false;
			}
			toDataSource.setDepartmentTos(departments);
			JAXBContext context = JAXBContext.newInstance(ToDataSource.class);
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

			marshaller.marshal(toDataSource, file);
			logger.debug("Db export to file {}", file.getName());
		} catch (JAXBException e) {
			logger.error("Unable to export to file {}", file.getName(), e);
			return false;
		}
		return true;
	}
}
