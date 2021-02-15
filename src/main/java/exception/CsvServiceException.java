package exception;

public class CsvServiceException extends RuntimeException {
	private static final long serialVersionUID = 8918326749145338924L;
	private String reason;
	
	public CsvServiceException(String message) {
		// TODO Auto-generated constructor stub
		super(message);
		this.reason = message;
	}
	
	public CsvServiceException(){
		
	}
	
	public String getReason()
    {
        return "CsvServiceException::getReason() - " + reason; // Prefixed with the method name to understand how printStackTrace() works.
    }
	
	@Override
    public String toString()
    {
        return "CsvServiceException::toString() - " + reason; // Prefixed with the method name to understand how printStackTrace() works.
    }
}
