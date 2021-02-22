package abstracts;

public interface CountryConverter {
	<T> T getCountryByCurrencyName(String currencyCode);
	<T> T getCodeByCountryName(String currencyCode);
}
