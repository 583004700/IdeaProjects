package org.springframework.format;

import java.util.Locale;

public interface Printer<T> {

	String print(T object, Locale locale);

}
