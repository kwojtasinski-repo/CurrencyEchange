package test;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.internal.WhiteboxImpl;

import abstracts.DataConverter;
import exception.CurrencyExchangeHttpException;

import implement.ExchangeWebServiceNBP;
import implement.JsonConverter;

@RunWith(PowerMockRunner.class)
public class ExchangeWebServiceNBPTests {
    
	@Rule
	  public final ExpectedException exception = ExpectedException.none();
	
	@Test
    public void should_throw_timeout_exception() throws Exception {
		// given
        String methodName = "getResponseData";
        String url = "http://api.nbp.pl:6060/api/exchangerates/rates/A/CHF";
        URL restApiUrl = new URL(url);
    	HttpURLConnection apiConnection = (HttpURLConnection) restApiUrl.openConnection();
		apiConnection.setRequestMethod("GET");
		apiConnection.setDoOutput(true);
		apiConnection.setRequestProperty("Content-Type", "application/json");
		apiConnection.setRequestProperty("Accept", "application/json");
		apiConnection.setUseCaches(false);
		apiConnection.setConnectTimeout(5000);
		apiConnection.setReadTimeout(5000);
		DataConverter converter = new JsonConverter();
		ExchangeWebServiceNBP exchangeService = new ExchangeWebServiceNBP(converter, new Date());
		
		// then
		exception.expect(CurrencyExchangeHttpException.class);
		exception.expectMessage("Connection timed out. Check your url");
		WhiteboxImpl.invokeMethod(exchangeService, methodName, apiConnection);
    }
	
	@Test
    public void should_exception_message_invalid_url() throws Exception {
		//given
		String methodName = "checkResponseString";
		String inputString = "";
		DataConverter converter = new JsonConverter();
		ExchangeWebServiceNBP exchangeService = new ExchangeWebServiceNBP(converter, new Date());
		
		//then
		exception.expect(CurrencyExchangeHttpException.class);
		exception.expectMessage("Check url address if correct. Response from Api is null");
		WhiteboxImpl.invokeMethod(exchangeService, methodName, inputString);
    }
	
	@Test
    public void should_throw_exception_message404() throws Exception {
		//given
		String methodName = "checkResponseString";
		String inputString = "404 NotFound";
		DataConverter converter = new JsonConverter();
		ExchangeWebServiceNBP exchangeService = new ExchangeWebServiceNBP(converter, new Date());
		
		//then
		exception.expect(CurrencyExchangeHttpException.class);
		exception.expectMessage("Check url address if correct. Response from Api is null");
		WhiteboxImpl.invokeMethod(exchangeService, methodName, inputString);
    }
	
	@Test
    public void should_throw_exception_message400() throws Exception {
		//given
		String methodName = "checkResponseString";
		String inputString = "400 BadRequest";
		DataConverter converter = new JsonConverter();
		ExchangeWebServiceNBP exchangeService = new ExchangeWebServiceNBP(converter, new Date());
		
		//then
		exception.expect(CurrencyExchangeHttpException.class);
		exception.expectMessage("Check url address if correct. Response from Api is null");
		WhiteboxImpl.invokeMethod(exchangeService, methodName, inputString);
    }
	
	@Test 
    public void should_throw_exception_message_check_url() throws Exception {
		//given
		String methodName = "getApiConnection";
		String url = "ssdbsv"; 
		String exceptionMessage = "Please check your url address";
		DataConverter converter = new JsonConverter();
		ExchangeWebServiceNBP exchangeService = new ExchangeWebServiceNBP(converter, new Date());
		
		//then
		exception.expect(CurrencyExchangeHttpException.class);
		exception.expectMessage(exceptionMessage);
		WhiteboxImpl.invokeMethod(exchangeService, methodName, url);
    }
}
