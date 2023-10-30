package clueGame;

public class BadConfigFormatException extends Exception{
	String file;
	public BadConfigFormatException() {
		super("Error: bad config format");
	}
	
	public BadConfigFormatException(String message) {
		super(message);
	}
	@Override
	public String toString() {
		return "BadConfigFormatException [file= " + file + "]";
	}
	
}