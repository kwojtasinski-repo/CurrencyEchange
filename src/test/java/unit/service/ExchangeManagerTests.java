package unit.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import abstracts.CountryConverter;
import abstracts.Service;
import entity.Currency;
import entity.CurrencyRate;
import exception.CurrencyNotFound;
import exception.DateException;
import implement.CurrencyDatabaseService;
import implement.ExchangeManager;
import implement.ExchangeWebServiceNBP;
import implement.JsonConverter;
import repository.CurrencyRepositoryImpl;

/**
 * Unit test for simple App.
 */
@RunWith(MockitoJUnitRunner.class)
public class ExchangeManagerTests {

	@Mock
	private JsonConverter json;

	@Mock
	private Service mockService;

	@Mock
	private CurrencyRepositoryImpl mockRepo;

	@Mock
	private CountryConverter mockCountryConverter;

	@Rule
	public final ExpectedException exception = ExpectedException.none();

	/**
	 * Rigorous Test :-)
	 */
	@Test
	public void shouldAnswerWithTrue() {
		assertTrue(true);
	}

	@Test
	public void should_be_invalid_currency() {
		// given
		String currencyCode = "T";
		ExchangeManager manager = new ExchangeManager(mockService);

		// then
		assertThatThrownBy(() -> manager.checkCurrency(currencyCode)).isInstanceOf(IllegalArgumentException.class)
				.hasMessage("Invalid currency code", "message");
	}

	@Test
	public void shouldnt_found_currency() throws ParseException {
		// given
		String currencyCode = "TSF";
		ExchangeManager manager = new ExchangeManager(mockService);

		// then
		assertThatThrownBy(() -> manager.checkCurrency(currencyCode)).isInstanceOf(CurrencyNotFound.class)
				.hasMessage("Enter currency code properly", "message");
	}

	@Test
	public void shouldnt_accept_date() throws ParseException {
		// given
		String dateStringAfter = "2121-02-10";
		String dateStringBefore = "1921-02-04";
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.GERMANY);
		final Date date = format.parse(dateStringBefore);
		Date dateBefore = format.parse("2002-01-02");
		ExchangeManager manager = new ExchangeManager(mockService);
		when(mockService.getLastCurrencyRateDate()).thenReturn(dateBefore);

		// then
		assertThatThrownBy(() -> manager.checkDate(null)).isInstanceOf(IllegalArgumentException.class)
				.hasMessage("Invalid date", "message");
		assertThatThrownBy(() -> manager.checkDate(date)).isInstanceOf(DateException.class)
				.hasMessage("Date cannot be before " + dateBefore, "message");
		assertThatThrownBy(() -> manager.checkDate(format.parse(dateStringAfter))).isInstanceOf(DateException.class)
				.hasMessage("Date cannot be after " + new SimpleDateFormat("yyyy-MM-dd").format(new Date()), "message");
	}

	@Test
	public void shouldnt_return_exchanged_currency() throws ParseException {
		// given
		String currencyCode = "CHF";
		String dateCurrency = "2021-02-15";
		String lastDateCurrency = "2021-02-15";
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.GERMANY);
		Date date = format.parse(dateCurrency);
		Date lastDate = format.parse(lastDateCurrency);
		ExchangeWebServiceNBP currencyRate = new ExchangeWebServiceNBP(json, lastDate);
		ExchangeManager manager = new ExchangeManager(currencyRate);
		
		// then
		assertThatThrownBy(() -> manager.exchangeCurrencyToPLN(currencyCode, date)).isInstanceOf(CurrencyNotFound.class)
		.hasMessage("Check your file if date of currency rate exists or check currency code. If exists check method getCurrencyRate if return correct in class defined structure of file", "message");
	}

	@Test
	public void should_return_currency() throws ParseException {
		// given
		String countryName = "SWITZERLAND";
		String currencyCode = "CHF";
		String dateCurrency = "2021-02-10";
		String lastDateCurrency = "2021-02-09";
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.GERMANY);
		Date date = format.parse(dateCurrency);
		Date lastDate = format.parse(lastDateCurrency);
		BigDecimal rate = new BigDecimal("4.20");
		Currency currency = new Currency();
		currency.setCurrencyCode(currencyCode);
		CurrencyRate currencyRateExpected = new CurrencyRate(currency, date, rate);
		when(mockRepo.getRateForCountryByDateAndCode(countryName, lastDate, currencyCode)).thenReturn(currencyRateExpected);
		CurrencyDatabaseService dbService = new CurrencyDatabaseService(mockService, mockRepo, lastDate);
		ExchangeManager manager = new ExchangeManager(dbService);

		// when
		CurrencyRate currencyRateActual = manager.exchangeCurrencyToPLN(currencyCode, lastDate);

		// then
		assertThat(currencyRateActual).isNotNull();
		assertThat(currencyRateActual.getCurrency().getCurrencyCode()).isEqualTo(currencyRateExpected.getCurrency().getCurrencyCode());
		assertThat(currencyRateActual.getCurrencyDate()).isEqualTo(currencyRateExpected.getCurrencyDate());
		assertThat(currencyRateActual.getCurrencyRate()).isEqualTo(currencyRateExpected.getCurrencyRate());
	}
}
