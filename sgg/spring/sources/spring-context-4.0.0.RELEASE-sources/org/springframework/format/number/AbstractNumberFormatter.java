package org.springframework.format.number;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Locale;

import org.springframework.format.Formatter;

public abstract class AbstractNumberFormatter implements Formatter<Number> {

	private boolean lenient = false;

	public void setLenient(boolean lenient) {
		this.lenient = lenient;
	}

	/**
	 * number对象格式化为字符串
	 * @param number
	 * @param locale
	 * @return
	 */
	@Override
	public String print(Number number, Locale locale) {
		return getNumberFormat(locale).format(number);
	}

	/**
	 * 字符串解析为number对象
	 * @param text
	 * @param locale
	 * @return
	 * @throws ParseException
	 */
	@Override
	public Number parse(String text, Locale locale) throws ParseException {
		NumberFormat format = getNumberFormat(locale);
		ParsePosition position = new ParsePosition(0);
		Number number = format.parse(text, position);
		if (position.getErrorIndex() != -1) {
			throw new ParseException(text, position.getIndex());
		}
		if (!this.lenient) {
			if (text.length() != position.getIndex()) {
				// indicates a part of the string that was not parsed
				throw new ParseException(text, position.getIndex());
			}
		}
		return number;
	}

	protected abstract NumberFormat getNumberFormat(Locale locale);

}
