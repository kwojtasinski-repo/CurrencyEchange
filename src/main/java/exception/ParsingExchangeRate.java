package exception;

public class ParsingExchangeRate extends RuntimeException  {
	private static final long serialVersionUID = -1225997025828721164L;
	private String reason;
	
	public ParsingExchangeRate(String message) {
		// TODO Auto-generated constructor stub
		super(message);
		this.reason = message;
	}
	
	public ParsingExchangeRate(){
		
	}
	
	public String getReason()
    {
        return "ParsingExchangeRate::getReason() - " + reason; // Prefixed with the method name to understand how printStackTrace() works.
    }
	
	@Override
    public String toString()
    {
        return "ParsingExchangeRate::toString() - " + reason; // Prefixed with the method name to understand how printStackTrace() works.
    }
}
