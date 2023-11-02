package ru.mngerasimenko.xmlparser;

import java.io.File;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import ru.mngerasimenko.xmlparser.service.DepartmentSynchronizer;

/**
 * Hello world!
 */

@Component
public class Starter {
	private static final Logger logger = LoggerFactory.getLogger(Starter.class);

	public static final String SYNC = "sync";
	public static final String EXPORT = "export";

	private final DepartmentSynchronizer synchronizer;

	@Autowired
	public Starter(DepartmentSynchronizer synchronizer) {
		this.synchronizer = synchronizer;
	}

	public static void main(String[] args) {
		if (args.length != 2) {
			logger.error("You need to enter the command \n" +
					"`sync` - to synchronize the XML file with the database " +
					"or `export` - to upload from the database to an XML file\n" +
					" and the file name. \n" +
					"For example: java -jar xmlparser-1.0-SNAPSHOT.jar sync filename.xml\n");
			return;
		}

		Starter starter;
		try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring.xml")) {
			starter = appCtx.getBean(Starter.class);
		}

		File file = new File(getClassPath() + args[1]);
		if (SYNC.equalsIgnoreCase(args[0])) {
			if (!file.exists() || file.isDirectory()) {
				logger.error("Unable to get file {}", file.getName());
				return;
			}
			starter.syncFromFile(starter, file);
		} else if (EXPORT.equalsIgnoreCase(args[0])) {
			starter.export(starter, file);
		} else {
			logger.error("invalid command: {}", args[0]);
		}
	}

	private void export(Starter starter, File file) {
		logger.info("Running export to file {}", file.getName());
		if(starter.synchronizer.exportToFile(file)) {
			logger.info("Export to file {} done", file.getName());
		}
	}

	private void syncFromFile(Starter starter, File file) {
		logger.info("Running synchronisation with file {}", file.getName());
		if(starter.synchronizer.syncFromXmlFile(file)) {
			logger.info("Synchronisation with file {} done", file.getName());
		}
	}

	public static String getClassPath() {
		String separator = "/";
		if (System.getProperty("os.name").contains("Windows")) {
			separator = "\\";
		}
		return System.getProperty("user.dir") + separator;
	}
}
