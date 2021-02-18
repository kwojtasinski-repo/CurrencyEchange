package test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.File;

import org.junit.Test;

import exception.CsvServiceException;
import implement.CsvService;

public class CsvServiceTests {
	
	@Test
	public void shouldnt_return_country_name() {
		//given
		File file = new File("C:\\Projects\\ExchangesRate\\src\\main\\java\\implement\\codes-all_csv.csv");
		CsvService csv = new CsvService(file);
		String countryName = "";
		String expectedMessage = "Check your file if " + countryName + " exists";
		
		//then
		assertThatThrownBy(() -> csv.getCodeByCurrencyName(countryName))
		.isInstanceOf(CsvServiceException.class)
		.hasMessage(expectedMessage, "message");
	}
	
	@Test
	public void should_throw_csv_service_exception() {
		//given
		File file = new File("C:\\Projects\\ExchangesRate\\src\\main\\java\\implement\\codes");
		CsvService csv = new CsvService(file);
		String countryName = "";
		String expectedMessage = "Check if your file of location "+ file.getPath() +" exists";
		
		//then
		assertThatThrownBy(() -> csv.getCodeByCurrencyName(countryName))
		.isInstanceOf(CsvServiceException.class)
		.hasMessage(expectedMessage, "message");
	}
	
	@Test
	public void should_return_country_name() {
		//given
		File file = new File("C:\\Projects\\ExchangesRate\\src\\main\\java\\implement\\codes-all_csv.csv");
		CsvService csv = new CsvService(file);
		String countryName = "Poland";
		String notExpectedCurrencyCode = "USD";
		String expectedCurrencyCode = "PLN";
		
		//when
		String currencyCode = csv.getCodeByCurrencyName(countryName);
		
		//then
		assertThat(currencyCode).isNot(null);
		assertThat(currencyCode.equals(expectedCurrencyCode));
		assertThat(!currencyCode.equals(notExpectedCurrencyCode));
	}
}
