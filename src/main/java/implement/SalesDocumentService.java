package implement;

import java.io.File;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import abstracts.CountryConverter;
import abstracts.DataConverter;
import abstracts.Service;
import common.CurrencyCodesWithCountries;
import repository.CurrencyRepository;

public class SalesDocumentService {

	public void countryExchange(String countryName, BigDecimal money, Date date) {
		File file = new File("C:\\Projects\\ExchangesRate\\src\\main\\java\\implement\\codes-all_csv.csv");
		CountryConverter csvService = new CsvService(file);
		String string = "2002-01-02"; // 2021-02-05   2002-01-02
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.GERMANY);
		Date dateArchival = new Date(1000L);
		try {
			dateArchival = format.parse(string);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Service serviceNBP = new ExchangeWebServiceNBP(dateArchival);
		DataConverter json = new JsonConverter();
		CurrencyRepository repository = new CurrencyRepository();
		ExchangeManager manager = new ExchangeManager(serviceNBP, json, repository, csvService);
		System.out.println(manager.exchangeCurrencyToPLNByCountryName(countryName, date, money));
	}
	
	public BigDecimal insert(BigDecimal money, String currencyCode, Date date) {
		// TODO Auto-generated method stub
		String string = "2002-01-02"; // 2021-02-05   2002-01-02
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.GERMANY);
		Date dateArchival = new Date(1000L);
		try {
			dateArchival = format.parse(string);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//File file = new File("C:\\Projects\\ExchangeRate\\src\\main\\java\\Implement\\CurrencyJson.json");
		Service serviceNBP = new ExchangeWebServiceNBP(dateArchival);
		DataConverter json = new JsonConverter();
		CurrencyRepository repository = new CurrencyRepository();
		ExchangeManager manager = new ExchangeManager(serviceNBP, json, repository);
		
		BigDecimal currency = manager.exchangeCurrencyToPLN(currencyCode, date, money).getCurrencyExchanged();
		return currency;
	}
	
	public static void main(String[] args) {
		SalesDocumentService s = new SalesDocumentService();
		String string = "2021-02-10"; // 2020-12-27
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.GERMANY);
		Date date = new Date(1000L);
		try {
			date = format.parse(string);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		System.out.println(s.insert(new BigDecimal("100"), "aud", date));
		s.countryExchange("Germany", new BigDecimal("100"), date);
	}
}
