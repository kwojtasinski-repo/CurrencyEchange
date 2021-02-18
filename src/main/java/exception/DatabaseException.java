package exception;

public class DatabaseException extends RuntimeException {
	private static final long serialVersionUID = -8656071017872734015L;
	private String reason;

	public DatabaseException(String message) {
		// TODO Auto-generated constructor stub
		super(message);
		this.reason = message;
	}
	
	public DatabaseException(){
		
	}
	
	public String getReason()
    {
        return "DatabaseException::getReason() - " + reason; // Prefixed with the method name to understand how printStackTrace() works.
    }
	
	@Override
    public String toString()
    {
        return "DatabaseException::toString() - " + reason; // Prefixed with the method name to understand how printStackTrace() works.
    }
}
