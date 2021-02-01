package Abstract;
import java.math.BigDecimal;

public interface ExchangeRate 
{
	BigDecimal getCurrencyRate(String currencyCode);
	BigDecimal calculateCurrency(BigDecimal currencyValue, String currencyCode);
}
