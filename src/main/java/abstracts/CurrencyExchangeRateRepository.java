package abstracts;

import java.sql.Date;
import java.util.List;

import entity.Country;
import entity.CurrencyExchange;
import entity.CurrencyExchangeKey;
import entity.CurrencyRate;
import common.CountryDto;

public interface CurrencyExchangeRateRepository {
	CurrencyExchangeKey addCurrencyExchange(CurrencyExchange currencyExchanged);
	Long addCountry(Country country);
	Long addRate(CurrencyRate rate);
	CurrencyRate getRateById(Long id);
	CurrencyExchange getCurrencyById(Long id);
	Country getCountryById(Long id);
	void updateCurrencyExchange(CurrencyExchange currencyExchanged);
	void updateCurrencyRate(CurrencyRate currencyRate);
	void updateCountry(Country country);
	void deleteCountry(Country country);
	void deleteCurrencyExchange(CurrencyExchange currencyExchanged);
	void deleteCurrencyRate(CurrencyRate rate);
	List<CurrencyExchange> getAllCurrencies();
	List<CurrencyRate> getAllRates();
	List<Country> getAllCountries();
	CurrencyRate getRateByDateAndCode(Date date, String code);
	CurrencyRate getRateForCountryByDateAndCode(CountryDto countryDto);
}
