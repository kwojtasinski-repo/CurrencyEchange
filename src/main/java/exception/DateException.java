package exception;

public class DateException extends RuntimeException {
	private static final long serialVersionUID = -4699646138295628174L;
	private String reason;
	
	public DateException(String message) {
		// TODO Auto-generated constructor stub
		super(message);
		this.reason = message;
	}
	
	public DateException(){
		
	}
	
	public String getReason()
    {
        return "DateException::getReason() - " + reason; // Prefixed with the method name to understand how printStackTrace() works.
    }
	
	@Override
    public String toString()
    {
        return "DateException::toString() - " + reason; // Prefixed with the method name to understand how printStackTrace() works.
    }
}
