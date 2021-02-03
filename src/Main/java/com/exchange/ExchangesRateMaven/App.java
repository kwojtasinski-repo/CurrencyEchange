package com.exchange.ExchangesRateMaven;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.exchange.ExchangesRateMaven.Domain.Entity.CurrencyTradeRate;
import com.exchange.ExchangesRateMaven.Domain.Interface.EntityRepository;
import com.exchange.ExchangesRateMaven.Repository.CurrencyTradeRateRepository;
import com.exchange.ExchangesRateMaven.Service.Abstract.ExchangeRate;
import com.exchange.ExchangesRateMaven.Service.Common.ExchangeAdaptee;
import com.exchange.ExchangesRateMaven.Service.Implement.ExchangeRateAdapter;


/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	// TODO Auto-generated method stub
    	// TODO Auto-generated method stub
		ExchangeAdaptee adaptee = new ExchangeAdaptee();
		ExchangeRate exchangeRate = new ExchangeRateAdapter(adaptee);
		Map<Integer, String> currencyDict = new HashMap<Integer, String>();
		currencyDict.put(1, "EUR");
		currencyDict.put(2, "USD");
		currencyDict.put(3, "CHF");
		currencyDict.put(4, "GBP");
		List<MenuAction> menuAction = Initialize();
		EntityRepository<CurrencyTradeRate> currencyTradeRateRepo = new CurrencyTradeRateRepository();
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
					for(Integer key : currencyDict.keySet())
					{
						System.out.println("Press "+ key +" if you want to exchange " + currencyDict.get(key));
					}
					Integer currencyDictKey = 1;
					try 
					{
					    currencyDictKey = Integer.parseInt(input.next());
					}
					catch (Exception e) 
					{
					    System.out.println(e.getMessage());
					}
					
					String currencyCode = "EUR";
					if(currencyDict.get(currencyDictKey) != null)
					{
						currencyCode = currencyDict.get(currencyDictKey);
					}
					
					BigDecimal money = new BigDecimal("100");
					try 
					{
						System.out.println("Please Enter the money you want to exchange");
						money = input.nextBigDecimal();
					}
					catch (Exception e) 
					{
					    System.out.println("Error " + e.getMessage());
					}
					BigDecimal rate = exchangeRate.getCurrencyRate(currencyCode);
					System.out.println("Exchange currency");
					System.out.println("Currency for 1" + currencyCode + " is " + rate + " PLN");
					BigDecimal currencyExchanged = exchangeRate.calculateCurrency(money, currencyCode);
					System.out.println("Current cash " + money + " " + currencyCode);
					System.out.println("Cash exchanged " + currencyExchanged.setScale(2, RoundingMode.HALF_UP) + " " + "PLN");
					
					CurrencyTradeRate tradeRate = new CurrencyTradeRate();
					tradeRate.setId(0L);
					tradeRate.setCashExchanged(currencyExchanged);
					tradeRate.setCashToExchange(money);
					tradeRate.setCurrencyCodeExchanging(currencyCode);
					tradeRate.setCurrencyCodeMain("PLN");
					tradeRate.setCurrencyRate(rate);
					java.util.Date date = new java.util.Date();
					tradeRate.setCurrencyRateDate(new java.sql.Timestamp(date.getTime()));
					Long id = currencyTradeRateRepo.add(tradeRate);
					System.out.println("Id after insert to db " + id);
					break;
				case 2:
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
					tradeRate = currencyTradeRateRepo.getById(id);
					System.out.println(tradeRate.toString());
					break;
				case 3:
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
					tradeRate = currencyTradeRateRepo.getById(id);
					System.out.println(tradeRate.toString());
					System.out.println("What do you want to choose?");
					showMenu(menuAction, "Update");
					int updateChoice = 0;
					updateChoice = Integer.parseInt(input.next());
					switch(updateChoice)
					{
						case 1:
							money = tradeRate.getCashToExchange();
							try 
							{
								System.out.println("Please Enter the money you want to exchange");
								money = input.nextBigDecimal();
							}
							catch (Exception e) 
							{
							    System.out.println("Error " + e.getMessage());
							}
							tradeRate.setCashToExchange(money);
							rate = exchangeRate.getCurrencyRate(tradeRate.getCurrencyCodeExchanging());
							currencyExchanged = exchangeRate.calculateCurrency(money, tradeRate.getCurrencyCodeExchanging());
							tradeRate.setCashExchanged(currencyExchanged);
							tradeRate.setCurrencyRate(rate);
							date = new java.util.Date();
							tradeRate.setCurrencyRateDate(new java.sql.Timestamp(date.getTime()));
							currencyTradeRateRepo.update(tradeRate);
							System.out.println("Transaction updated");
							System.out.println(tradeRate.toString());
							break;
						case 2:
							currencyDictKey = 1;
							try 
							{
								System.out.println("Please enter your currency");
								for(Integer key : currencyDict.keySet())
								{
									System.out.println("Press "+ key +" if you want to exchange " + currencyDict.get(key));
								}
								currencyDictKey = Integer.parseInt(input.next());
							}
							catch (Exception e) 
							{
							    System.out.println(e.getMessage());
							}
							
							currencyCode = tradeRate.getCurrencyCodeExchanging();
							if(currencyDict.get(currencyDictKey) != null)
							{
								currencyCode = currencyDict.get(currencyDictKey);
							}
							tradeRate.setCurrencyCodeExchanging(currencyCode);
							rate = exchangeRate.getCurrencyRate(currencyCode);
							tradeRate.setCurrencyRate(rate);
							System.out.println("Exchange currency");
							System.out.println("Currency for 1" + currencyCode + " is " + rate + " PLN");
							currencyExchanged = exchangeRate.calculateCurrency(tradeRate.getCashToExchange(), currencyCode);
							tradeRate.setCashExchanged(currencyExchanged);
							System.out.println("Current cash " + tradeRate.getCashToExchange() + " " + currencyCode);
							System.out.println("Cash exchanged " + currencyExchanged.setScale(2, RoundingMode.HALF_UP) + " " + "PLN");
							date = new java.util.Date();
							tradeRate.setCurrencyRateDate(new java.sql.Timestamp(date.getTime()));
							currencyTradeRateRepo.update(tradeRate);
							System.out.println("Transaction updated");
							System.out.println(tradeRate.toString());
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
					tradeRate = currencyTradeRateRepo.getById(id);
					currencyTradeRateRepo.delete(tradeRate);
					System.out.println(tradeRate.toString() + "\nDeleted successfully");
					break;
				case 5:
					System.out.println("Show all transactions");
					List<CurrencyTradeRate> currencyTrades = currencyTradeRateRepo.getAll();
					
					currencyTrades.sort((c1, c2) -> c1.getId().compareTo(c2.getId()));
					for(CurrencyTradeRate curr : currencyTrades)
					{
						System.out.println(curr.toString());
					}
					break;
				case 6:
					break;
			}
		}
		input.close();
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
}