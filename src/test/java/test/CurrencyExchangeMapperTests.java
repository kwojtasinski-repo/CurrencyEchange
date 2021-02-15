package test;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.junit.Test;

import abstracts.CurrencyExchangeMapper;
import common.CountryDto;
import common.ExchangeRateDto;
import entity.Country;
import entity.CurrencyRate;

public class CurrencyExchangeMapperTests {

	@Test // mapper test
	public void shouldMapExchangedCurrencyToCurrencyExchange() throws ParseException {
		// given
		String dateCurrency = "2021-02-10";
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.GERMANY);
		Date date = format.parse(dateCurrency);
		BigDecimal curRate = new BigDecimal("4.5215");
		String currencyCode = "EUR";
		ExchangeRateDto dto = new ExchangeRateDto();
		dto.setCurrencyCode(currencyCode);
		dto.setCurrencyDate(date);
		dto.setCurrencyRate(curRate);

		// when
		CurrencyRate currencyRate = CurrencyExchangeMapper.INSTANCE.mapToCurrencyRate(dto);

		// then
		assertThat(currencyRate).isNotNull();
		assertThat(currencyRate.getCurrencyCode()).isEqualTo(currencyCode);
		assertThat(currencyRate.getCurrencyDate()).isEqualTo(date);
		assertThat(currencyRate.getCurrencyRate()).isEqualTo(curRate);
		assertThat(currencyRate.getCurrencyId()).isNull();
	}
	
	@Test
	public void shouldMapToExchangeRateDto() throws ParseException {
		// given
		String dateCurrency = "2021-02-10";
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.GERMANY);
		Date date = format.parse(dateCurrency);
		BigDecimal curRate = new BigDecimal("4.5215");
		CurrencyRate rate = new CurrencyRate();
		String currencyCode = "EUR";
		Long id = 1L;
		rate.setCurrencyId(id);
		rate.setCurrencyCode(currencyCode);
		rate.setCurrencyDate(new java.sql.Date(date.getTime()));
		rate.setCurrencyRate(curRate);

		// when
		ExchangeRateDto dto = CurrencyExchangeMapper.INSTANCE.mapToExchangeRateDto(rate);

		// then
		assertThat(dto).isNotNull();
		assertThat(dto.getCurrencyCode()).isEqualTo(currencyCode);
		assertThat(dto.getCurrencyDate()).isEqualTo(date);
		assertThat(dto.getCurrencyRate()).isEqualTo(curRate);
	}
	
	@Test
	public void shouldMapToCountry() throws ParseException {
		// given
		String dateCurrency = "2021-02-10";
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.GERMANY);
		Date date = format.parse(dateCurrency);
		BigDecimal money = new BigDecimal("100");
		BigDecimal moneyExchanged = new BigDecimal("450");
		CountryDto dto = new CountryDto();
		String countryName = "Poland";
		dto.setCountryName(countryName);
		dto.setCurrencyDate(new java.sql.Date(date.getTime()));
		dto.setCurrencyExchanged(moneyExchanged);
		dto.setCurrencyToExchange(money);

		// when
		Country country = CurrencyExchangeMapper.INSTANCE.mapToCountry(dto);

		// then
		assertThat(country).isNotNull();
		assertThat(country.getCountryName()).isEqualTo(countryName);
		assertThat(country.getCurrencyDate()).isEqualTo(new java.sql.Date(date.getTime()));
		assertThat(country.getCurrencyExchanged()).isEqualTo(moneyExchanged);
		assertThat(country.getCurrencyToExchange()).isEqualTo(money);
		assertThat(country.getCountryId()).isNull();
	}
	
	@Test
	public void shouldMapToCountryDto() throws ParseException {
		// given
		String dateCurrency = "2021-02-10";
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.GERMANY);
		Date date = format.parse(dateCurrency);
		BigDecimal money = new BigDecimal("100");
		BigDecimal moneyExchanged = new BigDecimal("450");
		Country country = new Country();
		String countryName = "Poland";
		Long id = 1L;
		country.setCountryId(id);
		country.setCountryName(countryName);
		country.setCurrencyDate(new java.sql.Date(date.getTime()));
		country.setCurrencyExchanged(moneyExchanged);
		country.setCurrencyToExchange(money);

		// when
		CountryDto dto = CurrencyExchangeMapper.INSTANCE.mapToCountryDto(country);

		// then
		assertThat(dto).isNotNull();
		assertThat(dto.getCountryName()).isEqualTo(countryName);
		assertThat(dto.getCurrencyDate()).isEqualTo(new java.sql.Date(date.getTime()));
		assertThat(dto.getCurrencyExchanged()).isEqualTo(moneyExchanged);
		assertThat(dto.getCurrencyToExchange()).isEqualTo(money);
	}
}
