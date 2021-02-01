package Common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

public class ExchangeAdaptee 
{
	public BigDecimal getCurrencyRate(String currencyCode) throws JSONException
	{
		return getRateFromApi(currencyCode);
	}
	
	private BigDecimal getRateFromApi(String currencyCode)
	{
		BigDecimal rate = new BigDecimal("1");
		HttpURLConnection apiConnection = null;
		JSONObject requestFromApi = null;
		try 
		{
			apiConnection = getApiConnection("A", currencyCode);
			setDefaultHttpUrlConnectionSettings(apiConnection);
		} 
		catch (IOException ex) 
		{
			// TODO Auto-generated catch block
			System.out.println(ex.getMessage());
		}
		if(apiConnection == null)
		{
			return rate;
		}
		
	    try 
	    {
	    	requestFromApi = new JSONObject(getJsonData(apiConnection));
		}
	    catch (JSONException e) 
	    {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
	    
	    try 
	    {
			System.out.println(requestFromApi.getJSONArray("rates").getJSONObject(0));
			rate = new BigDecimal(requestFromApi.getJSONArray("rates").getJSONObject(0).get("mid").toString());
		}
	    catch (JSONException e) 
	    {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return rate;
	}
	
	private String getJsonData(HttpURLConnection apiConnection)
	{
		StringBuilder response = new StringBuilder();
		try
		{
		    BufferedReader br = new BufferedReader(new InputStreamReader(apiConnection.getInputStream(), "utf-8"));
		    String responseLine = null;
		    while ((responseLine = br.readLine()) != null) 
		    {
		        response.append(responseLine.trim());
		    }
		    System.out.println("Response Request:");
		    System.out.println(response.toString());
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		}
		return response.toString();
	}
	
	private static HttpURLConnection getApiConnection(String table, String code) throws IOException 
	{  
	    URL restApiUrl = new URL("http://api.nbp.pl/api/exchangerates/rates/" + table + "/" + code + "/");
	    HttpURLConnection restApiURLConnection = (HttpURLConnection) restApiUrl.openConnection();
	    return restApiURLConnection;
	}
	
	private HttpURLConnection setDefaultHttpUrlConnectionSettings(HttpURLConnection apiConnection) throws ProtocolException
	{
		apiConnection.setRequestMethod("GET");
		apiConnection.setDoOutput(true);
		apiConnection.setRequestProperty("Content-Type", "application/json");
		apiConnection.setRequestProperty("Accept", "application/json");
		apiConnection.setUseCaches(false);
		apiConnection.setConnectTimeout(15000);
		apiConnection.setReadTimeout(15000);
		return apiConnection;
	}
}
