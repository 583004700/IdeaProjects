package org.springframework.format;

import java.text.ParseException;
import java.util.Locale;

public interface Parser<T> {

	T parse(String text, Locale locale) throws ParseException;

}
