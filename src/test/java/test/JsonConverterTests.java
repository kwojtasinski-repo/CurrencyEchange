package test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
		
		//when
		CurrencyRate currencyRate = json.getCurrencyRate(data);
		
		//then
		assertThat(currencyRate).isNull();
	}
}