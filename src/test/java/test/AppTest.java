package test;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import exception.CurrencyNotFound;
import exception.DateException;
import exception.UncheckedIOException;
import implement.SalesDocumentService;

/**
 * Unit test for simple App.
 */
public class AppTest {
	@Rule
    public final ExpectedException exception = ExpectedException.none();
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void shouldBeInvalidCurrency() {
    	SalesDocumentService documentService = new SalesDocumentService();
    	String currencyCode = "T";
    	String string = "2021-02-08"; 
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.GERMANY);
		Date date = new Date();
		try {
			date = format.parse(string);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        //exception.expect(IllegalArgumentException.class);
		BigDecimal moneyExchanged = documentService.insert(new BigDecimal("100.00"), currencyCode, date);
    }
    
    @Test(expected=CurrencyNotFound.class)
    public void shouldntFoundCurrency() {
    	SalesDocumentService documentService = new SalesDocumentService();
    	String currencyCode = "TSW";
    	String string = "2021-02-08"; 
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.GERMANY);
		Date date = new Date();
		try {
			date = format.parse(string);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        //exception.expect(IllegalArgumentException.class);
		BigDecimal moneyExchanged = documentService.insert(new BigDecimal("100.00"), currencyCode, date);
    }
    
    @Test(expected=DateException.class)
    public void shouldntAcceptDate() {
    	SalesDocumentService documentService = new SalesDocumentService();
    	String currencyCode = "chf";
    	String dateStringAfter = "2021-02-10"; 
    	String dateStringBefore = "2021-02-04"; 
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.GERMANY);
		Date date = new Date();
		try {
			date = format.parse(dateStringBefore);
			//date = format.parse(dateStringBefore);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        //exception.expect(IllegalArgumentException.class);
		BigDecimal moneyExchanged = documentService.insert(new BigDecimal("100.00"), currencyCode, date);
    }
}
