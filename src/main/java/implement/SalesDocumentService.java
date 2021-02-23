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
import entity.Country;
import entity.CurrencyRate;
import repository.CurrencyRepositoryImpl;

public class SalesDocumentService {
	
	public BigDecimal insert(BigDecimal money, String currencyCode, Date date) {
		// TODO Auto-generated method stub
		currencyCode = currencyCode.toUpperCase();
		String string = "2002-01-02"; // 2021-02-05   2002-01-02
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.GERMANY);
		Date dateArchival = new Date(1000L);
		try {
			dateArchival = format.parse(string);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		DataConverter json = new JsonConverter();
		Service serviceNBP = new ExchangeWebServiceNBP(json, dateArchival);
		FileConverter fileConverter = new FileConverter();
		FileService fileService = new FileService(serviceNBP, fileConverter, dateArchival);
		CurrencyRepositoryImpl repository = new CurrencyRepositoryImpl();
		CurrencyDatabaseService dbService = new CurrencyDatabaseService(fileService, repository, dateArchival);
		ExchangeManager manager = new ExchangeManager(dbService);
		CurrencyRate rate = manager.exchangeCurrencyToPLN(currencyCode, date);
		BigDecimal currency = money.multiply(rate.getCurrencyRate());
		return currency;
	}
	
	public static void main(String[] args) {
		SalesDocumentService s = new SalesDocumentService();
		String string = "2021-02-21"; // 2020-12-27
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.GERMANY);
		Date date = new Date(1000L);
		try {
			date = format.parse(string);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(s.insert(new BigDecimal("500"), "inr", date));
	}
}
