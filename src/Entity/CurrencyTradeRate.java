package Entity;

import java.math.BigDecimal;

import javax.persistence.Entity;

@Entity
public class CurrencyTradeRate 
{
	private Long id;
	private String currencyCodeExchanging;
	private BigDecimal currencyRate;
	private String currencyCodeMain;
	private java.sql.Timestamp currencyRateDate;
	private BigDecimal cashToExchange;
	private BigDecimal cashExchanged;
	
	public Long getId() 
	{  
	    return id;  
	}
	
	public void setId(Long id) 
	{  
	    this.id = id;  
	}
	
	public String getCurrencyCodeExchanging() 
	{  
	    return currencyCodeExchanging;  
	}
	
	public void setCurrencyCodeExchanging(String currencyCodeExchanging) 
	{  
	    this.currencyCodeExchanging = currencyCodeExchanging;  
	}
	
	public BigDecimal getCurrencyRate() 
	{  
	    return currencyRate;  
	}
	
	public void setCurrencyRate(BigDecimal currencyRate) 
	{  
	    this.currencyRate = currencyRate;  
	}
	
	public String getCurrencyCodeMain() 
	{  
	    return currencyCodeMain;  
	}
	
	public void setCurrencyCodeMain(String currencyCodeMain) 
	{  
	    this.currencyCodeMain = currencyCodeMain;  
	}  
	
	public java.sql.Timestamp getCurrencyRateDate() 
	{  
	    return currencyRateDate;  
	}
	
	public void setCurrencyRateDate(java.sql.Timestamp currencyRateDate) 
	{  
	    this.currencyRateDate = currencyRateDate;  
	}
	
	public BigDecimal getCashToExchange() 
	{  
	    return cashToExchange;  
	}
	
	public void setCashToExchange(BigDecimal cashToExchange) 
	{  
	    this.cashToExchange = cashToExchange;  
	}
	
	public BigDecimal getCashExchanged() 
	{  
	    return cashExchanged;  
	}
	
	public void setCashExchanged(BigDecimal cashExchanged) 
	{  
	    this.cashExchanged = cashExchanged;  
	}
}
