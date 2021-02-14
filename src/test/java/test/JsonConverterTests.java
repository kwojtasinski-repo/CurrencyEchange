package test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

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
}