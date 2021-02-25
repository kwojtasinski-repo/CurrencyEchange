package unit.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.File;

import org.junit.Test;

import exception.CsvServiceException;
import implement.CsvService;

public class CsvServiceTests {
	
	@Test
	public void TestCsvService_getCodeByCountryName_shouldnt_return_currency_code() {
		//given
		File file = new File("C:\\Projects\\ExchangesRate\\src\\main\\java\\implement\\codes-all_csv.csv");
		CsvService csv = new CsvService(file);
		String countryName = "";
		String expectedMessage = "Check your file if " + countryName + " exists";
		//then
		assertThatThrownBy(() -> csv.getCodeByCountryName(countryName))
		.isInstanceOf(CsvServiceException.class)
		.hasMessage(expectedMessage, "message");
	}
	
	@Test
	public void TestCsvService_getCodeByCountryName_should_throw_csv_service_exception() {
		//given
		File file = new File("C:\\Projects\\ExchangesRate\\src\\main\\java\\implement\\codes");
		CsvService csv = new CsvService(file);
		String countryName = "";
		String expectedMessage = "Check if your file of location "+ file.getPath() +" exists";
		
		//then
		assertThatThrownBy(() -> csv.getCodeByCountryName(countryName))
		.isInstanceOf(CsvServiceException.class)
		.hasMessage(expectedMessage, "message");
	}
	
	@Test
	public void TestCsvService_getCodeByCountryName_should_return_currency_code() {
		//given
		File file = new File("C:\\Projects\\ExchangesRate\\src\\main\\java\\implement\\codes-all_csv.csv");
		CsvService csv = new CsvService(file);
		String countryName = "POLAND";
		String notExpectedCurrencyCode = "USD";
		String expectedCurrencyCode = "PLN";
		
		//when
		String currencyCode = csv.getCodeByCountryName(countryName);
		
		//then
		assertThat(currencyCode).isNotNull();
		assertThat(currencyCode.equals(expectedCurrencyCode));
		assertThat(!currencyCode.equals(notExpectedCurrencyCode));
	}
	
	@Test
	public void TestCsvService_getCountryByCurrencyName_shouldnt_return_country_name() {
		//given
		File file = new File("C:\\Projects\\ExchangesRate\\src\\main\\java\\implement\\codes-all_csv.csv");
		CsvService csv = new CsvService(file);
		String countryName = "T";
		String expectedMessage = "Check your file if " + countryName + " exists";

		//then
		assertThatThrownBy(() -> csv.getCountryByCurrencyName(countryName))
		.isInstanceOf(CsvServiceException.class)
		.hasMessage(expectedMessage, "message");
	}
	
	@Test
	public void TestCsvService_getCountryByCurrencyName_should_throw_csv_service_exception() {
		//given
		File file = new File("C:\\Projects\\ExchangesRate\\src\\main\\java\\implement\\codes");
		CsvService csv = new CsvService(file);
		String currencyCode = "";
		String expectedMessage = "Check if your file of location "+ file.getPath() +" exists";
		
		//then
		assertThatThrownBy(() -> csv.getCountryByCurrencyName(currencyCode))
		.isInstanceOf(CsvServiceException.class)
		.hasMessage(expectedMessage, "message");
	}
	
	@Test
	public void TestCsvService_getCodeByCountryName_should_return_country_name() {
		//given
		File file = new File("C:\\Projects\\ExchangesRate\\src\\main\\java\\implement\\codes-all_csv.csv");
		CsvService csv = new CsvService(file);
		String currencyCode = "PLN";
		String notExpectedCountryName = "CZECHIA";
		String expectedCountryName = "POLAND";
		
		//when
		String countryName = csv.getCountryByCurrencyName(currencyCode);
		
		//then
		assertThat(countryName).isNotNull();
		assertThat(countryName.equals(expectedCountryName));
		assertThat(!countryName.equals(notExpectedCountryName));
	}
}
