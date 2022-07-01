// Assignment 3
// COMP 249 William Zicha Student ID: 40127016
@SuppressWarnings("serial")
public class CSVDataMissing extends Exception {
	public CSVDataMissing() {
		super("Error: Input row cannot be parsed due to missing information");
	}

	public CSVDataMissing(String aString) {
		super(aString);
	}

	public String getMessage() {
		return super.getMessage();
	}
}
