package test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import common.ExchangeRateDto;
import exception.CurrencyExchangeHttpException;
import exception.ParsingExchangeRate;
import implement.JsonConverter;

public class JsonConverterTests {
//getCurrencyRate -> json invalid string
	@Rule
    public final ExpectedException exception = ExpectedException.none();

	@Test
	public void shouldntConvertToObject() {
		//given
		JsonConverter json = new JsonConverter();
		String data = "	{tst}";
		String exceptionMessage = "While conversion error occurred. Please check your data format";
		
		//then
		assertThatThrownBy(() -> json.getCurrencyRate(data)).isInstanceOf(ParsingExchangeRate.class).hasMessage(exceptionMessage, "message");
	}
	
	@Test
	public void shouldThrowParsingExchangeRate() {
		//given
		JsonConverter json = new JsonConverter();
		String data = "abc";
		String exceptionMessage = "While conversion error occurred. Please check your data format";
		
		//then
		assertThatThrownBy(() -> json.getCurrencyRate(data)).isInstanceOf(ParsingExchangeRate.class).hasMessage(exceptionMessage, "message");
	}
	
	@Test
	public void shouldReturnNull() {
		//given
		JsonConverter json = new JsonConverter();
		String data = "";
		
		//when
		ExchangeRateDto dto = json.getCurrencyRate(data);
		
		//then
		assertThat(dto).isNull();
	}
}