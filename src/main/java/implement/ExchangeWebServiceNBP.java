package implement;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

import abstracts.DataConverter;
import abstracts.Service;
import entity.CurrencyRate;
import exception.CurrencyExchangeHttpException;
import exception.UncheckedIOException;

public class ExchangeWebServiceNBP implements Service {
	private Date lastCurrencyRateDate;
	private static final String RATE_URL = "http://api.nbp.pl/api/exchangerates/rates/A/";
	private String format;
	private DataConverter converter;
	
	public ExchangeWebServiceNBP(DataConverter converter, Date lastCurrencyRateDate) {
		// TODO Auto-generated constructor stub
		this.lastCurrencyRateDate = lastCurrencyRateDate;
		this.converter = converter;
		this.format = converter.getFormat();
	}
	
	@Override
	public Date getLastCurrencyRateDate() {
		// TODO Auto-generated method stub
		return lastCurrencyRateDate;
	}
	
	@Override 
	public CurrencyRate getExchangeRate(String currencyCode, Date date) {
		// TODO Auto-generated method stub
		return getRateFromApi(currencyCode, date);
	}
	
	
	
	public CurrencyRate getRateFromApi(String currencyCode, Date date) {
		try {
			String response = null;
			HttpURLConnection apiConnection = null;
			apiConnection = getApiConnection(RATE_URL + currencyCode + "/" + setDateFormat(date) + "/");
			setDefaultHttpUrlConnectionSettings(apiConnection, getFormat());
			response = getResponseData(apiConnection);
			if(response.length()==0) {
				return null;
			}
			CurrencyRate currencyRate = converter.getCurrencyRate(response);
			return currencyRate;
		} catch (Exception ex) 	{
			// TODO Auto-generated catch block
			throw new UncheckedIOException(ex.getMessage());
		}
	}

	private String setDateFormat(Date date) {
		return new SimpleDateFormat("yyyy-MM-dd").format(date);
	}
		
	private String checkResponseString(String txt) {
		System.out.println("checkResponseString = " + txt);
		if(txt.length()==0) {
			throw new CurrencyExchangeHttpException("Check url address if correct. Response from Api is null");
		} 
		if(txt.equals("404 NotFound")) {  //if(txt.contains("404 NotFound")) 
			throw new CurrencyExchangeHttpException("Check url address if correct. Response from Api is null");
		}
		if(txt.contains("Brak danych")){
			//return new String("Currency rate for this day doesnt exist");
			return new String("");
		}
		if(txt.contains("400 BadRequest")) {
			throw new CurrencyExchangeHttpException("Check url address if correct. Response from Api is null");
		}
		return txt;
	}
	
	private String getResponseData(HttpURLConnection apiConnection) { 
		try {
			int code = 0;
			code = apiConnection.getResponseCode();
			System.out.println("from API " + apiConnection + "\ncode " + code);
			System.out.println("Status " + code);
			StringBuilder response = new StringBuilder();
			if(code == 200) {
			    BufferedReader br = new BufferedReader(new InputStreamReader(apiConnection.getInputStream(), "utf-8"));
			    String responseLine = null;
			    while ((responseLine = br.readLine()) != null) {
			        response.append(responseLine.trim());
			        System.out.println(response);
			    }
			    System.out.println("Response Request:");
			    System.out.println(response.toString());
			} else if(code == 404) {
				System.out.println("im in code = 404");
				BufferedReader br = new BufferedReader(new InputStreamReader(apiConnection.getErrorStream()));
			    String responseLine = null;
			    while ((responseLine = br.readLine()) != null) {
			        response.append(responseLine.trim());
			        System.out.println(response);
			    }
			    System.out.println("Response Request:");
			    System.out.println(response.toString());
			} else {
				throw new CurrencyExchangeHttpException("Check url format and settings : " + apiConnection);
			}
			String resp = response.toString().replaceAll("\\P{Print}","");
			resp = checkResponseString(resp);
			return resp;
		} catch (SocketTimeoutException e) {
			// TODO Auto-generated catch block
			throw new CurrencyExchangeHttpException("Connection timed out. Check your url");
		} catch(UnknownHostException e) {
			throw new CurrencyExchangeHttpException("Check your internet connection or url address");
		} catch(IOException ex) {
			throw new CurrencyExchangeHttpException(ex.getMessage());
		} catch (Exception e) {
			throw new UncheckedIOException(e.getMessage());
		}
	}
	
	private HttpURLConnection getApiConnection(String url) {  
		try {
			URL restApiUrl = new URL(url);
		    HttpURLConnection restApiURLConnection = (HttpURLConnection) restApiUrl.openConnection();
		    System.out.println("restApiUrl = " + restApiUrl);
		    System.out.println("HttpURLConnection " + restApiURLConnection);
		    return restApiURLConnection;
		} catch (MalformedURLException e) {
			throw new CurrencyExchangeHttpException("Please check your url address");
		} catch (Exception ex) 	{
			// TODO Auto-generated catch block
			throw new UncheckedIOException(ex.getMessage());
		}
	}
	
	private void setDefaultHttpUrlConnectionSettings(HttpURLConnection apiConnection, String format) {
		try {
			apiConnection.setRequestMethod("GET");
			apiConnection.setDoOutput(true);
			apiConnection.setRequestProperty("Content-Type", "application/" + format);
			apiConnection.setRequestProperty("Accept", "application/" + format);
			apiConnection.setUseCaches(false);
			apiConnection.setConnectTimeout(5000);
			apiConnection.setReadTimeout(5000);
			System.out.println(apiConnection);
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			throw new CurrencyExchangeHttpException(e.getMessage());
		} catch (Exception ex) 	{
			// TODO Auto-generated catch block
			throw new UncheckedIOException(ex.getMessage());
		}
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}
}
