/**
 * 
 */
package ms.luna.web.common.formatter;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Locale;

import org.springframework.format.Formatter;

/**
 * @author Mark
 *
 */
public class BigDecimalFormatter implements Formatter<BigDecimal> {

//	private String regexp="/[^\u4e00-\u9fa5A-Za-z_0-9]+/g";

	private String numberPattern;

	public BigDecimalFormatter(String numberPattern) {
		this.numberPattern = numberPattern;
	}
	@Override
	public String print(BigDecimal object, Locale locale) {
		if (object == null) {
			return "";
		}
		BigDecimal decimal = (BigDecimal)object;
		return decimal.toPlainString();
	}

	@Override
	public BigDecimal parse(String text, Locale locale) throws ParseException {
		if (text == null) {
			return null;
		}
		String number = text.replace(",", "");
		BigDecimal decimal;
		try {
			decimal = new BigDecimal(number)
					.setScale(6, BigDecimal.ROUND_HALF_UP);
		} catch (Exception e) {
			throw new IllegalArgumentException("不合法的数字格式");
		}
		return decimal;
	}

	/**
	 * @return the numberPattern
	 */
	public String getNumberPattern() {
		return numberPattern;
	}

	/**
	 * @param numberPattern the numberPattern to set
	 */
	public void setNumberPattern(String numberPattern) {
		this.numberPattern = numberPattern;
	}

}
