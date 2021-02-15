package test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.File;

import org.junit.Test;

import common.CountryDto;
import exception.CsvServiceException;
import implement.CsvService;

public class CsvServiceTests {
	
	@Test
	public void shouldntReturnCountryDto() {
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
	public void shouldThrowCsvServiceException() {
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
	public void shouldReturnCountryDto() {
		//given
		File file = new File("C:\\Projects\\ExchangesRate\\src\\main\\java\\implement\\codes-all_csv.csv");
		CsvService csv = new CsvService(file);
		String countryName = "Poland";
		String expectedCurrencyCode = "PLN";
		
		//when
		CountryDto countryDto = csv.getCodeByCurrencyName(countryName);
		
		//then
		assertThat(countryDto.getCountryName().equals(countryName.toUpperCase()));
		assertThat(countryDto.getCurrencyCode().equals(expectedCurrencyCode));
	}
}
