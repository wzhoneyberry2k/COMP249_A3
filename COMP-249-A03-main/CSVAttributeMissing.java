// Assignment 3
// COMP 249 William Zicha Student ID: 40127016
public class CSVAttributeMissing extends Exception {
    public CSVAttributeMissing() {
        super("Error: Input row cannot be parsed due to missing information");
    }
    public CSVAttributeMissing(String aString) {
        super(aString);
    }

    public String getMessage() {
        return super.getMessage();
    }
}
