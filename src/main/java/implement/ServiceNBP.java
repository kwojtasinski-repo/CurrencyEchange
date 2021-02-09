package implement;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONObject;

import abstracts.CurrencyExchangeFileService;
import common.ExchangeRate;
import exception.CurrencyExchangeHttpException;
import exception.UncheckedIOException;

public abstract class ServiceNBP extends CurrencyExchangeFileService {
	private static final String RATE_URL = "http://api.nbp.pl/api/exchangerates/rates/A/";

	@Override
	protected Date getLastCurrencyRateDate() {
		// TODO Auto-generated method stub
		try {
			String string = "2002-01-02";
			HttpURLConnection apiConnection = getApiConnection(RATE_URL + "eur/" + string);
			setDefaultHttpUrlConnectionSettings(apiConnection, "json");
			String response = getResponseData(apiConnection);
			JSONObject json = new JSONObject(response);
			System.out.println(json);
			System.out.println(json.getJSONArray("rates").getJSONObject(0).get("effectiveDate"));
			return null;
		} catch(Exception ex) {
			throw new RuntimeException(ex);
		}
	}
	
	@Override
	protected ExchangeRate getExchangeRate(String currencyCode, Date date) {
		// TODO Auto-generated method stub
		return getExchangeRateFromApi(currencyCode, date);
	}
	
	public String getData(String currencyCode, Date date) {
		// TODO Auto-generated method stub
		return getRateFromApi(currencyCode, date);
	}
	
	private String getRateFromApi(String currencyCode, Date date) {
		try {
			HttpURLConnection apiConnection = null;
			String response=null;
			apiConnection = getApiConnection(RATE_URL + currencyCode + "/" + setDateFormat(date) + "/");
			setDefaultHttpUrlConnectionSettings(apiConnection, getFormat());
			response = getResponseData(apiConnection);
			return response;
		} catch (IOException ex) 	{
			// TODO Auto-generated catch block
			throw new CurrencyExchangeHttpException(ex.getMessage());
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
		//if(!txt.contains("<?xml version")  || !txt.contains("{")) {
			System.out.println("zawiera Brak danych");
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
		} catch(SocketTimeoutException e) {
			throw new CurrencyExchangeHttpException(e.getMessage());
		} catch(IOException ex) {
			throw new CurrencyExchangeHttpException(ex.getMessage());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new UncheckedIOException(e.getMessage());
		} 
	}
	
	private HttpURLConnection getApiConnection(String url) throws IOException {  
		URL restApiUrl = new URL(url);
	    HttpURLConnection restApiURLConnection = (HttpURLConnection) restApiUrl.openConnection();
	    System.out.println("restApiUrl = " + restApiUrl);
	    System.out.println("HttpURLConnection " + restApiURLConnection);
	    return restApiURLConnection;
	}
	
	private void setDefaultHttpUrlConnectionSettings(HttpURLConnection apiConnection, String format) throws ProtocolException {
		apiConnection.setRequestMethod("GET");
		apiConnection.setDoOutput(true);
		apiConnection.setRequestProperty("Content-Type", "application/" + format);
		apiConnection.setRequestProperty("Accept", "application/json" + format);
		apiConnection.setUseCaches(false);
		apiConnection.setConnectTimeout(5000);
		apiConnection.setReadTimeout(5000);
		System.out.println(apiConnection);
		//return apiConnection;
	}

	protected abstract String getFormat();
	protected abstract ExchangeRate getExchangeRateFromApi(String currencyCode, Date date);
}
