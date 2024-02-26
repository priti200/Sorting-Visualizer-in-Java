import javax.swing.text.NumberFormatter; // A class that formats number
										//according to a specified 'NumberFormat'.
import java.text.ParseException; // An exception that indicates a problem
								//during parsing.
import java.text.NumberFormat; // An abstract base class for formatting and
								//parsing numbers

public class Eraser extends NumberFormatter { // Declares the 'MyFormatter' class which extends 'NumberFormatter'
													// to create a custom number Formatter.				

	public static final long serialVersionUID = 1L; // SerialVersionUID provides a version number for serialization to
													// ensure compatability between different version of the class.

	public Eraser(NumberFormat format) { // Constructor for the 'MyFormatter' class, which takes a 'NumberFormat'  as a parameter
	

	super(format); // Calls the superclass constructor with the specified 'NumberFormat' to initialize the formatter.
	}

	public Object stringToValue(String text) throws ParseException { // Overrides the 'stringToValue' method of
																	// 'NumberFormatter' to parse a string into a value
		if ("".equals(text)) // Checks if the text is empty and returns '0' as the value.
				return 0;
				return super.stringToValue(text); // Calls the superclass 'stringToValue' method to parse the string into a
													// value using the specified 'NumberFormat'.
	}
}
