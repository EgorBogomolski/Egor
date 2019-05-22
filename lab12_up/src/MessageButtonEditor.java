import java.beans.*;
import java.awt.*;

/**
 * This PropertyEditor defines the enumerated values of the alignment property
 * so that a bean box or IDE can present those values to the user for selection
 **/
public class MessageButtonEditor extends PropertyEditorSupport {
    /** Return the list of value names for the enumerated type. */
    public String[] getTags() {
	return new String[] { "Press", "Conclude", "OK" };
    }
    
    /** Convert each of those value names into the actual value. */
    public void setAsText(String s) { 
        setValue(s);
    }
    
    /** This is an important method for code generation. */
    public String getJavaInitializationString() {
	Object o = getValue();
        if (o.equals("Press")) return "Press";
	else if (o.equals("Conclude")) return "Conclude";
	else if (o.equals("OK")) return "OK";
	return null;
    }
}