package exception;

public class CurrencyNotFound extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private String reason;
	
	public CurrencyNotFound(String message) {
		// TODO Auto-generated constructor stub
		super(message);
		this.reason = message;
	}
	
	public CurrencyNotFound(){
		
	}
	
	public String getReason()
    {
        return "CurrencyNotFound::getReason() - " + reason; // Prefixed with the method name to understand how printStackTrace() works.
    }
	
	@Override
    public String toString()
    {
        return "CurrencyNotFound::toString() - " + reason; // Prefixed with the method name to understand how printStackTrace() works.
    }
}
