package org.springframework.format.number;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * 数字格式化
 */
public class NumberFormatter extends AbstractNumberFormatter {

	private String pattern;

	public NumberFormatter() {
	}

	public NumberFormatter(String pattern) {
		this.pattern = pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}


	@Override
	public NumberFormat getNumberFormat(Locale locale) {
		NumberFormat format = NumberFormat.getInstance(locale);
		if (!(format instanceof DecimalFormat)) {
			if (this.pattern != null) {
				throw new IllegalStateException("Cannot support pattern for non-DecimalFormat: " + format);
			}
			return format;
		}
		DecimalFormat decimalFormat = (DecimalFormat) format;
		decimalFormat.setParseBigDecimal(true);
		if (this.pattern != null) {
			decimalFormat.applyPattern(this.pattern);
		}
		return decimalFormat;
	}

}
