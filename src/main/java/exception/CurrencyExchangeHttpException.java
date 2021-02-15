package exception;

public class CurrencyExchangeHttpException  extends RuntimeException {
	private static final long serialVersionUID = 6531046031839808032L;
	private String reason;
	
	public CurrencyExchangeHttpException(String message) {
		// TODO Auto-generated constructor stub
		super(message);
		this.reason = message;
	}
	
	public CurrencyExchangeHttpException(){
		
	}
	
	public String getReason()
    {
        return "CurrencyExchangeHttpException::getReason() - " + reason; // Prefixed with the method name to understand how printStackTrace() works.
    }
	
	@Override
    public String toString()
    {
        return "CurrencyExchangeHttpException::toString() - " + reason; // Prefixed with the method name to understand how printStackTrace() works.
    }
}
