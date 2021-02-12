package abstracts;

import java.sql.Date;
import java.util.List;

import entity.CurrencyExchange;
import entity.CurrencyRate;

public interface CurrencyExchangeRateRepository {
	Long addCurrency(CurrencyExchange currencyExchanged);
	Long addRate(CurrencyRate rate);
	CurrencyRate getRateById(Long id);
	CurrencyExchange getCurrencyById(Long id);
	void updateCurrencyExchange(CurrencyExchange currencyExchanged);
	void updateCurrencyRate(CurrencyRate currencyRate);
	void deleteCurrencyExchange(CurrencyExchange currencyExchanged);
	void deleteCurrencyRate(CurrencyRate rate);
	List<CurrencyExchange> getAllCurrencies();
	List<CurrencyRate> getAllRates();
	CurrencyRate getRateByDateAndCode(Date date, String code);
}
