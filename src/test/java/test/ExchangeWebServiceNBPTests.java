package test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.powermock.api.support.membermodification.MemberMatcher.method;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.powermock.api.mockito.PowerMockito.doReturn;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.easymock.Mock;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mapstruct.ap.internal.conversion.JavaLocalDateTimeToDateConversion;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.internal.WhiteboxImpl;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

import abstracts.DataConverter;
import entity.CurrencyRate;
import exception.CurrencyExchangeHttpException;

import implement.ExchangeWebServiceNBP;
import implement.JsonConverter;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ExchangeWebServiceNBP.class)
public class ExchangeWebServiceNBPTests {

	private ExchangeWebServiceNBP nbpMock;

	public HttpURLConnection setUrl(String url) throws MalformedURLException, IOException {
        URL restApiUrl = new URL(url);
    	HttpURLConnection apiConnection = (HttpURLConnection) restApiUrl.openConnection();
		return apiConnection;
	}
	
	public void setupConnection(HttpURLConnection apiConnection, String format) throws ProtocolException, MalformedURLException, IOException {
		apiConnection.setRequestMethod("GET");
		apiConnection.setDoOutput(true);
		apiConnection.setRequestProperty("Content-Type", "application/" + format);
		apiConnection.setRequestProperty("Accept", "application/" + format);
		apiConnection.setUseCaches(false);
		apiConnection.setConnectTimeout(1000);
		apiConnection.setReadTimeout(1000);
	}
	
	@Before
	public void setUp() throws ParseException {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.GERMANY);
		Date currencyDate = format.parse("2021-02-14");
		DataConverter converter = new JsonConverter();
		nbpMock = PowerMockito.spy(new ExchangeWebServiceNBP(converter, currencyDate));
	}
	
	@Rule
	  public final ExpectedException exception = ExpectedException.none();
	
	@Mock
	private ExchangeWebServiceNBP mockServiceNBP;
	
	@Test
    public void should_throw_timeout_exception() throws Exception {
		// given api connection place in funtion?
		String methodName = "getResponseData";
        String url = "http://api.nbp.pl:6060/api/exchangerates/rates/A/CHF";
        HttpURLConnection apiConnection = setUrl(url);
        String format = "json";
        setupConnection(apiConnection, format);
		DataConverter converter = new JsonConverter();
		ExchangeWebServiceNBP exchangeService = new ExchangeWebServiceNBP(converter, new Date());
		
		// then
		exception.expect(CurrencyExchangeHttpException.class);
		exception.expectMessage("Connection timed out. Check your url");
		WhiteboxImpl.invokeMethod(exchangeService, methodName, apiConnection);
    }
	
	@Test
    public void should_throw_exception_message_invalid_url() throws Exception {
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
    public void should_throw_exception_message400() throws CurrencyExchangeHttpException, Exception{
		//given
		String methodName = "checkResponseString";
		String inputString = "400 BadRequest";
		String exceptionMessage = "Check url address if correct. Response from Api is null";
		DataConverter converter = new JsonConverter();
		ExchangeWebServiceNBP exchangeService = new ExchangeWebServiceNBP(converter, new Date());
		
		//then
		exception.expect(CurrencyExchangeHttpException.class);
		exception.expectMessage(exceptionMessage);
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

	/*@Test 
    public void should_return_rate() throws Exception {
		//given		
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.GERMANY);
		Date currencyDate = format.parse("2020-12-18");
		String currencyCode = "EUR";
		DataConverter converter = new JsonConverter();
		ExchangeWebServiceNBP exchangeService = new ExchangeWebServiceNBP(converter, currencyDate);
		
		// when
		CurrencyRate rate = exchangeService.getExchangeRate(currencyCode, currencyDate);
		
		//then
		assertThat(rate).isNotNull();
		assertThat(rate.getCurrencyCode()).isEqualTo(currencyCode);
		assertThat(rate.getCurrencyDate()).isEqualTo(currencyDate);
    }
	
	@Test 
    public void should_return_null_rate() throws Exception {
		//given		
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.GERMANY);
		Date currencyDate = format.parse("2021-02-14");
		String currencyCode = "EUR";
		DataConverter converter = new JsonConverter();
		ExchangeWebServiceNBP exchangeService = new ExchangeWebServiceNBP(converter, currencyDate);
		
		// when
		CurrencyRate rate = exchangeService.getExchangeRate(currencyCode, currencyDate);
		
		//then
		assertThat(rate).isNull();
    }*/
	
	@Test
	public void should_return_rate() throws Exception {
		//given
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.GERMANY);
		String dateString = "2020-12-18";
		Date currencyDate = format.parse(dateString);
		String currencyCode = "EUR";
		String jsonString = "{\"table\":\"A\",\"currency\":\"euro\",\"code\":\"EUR\",\"rates\":[{\"no\":\"247/A/NBP/2020\",\"effectiveDate\":\"2020-12-18\",\"mid\":4.4493}]}\r\n";
		String url = "http://api.nbp.pl/api/exchangerates/rates/A/" + currencyCode + "/"+ dateString +"/";
        HttpURLConnection apiConnection = setUrl(url);
        String formatData = "json";
		doReturn(apiConnection).when(nbpMock, "getApiConnection", url);
		doReturn(formatData).when(nbpMock, "getFormat");
        setupConnection(apiConnection, formatData);	
		doReturn(jsonString).when(nbpMock, "getResponseData", apiConnection);
		
		//when
		CurrencyRate rate = nbpMock.getRateFromApi(currencyCode, currencyDate);

		//then
		assertThat(rate).isNotNull();
		assertThat(rate.getCurrencyCode()).isEqualTo(currencyCode);
		assertThat(rate.getCurrencyDate()).isEqualTo(currencyDate);
	}
	
	@Test 
    public void should_return_null_rate() throws Exception {
		//given
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.GERMANY);
		String dateString = "2020-12-18";
		Date currencyDate = format.parse(dateString);
		String currencyCode = "EUR";
		String jsonString = "";
		String formatData = "json";
		String url = "http://api.nbp.pl/api/exchangerates/rates/A/" + currencyCode + "/"+ dateString +"/";
        HttpURLConnection apiConnection = setUrl(url);
		doReturn(apiConnection).when(nbpMock, "getApiConnection", url);
		doReturn(formatData).when(nbpMock, "getFormat");
        setupConnection(apiConnection, formatData);
		doReturn(jsonString).when(nbpMock, "getResponseData", apiConnection);
		
		//when
		CurrencyRate rate = nbpMock.getRateFromApi(currencyCode, currencyDate);

		//then
		assertThat(rate).isNull();
    }
}
