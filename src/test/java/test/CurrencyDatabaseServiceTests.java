package test;

import static org.assertj.core.api.Assertions.assertThat;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.junit.Test;

import entity.CurrencyRate;
import repository.CurrencyRepositoryImpl;

public class CurrencyDatabaseServiceTests {

	@Test
	public void should_return_currency_rate_from_db() throws ParseException {
		// given
		CurrencyRepositoryImpl repo = new CurrencyRepositoryImpl();
		String countryName = "SWITZERLAND";
		String currencyCode = "CHF";
		String stringDate = "2021-02-16";
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.GERMANY);
		Date date = format.parse(stringDate);
		
		// when
		CurrencyRate rate = repo.getRateForCountryByDateAndCode(countryName, date, currencyCode);
		
		// then
		assertThat(rate).isNotNull();
		assertThat(rate.getCurrencyCode()).isEqualTo(currencyCode);
		assertThat(rate.getCurrencyDate()).isEqualTo(date);
	}
	
	@Test
	public void shouldnt_return_currency_rate_from_db() throws ParseException {
		// given
		CurrencyRepositoryImpl repo = new CurrencyRepositoryImpl();
		String countryName = "Test";
		String currencyCode = "CHF";
		String stringDate = "2021-02-16";
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.GERMANY);
		Date date = format.parse(stringDate);
		
		// when
		CurrencyRate rate = repo.getRateForCountryByDateAndCode(countryName, date, currencyCode);
		
		// then
		assertThat(rate).isNull();
	}
}
