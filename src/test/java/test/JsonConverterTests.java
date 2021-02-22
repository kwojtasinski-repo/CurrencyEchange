package test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import entity.CurrencyRate;
import exception.ParsingExchangeRate;
import implement.JsonConverter;

public class JsonConverterTests {
//getCurrencyRate -> json invalid string
	@Rule
    public final ExpectedException exception = ExpectedException.none();

	@Test
	public void shouldnt_convert_to_object() {
		//given
		JsonConverter json = new JsonConverter();
		String data = "	{tst}";
		String exceptionMessage = "While conversion error occurred. Please check your data format";
		
		//then
		assertThatThrownBy(() -> json.getCurrencyRate(data)).isInstanceOf(ParsingExchangeRate.class).hasMessage(exceptionMessage, "message");
	}
	
	@Test
	public void should_throw_parsing_exchange_rate() {
		//given
		JsonConverter json = new JsonConverter();
		String data = "abc";
		String exceptionMessage = "While conversion error occurred. Please check your data format";
		
		//then
		assertThatThrownBy(() -> json.getCurrencyRate(data)).isInstanceOf(ParsingExchangeRate.class).hasMessage(exceptionMessage, "message");
	}
	
	@Test
	public void should_return_null() {
		//given
		JsonConverter json = new JsonConverter();
		String data = "";
		String exceptionMessage = "While conversion error occurred. Please check your data format";
		
		//then
		assertThatThrownBy(() -> json.getCurrencyRate(data)).isInstanceOf(ParsingExchangeRate.class).hasMessage(exceptionMessage, "message");
	}
	
	@Test
	public void should_convert_to_object() throws ParseException {
		//given
		JsonConverter json = new JsonConverter();
		String currencyCode = "GBP";
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.GERMANY);
		Date currencyDate = format.parse("2020-12-17");
		BigDecimal currencyRate = new BigDecimal("4.9251");
		String data = "{\"table\":\"A\",\"currency\":\"funt szterling\",\"code\":\"GBP\",\"rates\":[{\"no\":\"246/A/NBP/2020\",\"effectiveDate\":\"2020-12-17\",\"mid\":4.9251}]}\r\n";
	
		CurrencyRate rate = json.getCurrencyRate(data);
		
		//then
		assertThat(rate).isNotNull();
		assertThat(rate.getCurrencyCode()).isEqualTo(currencyCode);
		assertThat(rate.getCurrencyDate()).isEqualTo(currencyDate);
		assertThat(rate.getCurrencyRate()).isEqualTo(currencyRate);
	}
}