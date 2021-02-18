package abstracts;

public interface CountryConverter {
	<T> T getCodeByCurrencyName(String currencyCode);
}
