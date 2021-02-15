package exception;

public class UncheckedIOException extends RuntimeException {
	private static final long serialVersionUID = 4570368932879774732L;
	private String reason;
	
	public UncheckedIOException(String message) {
		// TODO Auto-generated constructor stub
		super(message);
		this.reason = message;
	}
	
	public UncheckedIOException(){
		
	}
	
	public String getReason()
    {
        return "UncheckedIOException::getReason() - " + reason; // Prefixed with the method name to understand how printStackTrace() works.
    }
	
	@Override
    public String toString()
    {
        return "UncheckedIOException::toString() - " + reason; // Prefixed with the method name to understand how printStackTrace() works.
    }
}
