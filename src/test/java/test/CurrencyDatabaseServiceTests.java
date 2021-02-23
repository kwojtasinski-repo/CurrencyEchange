package test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.runners.MockitoJUnitRunner;

import entity.Country;
import entity.Currency;
import entity.CurrencyRate;
import implement.CurrencyDatabaseService;
import implement.FileService;
import repository.CurrencyRepositoryImpl;

@RunWith(MockitoJUnitRunner.class)
public class CurrencyDatabaseServiceTests {
	
	@Mock
	private CurrencyRepositoryImpl mockRepo;
	
	@Mock
	private FileService mockFileService;
	
	@Test
	public void should_return_currency_rate_from_db() throws ParseException {
		// given
		String countryName = "SWITZERLAND";
		String currencyCode = "CHF";
		String stringDate = "2021-02-16";
		BigDecimal rate = new BigDecimal("4.20");
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.GERMANY);
		Date date = format.parse(stringDate);
		java.sql.Date sqlDate = new java.sql.Date(date.getTime());
		Currency currency = new Currency();
		currency.setCurrencyCode(currencyCode);
		CurrencyRate currencyExpected = new CurrencyRate(currency, date, rate);
		when(mockRepo.getRateForCountryByDateAndCode(countryName, date, currencyCode)).thenReturn(currencyExpected);
		CurrencyDatabaseService service = new CurrencyDatabaseService(mockFileService, mockRepo, date);		
		
		// when
		CurrencyRate currencyRate = service.getExchangeRate(currencyCode, date);
		
		// then
		assertThat(currencyRate).isNotNull();
		assertThat(currencyRate.getCurrency().getCurrencyCode()).isEqualTo(currencyCode);
		assertThat(currencyRate.getCurrencyDate()).isEqualTo(sqlDate);
		assertThat(currencyRate.getCurrencyRate()).isEqualTo(rate);
	}
	
	@Test
	public void shouldnt_return_currency_rate_from_db() throws ParseException {
		// given
		String currencyCode = "EUR";
		String stringDate = "2021-02-16";
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.GERMANY);
		Date date = format.parse(stringDate);
		CurrencyDatabaseService service = new CurrencyDatabaseService(mockFileService, mockRepo, date);
		when(mockFileService.getExchangeRate(currencyCode, date)).thenReturn(null);
		
		// when
		CurrencyRate rate = service.getExchangeRate(currencyCode, date);
		
		// then
		assertThat(rate).isNull();
	}
	
	@Test
	public void find_rate_for_country_by_date_and_currency_code() throws ParseException {
		String countryName = "AUSTRIA";
		String currencyCode = "EUR";
		String stringDate = "2020-12-24";
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.GERMANY);
		Date date = format.parse(stringDate);
		CurrencyRepositoryImpl repo = new CurrencyRepositoryImpl();

		CurrencyRate currencyRate = repo.getRateForCountryByDateAndCode(countryName, date, currencyCode);
		
		assertThat(currencyRate).isNotNull();
	}
	
	@Test//findMaxAndMinRate
	public void find_max_and_min_value_for_currency_on_period() throws ParseException {
		String stringDate = "2021-02-22";
		String stringDate2 = "2020-12-24";
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.GERMANY);
		Date dateFrom = format.parse(stringDate2);
		Date dateTo = format.parse(stringDate);
		CurrencyRepositoryImpl repo = new CurrencyRepositoryImpl();

		Object results = repo.findMaxAndMinRate(dateFrom, dateTo);
		
		
		assertThat(results).isNotNull();
	}
	
	@Test
	public void find_five_rates_currency_for_plus_and_minus() {
		String currencyCode = "EUR";
		CurrencyRepositoryImpl repo = new CurrencyRepositoryImpl();

		List<CurrencyRate> rates = repo.findFiveBestRatesForPlusAndMinus(currencyCode);
		
		assertThat(rates.size()).isNotEqualTo(0);
	}
	
	@Test//findMaxAndMinRate
	public void find_counties_with_amount_currencies() {
		int countriesAmount = 2;
		CurrencyRepositoryImpl repo = new CurrencyRepositoryImpl();

		List<Country> countries = repo.findCountryWithCurrencies(countriesAmount);
		
		assertThat(countries.size()).isNotEqualTo(0);
	}
	
	@Test
	public void find_rates_with_with_highest_difference_in_period() throws ParseException {
		String stringDate = "2021-02-22";
		String stringDate2 = "2020-12-24";
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.GERMANY);
		Date dateFrom = format.parse(stringDate2);
		Date dateTo = format.parse(stringDate);
		CurrencyRepositoryImpl repo = new CurrencyRepositoryImpl();

		List<Object> rates = repo.findRatesWithHigherDifferencePeriod(dateFrom, dateTo);
		
		assertThat(rates.size()).isNotEqualTo(0);
	}
}
