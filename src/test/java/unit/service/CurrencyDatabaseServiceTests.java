package unit.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import dao.CurrencyRepositoryImpl;
import entity.Country;
import entity.Currency;
import entity.CurrencyRate;
import implement.CurrencyDatabaseService;
import implement.FileService;

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
	public void should_add_currency_exchange() {
		Long countryId = 1L;
		Long currencyId = 1L;
		BigDecimal currencyRate = new BigDecimal("3.7512");
		Country country = new Country();
		country.setCountryId(countryId);
		country.setCountryName("DE");
		CurrencyRate rate = new CurrencyRate();
		rate.setCurrencyId(currencyId);
		rate.setCurrencyRate(currencyRate);
		CurrencyDatabaseService mock = mock(CurrencyDatabaseService.class);
		
		mock.addCountryWithRate(country, rate);
		
		verify(mock, atLeast(1)).addCountryWithRate(country, rate);
	}
}
