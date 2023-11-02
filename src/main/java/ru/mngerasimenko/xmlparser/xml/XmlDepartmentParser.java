package ru.mngerasimenko.xmlparser.xml;

import java.io.File;
import java.util.HashSet;
import java.util.Set;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import ru.mngerasimenko.xmlparser.exceptions.XmlParserException;
import ru.mngerasimenko.xmlparser.to.DepartmentTo;

@Component
public class XmlDepartmentParser {
	private static final Logger logger = LoggerFactory.getLogger(XmlDepartmentParser.class);

	public static final String ROOT_NODE_NAME = "Departments";
	public static final String DEPARTMENT_NODE_NAME = "Department";

	public Set<DepartmentTo> parseFile(File file) {
		Set<DepartmentTo> departments = null;
		try {
			Document doc = parseDocument(file);
			if (!ROOT_NODE_NAME.equals(doc.getDocumentElement().getNodeName())) {
				logger.error("Unable to find root node");
				throw new XmlParserException("Unable to find root node");
			}

			departments = getDepartmentsFromDom(doc);
		} catch (Exception e) {
			logger.error("Unable to parse xml file {}", file, e);
		}
		return departments;
	}

	private Document parseDocument(File file) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = factory.newDocumentBuilder();
		Document document = dBuilder.parse(file);
		document.getDocumentElement().normalize();
		return document;
	}

	private Set<DepartmentTo> getDepartmentsFromDom(Document doc) {
		Set<DepartmentTo> departments = new HashSet<>();
		NodeList nodeList = doc.getElementsByTagName(DEPARTMENT_NODE_NAME);
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node nNode = nodeList.item(i);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				DepartmentTo department = getDepartment(nNode);
				if (department != null) {
					logger.debug("Found department node: {}", department);
					if (departments.contains(department)) {
						logger.error("Dublicate department {}", department);
					} else {
						departments.add(department);
					}
				}
			}
		}
		return departments;
	}

	private DepartmentTo getDepartment(Node nNode) {
		Element elem = (Element) nNode;

		Node nodeDepCode = elem.getElementsByTagName("DepCode").item(0);
		String depCode = nodeDepCode.getTextContent().trim();

		Node nodeDepJob = elem.getElementsByTagName("DepJob").item(0);
		String depJob = nodeDepJob.getTextContent().trim();

		Node nodeDescription = elem.getElementsByTagName("Description").item(0);
		String description = nodeDescription.getTextContent().trim();

		if (StringUtils.isBlank(depCode) || StringUtils.isBlank(depJob)
				|| StringUtils.isBlank(description)) {
			logger.warn("Unable to parse node");
			return null;
		}

		return new DepartmentTo(depCode, depJob, description);
	}
}
