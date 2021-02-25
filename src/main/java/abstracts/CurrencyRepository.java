package abstracts;

import java.util.Date;
import java.util.List;

import entity.Country;
import entity.Currency;
import entity.CurrencyCountry;
import entity.CurrencyExchange;
import entity.CurrencyExchangeKey;
import entity.CurrencyRate;

public interface CurrencyRepository {
	CurrencyExchangeKey addCurrencyExchange(CurrencyExchange currencyExchanged);
	Long addCountry(Country country);
	Long addRate(CurrencyRate rate);
	CurrencyRate getRateById(Long id);
	CurrencyExchange getCurrencyExchangeById(Long id);
	Country getCountryById(Long id);
	void updateCurrencyExchange(CurrencyExchange currencyExchanged);
	void updateCurrencyRate(CurrencyRate currencyRate);
	void updateCountry(Country country);
	void deleteCountry(Country country);
	void deleteCurrencyExchange(CurrencyExchange currencyExchanged);
	void deleteCurrencyRate(CurrencyRate rate);
	List<Currency> getAllCurrencies();
	List<CurrencyExchange> getAllCurrenciesExchanged();
	List<CurrencyCountry> getAllCurrenciesCountries();
	List<CurrencyRate> getAllRates();
	List<Country> getAllCountries();
	CurrencyRate getRateByDateAndCode(Date date, String code);
	CurrencyRate getRateForCountryByDateAndCode(String countryName, Date date, String currencyCode);
	Country getCountryByCountryName(String countryName);
	Currency getCurrencyByCode(String currencyCode);
	CurrencyCountry addCurrencyCountry(CurrencyCountry currencyCountry);
	Long addCurrency(Currency currency);
	Currency getCurrencyById(Long id);
	CurrencyCountry getCurrencyCountryById(Long id); 	
	void updateCurrency(Currency currency);
	void updateCurrencyCountry(CurrencyCountry currencyCountry); 
	void deleteCurrency(Currency currency);
	void deleteCurrencyCountry(CurrencyCountry currencyCountry);
}
