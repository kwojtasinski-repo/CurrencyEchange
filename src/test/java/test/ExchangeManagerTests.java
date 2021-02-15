package test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
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

import abstracts.CountryConverter;
import abstracts.CurrencyExchangeMapper;
import abstracts.CurrencyExchangeRateRepository;
import abstracts.DataConverter;
import abstracts.Service;
import common.CountryDto;
import common.ExchangeRateDto;
import common.ExchangedCurrencyDto;
import entity.Country;
import entity.CurrencyExchange;
import entity.CurrencyExchangeKey;
import entity.CurrencyRate;
import exception.CurrencyNotFound;
import exception.DateException;
import implement.CsvService;
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
	public void shouldBeInvalidCurrency() {
		// given
		String currencyCode = "T";
		ExchangeManager manager = new ExchangeManager(mockService, json, mockRepo);

		// then
		assertThatThrownBy(() -> manager.checkCurrency(currencyCode)).isInstanceOf(IllegalArgumentException.class)
				.hasMessage("Invalid currency code", "message");
	}

	@Test
	public void shouldntFoundCurrency() throws ParseException {
		// given
		String currencyCode = "TSF";
		ExchangeManager manager = new ExchangeManager(mockService, json, mockRepo);

		// then
		assertThatThrownBy(() -> manager.checkCurrency(currencyCode)).isInstanceOf(CurrencyNotFound.class)
				.hasMessage("Enter currency code properly", "message");
	}

	@Test
	public void shouldntAcceptDate() throws ParseException {
		// given
		String dateStringAfter = "2121-02-10";
		String dateStringBefore = "1921-02-04";
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.GERMANY);
		final Date date = format.parse(dateStringBefore);
		Date dateBefore = format.parse("2002-01-02");
		ExchangeManager manager = new ExchangeManager(mockService, json, mockRepo);
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
	public void shouldntReturnExchangedCurrencyFromMockedConverterReturningNull() throws ParseException {
		// given
		String currencyCode = "CHF";
		String dateCurrency = "2021-02-15";
		String lastDateCurrency = "2021-02-15";
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
				.hasMessage("Check your file if currency code " + currencyCode.toUpperCase() + " for date " + date
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

	@Test
	public void shouldntExchangeCurrencyToPLNByCountryName() throws ParseException {
		// given
		String countryName = "Germany";
		String currencyCode = "EUR";
		String stringDate = "2021-02-10";
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.GERMANY);
		Date dateArchival = format.parse(stringDate);
		BigDecimal money = new BigDecimal("100");
		BigDecimal moneyExchanged = new BigDecimal("441.26");
		CountryDto currencyRateDto = new CountryDto();
		currencyRateDto.setCurrencyCode(currencyCode);
		currencyRateDto.setCurrencyDate(new java.sql.Date(dateArchival.getTime()));
		currencyRateDto.setCountryName(countryName);
		currencyRateDto.setCurrencyExchanged(moneyExchanged);
		currencyRateDto.setCurrencyToExchange(money);
		when(mockCountryConverter.getCodeByCurrencyName(countryName)).thenReturn(currencyRateDto);
		when(mockRepo.getRateByDateAndCode(currencyRateDto.getCurrencyDate(), currencyRateDto.getCurrencyCode()))
				.thenReturn(null);
		when(mockService.getExchangeRate(currencyCode, dateArchival)).thenReturn("");
		when(mockService.getLastCurrencyRateDate()).thenReturn(dateArchival);
		DataConverter json = new JsonConverter();
		ExchangeManager manager = new ExchangeManager(mockService, json, mockRepo, mockCountryConverter);

		// then
		assertThatThrownBy(() -> manager.exchangeCurrencyToPLNByCountryName(countryName, dateArchival, money))
				.isInstanceOf(CurrencyNotFound.class)
				.hasMessage("Check your file if currency code " + currencyCode + " for date " + dateArchival
						+ " exists or check if currency code exists. If exists check method getCurrencyRate "
						+ "if return correct in class defined structure of file", "message");
	}

	@Test
	public void shouldExchangeCurrencyToPLNByCountryName() throws ParseException {
		// given
		String countryName = "Germany";
		String currencyCode = "EUR";
		String stringDate = "2021-02-10";
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.GERMANY);
		Date dateArchival = format.parse(stringDate);
		ExchangeWebServiceNBP service = new ExchangeWebServiceNBP(dateArchival);
		DataConverter jsonConverter = new JsonConverter();
		BigDecimal money = new BigDecimal("100");
		BigDecimal moneyExchanged = new BigDecimal("441.26");
		BigDecimal rate = new BigDecimal("4.4126");
		Long id = 1L;
		CurrencyRate currencyRate = new CurrencyRate();
		currencyRate.setCurrencyCode(currencyCode);
		currencyRate.setCurrencyDate(new java.sql.Date(dateArchival.getTime()));
		currencyRate.setCurrencyId(id);
		currencyRate.setCurrencyRate(rate);
		CountryDto countryDto = new CountryDto();
		countryDto.setCurrencyCode(currencyCode);
		countryDto.setCurrencyDate(new java.sql.Date(dateArchival.getTime()));
		countryDto.setCountryName(countryName);
		countryDto.setCurrencyExchanged(moneyExchanged);
		countryDto.setCurrencyToExchange(money);
		when(mockCountryConverter.getCodeByCurrencyName(countryName)).thenReturn(countryDto);
		when(mockRepo.getRateByDateAndCode(new java.sql.Date(dateArchival.getTime()), currencyCode)).thenReturn(currencyRate);
		Country country = CurrencyExchangeMapper.INSTANCE.mapToCountry(countryDto);
		when(mockRepo.addCountry(country)).thenReturn(id);
		CurrencyExchangeKey currencyExchangeKey = new CurrencyExchangeKey(id, id);
		CurrencyExchange currencyExchange = new CurrencyExchange(currencyExchangeKey, country, currencyRate);
		when(mockRepo.addCurrencyExchange(currencyExchange)).thenReturn(currencyExchangeKey);
		ExchangeManager manager = new ExchangeManager(service, jsonConverter, mockRepo, mockCountryConverter);

		// when
		BigDecimal moneyExch = manager.exchangeCurrencyToPLNByCountryName(countryName, dateArchival, money);

		// then
		assertThat(moneyExch).isNotNull();
		assertThat(moneyExch).isEqualTo(money.multiply(rate).setScale(2, RoundingMode.HALF_EVEN));
	}
}
