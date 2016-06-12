/**
 * 
 */
package ms.luna.web.common.formatter;

import org.springframework.format.FormatterRegistrar;
import org.springframework.format.FormatterRegistry;

/**
 * @author Mark
 *
 */
public class LunaFormatterRegistrar implements FormatterRegistrar {

	private String numberPattern;
	public LunaFormatterRegistrar(String numberPattern) {
		this.numberPattern = numberPattern;
	}
	
	@Override
	public void registerFormatters(FormatterRegistry registry) {
		registry.addFormatter(new BigDecimalFormatter(numberPattern));
	}

}
