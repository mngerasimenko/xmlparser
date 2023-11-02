package ru.mngerasimenko.xmlparser.utils;

import ru.mngerasimenko.xmlparser.exceptions.NotFoundException;

public abstract class ValidationUtils {

	public static <T> T checkNotFoundWithId(T object, int id) {
		checkNotFoundWithId(object != null, id);
		return object;
	}

	public static void checkNotFoundWithId(boolean found, int id) {
		checkNotFound(found, "id=" + id);
	}

	public static void checkNotFound(boolean found, String msg) {
		if (!found) {
			throw new NotFoundException("Not found entity with " + msg);
		}
	}
}
