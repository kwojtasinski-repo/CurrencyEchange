package main;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Collectors;

import implement.ExchangeManager;
import implement.ExchangeWebServiceNBP;
import implement.JsonConverter;
import abstracts.CurrencyRepository;
import abstracts.DataConverter;
import abstracts.Service;
import repository.CurrencyRepositoryImpl;
import entity.CurrencyExchange;
import entity.CurrencyRate;

public class Program {
	
	public static void main(String[] args) {
		/*Service serviceNBP = new ExchangeWebServiceNBP(setDate("2002-01-02"));
		DataConverter json = new JsonConverter();
		CurrencyRepository repo = new CurrencyRepository();
		ExchangeManager manager = new ExchangeManager(serviceNBP, json, repo);
		List<MenuAction> menuAction = Initialize();
		CurrencyExchangeRateRepository currencyRepo = new CurrencyRepository();
		Scanner input = new Scanner(System.in);
		Integer choice = 0;
		System.out.println("Welcome in Currency Exchange");
		
		while(choice != 6)
		{
			showMenu(menuAction, "Main");
			choice = Integer.parseInt(input.next());
			
			switch(choice)
			{
				case 1:
					System.out.println("Please enter your currency");
					String currencyCode = input.next();
					
					BigDecimal money = null;
					try 
					{
						System.out.println("Please Enter the money you want to exchange");
						money = input.nextBigDecimal();
					}
					catch (Exception e) 
					{
					    throw new IllegalArgumentException();
					}
					
					System.out.println("Please enter date for currency exchange. Date format yyyy-MM-dd");
					Date date = null;
					String dateString = input.next();
					date = setDate(dateString);
					
					ExchangeRate rate = manager.getCurrencyRate(currencyCode, date, money);
					System.out.println("Exchange currency");
					System.out.println("Currency for 1" + currencyCode + " is " + rate.getCurrencyRate() + " PLN");
					ExchangedCurrency exchangedCurrency = manager.exchangeCurrencyToPLN(currencyCode, date, money);
					System.out.println("Current cash " + money + " " + currencyCode);
					System.out.println("Cash exchanged " + exchangedCurrency.getCurrencyExchanged().setScale(2, RoundingMode.HALF_UP) + " " + "PLN");
					
					//CurrencyExchange currency2 = CurrencyExchangeMapper.INSTANCE.mapToCurrencyExchange(exchangedCurrency);
					CurrencyExchange currency = new CurrencyExchange();
					currency.setCurrencyCode(exchangedCurrency.getCurrencyCode());
					currency.setCurrencyCodeMain(exchangedCurrency.getCurrencyCodeMain());
					Calendar cal = Calendar.getInstance();
					cal.setTime(exchangedCurrency.getCurrencyDate());
					cal.set(Calendar.HOUR_OF_DAY, 0);
					cal.set(Calendar.MINUTE, 0);
					cal.set(Calendar.SECOND, 0);
					cal.set(Calendar.MILLISECOND, 0);    
					java.sql.Date sqlDate = new java.sql.Date(cal.getTime().getTime()); // your sql date
					currency.setCurrencyDate(sqlDate);
					currency.setCurrencyExchanged(exchangedCurrency.getCurrencyExchanged());
					currency.setCurrencyToExchange(exchangedCurrency.getCurrencyToExchange());
					currency.setRate(exchangedCurrency.getCurrencyRate());
					currency.setId(0L);
					Long idCurrency = currencyRepo.addCurrency(currency);
					System.out.println("Id currency after insert to db " + idCurrency);
					break;
				case 2:
					System.out.println("Enter id of transaction");
					Long id = null;
					try 
					{
					    id = Long.parseLong(input.next());
					}
					catch (Exception e) 
					{
					    System.out.println(e.getMessage());
					}
					currency = currencyRepo.getCurrencyById(id);
					System.out.println(currency.toString()); 
					break;
				case 3:
					System.out.println("What do you want to choose?");
					showMenu(menuAction, "Update");
					int updateChoice = 0;
					updateChoice = Integer.parseInt(input.next());
					System.out.println("Enter id of transaction");
					id = null;
					try 
					{
					    id = Long.parseLong(input.next());
					}
					catch (Exception e) 
					{
					    System.out.println(e.getMessage());
					}
					
					switch(updateChoice)
					{
						case 1:
							currency = currencyRepo.getCurrencyById(id);
							System.out.println(currency.toString());
							money = currency.getCurrencyToExchange();
							try 
							{
								System.out.println("Please Enter the money you want to exchange");
								money = input.nextBigDecimal();
							}
							catch (Exception e) 
							{
							    System.out.println("Error " + e.getMessage());
							}
							currency.setCurrencyToExchange(money);
							ExchangedCurrency currencyModified = manager.exchangeCurrencyToPLN(currency.getCurrencyCode(), currency.getCurrencyDate(), money);
							CurrencyExchange curMod = new CurrencyExchange();
							currency.setCurrencyCode(currencyModified.getCurrencyCode());
							currency.setCurrencyCodeMain(currencyModified.getCurrencyCodeMain());
							cal = Calendar.getInstance();
							cal.setTime(currencyModified.getCurrencyDate());
							cal.set(Calendar.HOUR_OF_DAY, 0);
							cal.set(Calendar.MINUTE, 0);
							cal.set(Calendar.SECOND, 0);
							cal.set(Calendar.MILLISECOND, 0);    
							sqlDate = new java.sql.Date(cal.getTime().getTime()); // your sql date
							currency.setCurrencyDate(sqlDate);
							currency.setCurrencyExchanged(currencyModified.getCurrencyExchanged());
							currency.setCurrencyToExchange(currencyModified.getCurrencyToExchange());
							currency.setRate(currencyModified.getCurrencyRate());
							currency.setId(0L);
							//CurrencyExchange curMod = CurrencyExchangeMapper.INSTANCE.mapToCurrencyExchange(currencyModified);
							curMod.setId(id);
							currencyRepo.updateCurrencyExchange(curMod);
							System.out.println("Transaction updated");
							System.out.println(curMod.toString());
							break;
						case 2:
							currency = currencyRepo.getCurrencyById(id);
							System.out.println(currency.toString());
							currencyCode = null;
							try {
								System.out.println("Please enter currency code");
								currencyCode = input.next();
							}
							catch (Exception e)	{
							    System.out.println(e.getMessage());
							}
							
							rate = manager.getCurrencyRate(currencyCode, new Date(), currency.getCurrencyToExchange());
							System.out.println("Exchange currency");
							System.out.println("Currency for 1" + rate.getCurrencyCode() + " is " + rate.getCurrencyRate() + " PLN");
							
							currencyModified = manager.exchangeCurrencyToPLN(currencyCode, currency.getCurrencyDate(), currency.getCurrencyToExchange());
							System.out.println("Current cash " + currency.getCurrencyToExchange() + " " + currencyCode);
							System.out.println("Cash exchanged " + currency.getCurrencyExchanged().setScale(2, RoundingMode.HALF_UP) + " " + "PLN");
					
							//curMod = CurrencyExchangeMapper.INSTANCE.mapToCurrencyExchange(currencyModified);
							curMod = new CurrencyExchange();
							currency.setCurrencyCode(currencyModified.getCurrencyCode());
							currency.setCurrencyCodeMain(currencyModified.getCurrencyCodeMain());
							cal = Calendar.getInstance();
							cal.setTime(currencyModified.getCurrencyDate());
							cal.set(Calendar.HOUR_OF_DAY, 0);
							cal.set(Calendar.MINUTE, 0);
							cal.set(Calendar.SECOND, 0);
							cal.set(Calendar.MILLISECOND, 0);    
							sqlDate = new java.sql.Date(cal.getTime().getTime()); // your sql date
							currency.setCurrencyDate(sqlDate);
							currency.setCurrencyExchanged(currencyModified.getCurrencyExchanged());
							currency.setCurrencyToExchange(currencyModified.getCurrencyToExchange());
							currency.setRate(currencyModified.getCurrencyRate());
							currency.setId(0L);
							curMod.setId(id);
							currencyRepo.updateCurrencyExchange(curMod);
							System.out.println("Transaction updated");
							System.out.println(currencyRepo.toString());
							break;
					}
					break;
				case 4:
					System.out.println("Enter id of transaction");
					id = null;
					try 
					{
					    id = Long.parseLong(input.next());
					}
					catch (Exception e) 
					{
					    System.out.println(e.getMessage());
					}
					currency = currencyRepo.getCurrencyById(id);
					currencyRepo.deleteCurrencyExchange(currency);
					System.out.println(currencyRepo.toString() + "\nDeleted successfully");
					break;
				case 5:
					System.out.println("Show all currencies and rates");
					List<CurrencyExchange> currenciesExchanged = currencyRepo.getAllCurrencies();
					
					currenciesExchanged.sort((c1, c2) -> c1.getId().compareTo(c2.getId()));
					for(CurrencyExchange curr : currenciesExchanged)
					{
						System.out.println(curr.toString());
					}
					break;
				case 6:
					break;
			}
		}
		input.close();*/
	}
	
	private static void showMenu(List<MenuAction> menuAction, String category)
	{
		menuAction.stream().filter(t->t.getCategory()==category).collect(Collectors.toList()).forEach(t-> System.out.println(t.getName()));
	}
	
	private static List<MenuAction> Initialize()
	{
		List<MenuAction> menuAction = new ArrayList<MenuAction>();
		menuAction.add(new MenuAction(1, "1. Exchange the money", "Main"));
		menuAction.add(new MenuAction(2, "2. Get transaction by id", "Main"));
		menuAction.add(new MenuAction(3, "3. Update exchange", "Main"));
		menuAction.add(new MenuAction(4, "4. Delete transaction by id", "Main"));
		menuAction.add(new MenuAction(5, "5. Get all transactions", "Main"));
		menuAction.add(new MenuAction(6, "6. Exit", "Main"));
		menuAction.add(new MenuAction(7, "1. Money", "Update"));
		menuAction.add(new MenuAction(8, "2. Currency", "Update"));
		return menuAction;
	}
	
	private static Date setDate(String dateString) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.GERMANY);
		Date date = null;
		try {
			date = format.parse(dateString);
			return date;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			throw new IllegalArgumentException();
		}
	}
}
