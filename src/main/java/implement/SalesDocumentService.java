package implement;

import java.io.File;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SalesDocumentService {

	public BigDecimal insert(BigDecimal money, String currencyCode, Date date) {
		// TODO Auto-generated method stub
		String string = "2021-02-05"; // 2021-02-05   2002-01-02
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.GERMANY);
		Date dateArchival = new Date(1000L);
		try {
			dateArchival = format.parse(string);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		File file = new File("C:\\Projects\\ExchangeRate\\src\\main\\java\\Implement\\CurrencyJson.json");
		CurrencyRateFromNBPJson serviceNBPJson = new CurrencyRateFromNBPJson(dateArchival);
		CurrencyRateFromNBPXml serviceNBPXml = new CurrencyRateFromNBPXml(dateArchival);
		CurrencyRateFromFileJson fileServiceJson = new CurrencyRateFromFileJson(file, dateArchival);
		ExchangeManager manager = new ExchangeManager(fileServiceJson);
		
		BigDecimal currency = manager.exchangeCurrencyToPLN(currencyCode, date, money).getCurrencyExchanged();
		return currency;
	}
	
	public static void main(String[] args) {
		SalesDocumentService s = new SalesDocumentService();
		String string = "2021-02-08"; // 2020-12-27
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.GERMANY);
		Date date = new Date(1000L);
		try {
			date = format.parse(string);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(s.insert(new BigDecimal("500"), "chg", date));
	}
}
