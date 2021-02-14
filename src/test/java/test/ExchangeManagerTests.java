package test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.persistence.Column;

import org.apache.log4j.spi.RepositorySelector;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import abstracts.CurrencyExchangeMapper;
import abstracts.CurrencyExchangeRateRepository;
import abstracts.DataConverter;
import abstracts.Service;
import common.ExchangedCurrencyDto;
import entity.CurrencyExchange;
import entity.CurrencyRate;
import exception.CurrencyNotFound;
import exception.DateException;
import implement.ExchangeManager;
import implement.ExchangeWebServiceNBP;
import implement.JsonConverter;
import implement.SalesDocumentService;
import repository.CurrencyRepository;

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
	private CurrencyRepository mockRepo;

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
	public void shouldBeInvalidCurrency() {
		//given
		String currencyCode = "T";
		ExchangeManager manager = new ExchangeManager(mockService, json, mockRepo);

		//then
		assertThatThrownBy(() -> manager.checkCurrency(currencyCode)).isInstanceOf(IllegalArgumentException.class).hasMessage("Invalid currency code", "message");
	}

	@Test
	public void shouldntFoundCurrency() throws ParseException {
		//given
		String currencyCode = "TSF";
		ExchangeManager manager = new ExchangeManager(mockService, json, mockRepo);

		//then
		assertThatThrownBy(() -> manager.checkCurrency(currencyCode)).isInstanceOf(CurrencyNotFound.class).hasMessage("Enter currency code properly", "message");
	}

	@Test
	public void shouldntAcceptDate() throws ParseException {
		//given
		String dateStringAfter = "2121-02-10";
		String dateStringBefore = "1921-02-04";
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.GERMANY);
		final Date date = format.parse(dateStringBefore); 
		Date dateBefore = format.parse("2002-01-02");
		ExchangeManager manager = new ExchangeManager(mockService, json, mockRepo);
		when(mockService.getLastCurrencyRateDate()).thenReturn(dateBefore);
		 
		//then
		assertThatThrownBy(() -> manager.checkDate(null)).isInstanceOf(IllegalArgumentException.class).hasMessage("Invalid date", "message");
		assertThatThrownBy(() -> manager.checkDate(date)).isInstanceOf(DateException.class).hasMessage("Date cannot be before " + dateBefore, "message");
		assertThatThrownBy(() -> manager.checkDate(format.parse(dateStringAfter))).isInstanceOf(DateException.class).hasMessage("Date cannot be after " + new SimpleDateFormat("yyyy-MM-dd").format(new Date()), "message");
	}

	@Test
	public void shouldntReturnExchangedCurrencyFromMockedConverterReturningNull() throws ParseException {
		// given
		String currencyCode = "chf";
		String dateCurrency = "2021-02-10";
		String lastDateCurrency = "2021-02-09";
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.GERMANY);
		Date date = format.parse(dateCurrency);
		Date lastDate = format.parse(lastDateCurrency);
		ExchangeWebServiceNBP currencyRate = new ExchangeWebServiceNBP(lastDate);
		when(json.getCurrencyRate(currencyRate.getExchangeRate(currencyCode, date))).thenReturn(null);
		CurrencyRepository repo = new CurrencyRepository();
		BigDecimal cash = new BigDecimal("100.00");
		ExchangeManager manager = new ExchangeManager(currencyRate, json, repo);
		
		// then
		assertThatThrownBy(() -> manager.exchangeCurrencyToPLN(currencyCode, date, cash))
		.isInstanceOf(CurrencyNotFound.class)
		.hasMessage("Check your file if currency code " + currencyCode.toUpperCase() + " for date "+ date 
				+ " exists or check if currency code exists. If exists check method getCurrencyRate "
				+ "if return correct in class defined structure of file", "message");
	}

	@Test
	public void shouldReturnCurrencyFromMockedRepositoryReturningObject() throws ParseException {
		// given
		String currencyCode = "CHF";
		String dateCurrency = "2021-02-10";
		String lastDateCurrency = "2021-02-09";
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.GERMANY);
		Date date = format.parse(dateCurrency);
		Date lastDate = format.parse(lastDateCurrency);
		ExchangeWebServiceNBP currencyRate = new ExchangeWebServiceNBP(lastDate);
		DataConverter jsonConverter = new JsonConverter();
		CurrencyRate curRate = new CurrencyRate();
		curRate.setCurrencyId(1L);
		curRate.setCurrencyCode(currencyCode);
		curRate.setCurrencyDate(new java.sql.Date(date.getTime()));
		curRate.setCurrencyRate(new BigDecimal("4.20"));
		when(mockRepo.getRateByDateAndCode(new java.sql.Date(lastDate.getTime()), currencyCode)).thenReturn(curRate);		
		BigDecimal cash = new BigDecimal("100.00");
		ExchangeManager manager = new ExchangeManager(currencyRate, jsonConverter, mockRepo);
		
		// when
		ExchangedCurrencyDto currency = manager.exchangeCurrencyToPLN(currencyCode, lastDate, cash);
		
		// then
		assertThat(currency).isNotNull();
		assertThat(currency.getCurrencyCode()).isEqualTo(curRate.getCurrencyCode());
		assertThat(currency.getCurrencyDate()).isEqualTo(new Date(curRate.getCurrencyDate().getTime()));
		assertThat(currency.getCurrencyRate()).isEqualTo(curRate.getCurrencyRate());
	}
	
	/*
	@Test // mapper test
	public void shouldMapExchangedCurrencyToCurrencyExchange() {
		// given
		Date date = new Date();
		ExchangedCurrency exchangedCurrency = new ExchangedCurrency("EUR", date, new BigDecimal("5000.00"),
				new BigDecimal("22404.50"), new BigDecimal("4.48"), "PLN");

		// when
		CurrencyExchange currencyExchange = CurrencyExchangeMapper.INSTANCE.mapToCurrencyExchange(exchangedCurrency);

		// then
		assertThat(currencyExchange).isNotNull();
		assertThat(currencyExchange.getCurrencyCode()).isEqualTo("EUR");
		assertThat(currencyExchange.getCurrencyDate()).isEqualTo(date);
		assertThat(currencyExchange.getCurrencyToExchange()).isEqualTo(new BigDecimal("5000.00"));
	}*/
}
