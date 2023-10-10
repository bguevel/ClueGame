package experiment;

public class BadConfigFormatException extends Exception{
	String file;
	public BadConfigFormatException(String fl) {
		super("Error: bad config format");
		file =fl;
	}
	@Override
	public String toString() {
		return "BadConfigFormatException [file= " + file + "]";
	}
	
}